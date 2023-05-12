package com.example.storyapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ActivityDetailStoryBinding
import com.example.storyapp.model.ListStoryItem
import com.example.storyapp.model.Story
import com.example.storyapp.ui.viewmodel.DetailViewModel
import com.example.storyapp.utils.Constanta.EXTRA_STORY
import com.example.storyapp.utils.Preferences
import com.example.storyapp.utils.Setting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var pref: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY) as ListStoryItem

        pref = Preferences(this)
        val token = pref.token.toString()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetail("Bearer $token", story.id).observe(this@DetailStoryActivity) {
                setDetailData(it.story)
            }
            viewModel.isLoading.observe(this@DetailStoryActivity){
                showLoading(it)
            }
        }
    }

    private fun setDetailData(detail: Story?) {
        binding.apply {
            Glide.with(this@DetailStoryActivity)
                .load(detail?.photoUrl)
                .into(ivStory)
            tvUploadTime.text =
                Setting.getStoryTime(
                    this@DetailStoryActivity,
                    detail?.createdAt ?: "0"
                )
            tvUsername.text = detail?.name
            tvDesc.text = detail?.description
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.root.visibility = View.VISIBLE
        } else {
            binding.loading.root.visibility = View.GONE
        }
    }
}