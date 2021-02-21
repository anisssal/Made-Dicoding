package id.anis.bajpsub1.core.utils

import id.anis.bajpsub1.core.data.source.local.entity.MovieEntity
import id.anis.bajpsub1.core.data.source.local.entity.TvEntity
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailMovieResponse
import id.anis.bajpsub1.core.data.source.remote.response.detail.DetailTvResponse
import id.anis.bajpsub1.core.data.source.remote.response.movie.ResultMovie
import id.anis.bajpsub1.core.data.source.remote.response.tvshow.ResultsTv
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.model.Tv

object DataMapperHelper {
    fun convertMovieResponseToMovieEntity(listResponse: List<ResultMovie>): List<MovieEntity> = listResponse.map { response ->
        MovieEntity(
                response.id.toString(),
                response.poster_path,
                response.title
        )
    }

    fun convertTvResponseToTvEntity(listResponse: List<ResultsTv>): List<TvEntity> = listResponse.map { response ->
        TvEntity(
                response.id.toString(),
                response.poster_path,
                response.name
        )
    }


    fun convertDetailMovieResponseToMovieEntity(response: DetailMovieResponse): MovieEntity {
        val genresList = response.genres.map { it.name }
        return MovieEntity(
                response.id.toString(),
                response.poster_path,
                response.title,
                response.vote_average.toString(),
                response.release_date,
                genresList.joinToString(separator = ", "),
                response.overview
        )
    }

    fun convertDetailTvResponseToTvEntity(response: DetailTvResponse): TvEntity {
        val genresList = response.genres.map { it.name }
        return TvEntity(
                response.id.toString(),
                response.poster_path,
                response.name,
                response.vote_average.toString(),
                response.first_air_date,
                genresList.joinToString(separator = ", "),
                response.overview
        )
    }

    fun mapMovieEntitiesToDomains(input: List<MovieEntity>): List<Movie> =
            input.map { entity ->
                Movie(
                        entity.id,
                        entity.poster,
                        entity.title,
                        entity.score.toString(),
                        entity.release,
                        entity.genre,
                        entity.description,
                    entity.isFavorite
                )

            }

    fun mapTvEntitiesToDomains(input: List<TvEntity>): List<Tv> =
            input.map { entity ->
                Tv(
                        entity.id,
                        entity.poster,
                        entity.title,
                        entity.score.toString(),
                        entity.release,
                        entity.genre,
                        entity.description,
                    entity.isFavorite
                )

            }

    fun mapMovieEntityToDomain(input: MovieEntity): Movie =
            Movie(
                    input.id,
                    input.poster,
                    input.title,
                    input.score.toString(),
                    input.release,
                    input.genre,
                    input.description,
                input.isFavorite
            )
    fun mapTvEntityToDomain(input: TvEntity): Tv =
            Tv(
                    input.id,
                    input.poster,
                    input.title,
                    input.score.toString(),
                    input.release,
                    input.genre,
                    input.description,
                    input.isFavorite

            )

    fun mapMovieDomainToEntity(input: Movie): MovieEntity =
            MovieEntity(
                    input.id,
                    input.poster,
                    input.title,
                    input.score.toString(),
                    input.release,
                    input.genre,
                    input.description,
                input.isFavorite
            )

    fun mapTvDomainToEntity(input: Tv): TvEntity =
            TvEntity(
                    input.id,
                    input.poster,
                    input.title,
                    input.score.toString(),
                    input.release,
                    input.genre,
                    input.description,
                input.isFavorite
            )

    fun mapMovieResponseToMovieDomain(listResponse: List<ResultMovie>): List<Movie> = listResponse.map { response ->
        Movie(
            response.id.toString(),
            response.poster_path,
            response.title
        )

    }

    fun mapTvResponseToTvDomain(listResponse: List<ResultsTv>): List<Tv> = listResponse.map { response ->
        Tv(
            response.id.toString(),
            response.poster_path,
            response.name
        )

    }


}