package com.example.clone_instagram.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_instagram.R
import com.example.clone_instagram.common.model.Post

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    var items: List<Post> = mutableListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
            return FeedViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_post_list,parent,false)
            )
        }

        override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int {
            return  items.size
        }


        class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            fun bind(posts: Post) {
                itemView.findViewById<ImageView>(R.id.home_img_post).setImageURI(posts.uri)
            }
        }

    }