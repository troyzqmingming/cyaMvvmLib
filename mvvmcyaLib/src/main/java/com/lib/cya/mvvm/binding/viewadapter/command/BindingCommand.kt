package com.lib.cya.mvvm.binding.viewadapter.command

class BindingCommand(private val bindingAction: BindingAction) {

    interface BindingAction {
        fun call()
    }

    fun execute() {
        bindingAction.call()
    }
}