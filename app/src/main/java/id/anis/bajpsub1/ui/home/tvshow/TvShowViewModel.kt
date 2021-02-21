package id.anis.bajpsub1.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class TvShowViewModel(private val useCase: MovieUseCase) : ViewModel() {

    fun getTVShows(): LiveData<Resource<List<Tv>>> = useCase.getTvs().asLiveData()
}