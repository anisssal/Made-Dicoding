package id.anis.bajpsub1.favorite.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.anis.bajpsub1.favorite.R
import id.anis.bajpsub1.favorite.di.favoriteViewModelModule
import kotlinx.android.synthetic.main.activity_fav.*
import org.koin.core.context.loadKoinModules

class FavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)
        loadKoinModules(favoriteViewModelModule)
        setUpToolbar()
        val sectionPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager
        )

        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}
