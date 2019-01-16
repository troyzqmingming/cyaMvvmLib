package com.lib.cya.mvvm.binding.viewadapter.command

class BindingCommand2<T>(private val bindingConsumer: BindingConsumer<T>) {

    interface BindingConsumer<T> {
        fun call(t: T)
    }

    fun execute(t: T) {
        bindingConsumer.call(t)
    }
}