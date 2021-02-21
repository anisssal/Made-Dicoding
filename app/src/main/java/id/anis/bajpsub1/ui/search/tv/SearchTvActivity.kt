package id.anis.bajpsub1.ui.search.tv

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
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.ui.TvAdapter
import id.anis.bajpsub1.core.utils.MyConstant
import id.anis.bajpsub1.ui.detail.tv.DetailTvActivity
import id.anis.bajpsub1.core.utils.gone
import id.anis.bajpsub1.core.utils.visible
import kotlinx.android.synthetic.main.activity_home.toolbar
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Field

class SearchTvActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var searchView: SearchView
    private val tvAdapater by lazy {
        TvAdapter()
    }
    private val viewModel: SearchTvViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setUpToolbar()
        
        tvAdapater.onItemClick = { data ->
            Intent(this, DetailTvActivity::class.java).apply {
                putExtra(MyConstant.EXTRA_ID, data.id)
                startActivity(this)
            }
        }
        with(recycler_view) {
            val layoutManager = GridLayoutManager(context, 2)
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            this.adapter = tvAdapater
        }
    }



    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        tv_title.text = getString(R.string.search_tv)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }





    private fun initSearcView(searchMenuItem: MenuItem) {
        searchView = searchMenuItem.actionView as SearchView
        val txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = getString(R.string.search)
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
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            collapseSearchView()
            return
        }
        super.onBackPressed()
    }

    private fun collapseSearchView(){
        searchView.clearFocus()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            return true
        }
        viewModel.setQuery(query)
        viewModel.tv.observe(this, Observer(this@SearchTvActivity::observeData))
        collapseSearchView()
        return true
    }

    private fun loading() {
        recycler_view.gone()
        tv_no_data.gone()
        progress_bar.visible()
    }

    private fun observeData(resource: Resource<List<Tv>>?) {
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
                    tvAdapater.setData(resource.data)
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