package com.cjuca.giphy.ui.random

import androidx.core.view.isVisible
import com.cjuca.giphy.R
import com.cjuca.giphy.databinding.RandomGifFragmentBinding
import com.cjuca.giphy.ui.common.ScreenUiData
import com.cjuca.giphy.ui.common.State
import com.cjuca.giphy.ui.random.data.GifUiData
import com.cjuca.giphy.util.loadImage
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RandomGifViewHolder(
    private val binding: RandomGifFragmentBinding,
    private val viewModel: RandomGifViewModel,
    onSearchClickAction: () -> Unit,
) {
    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(viewModel.uiDataSource.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { screenUiData ->
                updateUiDataForChange(screenUiData)
            })
    }

    private fun updateUiDataForChange(screenUiData: ScreenUiData<GifUiData>) {
        when (screenUiData.state) {
            State.LOADING -> {
                binding.progress.isVisible = true
                binding.content.isVisible = false
            }
            State.IDLE -> {
                binding.progress.isVisible = false
                binding.content.isVisible = true

                binding.title.text = screenUiData.data.title
                binding.gif.loadImage(screenUiData.data.url, R.drawable.ic_placeholder)
            }
            State.ERROR -> {
                binding.progress.isVisible = false
                binding.content.isVisible = false
                if (!screenUiData.error.isNullOrEmpty()) {
                    Snackbar.make(binding.root, screenUiData.error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry) {
                            viewModel.fetchData()
                        }.show()
                }
            }
            State.EMPTY -> {
                Timber.w("empty state screen is not supposed to happen for detail screen")
            }
        }
    }

    fun release() {
        compositeDisposable.clear()
    }
}