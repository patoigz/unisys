package com.unisys.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.unisys.news.news.repo.dto.Article
import com.unisys.news.news.repo.local.DBConstant
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    private var item: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(DBConstant.NEWS)) {
                item = it.getParcelable(DBConstant.NEWS)
                activity?.toolbar_layout?.title = item?.author
                Picasso
                    .get()
                    .load(item?.urlToImage)
                    .into(activity?.backdrop)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item.let {
            rootView.title.text = it?.title
            rootView.description.text = it?.description
            rootView.content.text = it?.content
            rootView.publishedAt.text = it?.publishedAt
            rootView.url.text = it?.url
        }

        return rootView
    }
}
