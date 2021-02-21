package id.anis.bajpsub1.ui.detail.movie

import androidx.lifecycle.*
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private var movieId = MutableLiveData<String>()


    fun setMovieId(movieId: String) {
        this.movieId.value = movieId
    }

    var movie: LiveData<Resource<Movie>> = Transformations.switchMap(movieId) { movieId ->
        movieUseCase.getDetailMovie(movieId).asLiveData()
    }

    fun setFavoriteMovie(data: Movie, newStatus:Boolean) = movieUseCase.setFavoriteMovie(data, newStatus)



}