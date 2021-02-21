package id.anis.bajpsub1.favorite.ui.favmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class MovieFavViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getMovies(): LiveData<List<Movie>> = movieUseCase.getFavMovies().asLiveData()

    fun setFavoriteMovie(data: Movie, newStatus:Boolean) = movieUseCase.setFavoriteMovie(data, newStatus)


}