package id.anis.bajpsub1.ui.search.movie

import androidx.lifecycle.*
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class SearchMovieViewModel(
    private val useCase: MovieUseCase
) : ViewModel() {

    private var queryLiveData = MutableLiveData<String>()

    fun setQuery(query: String){
        this.queryLiveData.value = query
    }


    var movie: LiveData<Resource<List<Movie>>> = Transformations.switchMap(queryLiveData) { query ->
        useCase.searchMovies(query).asLiveData()
    }



}