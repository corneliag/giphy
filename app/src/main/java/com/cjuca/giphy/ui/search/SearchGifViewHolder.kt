package com.cjuca.giphy.ui.search

import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjuca.giphy.R
import com.cjuca.giphy.databinding.SearchGifFragmentBinding
import com.cjuca.giphy.ui.common.ScreenUiData
import com.cjuca.giphy.ui.common.State
import com.cjuca.giphy.ui.search.adapter.SearchGifAdapter
import com.cjuca.giphy.ui.search.data.EmptyItem
import com.cjuca.giphy.ui.search.data.SearchGifUiData
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SearchGifViewHolder(
    private val binding: SearchGifFragmentBinding,
    private val viewModel: SearchGifViewModel,
    private val onGifClickAction: (id: String) -> Unit,
) {
    private val compositeDisposable = CompositeDisposable()
    private val adapter = SearchGifAdapter {
        onGifClickAction.invoke(it)
    }

    companion object {
        private const val LONG_TIMEOUT_USER_INPUT_IN_MS = 300L
        private const val NETWORK_THROTTLE_TIMEOUT_IN_MILLIS = 1000L
    }

    init {
        binding.recycler.adapter = adapter
        initializeSearchView()
        compositeDisposable.add(viewModel.dataSource.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { screenUiData ->
                updateUiDataForChange(screenUiData)
            })

    }

    private fun initializeSearchView() {
        val searchView = binding.search.searchView
        searchView.requestFocus()
        searchView.isIconified = true
        searchView.isIconified = false
        searchView.apply {
            val searchTextChangeStream = queryTextChangeEvents().distinctUntilChanged()
                .share()
                .debounce(LONG_TIMEOUT_USER_INPUT_IN_MS, TimeUnit.MILLISECONDS)

            compositeDisposable.addAll(searchTextChangeStream.subscribeOn(AndroidSchedulers.mainThread())
                .filter { it.isSubmitted.not() }
                .observeOn(Schedulers.io())
                .debounce(NETWORK_THROTTLE_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.setSearchQueryText(
                        it.queryText.toString()
                            .trim()
                    )
                },
                searchTextChangeStream.filter { it.isSubmitted }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        it.view.clearFocus()
                        viewModel.setSearchQueryText(
                            it.queryText.toString()
                                .trim()
                        )
                    })
        }
    }

    private fun updateUiDataForChange(screenUiData: ScreenUiData<SearchGifUiData>) {
        val gifList = screenUiData.data.gifList
        adapter.submitList(gifList)

        val currentLayoutManager = binding.recycler.layoutManager
        val context = binding.root.context
        if (gifList.isNotEmpty() && gifList.first() is EmptyItem) {
            if (currentLayoutManager is GridLayoutManager) {
                binding.recycler.layoutManager = LinearLayoutManager(context)
            }
        } else {
            if (currentLayoutManager !is GridLayoutManager) {
                binding.recycler.layoutManager = GridLayoutManager(
                    context,
                    context.resources.getInteger(R.integer.gridImageCount)
                )
            }
        }
        when (screenUiData.state) {
            State.EMPTY, State.IDLE -> {
                binding.progress.isVisible = false
            }
            State.LOADING -> {
                binding.progress.isVisible = true
            }
            State.ERROR -> {
                Snackbar.make(binding.root, screenUiData.error ?: "", Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
        }
    }

    fun release() {
        compositeDisposable.clear()
    }
}