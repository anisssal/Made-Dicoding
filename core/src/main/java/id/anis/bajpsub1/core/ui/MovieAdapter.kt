package id.anis.bajpsub1.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import id.anis.bajpsub1.core.R
import id.anis.bajpsub1.core.domain.model.Movie
import id.anis.bajpsub1.core.utils.loadImageFromUrl
import kotlinx.android.synthetic.main.item_grid.view.*
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MyHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rootView.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

        fun bindData(data: Movie) {
            with(itemView) {
                tv_title.text = data.title

                data.poster?.let {
                    imagePoster.loadImageFromUrl(it)
                }

                val background = when (adapterPosition % 5) {
                    0 -> R.color.colorBackground1
                    1 -> R.color.colorBackground2
                    2 -> R.color.colorBackground3
                    3 -> R.color.colorBackground4
                    4 -> R.color.colorBackground5
                    else -> R.color.colorBackground1
                }
                tv_title.setBackgroundColor(
                    ActivityCompat.getColor(
                        this.container.context,
                        background
                    )
                )

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data = listData[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    fun getItemByPos(position: Int) = listData[position]

}