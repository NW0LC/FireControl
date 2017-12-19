package com.exz.firecontrol

import android.content.Context
import com.exz.carprofitmuch.config.Urls
import com.exz.firecontrol.app.ToolApplication
import com.exz.firecontrol.bean.ChangeKeyBean
import com.exz.firecontrol.bean.LoginBean
import com.exz.firecontrol.utils.RC4.encry2String
import com.exz.firecontrol.utils.RSAUtils
import com.exz.firecontrol.utils.net.DialogCallback
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import org.jetbrains.anko.toast
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


/**
 * Created by 史忠文
 * on 2017/10/18.
 */

object DataCtrlClass {
    private const val NetCode_Success=0
    private const val NetCode_NoKey=6
    /**
     *  0;//成功
     * -1;//参数错误
     * -2;//用户名或密码错误
     * -6;//用户已存在
     * -7;//用户不存在
     * -8;//注册失败
     * -9;//登录失败
     * -10;//帐户冻结
     */
    fun isSuccess(context: Context?, code: Int = 6, listener: () -> Unit): Boolean {
        return when (code) {
            NetCode_Success -> {
                listener.invoke()
                true
            }
            1 -> {
                context?.toast("参数错误")
                false
            }
            -2 -> {
                context?.toast("用户名或密码错误")
                false
            }
            -6 -> {
                context?.toast("用户已存在")
                false
            }
            -7 -> {
                context?.toast("用户不存在")
                false
            }
            -8 -> {
                context?.toast("注册失败")
                false
            }
            -9 -> {
                context?.toast("登录失败")
                false
            }
            -10 -> {
                context?.toast("帐户冻结")
                false
            }

            NetCode_NoKey -> {
//                    交换密钥接口
                changeKey(context) {
                    listener.invoke()
                }
                false
            }
            else -> false
        }

    }

    private fun changeFun(hashMap: HashMap<String, String>): HashMap<String, String> {
        val params = HashMap<String, String>()
        params.put("param", encry2String(Gson().toJson(hashMap), ToolApplication.changeKey?.rc4Key ?: ""))
        params.put("token", ToolApplication.changeKey?.token ?: "")
        return params
    }

    /**
     * 交换密钥接口
     *
     * */
    private fun changeKey(context: Context?, listener: (code: Int) -> Unit) {
//        @param[rsa_m] string	必填	rsa公钥模数
//        @param[rsa_e] string	必填	rsa公钥指数
        val map = RSAUtils.getKeys()
        //生成公钥和私钥
        val publicKey = map["public"] as RSAPublicKey
        val privateKey = map["private"] as RSAPrivateKey
        //模
        val modulus = publicKey.modulus.toString()
        //公钥指数
        val publicExponent = publicKey.publicExponent.toString()
        val params = HashMap<String, String>()
        params.put("rsa_m", modulus)
        params.put("rsa_e", publicExponent)
        OkGo.post<ChangeKeyBean>(Urls.changeKey)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<ChangeKeyBean>(context) {
                    override fun onSuccess(response: Response<ChangeKeyBean>) {
                        if (isSuccess(context, response.body().getCode()) {}) {
                            ToolApplication.changeKey = response.body()
                            ToolApplication.changeKey?.rc4Key = RSAUtils.decryptByPrivateKey(response.body().key, privateKey)
                            listener.invoke(response.body().getCode())
                        }
                    }

                })
    }

    /**
     * 登录接口
     *
     * */
     fun userLogin(context: Context?,userNO:String,password:String, listener: (l: LoginBean?) -> Unit) {
//        userNO	账号	Y	string
//        password	密码	Y	string
        val params = HashMap<String, String>()
        params.put("userNO", userNO)
        params.put("password", password)
        isSuccess(context,if (ToolApplication.changeKey==null) NetCode_NoKey else NetCode_Success) {
            OkGo.post<LoginBean>(Urls.userLogin)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<LoginBean>(context) {
                        override fun onSuccess(response: Response<LoginBean>) {
                            if (isSuccess(context, response.body().getCode()) {}) {
                                listener.invoke(response.body())
                            }else{
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<LoginBean>) {
                            listener.invoke(null)
                        }

                    })
        }
    }
}