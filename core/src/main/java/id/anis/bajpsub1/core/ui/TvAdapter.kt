package id.anis.bajpsub1.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import id.anis.bajpsub1.core.R
import id.anis.bajpsub1.core.domain.model.Tv
import id.anis.bajpsub1.core.utils.loadImageFromUrl
import kotlinx.android.synthetic.main.item_grid.view.*
import java.util.*

class TvAdapter : RecyclerView.Adapter<TvAdapter.MyHolder>() {

    private var listData = ArrayList<Tv>()
    var onItemClick: ((Tv) -> Unit)? = null

    fun setData(newListData: List<Tv>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindData(data: Tv) {
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

        init {
            itemView.rootView.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        )
    }

    fun getItemByPos(position: Int) = listData[position]


    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data = listData[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}