package id.anis.bajpsub1.di

import id.anis.bajpsub1.core.domain.usecase.MovieInteractor
import id.anis.bajpsub1.core.domain.usecase.MovieUseCase
import id.anis.bajpsub1.ui.detail.movie.DetailMovieViewModel
import id.anis.bajpsub1.ui.detail.tv.DetailTvViewModel
import id.anis.bajpsub1.ui.home.movie.MovieViewModel
import id.anis.bajpsub1.ui.home.tvshow.TvShowViewModel
import id.anis.bajpsub1.ui.search.movie.SearchMovieViewModel
import id.anis.bajpsub1.ui.search.tv.SearchTvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> {
        MovieInteractor(get())
    }
}

val viewModelModule = module {
    viewModel {
        DetailMovieViewModel(get())
    }
    viewModel {
        DetailTvViewModel(get())
    }

    viewModel {
        MovieViewModel(get())
    }
    viewModel {
        TvShowViewModel(get())
    }

    viewModel {
        SearchMovieViewModel(get())
    }
    viewModel {
        SearchTvViewModel(get())
    }


}