package id.anis.bajpsub1.core.data.source.remote

import id.anis.bajpsub1.core.data.source.remote.network.ApiResponse
import id.anis.bajpsub1.core.data.source.remote.network.ApiService
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailMovieResponse
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailTvResponse
import id.anis.bajpsub1.core.data.source.remote.response.movie.PopularMoviesResponse
import id.anis.bajpsub1.core.data.source.remote.response.movie.ResultMovie
import id.anis.bajpsub1.core.data.source.remote.response.tvshow.PopularTvResponse
import id.anis.bajpsub1.core.data.source.remote.response.tvshow.ResultsTv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {


    fun getPopularMovies() : Flow<ApiResponse<List<ResultMovie>>>{
        return flow {
            try{
                val data: PopularMoviesResponse = apiService.getMovies()
                if (data.results.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(data.results))
                }
            }catch (e : Exception){
              emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getPopularTv() : Flow<ApiResponse<List<ResultsTv>>>{
        return flow {
            try{
                val data: PopularTvResponse = apiService.getTvShows()
                if (data.results.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(data.results))
                }
            }catch (e : Exception){
              emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }



    fun getDetailMovie(id : String) : Flow<ApiResponse<DetailMovieResponse>>{
        return flow {
            try{
                val data: DetailMovieResponse = apiService.getDetailMovie(id)
                emit(ApiResponse.Success(data))

            }catch (e : Exception){
              emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getDetailTv(id : String) : Flow<ApiResponse<DetailTvResponse>>{
        return flow {
            try{
                val data: DetailTvResponse = apiService.getDetailTv(id)
                emit(ApiResponse.Success(data))

            }catch (e : Exception){
              emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


    fun searchMovie(query: String) : Flow<ApiResponse<List<ResultMovie>>>{
        return flow {
            try{
                val data: PopularMoviesResponse = apiService.searchMovie(query = query)
                if (data.results.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(data.results))
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchTv(query: String) : Flow<ApiResponse<List<ResultsTv>>>{
        return flow {
            try{
                val data: PopularTvResponse = apiService.searchTv(query = query)
                if (data.results.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                }else{
                    emit(ApiResponse.Success(data.results))
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }




}