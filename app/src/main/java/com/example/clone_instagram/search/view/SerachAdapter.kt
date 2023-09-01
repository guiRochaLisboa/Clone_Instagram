package com.example.clone_instagram.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clone_instagram.R
import com.example.clone_instagram.common.model.User
import com.example.clone_instagram.common.model.UserAuth
import org.w3c.dom.Text

class SearchAdapter(
    private val itemClick: (String) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var items : List<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_list,parent,false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return  items.size
    }


    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {

            Glide.with(itemView.context).load(user.photoUrl).into(itemView.findViewById<ImageView>(R.id.search_img_user))
            itemView.findViewById<TextView>(R.id.search_txt_username).text = user.name
            itemView.setOnClickListener {
                if(user.uuid != null)
                    itemClick.invoke(user.uuid)
            }
        }

    }

}