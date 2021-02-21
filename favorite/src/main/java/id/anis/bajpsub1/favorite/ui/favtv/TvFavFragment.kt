package id.anis.bajpsub1.favorite.ui.favtv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.ui.TvAdapter
import id.anis.bajpsub1.core.utils.MyConstant
import id.anis.bajpsub1.favorite.R
import id.anis.bajpsub1.core.utils.gone
import id.anis.bajpsub1.core.utils.visible
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class TvFavFragment : Fragment(){

    private val tvAdapter = TvAdapter()
    private val viewModel: TvFavViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(recycler_view)
        if (activity != null) {
            val movies = viewModel.getTvs()
            with(recycler_view) {
                val layoutManager = GridLayoutManager(context, 2)
                this.layoutManager = layoutManager
                setHasFixedSize(true)
                this.adapter = tvAdapter
            }
            tvAdapter.onItemClick = { data ->
                Intent(
                    context, Class.forName(
                        "id.anis.bajpsub1.ui.detail.tv.DetailTvActivity"

                    )
                ).apply {
                    putExtra(MyConstant.EXTRA_ID, data.id)
                    startActivity(this)
                }


            }
            movies.observe(viewLifecycleOwner, Observer(this@TvFavFragment::handleData))

        }
    }

    private fun handleData(data: List<Tv>) {
        progress_bar.gone()
        if (data.isNullOrEmpty()){
            handleEmptyData()
            return
        }
        recycler_view.visible()
        tv_no_data.gone()
        tvAdapter.setData(data)
    }

    private fun handleEmptyData(){
        recycler_view.gone()
        tv_no_data.visible()
    }



    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = tvAdapter.getItemByPos(swipedPosition)
                movieEntity.let { viewModel.setFavoriteTv(it, !it.isFavorite) }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_delete),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    })


}
