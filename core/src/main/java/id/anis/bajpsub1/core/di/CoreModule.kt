package id.anis.bajpsub1.core.di

import androidx.room.Room
import id.anis.bajpsub1.core.BuildConfig
import id.anis.bajpsub1.core.data.MovieRepository
import id.anis.bajpsub1.core.data.source.local.LocalDataSource
import id.anis.bajpsub1.core.data.source.local.room.TheMovieDatabase
import id.anis.bajpsub1.core.data.source.remote.RemoteDataSource
import id.anis.bajpsub1.core.data.source.remote.network.ApiService
import id.anis.bajpsub1.core.domain.repository.IMovieRepository
import id.anis.bajpsub1.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    factory { get<TheMovieDatabase>().movieTvDao() }
    single {
        val passphrase : ByteArray = SQLiteDatabase.getBytes("anismade".toCharArray())
        val factory =SupportFactory(passphrase)
        Room.databaseBuilder(
                androidContext().applicationContext,
                TheMovieDatabase::class.java,
                "movie_tv.db"
        ).fallbackToDestructiveMigration().
        openHelperFactory(factory).build()
    }
}

val networkModule = module {
    single {

        val retrofit = Retrofit.Builder()
                .client(get())
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(ApiService::class.java)


    }
    single {

        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.HOSTNAME, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(BuildConfig.HOSTNAME, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(BuildConfig.HOSTNAME, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()
        OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .certificatePinner(certificatePinner)
                .build()
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(get())
    }
    single {
        RemoteDataSource(get())
    }
    factory {
        AppExecutors()
    }
    single<IMovieRepository> {
        MovieRepository(get(), get(), get())
    }
}
