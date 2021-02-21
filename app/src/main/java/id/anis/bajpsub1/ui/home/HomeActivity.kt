package id.anis.bajpsub1.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.anis.bajpsub1.R
import id.anis.bajpsub1.ui.search.movie.SearchMovieActivity
import id.anis.bajpsub1.ui.search.tv.SearchTvActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpToolbar()
        val sectionPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_favorite) {
            val uri = Uri.parse("moviedbmadeanis://fav")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        if (item.itemId == R.id.menu_search_movie) {
            startActivity(Intent(this , SearchMovieActivity::class.java))
        }
        if (item.itemId == R.id.menu_search_tv) {
            startActivity(Intent(this , SearchTvActivity::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

}
