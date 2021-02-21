package id.anis.bajpsub1.favorite.ui.favtv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase

class TvFavViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getTvs(): LiveData<List<Tv>> = movieUseCase.getFavTvs().asLiveData()

    fun setFavoriteTv(data: Tv, newStatus:Boolean) = movieUseCase.setFavoriteTv(data, newStatus)


}