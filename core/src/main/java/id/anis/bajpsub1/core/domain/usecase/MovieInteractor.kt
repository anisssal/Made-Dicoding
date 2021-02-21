package id.anis.bajpsub1.core.domain.usecase

import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val repo : IMovieRepository) : MovieUseCase {
    override fun getMovies(): Flow<Resource<List<Movie>>> = repo.getMovies()

    override fun getTvs(): Flow<Resource<List<Tv>>> = repo.getTvs()

    override fun getDetailMovie(id: String): Flow<Resource<Movie>> = repo.getDetailMovie(id)

    override fun getDetailTv(id: String): Flow<Resource<Tv>> = repo.getDetailTv(id)

    override fun getFavMovies(): Flow<List<Movie>> = repo.getFavMovies()

    override fun getFavTvs(): Flow<List<Tv>> = repo.getFavTvs()

    override fun setFavoriteMovie(movie: Movie, newState: Boolean) = repo.setFavoriteMovie(movie , newState)

    override fun setFavoriteTv(tv: Tv, newState: Boolean) = repo.setFavoriteTv(tv , newState)
    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> = repo.searchMovies(query)
    override fun searchTv(query: String): Flow<Resource<List<Tv>>> = repo.searchTv(query)
}