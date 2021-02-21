package id.anis.bajpsub1.ui.home.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_error_retry.*
import org.koin.android.viewmodel.ext.android.viewModel


class MovieFragment : Fragment() {

    private val movieAdapter = MovieAdapter()
        private val viewModel : MovieViewModel by viewModel()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setUpRecyclerView()
            val movies = viewModel.getMovies()
            movies.observe(viewLifecycleOwner, Observer(this@MovieFragment::observeData))

            movieAdapter.onItemClick = { data ->
                Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra(MyConstant.EXTRA_ID, data.id)
                    startActivity(this)

                }
            }

            btn_retry.setOnClickListener {
                viewModel.getMovies()
                    .observe(viewLifecycleOwner, Observer(this@MovieFragment::observeData))
            }


        }
    }

    private fun setUpRecyclerView() {
        with(recycler_view) {
            val layoutManager = GridLayoutManager(context, 2)
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun loading() {
        recycler_view.gone()
        progress_bar.visible()
        layout_error_detail.gone()
    }

    private fun observeData(data: Resource<List< Movie>>?) {
        if (data != null) {
            when (data) {
                is Resource.Loading -> loading()
                is Resource.Success -> {
                    recycler_view.visible()
                    progress_bar.gone()
                    movieAdapter.setData(data.data)
                }
                is Resource.Error -> {
                    layout_error_detail?.visible()
                    progress_bar.gone()
                    Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
