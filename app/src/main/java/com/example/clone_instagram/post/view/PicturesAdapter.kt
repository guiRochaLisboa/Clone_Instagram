package com.example.clone_instagram.post.view

import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_instagram.R
import com.example.clone_instagram.common.model.Post


class PicturesAdapter(private val onClick : (Uri) -> Unit) : RecyclerView.Adapter<PicturesAdapter.PostViewHolder>() {

    var items: List<Uri> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid,parent,false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return  items.size
    }


     inner class PostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Uri) {
            val bitmap = itemView.context.contentResolver.loadThumbnail(image, Size(200,200),null)
            itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageURI(image)
            itemView.setOnClickListener {
            onClick.invoke(image)
            }
        }
    }
}