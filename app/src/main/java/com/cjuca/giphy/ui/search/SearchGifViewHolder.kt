package com.cjuca.giphy.ui.search

import com.cjuca.giphy.databinding.SearchGifFragmentBinding
import io.reactivex.disposables.CompositeDisposable


class SearchGifViewHolder(
    private val binding: SearchGifFragmentBinding,
    private val viewModel: SearchGifViewModel,
    private val onGifClickAction: (id: String) -> Unit,
) {
    private val compositeDisposable = CompositeDisposable()

    init {
        binding.search.searchView.requestFocus()
    }
    fun release() {
        compositeDisposable.clear()
    }
}