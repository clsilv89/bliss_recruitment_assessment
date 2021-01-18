package com.caio.modulo1.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caio.modulo1.R
import com.caio.modulo1.data.model.Emoji
import com.caio.modulo1.databinding.RecyclerViewItemBinding
import com.caio.modulo1.util.loadImageWithUrl

class RecyclerViewAdapter(private val emojis: List<Emoji>,
                          private val click: () -> Unit,
                          private val loadSuccess: () -> Unit,
                          private val loadError: (message: String) -> Unit,
                          private val context: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = emojis.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(emojis[position]) {
                dataBinding.imageView.loadImageWithUrl(url, loadFinish = {
                    if(position == 0) loadSuccess.invoke()
                }, loadError = {
                    val message = context.getString(R.string.main_load_image_error, name)
                    loadError.invoke(message)
                })
                dataBinding.executePendingBindings()
            }
        }
    }

    inner class MyViewHolder(val dataBinding: RecyclerViewItemBinding): RecyclerView.ViewHolder(dataBinding.root)

}
