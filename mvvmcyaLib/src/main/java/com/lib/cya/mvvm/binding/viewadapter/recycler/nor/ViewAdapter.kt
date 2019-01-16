package com.lib.cya.mvvm.binding.viewadapter.recycler.nor

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.lib.cya.mvvm.adapter.BaseRecyclerAdapter

open class ViewAdapter {

    companion object {

        @BindingAdapter(value = ["adapter", "layoutManager"])
        @JvmStatic
        fun onBindRecyclerCommand(recyclerView: RecyclerView,
                                  adapter: BaseRecyclerAdapter<*, *>,
                                  layoutManager: RecyclerView.LayoutManager) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
        }
    }
}