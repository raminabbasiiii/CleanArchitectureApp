package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.raminabbasiiii.cleanarchitectureapp.databinding.MovieListItemBinding
import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie
import com.raminabbasiiii.cleanarchitectureapp.presentation.util.MovieDiffCallback
import dagger.hilt.android.qualifiers.ApplicationContext

class MovieListAdapter(
    @ApplicationContext val context: Context,
    private val clicked: (View, Int) -> Unit
): PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(
    MovieDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(
        private val binding: MovieListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?) {
            binding.tvTitle.text = item?.title
            Glide.with(binding.root)
                .load(item?.poster)
                .transition(withCrossFade())
                .into(binding.imgPoster)

            binding.cardView.transitionName = "movie_card_%1${item?.id}"

            binding.root.setOnClickListener {
                item?.id?.let { id -> clicked.invoke(it,id) }
            }
        }
    }
}


