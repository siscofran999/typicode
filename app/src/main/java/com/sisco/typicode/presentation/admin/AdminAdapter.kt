package com.sisco.typicode.presentation.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ListUserBinding
import com.sisco.typicode.domain.model.User
import javax.inject.Inject

class AdminAdapter @Inject constructor() :
    ListAdapter<User, AdminAdapter.AdminViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListUserBinding.inflate(inflater, parent, false)
        return AdminViewHolder(view)

    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AdminViewHolder(private val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvId.text = user.id.toString()
            binding.tvUsername.text =
                binding.root.resources.getString(R.string.data_username, user.username)
            binding.tvEmail.text = binding.root.resources.getString(R.string.data_email, user.email)
            binding.tvRole.text = binding.root.resources.getString(R.string.data_role, user.role)
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(item: User)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}