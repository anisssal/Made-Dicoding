package id.anis.bajpsub1.favorite.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.favorite.R
import id.anis.bajpsub1.favorite.ui.favmovie.MovieFavFragment
import id.anis.bajpsub1.core.utils.gone
import id.anis.bajpsub1.core.utils.loadImageFromUrl
import id.anis.bajpsub1.core.utils.visible
import kotlinx.android.synthetic.main.activity_detail_fav.app_bar_layout
import kotlinx.android.synthetic.main.activity_detail_fav.collapsing_toolbar
import kotlinx.android.synthetic.main.activity_detail_fav.fab_fav
import kotlinx.android.synthetic.main.activity_detail_fav.image_background
import kotlinx.android.synthetic.main.activity_detail_fav.progress_bar
import kotlinx.android.synthetic.main.activity_detail_fav.scroll_view
import kotlinx.android.synthetic.main.activity_detail_fav.toolbar
import kotlinx.android.synthetic.main.activity_detail_fav.*

class DetailFavMovieTvActivity : AppCompatActivity() {


    private var typeData: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_fav)
        setUpToolbar()
        getData()
    }


    private fun getData() {
        intent.extras?.apply {
            typeData = this.getInt(EXTRA_TYPE, 0)
            if (typeData == TYPE_MOVIE) {
                val data = this.getParcelable<Movie>(EXTRA_DATA)
                handleDataMovie(data)
            } else {
                val data = this.getParcelable<Tv>(EXTRA_DATA)
                handleDataTv(data)
            }
        }
    }


    private fun handleDataMovie(data: Movie?) {
        if (data != null) populateView(data as Any) else {
            progress_bar.gone()
            layout_error_no_content.visible()
            Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
        }
    }

    private fun handleDataTv(data: Tv?) {
        if (data != null) populateView(data as Any) else {
            progress_bar.gone()
            layout_error_no_content.visible()
            Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
        }
    }

    private fun populateView(data: Any) {
        fab_fav.setImageDrawable(
                ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite
                )
        )
        progress_bar.gone()
        scroll_view.visible()
        image_poster.visible()
        image_background.visible()

        if (typeData == TYPE_MOVIE) {
            val dataMovie = data as Movie
            tv_title.text = dataMovie.title
            tv_desc.text = if (dataMovie.description.isNullOrEmpty()) "-" else dataMovie.description
            tv_release_date.text = dataMovie.release
            tv_rating.text = dataMovie.score
            tv_genres.text = dataMovie.genre
            dataMovie.poster?.let {
                image_poster.loadImageFromUrl(it)
            }
        } else {
            val dataTv = data as Tv
            tv_title.text = dataTv.title
            tv_desc.text = if (dataTv.description.isNullOrEmpty()) "-" else dataTv.description
            tv_release_date.text = dataTv.release
            tv_rating.text = dataTv.score
            tv_genres.text = dataTv.genre
            dataTv.poster?.let {
                image_poster.loadImageFromUrl(it)
            }

        }

        fab_fav.setOnClickListener {
            Intent().apply {
                this.putExtra(
                        EXTRA_DATA,
                        if (typeData == TYPE_MOVIE) data as Movie else data as Tv
                )
                finish()
            }
        }

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var isShow = true
        var scrollRange = -1
        app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                if (typeData == TYPE_MOVIE) {
                    collapsing_toolbar.title = getString(R.string.title_detail_movie_fav)
                } else {
                    collapsing_toolbar.title = getString(R.string.title_detail_tvshow_fav)
                }
                isShow = true
            } else if (isShow) {
                collapsing_toolbar.title = " "
                isShow = false
            }
        })
    }


    companion object {
        const val EXTRA_TYPE = "exstra_typedata"
        const val EXTRA_DATA = "exstra_data"
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
