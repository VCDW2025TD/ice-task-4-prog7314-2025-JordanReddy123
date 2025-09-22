package com.altaaf.memeice.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altaaf.memeice.R
import com.altaaf.memeice.model.Meme
import com.bumptech.glide.Glide

class MemeAdapter : ListAdapter<Meme, MemeAdapter.VH>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Meme>() {
            override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean = oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean = oldItem == newItem
        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgMeme)
        val title: TextView = view.findViewById(R.id.txtTitle)
        val caption: TextView = view.findViewById(R.id.txtCaption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_meme, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.caption.text = item.caption ?: ""
        Glide.with(holder.itemView).load(item.imageUrl).into(holder.img)
    }
}