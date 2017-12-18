package com.exz.firecontrol.app

import com.exz.firecontrol.bean.ChangeKeyBean
import com.szw.framelibrary.app.MyApplication

class ToolApplication: MyApplication() {
    override fun getSaltStr(): String? = "9E127DFDDA4F0BAB43B3"

    override fun onCreate() {
        super.onCreate()
        init()

    }
    companion object {
        //秘钥
        var changeKey: ChangeKeyBean?=null
    }
}
