package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.platform.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.raminabbasiiii.cleanarchitectureapp.R
import com.raminabbasiiii.cleanarchitectureapp.databinding.FragmentMoviesBinding
import com.raminabbasiiii.cleanarchitectureapp.presentation.util.showRetry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var searchView: SearchView
    private lateinit var menua: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = 300L
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initToolbar()
        initMenu()
        initRecyclerView()
        handleLoadingStateAdapter()
        subscribeObserver()
        retry()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.moviesToolbar)
    }

    private fun initMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menua = menu
                menuInflater.inflate(R.menu.search_menu,menua)
                initSearchView()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }

    private fun initSearchView() {
        activity?.apply {
            val searchManager: SearchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView = menua.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.setIconifiedByDefault(true)
            searchView.maxWidth = Integer.MAX_VALUE
            //searchView.isSubmitButtonEnabled = true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    executeQuery(newText)
                    movieListAdapter.refresh()
                    //subscribeObserver()
                }
                return true
            }
        })
    }

    private fun executeQuery(query: String){
        viewModel.onEvent(MoviesEvents.UpdateQuery(query))
        viewModel.onEvent(MoviesEvents.SearchMovies)
        //hideKeyboard()
    }

    private fun initRecyclerView() {
        binding.moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            //val spacingDecorator = SpacingItemDecoration(24)
            //removeItemDecoration(spacingDecorator)
            //addItemDecoration(spacingDecorator)
            movieListAdapter = MovieListAdapter(requireContext()) { cardView: View,movieId: Int ->

                exitTransition = MaterialElevationScale(false).apply {
                    duration = 300L
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = 300L
                }
                val movieCardDetailTransitionName = getString(R.string.movie_card_detail_transition_name)
                val extras = FragmentNavigatorExtras(cardView to movieCardDetailTransitionName)
                findNavController().navigate(
                    MoviesFragmentDirections
                        .actionMoviesFragmentToMovieDetailFragment(movieId = movieId ),
                    extras
                )
            }

            adapter = movieListAdapter

            binding.moviesRecyclerView.adapter = movieListAdapter.withLoadStateFooter(
                footer = MovieLoadingStateAdapter(movieListAdapter::retry)
            )
        }
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.movies?.let { movieListAdapter.submitData(it) }
                }
            }
        }
    }

    private fun handleLoadingStateAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListAdapter.addLoadStateListener { loadState ->
                    if (loadState.source.refresh is LoadState.Loading) {
                        if (movieListAdapter.snapshot().isEmpty()) {
                            binding.shimmerLayout.startShimmer()
                        }

                        activity?.showRetry(false,null)

                    } else {
                        binding.shimmerLayout.apply {
                            stopShimmer()
                            visibility = View.GONE
                        }

                        val error = when {
                            loadState.source.prepend is LoadState.Error -> loadState.source.prepend as LoadState.Error
                            loadState.source.append is LoadState.Error -> loadState.source.append as LoadState.Error
                            loadState.source.refresh is LoadState.Error -> loadState.source.refresh as LoadState.Error
                            else -> null
                        }
                        error?.let {
                            if (movieListAdapter.snapshot().isEmpty()) {
                                activity?.showRetry(true,"Check Network Connection!")
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
            }
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!searchView.isIconified) {
                searchView.onActionViewCollapsed()
            } else
                requireActivity().finish()
        }
    }

    private fun retry() {
        requireActivity().findViewById<Button>(R.id.btn_retry).setOnClickListener {
            movieListAdapter.retry()
        }
    }

}























