package com.example.storyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.databinding.FragmentHomeBinding
import com.example.storyapp.model.ListStoryItem
import com.example.storyapp.ui.viewmodel.HomeViewModel
import com.example.storyapp.utils.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: Preferences
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        pref = Preferences(requireActivity())
        val token = pref.token.toString()

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listStory.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.listStory.addItemDecoration(itemDecoration)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getListStory("Bearer $token").observe(requireActivity()) {
                setStory(it)
            }

            viewModel.isLoading.observe(requireActivity()) {
                showLoading(it)
            }
        }
    }

    private fun setStory(storyList: List<ListStoryItem>) {
        val adapter = StoryAdapter(storyList)
        binding.listStory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.root.visibility = View.VISIBLE
        } else {
            binding.loading.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}