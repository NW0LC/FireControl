package com.exz.carprofitmuch.config

/**
 * Created by 史忠文
 * on 2017/10/17.
 */
object Urls{
    var APP_ID = ""
    var url = "http://139.129.23.185:9654/"

    /**
     * 交换密钥接口
     */
    val changeKey=url+"user/encrypt/changeKey.jn"
    /**
     * 登录接口
     */
    val userLogin=url+"user/userLogin.jn"
}