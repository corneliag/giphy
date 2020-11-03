package com.cjuca.giphy.ui.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cjuca.giphy.R
import com.cjuca.giphy.databinding.FullScreenGifFragmentBinding
import com.cjuca.giphy.util.loadImage

class FullScreenGifFragment : Fragment() {

    private var _binding: FullScreenGifFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: FullScreenGifFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FullScreenGifFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.loadImage(args.gifId, R.drawable.ic_placeholder)
        binding.image.setOnClickListener { requireActivity().onBackPressed() }
        binding.close.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}