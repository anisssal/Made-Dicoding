package id.anis.bajpsub1.core.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName


data class PopularMoviesResponse(
    @SerializedName("results") val results: List<ResultMovie>
)