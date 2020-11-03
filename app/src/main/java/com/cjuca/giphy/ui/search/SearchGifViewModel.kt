package com.cjuca.giphy.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cjuca.giphy.R
import com.cjuca.giphy.service.manager.IGiphyManager
import com.cjuca.giphy.service.model.Rating
import com.cjuca.giphy.ui.common.ScreenUiData
import com.cjuca.giphy.ui.common.State
import com.cjuca.giphy.ui.search.data.LoadingMoreItem
import com.cjuca.giphy.ui.search.data.SearchGifUiData
import com.cjuca.giphy.ui.search.data.SearchGifUiMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchGifViewModel(private val context: Application, private val manager: IGiphyManager) :
    AndroidViewModel(context) {

    companion object {
        private const val RESULTS_LIMIT = 25
    }

    private val searchSource: BehaviorProcessor<String> = BehaviorProcessor.createDefault("")
    private val compositeDisposable = CompositeDisposable()
    private val mapper = SearchGifUiMapper(context)
    val dataSource: BehaviorProcessor<ScreenUiData<SearchGifUiData>> by lazy {
        BehaviorProcessor.createDefault(
            ScreenUiData(
                State.LOADING,
                data = SearchGifUiData(mapper.emptyList)
            )
        )
    }
    private var localDataHolder: DataHolder = DataHolder()

    init {
        compositeDisposable.add(searchSource.distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .subscribe { searchInput ->
                localDataHolder = DataHolder()
                fetchData(searchInput, localDataHolder.getNextPage(), true)
            })
    }

    private fun fetchData(search: String, nextPage: Int = 0, clearList: Boolean) {
        dataSource.value?.let { uiData ->
            dataSource.onNext(
                uiData.copy(
                    state = State.LOADING,
                    data = uiData.data.copy(gifList = emptyList())
                )
            )

            compositeDisposable.add(
                manager.searchGifs(
                    Rating.LEVEL_1.type,
                    search,
                    limit = RESULTS_LIMIT,
                    offset = nextPage
                ).map {
                    localDataHolder.numberOfPages = it.pagination?.totalCount ?: 0
                    localDataHolder.currentPage = it.pagination?.offset ?: 0

                    mapper.mapGifListToUiData(it.data, search)
                }
                    .subscribeOn(Schedulers.io())
                    .subscribe({ fetchedList ->
                        val previousList = uiData.data.gifList.toMutableList()
                        if (clearList) {
                            previousList.clear()
                        } else {
                            previousList.removeAt(previousList.lastIndex)
                        }
                        previousList.addAll(fetchedList)
                        if (localDataHolder.hasMoreContent()) {
                            previousList.add(LoadingMoreItem)
                        }
                        dataSource.onNext(
                            uiData.copy(
                                state = State.IDLE,
                                data = uiData.data.copy(
                                    gifList = fetchedList
                                )
                            )
                        )
                      // retrieveNextPage(search, false)
                    }, { error ->
                        Timber.e(error, "error fetch data : ")
                        dataSource.onNext(
                            uiData.copy(
                                state = State.ERROR,
                                error = context.getString(R.string.error_fetching_data)
                            )
                        )
                    })
            )
        }
    }

    private fun retrieveNextPage(searchInput: String, clearList: Boolean = false) {
        if (localDataHolder.hasMoreContent()) {
            fetchData(searchInput, localDataHolder.getNextPage(), clearList)
        } else if (!localDataHolder.hasMoreContent() && clearList) {
            dataSource.value?.let { uiData ->
                dataSource.onNext(
                    uiData.copy(
                        state = State.EMPTY,
                        data = uiData.data.copy(
                            gifList = if (searchInput.isBlank()) mapper.emptyList else mapper.searchNoResult
                        )
                    )
                )
            }
        }
        // else
        // no more content to load, but previous content is already displayed (no empty state)
        // do nothing
    }

    fun loadMoreContentIfExist() {
        retrieveNextPage(searchInput = searchSource.value ?: "")
    }

    fun setSearchQueryText(search: String?) {
        if (search != null && search.trim().length > 1) {
            searchSource.onNext(search)
        } else {
            searchSource.onNext("")
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private class DataHolder(
        var numberOfPages: Int = 0, var currentPage: Int = 0
    ) {
        fun hasMoreContent() = currentPage < numberOfPages

        fun getNextPage() = currentPage.inc()
    }
}