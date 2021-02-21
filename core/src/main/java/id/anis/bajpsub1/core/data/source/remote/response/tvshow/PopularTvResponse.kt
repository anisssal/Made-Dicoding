package id.anis.bajpsub1.core.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName


data class PopularTvResponse(

    @SerializedName("results") val results: List<ResultsTv>
)