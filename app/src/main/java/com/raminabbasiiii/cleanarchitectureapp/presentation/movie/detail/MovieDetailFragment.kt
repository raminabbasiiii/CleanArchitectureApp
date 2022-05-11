package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.detail

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.raminabbasiiii.cleanarchitectureapp.R
import com.raminabbasiiii.cleanarchitectureapp.databinding.FragmentMovieDetailBinding
import com.raminabbasiiii.cleanarchitectureapp.domain.model.MovieDetail
import com.raminabbasiiii.cleanarchitectureapp.presentation.util.showProgressBar
import com.raminabbasiiii.cleanarchitectureapp.presentation.util.showRetry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_fragment_container
            duration = 300L
            scrimColor = Color.TRANSPARENT
            //setAllContainerColors(requireActivity().themeColor(R.attr.colorSurface))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObserver()
        retry()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->

                    activity?.showProgressBar(uiState.isLoading)

                    uiState.movieDetail?.let {
                        binding.detailConstraint.isVisible = true
                        setMovieDetail(movieDetail = it)
                    }

                    if (uiState.errorMessage != null) {
                        binding.detailConstraint.isVisible = false
                        activity?.showRetry(true,uiState.errorMessage)
                    } else
                        activity?.showRetry(false,null)
                }
            }
        }
    }

    private fun setMovieDetail(movieDetail: MovieDetail) {
        movieDetail.images?.let { images -> initImageSlider(images) }

        binding.tvActors.text = "Actors : ${movieDetail.actors}"
        binding.tvAwards.text = "Awards : ${movieDetail.awards}"
        binding.tvCountry.text = "Country : ${movieDetail.country}"
        binding.tvDirector.text = "Director : ${movieDetail.director}"
        binding.tvPlot.text = "Plot : ${movieDetail.plot}"
        binding.tvRating.text = "Rating : ${movieDetail.rating}"
        binding.tvReleased.text = "Released : ${movieDetail.released}"
        binding.tvRuntime.text = "Runtime : ${movieDetail.runtime}"
        binding.tvTitle.text = movieDetail.title
        binding.tvWriter.text = "Writer : ${movieDetail.writer}"
        binding.tvYear.text = "Year : ${movieDetail.year}"

        var allGenres = ""
        for (genre in movieDetail.genres!!) {
            allGenres += "$genre, "
        }
        binding.tvGenres.text = "Genres : $allGenres"
    }

    private fun initImageSlider(images: List<String>) {
        val imageList = ArrayList<SlideModel>()
        for(image in images) {
            imageList.add(SlideModel(image))
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

            activity?.showRetry(false,null)
            activity?.showProgressBar(false)
            findNavController().popBackStack()
        }
    }

    private fun retry() {
        requireActivity()
            .findViewById<Button>(R.id.btn_retry)
            .setOnClickListener { viewModel.onEvent(MovieDetailEvents.Refresh)}
    }
}