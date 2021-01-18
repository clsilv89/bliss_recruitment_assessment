package com.caio.bliss.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caio.bliss.data.model.Emoji
import com.caio.bliss.databinding.EmojiItemRvBinding
import com.caio.bliss.ui.recyclerview.EmojiListAdapter.MyViewHolder
import com.caio.bliss.util.loadImageWithUrl

class EmojiListAdapter(
    private val emojis: ArrayList<Emoji>,
    private val listener: ItemClickListener? = null
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = EmojiItemRvBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = emojis.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            itemView.setOnClickListener {
                listener?.click(emojis[position].name)
                emojis.removeAt(position)
                notifyDataSetChanged()
            }
            with(emojis[position]) {
                dataBinding.imageView.loadImageWithUrl(url)
                dataBinding.executePendingBindings()
            }
        }
    }

    inner class MyViewHolder(val dataBinding: EmojiItemRvBinding) :
        RecyclerView.ViewHolder(dataBinding.root)
}
