package id.anis.bajpsub1.ui.detail.tv

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import id.anis.bajpsub1.R
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.utils.*
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.activity_detail_tv.*
import kotlinx.android.synthetic.main.activity_detail_tv.app_bar_layout
import kotlinx.android.synthetic.main.activity_detail_tv.collapsing_toolbar
import kotlinx.android.synthetic.main.activity_detail_tv.fab_fav
import kotlinx.android.synthetic.main.activity_detail_tv.image_background
import kotlinx.android.synthetic.main.activity_detail_tv.progress_bar
import kotlinx.android.synthetic.main.activity_detail_tv.scroll_view
import kotlinx.android.synthetic.main.activity_detail_tv.toolbar
import kotlinx.android.synthetic.main.layout_error_retry.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTvActivity : AppCompatActivity() {

    private  val viewModel: DetailTvViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
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
                viewModel.setTvShowId(id)
                val data: LiveData<Resource<Tv>> = viewModel.tv
                data.observe(this@DetailTvActivity, Observer(this@DetailTvActivity::handleDataTv))

            }

        }
    }


    private fun handleFavTvState(isFav : Boolean) {
        if (isFav) {
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

    private fun handleDataTv(data: Resource<Tv>) {
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
                layout_error_no_content.visible()
                Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun populateView(data: Tv) {
        progress_bar.gone()
        scroll_view.visible()
        image_poster.visible()
        image_background.visible()
        tv_title.text = data.title
        tv_desc.text = if (data.description.isNullOrEmpty()) "-" else data.description
        tv_release_date.text = data.release?.convertDateFormat()
        tv_rating.text = data.score
        tv_genres.text = data.genre

        data.poster?.let {
            image_poster.loadImageFromUrl(it)
        }
        val isFav = data.isFavorite
        handleFavTvState(isFav)
        fab_fav.setOnClickListener {
            viewModel.setFavoriteTv(data , !isFav  )
        }
    }

    private fun loading() {
        progress_bar.visible()
        layout_error.gone()
        layout_error_no_content.gone()
        scroll_view.gone()
        image_poster.gone()
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
                collapsing_toolbar.title = getString(R.string.title_detail_tvshow)
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
