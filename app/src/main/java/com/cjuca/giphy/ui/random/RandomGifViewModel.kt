package com.cjuca.giphy.ui.random

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cjuca.giphy.R
import com.cjuca.giphy.service.manager.IGiphyManager
import com.cjuca.giphy.service.model.Rating
import com.cjuca.giphy.ui.common.ScreenUiData
import com.cjuca.giphy.ui.common.State
import com.cjuca.giphy.ui.random.data.GifUiData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RandomGifViewModel(private val context: Application, private val manager: IGiphyManager) :
    AndroidViewModel(context) {

    val uiDataSource: BehaviorProcessor<ScreenUiData<GifUiData>> by lazy {
        BehaviorProcessor.createDefault(
            ScreenUiData(
                state = State.LOADING,
                data = GifUiData()
            )
        )
    }
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    init {
        fetchData()
    }

    fun fetchData() {
        uiDataSource.value?.let { screenUiData ->
            compositeDisposable.add(
                manager.getRandomGif(Rating.LEVEL_1.type)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { data ->
                            uiDataSource.onNext(
                                screenUiData.copy(
                                    state = State.IDLE,
                                    data = GifUiData(
                                        data.images.fixed_height?.url ?: "",
                                        data.title
                                    )
                                )
                            )
                        },
                        { error ->
                            Timber.e(
                                error,
                                "Error while getting the random gif"
                            )
                            uiDataSource.onNext(
                                screenUiData.copy(
                                    state = State.ERROR,
                                    error = context.getString(R.string.error_fetching_data)
                                )
                            )
                        }
                    )
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}