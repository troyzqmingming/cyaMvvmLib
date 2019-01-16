package com.lib.cya.mvvm.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.lib.cya.mvvm.MVVMSDK
import com.lib.cya.mvvm.utils.NetworkUtil
import com.lib.cya.mvvm.vm.BaseViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : RxAppCompatActivity(), IBaseActivity {

    lateinit var binding: V
    lateinit var vm: VM

    abstract fun getLayoutId(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun getVariableId(): Int?

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    abstract fun getViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeLoadLayout()
        MVVMSDK.addSystemActivity(this)
        binding = DataBindingUtil.setContentView(this, getLayoutId(savedInstanceState))
        vm = getViewModel()
        getVariableId()?.let {
            binding.setVariable(it, vm)
        }
        initData()
        initViewObservable()
        vm.onCreate()
        vm.registerRxBus()
        if (!NetworkUtil.isNetworkAvailable(this)) {
            vm.loadWithoutNet()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.cancelDisposable()?.let {
            for (index in it) {
                if (index != null && !index.isDisposed) {
                    index.dispose()
                }
            }
        }
        vm.onDestroy()
        vm.removeRxBus()
        binding.unbind()
    }

    override fun beforeLoadLayout() {
    }

    override fun initData() {
    }

    override fun initViewObservable() {
    }

    /**
     * 刷新布局
     */
    fun refreshLayout() {
        getVariableId()?.let {
            binding.setVariable(it, vm)
        }
    }

}