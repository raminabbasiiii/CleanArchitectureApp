package com.raminabbasiiii.cleanarchitectureapp.presentation.util

import androidx.recyclerview.widget.DiffUtil
import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}