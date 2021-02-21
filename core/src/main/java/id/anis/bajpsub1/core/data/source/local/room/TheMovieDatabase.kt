package id.anis.bajpsub1.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.anis.bajpsub1.core.data.source.local.entity.MovieEntity
import id.anis.bajpsub1.core.data.source.local.entity.TvEntity


@Database(
    entities = [MovieEntity::class, TvEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TheMovieDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao

}