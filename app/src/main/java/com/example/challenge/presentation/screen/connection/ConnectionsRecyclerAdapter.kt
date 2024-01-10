package com.example.challenge.presentation.screen.connection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge.R
import com.example.challenge.databinding.ItemConnectionLayoutBinding
import com.example.challenge.presentation.extension.loadImage
import com.example.challenge.presentation.model.connection.Connection

class ConnectionsRecyclerAdapter :
    ListAdapter<Connection, ConnectionsRecyclerAdapter.ConnectionsViewHolder>(ConnectionsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConnectionsViewHolder(
        ItemConnectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ConnectionsViewHolder, position: Int) {
        holder.bind()
    }

    inner class ConnectionsViewHolder(private val binding: ItemConnectionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Connection

        fun bind() {
            model = currentList[adapterPosition]
            binding.apply {
                imvProfile.loadImage(model.avatar)
                tvFullName.text = model.fullName
            }
        }
    }

    class ConnectionsDiffUtil : DiffUtil.ItemCallback<Connection>() {
        override fun areItemsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem == newItem
        }
    }
}