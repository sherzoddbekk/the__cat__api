package com.example.thecatapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thecatapi.R
import com.example.thecatapi.model.CatElement

class AllCatAdapter(var context: Context,var items:ArrayList<CatElement>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_cat,parent,false)
        return AllCatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is AllCatViewHolder){

            val image = holder.image

            Glide
                .with(context)
                .load(item.url)
                .into(image);
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class AllCatViewHolder(view: View):RecyclerView.ViewHolder(view){
        var image:ImageView = view.findViewById(R.id.iv_pic_all_cat)
    }
}