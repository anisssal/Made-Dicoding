package id.anis.bajpsub1.core.data.source.local.room

import androidx.room.*
import id.anis.bajpsub1.core.data.source.local.entity.MovieEntity
import id.anis.bajpsub1.core.data.source.local.entity.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertMovies(tourism: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvs(tourism: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertMovie(entity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(entity: TvEntity)


    @Query("SELECT * FROM tableMovie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tableMovie where isFavorite = 1")
    fun getFavMovies(): Flow<List<MovieEntity>>


    @Query("SELECT * FROM tableTv")
    fun getTvs(): Flow<List<TvEntity>>


    @Query("SELECT * FROM tableTv where isFavorite = 1")
    fun getFavTv(): Flow<List<TvEntity>>


    @Query("SELECT * FROM tableMovie WHERE id = :movieId")
    fun getMovieById(movieId: String): Flow<MovieEntity>

    @Query("SELECT * FROM tableTv WHERE id = :movieId")
    fun getTvById(movieId: String): Flow<TvEntity>


    @Update
    fun updateFavoriteMovie(entity: MovieEntity)

    @Update
    fun updateFavoriteTv(entity: TvEntity)






}