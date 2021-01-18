package com.caio.modulo1.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.caio.modulo1.R
import com.caio.modulo1.data.di.CustomKoinComponent
import com.caio.modulo1.data.di.ModulesFactory
import com.caio.modulo1.ui.recyclerview.RecyclerViewAdapter
import com.caio.modulo1.util.showSnackbar
import kotlinx.android.synthetic.main.activity_module.*
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class ModuleActivity : AppCompatActivity(), CustomKoinComponent {

    companion object {
        private const val SCOPE = "scope"
        private const val SPAN_COUNT = 4
    }

    private val scope = getKoin().getOrCreateScope(SCOPE, named(ModulesFactory.MODULE))
    private var adapter: RecyclerViewAdapter? = null

    private val viewModel by scope.inject<ViewModel> {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getEmojis()
        viewModel.response().observe(this, Observer {
            it?.let {
                adapter = RecyclerViewAdapter(it, click = {
                }, loadSuccess = {
                }, loadError = { message ->
                    showSnackbar(message)
                }, context = this)

                adapter?.apply {
                    notifyDataSetChanged()
                }
            }
            setupRecyclerView(adapter)
        })

        viewModel.error().observe(this, Observer {
            if (it!!) {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupRecyclerView(rvAdapter: RecyclerViewAdapter?) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@ModuleActivity, SPAN_COUNT)
            adapter = rvAdapter
        }
    }
}