package com.lib.cya.mvvm.retrofit

import android.app.Dialog
import io.reactivex.disposables.Disposable

interface IRetrofitProgressDialog {

    fun showDialog(disposable: Disposable?): Dialog

    fun dismissDialog()
}