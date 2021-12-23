package com.ndzindo.tmdb.ui.movielist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ndzindo.tmdb.R
import com.ndzindo.tmdb.cache.MoviePreferences
import com.ndzindo.tmdb.data.CustomMovie
import com.ndzindo.tmdb.data.Movie
import com.ndzindo.tmdb.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list),
    MovieAdapter.OnItemClickInterface,
    FavoriteMovieAdapter.OnClickListenerInterface,
    CustomMovieAdapter.OnCustomMovieClickInterface {

    private val viewModel by viewModels<MovieListViewModel>()

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieAdapter
    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter
    private lateinit var customMovieAdapter: CustomMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieListBinding.bind(view)

        initAdapters()

        binding.apply {
            recyclerViewList.setHasFixedSize(true)
            recyclerViewList.adapter = adapter.withLoadStateFooter(
                MovieLoadStateAdapter { adapter.retry() }
            )
            buttonAddCustomMovie.setOnClickListener {
                val action = MovieListFragmentDirections.actionMovieListFragmentToAddMovieFragment()
                findNavController().navigate(action)
            }
        }

        setHasOptionsMenu(true)
        updateUI(MoviePreferences.getPreferredListType(context))
    }

    private fun initAdapters() {

        adapter = MovieAdapter(this)
        favoriteMovieAdapter = FavoriteMovieAdapter(this)
        customMovieAdapter = CustomMovieAdapter(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val movieList = when (item.itemId) {
            R.id.popular -> "popular"
            R.id.top_rated -> "top_rated"
            R.id.upcoming -> "upcoming"
            R.id.favourite -> "favourite"
            R.id.added_movies -> "added_movies"
            else -> return super.onOptionsItemSelected(item)
        }

        MoviePreferences.setPreferredListType(context, movieList)
        updateUI(movieList)
        return true
    }

    private fun updateUI(movieList: String) {
        when (movieList) {
            "favourite" -> {
                binding.recyclerViewList.adapter = favoriteMovieAdapter
                observeFavoriteMovies()
            }
            "added_movies" -> {
                binding.recyclerViewList.adapter = customMovieAdapter
                observeAddedMovies()
            }
            else -> {
                binding.recyclerViewList.adapter = adapter
                observeApiMovies(movieList)
            }
        }

        binding.buttonAddCustomMovie.isVisible = movieList == "added_movies"
    }

    private fun observeApiMovies(movieList: String) {
        viewModel.getMovies(movieList)
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun observeAddedMovies() {
        viewModel.customMovies.observe(viewLifecycleOwner) {
            customMovieAdapter.differ.submitList(it)

        }
    }

    private fun observeFavoriteMovies() {

        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            favoriteMovieAdapter.differ.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_movie_list, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun OnItemClick(movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToDetailsFragment(movie)
        findNavController().navigate(action)
    }

    override fun onCustomMovieItemClick(movie: CustomMovie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToCustomMovieDetailsFragment(movie)
        findNavController().navigate(action)
    }
}