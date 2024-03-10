package com.sisco.typicode.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ListPhotoBinding
import com.sisco.typicode.domain.model.Photo
import javax.inject.Inject

class MainPagingAdapter @Inject constructor(): PagingDataAdapter<Photo, MainPagingAdapter.MainPagingViewHolder>(DIFF_UTIL) {

    override fun onBindViewHolder(holder: MainPagingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPagingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListPhotoBinding.inflate(inflater, parent, false)
        return MainPagingViewHolder(view)
    }

    inner class MainPagingViewHolder(private val binding: ListPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo?) {
            binding.apply {
                tvId.text = photo?.id.toString()
                tvTitle.text = photo?.title
                tvUrl.text = photo?.url
                Glide.with(root.context)
                    .load(photo?.thumbnailUrl)
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