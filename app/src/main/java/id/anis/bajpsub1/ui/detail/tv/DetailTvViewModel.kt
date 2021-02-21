package id.anis.bajpsub1.ui.detail.tv

import androidx.lifecycle.*
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class DetailTvViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private var tvShowId = MutableLiveData<String>()

    fun setTvShowId(tvShowId: String) {
        this.tvShowId.value = tvShowId
    }

    var tv: LiveData<Resource<Tv>> = Transformations.switchMap(tvShowId) { tvId ->
        movieUseCase.getDetailTv(tvId).asLiveData()
    }

    fun setFavoriteTv(data: Tv, newStatus:Boolean) = movieUseCase.setFavoriteTv(data, newStatus)
}
