package com.exz.firecontrol.app

import com.exz.firecontrol.bean.ChangeKeyBean
import com.szw.framelibrary.app.MyApplication
import com.tencent.rtmp.TXLiveBase
import io.rong.imkit.RongIM

class ToolApplication: MyApplication() {
    override fun getSaltStr(): String? = "9E127DFDDA4F0BAB43B3"

    override fun onCreate() {
        super.onCreate()
        init()
        RongIM.init(this)

        TXLiveBase.setConsoleEnabled(true)
    }
    companion object {
        //秘钥
        var changeKey: ChangeKeyBean?=null
    }



}
