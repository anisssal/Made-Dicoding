package id.anis.bajpsub1.ui.detail.movie

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import id.anis.bajpsub1.R
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.utils.*
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.layout_error_retry.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private  val viewModel: DetailMovieViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setUpToolbar()
        getData()
        btn_retry.setOnClickListener {
            getData()
        }

    }


    private fun getData() {
        val extras = intent.extras
        extras?.apply {
            extras.getString(MyConstant.EXTRA_ID)?.let { id ->
                viewModel.setMovieId(id)
                val data = viewModel.movie
                data.observe(this@DetailMovieActivity, Observer(this@DetailMovieActivity::handleDataMovie))
            }
        }
    }

    private fun handleFavMovieState(isFavorite: Boolean) {
        if (isFavorite) {
            fab_fav.setImageDrawable(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_favorite
                    )
            )
        } else {
            fab_fav.setImageDrawable(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_favorite_border
                    )
            )
        }
    }


    private fun handleDataMovie(data: Resource<Movie>) {
        when (data) {
            is Resource.Loading -> {
                loading()
            }
            is Resource.Success -> {
                data.data?.run {
                    populateView(this)
                }
            }
            is Resource.Error -> {
                progress_bar.gone()
                layout_error_no_content_movie.visible()
                Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun populateView(data: Movie) {
        progress_bar.gone()
        scroll_view.visible()
        image_posters.visible()
        image_background.visible()
        tv_title_movie.text = data.title
        tv_desc_movie.text = if (data.description.isNullOrEmpty()) "-" else data.description
        tv_release_date_movie.text = data.release?.convertDateFormat()
        tv_rating_movie.text = data.score
        tv_genres_movie.text = data.genre

        data.poster?.let {
            image_posters.loadImageFromUrl(it)
        }
        val isFav = data.isFavorite
        print("apakah fav $isFav")
        handleFavMovieState(isFav)
        fab_fav.setOnClickListener {
            viewModel.setFavoriteMovie(data , !isFav )
        }
    }

    private fun loading() {
        progress_bar.visible()
        layout_error_detail.gone()
        layout_error_no_content_movie.gone()
        scroll_view.gone()
        image_posters.gone()
        image_background.gone()
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
                collapsing_toolbar.title = getString(R.string.title_detail_movie)
                isShow = true
            } else if (isShow) {
                collapsing_toolbar.title = " "
                isShow = false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}
