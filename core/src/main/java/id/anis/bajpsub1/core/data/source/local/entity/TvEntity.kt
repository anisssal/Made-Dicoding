package id.anis.bajpsub1.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tableTv")
data class TvEntity(
    @PrimaryKey
    @NonNull
    var id: String,
    var poster: String? = null,
    var title: String? = null,
    var score: String? = null,
    var release: String? = null,
    var genre: String? = null,
    var description: String? = null,
    var isFavorite : Boolean = false
)