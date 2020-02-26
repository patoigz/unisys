package com.unisys.news

import android.Manifest
import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.unisys.news.base.view.BaseActivity
import com.unisys.news.news.adapter.SimpleItemRecyclerViewAdapter
import com.unisys.news.news.model.NewsResponse
import com.unisys.news.news.presenter.NewsPresenter
import com.unisys.news.news.presenter.NewsPresenterImpl
import com.unisys.news.news.repo.NewsRepo
import com.unisys.news.news.repo.NewsRepoImpl
import com.unisys.news.news.repo.local.AppDatabase
import com.unisys.news.news.repo.local.NewsLocalRepo
import com.unisys.news.news.repo.local.NewsLocalRepoImpl
import com.unisys.news.news.repo.remote.NewsRemoteRepo
import com.unisys.news.news.repo.remote.NewsRemoteRepoImpl
import com.unisys.news.news.view.NewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_item_detail.item_detail_container
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : BaseActivity<NewsPresenter?>(), NewsView {

    companion object {
        private val TAG = ItemListActivity::class.java.simpleName

        private val INTERNET_REQUEST_CODE = 101
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        presenter.getNews
    }

    override fun createPresenter(): NewsPresenter {
        val remoteNewsRepo: NewsRemoteRepo = NewsRemoteRepoImpl()

        val db = AppDatabase(this)

        val localNewsRepo: NewsLocalRepo = NewsLocalRepoImpl(db.newsDao())
        val newsRepo: NewsRepo = NewsRepoImpl(remoteNewsRepo, localNewsRepo)
        return NewsPresenterImpl(newsRepo, AndroidSchedulers.mainThread())
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.INTERNET
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET),
            INTERNET_REQUEST_CODE
        )
    }

    override fun onStart() {
        super.onStart()
        setupPermissions()
    }

    override fun showNews(news: NewsResponse) {
        item_list_view.adapter = SimpleItemRecyclerViewAdapter(this, news.articles, twoPane)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_main, menu)
            //Creates input field for the user search
            setUpSearchMenuItem(menu)
        }
        return true
    }

    private fun setUpSearchMenuItem(menu: Menu) {
        val searchManager: SearchManager =
            (getSystemService(Context.SEARCH_SERVICE)) as SearchManager
        val searchView: SearchView = ((menu.findItem(R.id.action_search)?.actionView)) as SearchView
        val searchMenuItem: MenuItem = menu.findItem(R.id.action_search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_enter)
        searchView.setOnQueryTextListener(DebouncingQueryTextListener(
            this@ItemListActivity.lifecycle
        ) { newText ->
            newText?.let {
                if (it.isEmpty()) {
                    presenter.getNews
                } else {
                    presenter.queryTopHeadlines(it)
                }
            }
        })
        searchMenuItem.icon.setVisible(false, false)
    }

    internal class DebouncingQueryTextListener(
        lifecycle: Lifecycle,
        private val onDebouncingQueryTextChange: (String?) -> Unit
    ) : SearchView.OnQueryTextListener {

        var debouncePeriod: Long = 1250

        private val coroutineScope = lifecycle.coroutineScope

        private var searchJob: Job? = null

        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                newText?.let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextChange(newText)
                }
            }
            return false
        }
    }

    override fun showLoading() {
//        Log.d(TAG, "showLoading() returned: ")
//        val progress = ProgressDialog(this)
//        progress.setMessage("Test")
//        progress.show()
    }

    override fun hideLoading() {
        Log.d(TAG, "hideLoading() returned: ")
    }

    override fun showError(error: String?) {
        Log.d(TAG, "showError() returned: $error")
    }
}
