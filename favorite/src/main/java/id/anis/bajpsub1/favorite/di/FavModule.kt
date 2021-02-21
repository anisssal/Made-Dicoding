package id.anis.bajpsub1.favorite.di
import id.anis.bajpsub1.favorite.ui.favmovie.MovieFavViewModel
import id.anis.bajpsub1.favorite.ui.favtv.TvFavViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModel {
        MovieFavViewModel(get())
    }
    viewModel {
        TvFavViewModel(get())
    }
}