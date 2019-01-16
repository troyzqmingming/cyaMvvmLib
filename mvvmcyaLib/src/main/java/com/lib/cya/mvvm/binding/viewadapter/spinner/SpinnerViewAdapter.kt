package com.lib.cya.mvvm.binding.viewadapter.spinner

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.lib.cya.mvvm.binding.viewadapter.command.BindingCommand2

open class SpinnerViewAdapter {

    companion object {
        /**
         * @param spinner        控件本身
         * @param dataList       下拉条目的集合
         * @param selectValue     显示的value
         * @param onItemSelectedCommand 点击的监听
         */
        @BindingAdapter(value = ["dataList", "selectValue", "onItemSelectedCommand"], requireAll = false)
        @JvmStatic
        fun onItemSelectedCommand(spinner: Spinner, dataList: MutableList<ISpinner>, selectValue: String, bindingCommand: BindingCommand2<ISpinner>) {
            val datas = mutableListOf<String>()
            for (item in dataList) {
                datas.add(item.getValue())
            }
            val arrayAdapter = ArrayAdapter<String>(spinner.context, android.R.layout.simple_spinner_dropdown_item, datas)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            spinner.adapter = arrayAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val item = dataList[p2]

                    bindingCommand.execute(item)
                }
            }
            if (!TextUtils.isEmpty(selectValue)) {
                for (i in 0 until dataList.size - 1) {
                    if (dataList[i].getValue() == selectValue) {
                        spinner.setSelection(i)
                        return
                    }
                }
            }
        }

    }
}