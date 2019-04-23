package com.lib.cya.mvvm.binding.viewadapter.viewpager

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.lib.cya.mvvm.adapter.BaseFragmentAdapter

class ViewAdapter {


    companion object {
        @BindingAdapter(value = ["adapter"])
        @JvmStatic
        fun adapterCommand(viewPager: ViewPager, adapter: BaseFragmentAdapter<*>) {
            viewPager.adapter = adapter
        }
    }
}