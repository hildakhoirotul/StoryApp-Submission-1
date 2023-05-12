package com.example.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.model.ListStoryItem
import com.example.storyapp.ui.DetailStoryActivity
import com.example.storyapp.utils.Constanta.EXTRA_STORY
import com.example.storyapp.utils.Setting

class StoryAdapter(private val listStory: List<ListStoryItem>) : RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = listStory[position]
        holder.binding.tvUsername.text = story.name
        holder.binding.tvUploadTime.text =
            Setting.getStoryTime(
                holder.itemView.context,
                story.createdAt
            )
        Glide.with(holder.itemView.context)
            .load(story.photoUrl)
            .into(holder.binding.ivStory)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java).apply {
                putExtra(EXTRA_STORY, story)
            }

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.tvUsername, "name"),
                    Pair(holder.binding.tvUploadTime, "time"),
                    Pair(holder.binding.ivStory, "story"),
                )
            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

}