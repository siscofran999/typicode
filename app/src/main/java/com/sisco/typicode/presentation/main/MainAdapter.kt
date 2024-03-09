package com.sisco.typicode.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ListPhotoBinding
import com.sisco.typicode.domain.model.Photo
import javax.inject.Inject

class MainAdapter @Inject constructor() :
    ListAdapter<Photo, MainAdapter.MainViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListPhotoBinding.inflate(inflater, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainViewHolder(private val binding: ListPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.apply {
                tvId.text = photo.id.toString()
                tvTitle.text = photo.title
                tvUrl.text = photo.url
                Glide.with(root.context)
                    .load(photo.thumbnailUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(img)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }
}