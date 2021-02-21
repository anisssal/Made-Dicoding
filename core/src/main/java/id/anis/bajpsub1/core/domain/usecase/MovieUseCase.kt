package id.anis.bajpsub1.core.domain.usecase

import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface MovieUseCase   {
    fun getMovies() : Flow<Resource<List<Movie>>>
    fun getTvs() : Flow<Resource<List<Tv>>>

    fun getDetailMovie(id : String) : Flow<Resource<Movie>>
    fun getDetailTv(id : String) : Flow<Resource<Tv>>

    fun getFavMovies() : Flow<List<Movie>>
    fun getFavTvs() : Flow<List<Tv>>

    fun setFavoriteMovie(movie: Movie, newState : Boolean)
    fun setFavoriteTv(tv: Tv, newState : Boolean)

    fun searchMovies(query: String): Flow<Resource<List<Movie>>>
    fun searchTv(query: String): Flow<Resource<List<Tv>>>

}