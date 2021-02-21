package id.anis.bajpsub1.core.data.source.local

import id.anis.bajpsub1.core.data.source.local.entity.MovieEntity
import id.anis.bajpsub1.core.data.source.local.entity.TvEntity
import id.anis.bajpsub1.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieTvDao: MovieTvDao) {


    fun getAllMovie(): Flow<List<MovieEntity>> = movieTvDao.getMovies()
    fun getAllTv(): Flow<List<TvEntity>> = movieTvDao.getTvs()

    fun getTvById(id: String): Flow<TvEntity?> = movieTvDao.getTvById(id)

    fun getMovieById(id: String): Flow<MovieEntity?> = movieTvDao.getMovieById(id)

    fun getFavMovie(): Flow<List<MovieEntity>> = movieTvDao.getFavMovies()
    fun getFavTv(): Flow<List<TvEntity>> = movieTvDao.getFavTv()

    suspend fun insertMovies(list: List<MovieEntity>) = movieTvDao.insertMovies(list)
    suspend fun insertMovie(entity: MovieEntity) = movieTvDao.insertMovie(entity)

    suspend fun insertTvs(list: List<TvEntity>) = movieTvDao.insertTvs(list)
    suspend fun insertTv(entity: TvEntity) = movieTvDao.insertTv(entity)


    fun setFavoriteMovie(entity: MovieEntity, newState: Boolean) {
        entity.isFavorite = newState
        movieTvDao.updateFavoriteMovie(entity)
    }

    fun setFavoriteTv(entity: TvEntity, newState: Boolean) {
        entity.isFavorite = newState
        movieTvDao.updateFavoriteTv(entity)
    }


}