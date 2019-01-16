package com.lib.cya.mvvm.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lib.cya.mvvm.utils.NetworkUtil
import com.lib.cya.mvvm.vm.BaseViewModel
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : RxFragment(), IBaseActivity {

    lateinit var vm: VM
    lateinit var binding: V

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

    override fun beforeLoadLayout() {
    }

    override fun initData() {
    }

    override fun initViewObservable() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(savedInstanceState), container, false)
        vm = getViewModel()
        getVariableId()?.let {
            binding.setVariable(it, vm)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViewObservable()
        vm.onCreate()
        vm.registerRxBus()
        activity?.let {
            if (!NetworkUtil.isNetworkAvailable(it)) {
                vm.loadWithoutNet()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.onDestroy()
        vm.removeRxBus()
        binding.unbind()
    }

    /**
     * 刷新布局
     */
    fun refreshLayout() {
        getVariableId()?.let {
            binding.setVariable(it, vm)
        }
    }

    fun getBaseActivity(): BaseActivity<*, *> {
        return this.activity as BaseActivity<*, *>
    }
}