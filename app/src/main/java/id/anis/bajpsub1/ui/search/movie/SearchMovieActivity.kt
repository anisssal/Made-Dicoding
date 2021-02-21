package id.anis.bajpsub1.ui.search.movie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import id.anis.bajpsub1.R
import id.anis.bajpsub1.core.data.Resource
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.ui.MovieAdapter
import id.anis.bajpsub1.core.utils.MyConstant
import id.anis.bajpsub1.ui.detail.movie.DetailMovieActivity
import id.anis.bajpsub1.core.utils.gone
import id.anis.bajpsub1.core.utils.visible
import kotlinx.android.synthetic.main.activity_home.toolbar
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Field

class SearchMovieActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var searchView: SearchView
    private val movieAdapter by lazy {
        MovieAdapter()
    }
    private val viewModel: SearchMovieViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setUpToolbar()

        movieAdapter.onItemClick = { data ->
            Intent(this, DetailMovieActivity::class.java).apply {
                putExtra(MyConstant.EXTRA_ID, data.id)
                startActivity(this)
            }
        }
        with(recycler_view) {
            val layoutManager = GridLayoutManager(context, 2)
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            this.adapter = movieAdapter
        }
    }


    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        tv_title.text = getString(R.string.search_movie)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun initSearcView(searchMenuItem: MenuItem) {
        searchView = searchMenuItem.actionView as SearchView
        val txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = "Search.."
        txtSearch.setHintTextColor(Color.WHITE)
        txtSearch.setTextColor(ContextCompat.getColor(this, R.color.white_to_grey))
        try {
            val f: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(txtSearch, R.drawable.cursor_edittext)
        } catch (ignored: Exception) {
        }
        searchView.setOnCloseListener {
            true
        }
        searchView.setOnQueryTextListener(this)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        initSearcView(menu.findItem(R.id.item_search))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            collapseSearchView()
            return
        }
        super.onBackPressed()
    }

    private fun collapseSearchView() {
        searchView.clearFocus()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            return true
        }
        viewModel.setQuery(query)
        viewModel.movie.observe(this, Observer(this@SearchMovieActivity::observeData))
        collapseSearchView()
        return true
    }

    private fun loading() {
        recycler_view.gone()
        tv_no_data.gone()
        progress_bar.visible()
    }

    private fun observeData(resource: Resource<List<Movie>>?) {
        if (resource != null) {
            when (resource) {
                is Resource.Loading -> loading()
                is Resource.Success -> {
                    progress_bar.gone()
                    if (resource.data.isNullOrEmpty()) {
                        Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT)
                            .show()
                        tv_no_data.visible()
                        recycler_view.gone()
                        return
                    }
                    tv_no_data.gone()
                    recycler_view.visible()
                    movieAdapter.setData(resource.data)
                }
                is Resource.Error -> {
                    progress_bar.gone()
                    tv_no_data.gone()
                    recycler_view.gone()
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}