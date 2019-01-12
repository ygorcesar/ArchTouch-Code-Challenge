package com.arctouch.codechallenge.base.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewAdapter<T>(
    private var items: MutableList<T> = mutableListOf(),
    @LayoutRes val layoutResId: Int,
    private val bindView: (view: View, item: T) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val viewItem = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return BaseViewHolder(viewItem, bindView)
    }

    override fun getItemCount() = this.items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindView(getItem(position))
    }

    fun getItems() = items

    fun setItems(items: MutableList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = items[position]

    fun addItem(item: T) {
        this.items.add(item)
        notifyItemInserted(itemCount)
    }

    fun addItems(items: List<T>) {
        val currentCount = itemCount
        this.items.addAll(items)
        for (i in currentCount..itemCount) {
            notifyItemInserted(i)
        }
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

}