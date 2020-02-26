package com.unisys.news.news.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unisys.news.ItemDetailActivity
import com.unisys.news.ItemDetailFragment
import com.unisys.news.ItemListActivity
import com.unisys.news.R
import com.unisys.news.news.repo.dto.Article
import com.unisys.news.news.repo.local.DBConstant
import kotlinx.android.synthetic.main.item_list_content.view.*


/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
class SimpleItemRecyclerViewAdapter(
    private val parentActivity: ItemListActivity,
    private val values: List<Article>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { view ->
            val item = view.tag as Article
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(DBConstant.NEWS, item)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(view.context, ItemDetailActivity::class.java).apply {
                    putExtra(DBConstant.NEWS, item)
                }
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.contentView.text = item.content?.substring(0, 50) + "..."
        holder.authorView.text = item.author
        holder.dateView.text = item.publishedAt

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.title
        val contentView: TextView = view.content
        val authorView: TextView = view.author
        val dateView: TextView = view.date
    }
}
