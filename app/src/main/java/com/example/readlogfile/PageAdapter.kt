package com.example.readlogfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.page_item.view.*

class PageAdapter(val pageList: List<String>) : RecyclerView.Adapter<PageItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.page_item, parent, false)
        return PageItemViewHolder(view)
    }

    override fun getItemCount(): Int = pageList.size

    override fun onBindViewHolder(holderItem: PageItemViewHolder, position: Int) {
        holderItem.showPages(pageList[position])
    }

}

class PageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun showPages(page: String) {
        itemView.page.text = page
    }

}

