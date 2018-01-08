package com.exz.firecontrol.app

import android.util.Log
import com.exz.firecontrol.bean.ChangeKeyBean
import com.szw.framelibrary.app.MyApplication
import com.tencent.rtmp.TXLiveBase
import io.rong.imkit.RongIM







class ToolApplication: MyApplication() {

    override fun getSaltStr(): String? = "9E127DFDDA4F0BAB43B3"

    override fun onCreate() {
        super.onCreate()
        initData()
    }

    public fun initData() {
        myApplication=this
        init()
        RongIM.init(this)

//        TXLiveBase.setConsoleEnabled(true)
        val sdkver = TXLiveBase.getSDKVersionStr()
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver)


//        //监视应用异常
//        val crashHandler = CrashHandler.getInstance()
//        crashHandler.init(applicationContext)
    }

    companion object {
        private lateinit var myApplication :ToolApplication
        //秘钥
        var changeKey: ChangeKeyBean?=null

        fun getInstance(): ToolApplication {
            return myApplication
        }
    }



}
