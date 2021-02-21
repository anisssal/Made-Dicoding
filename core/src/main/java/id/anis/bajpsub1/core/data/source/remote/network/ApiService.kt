package id.anis.bajpsub1.core.data.source.remote.network

import id.anis.bajpsub1.core.BuildConfig
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailMovieResponse
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailTvResponse
import id.anis.bajpsub1.core.data.source.remote.response.movie.PopularMoviesResponse
import id.anis.bajpsub1.core.data.source.remote.response.tvshow.PopularTvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular")
    suspend fun getMovies(@Query("page") page: Int =1 , @Query("api_key") apiKey: String = BuildConfig.API_KEY): PopularMoviesResponse

    @GET("/3/discover/tv")
    suspend fun getTvShows(@Query("page") page: Int =1, @Query("api_key") apiKey: String = BuildConfig.API_KEY): PopularTvResponse

    @GET("/3/movie/{id}")
    suspend fun getDetailMovie(@Path("id") id: String, @Query("api_key") apiKey: String = BuildConfig.API_KEY): DetailMovieResponse

    @GET("/3/tv/{id}")
    suspend fun getDetailTv(@Path("id") id: String, @Query("api_key") apiKey: String = BuildConfig.API_KEY): DetailTvResponse

    @GET("/3/search/movie")
    suspend fun searchMovie(@Query("api_key") apiKey: String = BuildConfig.API_KEY , @Query("query") query : String): PopularMoviesResponse
    @GET("/3/search/tv")
    suspend fun searchTv(@Query("api_key") apiKey: String = BuildConfig.API_KEY , @Query("query") query : String): PopularTvResponse

}