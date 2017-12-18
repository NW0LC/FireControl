package com.exz.firecontrol.app

import com.szw.framelibrary.app.MyApplication

/**
 * Created by pc on 2017/12/18.
 */

class ToolApplication: MyApplication() {
    override fun getSaltStr(): String? = "9E127DFDDA4F0BAB43B3"

    override fun onCreate() {
        super.onCreate()
        init()

    }
}
