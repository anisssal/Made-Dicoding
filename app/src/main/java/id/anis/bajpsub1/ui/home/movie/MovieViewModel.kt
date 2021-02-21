package id.anis.bajpsub1.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class MovieViewModel(
    private val useCase: MovieUseCase
) : ViewModel() {



    fun getMovies(): LiveData<Resource<List<Movie>>> = useCase.getMovies().asLiveData()

}