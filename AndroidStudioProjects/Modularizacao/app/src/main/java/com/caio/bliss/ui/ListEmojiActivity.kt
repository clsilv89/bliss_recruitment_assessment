package com.caio.bliss.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.caio.bliss.R
import com.caio.bliss.application.MyApplication.Companion.emojiDatabase
import com.caio.bliss.application.MyApplication.Companion.scope
import com.caio.bliss.data.model.Emoji
import com.caio.bliss.ui.recyclerview.ItemClickListener
import com.caio.bliss.ui.recyclerview.EmojiListAdapter
import com.caio.bliss.ui.viewModel.ListEmojiViewModel
import com.caio.bliss.util.showSnackbar
import kotlinx.android.synthetic.main.list_emoji_activity.*
import org.koin.core.parameter.parametersOf

class ListEmojiActivity : AppCompatActivity(), OnRefreshListener {

    companion object {
        private const val SPAN_COUNT = 4
        private var emojiList: ArrayList<Emoji>? = null
        private var listener: ItemClickListener? = null

        fun newIntent(
            context: Context,
            list: List<Emoji>,
            clickListener: ItemClickListener? = null
        ) =
            Intent(context, ListEmojiActivity::class.java).apply {
                //Intent is kinda finicky and tends to null the list
                emojiList = list as ArrayList<Emoji>
                listener = clickListener
            }
    }

    private var adapter: EmojiListAdapter? = null
    private lateinit var list: ArrayList<Emoji>

    private val viewModel by scope?.inject<ListEmojiViewModel> {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_emoji_activity)

        swipeRefreshLayout.setOnRefreshListener(this)
        setupObservers()
        fetchEmojiList()
    }

    private fun setupObservers() {
        viewModel.emojiResponse().observe(this, Observer {
            it?.let {
                list = it as ArrayList<Emoji>
                adapter = EmojiListAdapter(list)
                adapter?.apply {
                    notifyDataSetChanged()
                }
            }
            setupRecyclerView(adapter)
        })
        viewModel.statusLabel().observe(this, Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.error().observe(this, Observer {
            it.let {
                if (it) {
                    showSnackbar(R.string.main_load_content_error, Toast.LENGTH_LONG)
                }
            }
        })
    }

    private fun setupRecyclerView(rvAdapter: EmojiListAdapter?) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@ListEmojiActivity, SPAN_COUNT)
            adapter = rvAdapter
        }
    }

    private fun fetchEmojiList() {
        if (emojiList.isNullOrEmpty()) {
            viewModel.getEmojis()
        } else {
            list = emojiList as ArrayList<Emoji>
            adapter = EmojiListAdapter(list, listener)
            setupRecyclerView(adapter)
        }
    }

    override fun onRefresh() {
        val list = emojiDatabase?.emojiDAO()?.all() as ArrayList<Emoji>
        adapter = EmojiListAdapter(list)

        Handler().postDelayed({
            setupRecyclerView(adapter)
            swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }
}
