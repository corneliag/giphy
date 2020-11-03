package com.cjuca.giphy.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cjuca.giphy.R
import com.cjuca.giphy.databinding.SearchGifFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates


class SearchGifFragment : Fragment() {

    private val viewModel: SearchGifViewModel by viewModel()
    private var viewHolder by Delegates.notNull<SearchGifViewHolder>()

    private var _binding: SearchGifFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchGifFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHolder = SearchGifViewHolder(binding, viewModel) {
            //TODO open a gif
        }
        initializeToolbar()
    }

    private fun initializeToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_close)
        }
        binding.toolbar.setNavigationOnClickListener {
            hideKeyboard()
            requireActivity().onBackPressed()
        }
    }

    private fun hideKeyboard() {
        val imm =
            binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val currentFocus: View? = binding.root.findFocus()
        if (currentFocus != null && imm != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        viewHolder.release()
        _binding = null
        super.onDestroyView()
    }
}