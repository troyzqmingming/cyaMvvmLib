package com.lib.cya.mvvm.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T, V : ViewDataBinding>(val context: Context) : RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerViewHolder<V>>() {

    interface ClickCallback<in T> {
        fun onItemClickCallback(t: T, position: Int)
    }

    var clickCallback: ClickCallback<T>? = null

    private var list: MutableList<T> = mutableListOf()


    fun setList(newList: MutableList<T>, isLoadMore: Boolean) {
        if (isLoadMore) {
            list.addAll(newList)
        } else {
            list = newList
        }
        notifyDataSetChanged()
    }

    fun getList(): MutableList<T> {
        return list
    }

    fun cleanList() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<V> {
        return BaseRecyclerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<V>, position: Int) {
        holder.viewDataBinding.setVariable(getVariableId(), list[position])
        holder.itemView.setOnClickListener {
            clickCallback?.onItemClickCallback(getList()[holder.adapterPosition], holder.adapterPosition)
            onItemClick(getList()[holder.adapterPosition], holder.adapterPosition)
        }
    }

    class BaseRecyclerViewHolder<V : ViewDataBinding>(val viewDataBinding: V) : RecyclerView.ViewHolder(viewDataBinding.root)

    abstract fun getLayoutId(): Int

    abstract fun getLayoutManager(): RecyclerView.LayoutManager

    abstract fun getVariableId(): Int

    open fun onItemClick(t: T, position: Int) {}
}