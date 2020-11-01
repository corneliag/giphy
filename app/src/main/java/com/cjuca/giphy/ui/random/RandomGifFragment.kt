package com.cjuca.giphy.ui.random

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cjuca.giphy.R
import com.cjuca.giphy.databinding.RandomGifFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class RandomGifFragment : Fragment() {

    private val viewModel: RandomGifViewModel by viewModel()
    private var viewHolder by Delegates.notNull<RandomGifViewHolder>()

    private var _binding: RandomGifFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RandomGifFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHolder = RandomGifViewHolder(binding, viewModel) {
            val directions =
                RandomGifFragmentDirections.actionRandomGifFragmentToSearchGifFragment()
            findNavController().navigate(directions)
        }
        setHasOptionsMenu(true)
        initializeToolbar()
    }

    private fun initializeToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.random_gif_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refreshRandomGif -> {
                viewModel.fetchData()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        viewHolder.release()
        _binding = null
        super.onDestroyView()
    }
}