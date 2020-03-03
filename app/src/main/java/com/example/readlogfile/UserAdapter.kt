package com.example.readlogfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_items.view.*


class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mUsersList = ArrayList<FinalLogInfo>()
    var loadingMore = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.user_items, parent, false)
                UserItemViewHolder(view)
            }
            else -> {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.loading_layout, parent, false)
                LoadingViewHolder(view)
            }
        }

    override fun getItemCount(): Int = if (loadingMore) mUsersList.size + 1 else mUsersList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_NORMAL) {
            (holder as UserItemViewHolder).showUsers(mUsersList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (mUsersList[position] == FinalLogInfo("", arrayListOf())) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    fun addItems(usersList: List<FinalLogInfo>) {
        this.mUsersList.clear()
        this.mUsersList.addAll(usersList)
        notifyDataSetChanged()
    }

    fun getExistingCount() = mUsersList.size

    fun showLoadingMoreIndicator() {
        mUsersList.add(FinalLogInfo("", arrayListOf("")))
        loadingMore = true
        notifyDataSetChanged()
    }

    fun hideLoadingMoreIndicator() {
        mUsersList.remove(FinalLogInfo("", arrayListOf("")))
        loadingMore = false
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }


}

class UserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun showUsers(finalLogInfo: FinalLogInfo) {
        itemView.user_name.text = (itemView.context).getString(R.string.user, finalLogInfo.user)
        itemView.count.text = (itemView.context).getString(R.string.consecutive, finalLogInfo.pages.size)
        itemView.page_name_rv.adapter = PageAdapter(finalLogInfo.pages)
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
