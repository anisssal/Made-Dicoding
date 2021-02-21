package id.anis.bajpsub1.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tv(
        var id: String = "0",
        var poster: String? = null,
        var title: String? = null,
        var score: String? = null,
        var release: String? = null,
        var genre: String? = null,
        var description: String? = null,
        var isFavorite : Boolean = false
) : Parcelable