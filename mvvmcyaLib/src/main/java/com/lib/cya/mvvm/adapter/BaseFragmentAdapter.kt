package com.lib.cya.mvvm.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

class BaseFragmentAdapter<out T : Fragment>(fm: FragmentManager, list: ArrayList<T>) : FragmentPagerAdapter(fm) {

    private val dataList = arrayListOf<T>()

    init {
        if (dataList.size > 0) {
            dataList.clear()
        }
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = dataList[position]

    override fun getCount() = dataList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //防止page销毁，保存各个page的状态
//        super.destroyItem(container, position, `object`)
    }
}