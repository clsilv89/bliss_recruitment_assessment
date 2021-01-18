package com.caio.bliss.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caio.bliss.data.model.Repo
import com.caio.bliss.databinding.ReposItemBinding
import com.caio.bliss.ui.recyclerview.ReposListAdapter.MyViewHolder

class ReposListAdapter(private val repos: List<Repo>)
    : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ReposItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(repos[position]) {
                dataBinding.reposItemTv.text = full_name
                dataBinding.executePendingBindings()
            }
        }
    }

    inner class MyViewHolder(val dataBinding: ReposItemBinding) :
        RecyclerView.ViewHolder(dataBinding.root)
}