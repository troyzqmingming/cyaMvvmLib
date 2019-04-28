package com.lib.cya.mvvm.update

import zlc.season.rxdownload3.core.Mission
import java.io.File

class UpdateMission(url: String, saveName: String, savePath: String) : Mission(url, saveName, savePath) {


    val file = File(savePath + File.separator + saveName)

    fun isExists(): Boolean {
        return file.exists()
    }
}