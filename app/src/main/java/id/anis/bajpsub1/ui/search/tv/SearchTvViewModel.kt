package id.anis.bajpsub1.ui.search.tv

import androidx.lifecycle.*
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class SearchTvViewModel(
    private val useCase: MovieUseCase
) : ViewModel() {

    private var queryLiveData = MutableLiveData<String>()

    fun setQuery(query: String){
        this.queryLiveData.value = query
    }

    var tv: LiveData<Resource<List<Tv>>> = Transformations.switchMap(queryLiveData) { query ->
        useCase.searchTv(query).asLiveData()
    }



}