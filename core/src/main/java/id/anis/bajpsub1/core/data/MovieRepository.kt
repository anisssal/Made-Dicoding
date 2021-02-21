package id.anis.bajpsub1.core.data

import id.anis.bajpsub1.core.data.source.local.LocalDataSource
import id.anis.bajpsub1.core.data.source.remote.RemoteDataSource
import id.anis.bajpsub1.core.data.source.remote.network.ApiResponse
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailMovieResponse
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailTvResponse
import id.anis.bajpsub1.core.data.source.remote.response.movie.ResultMovie
import id.anis.bajpsub1.core.data.source.remote.response.tvshow.ResultsTv
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.domain.repository.IMovieRepository
import id.anis.bajpsub1.core.utils.AppExecutors
import id.anis.bajpsub1.core.utils.DataMapperHelper
import kotlinx.coroutines.flow.*

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {


    override fun getMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultMovie>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapperHelper.mapMovieEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun saveCallResult(data: List<ResultMovie>) {
                val movieList = DataMapperHelper.convertMovieResponseToMovieEntity(data)
                localDataSource.insertMovies(movieList)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultMovie>>> {
                return remoteDataSource.getPopularMovies()
            }

        }.asFlow()


    override fun getTvs(): Flow<Resource<List<Tv>>> =
        object : NetworkBoundResource<List<Tv>, List<ResultsTv>>() {
            override fun loadFromDB(): Flow<List<Tv>> {
                return localDataSource.getAllTv().map {
                    DataMapperHelper.mapTvEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<Tv>?): Boolean {
                return data == null || data.isEmpty()

            }


            override suspend fun saveCallResult(data: List<ResultsTv>) {
                val movieList = DataMapperHelper.convertTvResponseToTvEntity(data)
                localDataSource.insertTvs(movieList)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsTv>>> {
                return remoteDataSource.getPopularTv()
            }

        }.asFlow()


    override fun getDetailMovie(id: String): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieById(id).map {
                    if (it!=null) DataMapperHelper.mapMovieEntityToDomain(it) else Movie()
                }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                return data == null || data.id == "0" ||data.description.isNullOrBlank()
            }


            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: DetailMovieResponse) {
                val movieEntity = DataMapperHelper.convertDetailMovieResponseToMovieEntity(data)
                localDataSource.insertMovie(movieEntity)
            }


        }.asFlow()
    }

    override fun getDetailTv(id: String): Flow<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, DetailTvResponse>() {
            override fun loadFromDB(): Flow<Tv> {
                return localDataSource.getTvById(id).map {
                    if (it!=null) DataMapperHelper.mapTvEntityToDomain(it) else Tv()

                }
            }

            override fun shouldFetch(data: Tv?): Boolean {
                return data == null ||data.id == "0"|| data.description.isNullOrBlank()
            }


            override suspend fun createCall(): Flow<ApiResponse<DetailTvResponse>> {
                return remoteDataSource.getDetailTv(id)
            }

            override suspend fun saveCallResult(data: DetailTvResponse) {
                val tvEntity = DataMapperHelper.convertDetailTvResponseToTvEntity(data)
                localDataSource.insertTv(tvEntity)
            }


        }.asFlow()

    }

    override fun getFavMovies(): Flow<List<Movie>> {
        return localDataSource.getFavMovie().map {
            DataMapperHelper.mapMovieEntitiesToDomains(it)
        }
    }

    override fun getFavTvs(): Flow<List<Tv>> {
        return localDataSource.getFavTv().map {
            DataMapperHelper.mapTvEntitiesToDomains(it)
        }
    }


    override fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        val entity = DataMapperHelper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(entity, newState) }
    }

    override fun setFavoriteTv(tv: Tv, newState: Boolean) {
        val entity = DataMapperHelper.mapTvDomainToEntity(tv)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTv(entity, newState) }
    }

    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchMovie(query).first()) {
            is ApiResponse.Success -> {
                emit(
                    Resource.Success(
                        DataMapperHelper.mapMovieResponseToMovieDomain(
                            apiResponse.data
                        )
                    )
                )
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success<List<Movie>>(listOf()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<List<Movie>>(apiResponse.errorMessage))
            }

        }

    }


    override fun searchTv(query: String): Flow<Resource<List<Tv>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchTv(query).first()) {
            is ApiResponse.Success -> {
                emit(
                    Resource.Success(
                        DataMapperHelper.mapTvResponseToTvDomain(
                            apiResponse.data
                        )
                    )
                )
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success<List<Tv>>(listOf()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<List<Tv>>(apiResponse.errorMessage))
            }

        }

    }

}