package com.caio.bliss.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.caio.bliss.R
import com.caio.bliss.application.MyApplication
import com.caio.bliss.data.model.Repo
import com.caio.bliss.ui.recyclerview.ReposListAdapter
import com.caio.bliss.ui.viewModel.ListReposActvityViewModel
import kotlinx.android.synthetic.main.activity_list_repos.*
import org.koin.core.parameter.parametersOf

class ListReposActivity : AppCompatActivity() {

    companion object {
        private var repoList: ArrayList<Repo>? = null
        fun newIntent(
            context: Context,
            repos: List<Repo>
        ) =
            Intent(context, ListReposActivity::class.java).apply {
                repoList = repos as ArrayList<Repo>
            }
    }

    private val viewModel by MyApplication.scope?.inject<ListReposActvityViewModel> {
        parametersOf(this)
    }
    private var rvAdapter: ReposListAdapter? = null
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repos)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.run {
            repoResponse().observe(this@ListReposActivity, Observer {
                it?.let {
                    repoList?.addAll(it)
                    rvAdapter?.notifyDataSetChanged()
                }
            })
            error().observe(this@ListReposActivity, Observer {
                it?.let {
                    if (it) {
                        Toast.makeText(
                            this@ListReposActivity,
                            getString(R.string.main_load_content_error), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    private fun setupRecyclerView() {
        repoList?.let { list ->
            rvAdapter = ReposListAdapter(list)
            reposRv.apply {
                layoutManager = LinearLayoutManager(this@ListReposActivity)
                adapter = rvAdapter
                addOnScrollListener(object : OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val visibleItemCount = reposRv.layoutManager?.childCount
                        val totalItemCount = reposRv.layoutManager?.itemCount
                        val pastVisiblesItems =
                            (reposRv.layoutManager as LinearLayoutManager?)?.findFirstVisibleItemPosition()
                        if (visibleItemCount != null && pastVisiblesItems != null) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount!!) {
                                viewModel.getMoreRepos(page)
                            }
                        }
                    }
                })
            }
            page += 1
        }
    }
}