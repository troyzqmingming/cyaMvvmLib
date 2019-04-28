package com.lib.cya.mvvm.update

import android.content.Context
import com.lib.cya.mvvm.data.shared.SharePreferenceUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Succeed
import zlc.season.rxdownload3.extension.ApkInstallExtension
import zlc.season.rxdownload3.extension.ApkOpenExtension
import zlc.season.rxdownload3.helper.dispose
import zlc.season.rxdownload3.notification.NotificationFactory
import java.io.File


object UpdateManager {

    private lateinit var updateMission: Mission
    private var autoInstall: Boolean = false
    private var disposable: Disposable? = null

    fun initApplication(context: Context,
                        autoInstall: Boolean = false,//不自动安装
                        enableNotification: Boolean = true,//默认显示
                        notificationFactory: NotificationFactory = UpdateNotification()//默认提供点击安装
    ) {
        this.autoInstall = autoInstall
        val builder = DownloadConfig.Builder.Companion.create(context)
                .enableDb(true)
                .enableNotification(enableNotification)
                .setNotificationFactory(notificationFactory)
                .addExtension(ApkOpenExtension::class.java)
                .addExtension(ApkInstallExtension::class.java)
        DownloadConfig.init(builder)
    }

    fun setUpdateMission(context: Context, mission: Mission): UpdateManager {
        updateMission = mission
        disposable = RxDownload.create(mission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it is Succeed) {
                        if (!SharePreferenceUtils.getInstance(context).getBoolean("is_show_install_dialog", false) && autoInstall) {
                            SharePreferenceUtils.getInstance(context).putBoolean("is_show_install_dialog", true)
                            install(context, File(mission.savePath + File.separator + mission.saveName))
                        }
                    }
                }
        return this
    }


    fun download(context: Context) {
        //防止下载完成重复弹出安装框
        SharePreferenceUtils.getInstance(context).putBoolean("is_show_install_dialog", false)
        RxDownload.start(updateMission).subscribe()
    }

    fun stop() = RxDownload.stop(updateMission).subscribe()

    fun install(context: Context, apkFile: File) {
        if (apkFile.exists()) {
            ApkInstallExtension.ApkInstallActivity.start(context, apkFile.path)
        }
    }

    fun onDestroy() {
        disposable?.let {
            dispose(it)
        }

    }
}