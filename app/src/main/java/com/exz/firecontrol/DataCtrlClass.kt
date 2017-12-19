package com.exz.firecontrol

import android.content.Context
import com.exz.carprofitmuch.config.Urls
import com.exz.firecontrol.app.ToolApplication
import com.exz.firecontrol.bean.*
import com.exz.firecontrol.module.login.LoginActivity.Companion.USER_NAME
import com.exz.firecontrol.module.login.LoginActivity.Companion.USER_PWD
import com.exz.firecontrol.utils.RC4.encry2String
import com.exz.firecontrol.utils.RSAUtils
import com.exz.firecontrol.utils.net.DialogCallback
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.utils.net.AbsNetBean
import org.jetbrains.anko.toast
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import javax.annotation.Nullable


/**
 * Created by 史忠文
 * on 2017/10/18.
 */

object DataCtrlClass {
    const val HttpCode_Success = 200
    const val HttpCode_Error = 500
    const val HttpCode_Error_Key = 511
    private const val NetCode_Success = 0
    private const val NetCode_NoKey = 6
    private val pageSize = 20
    /**
     *  0;//成功
     * -1;//参数错误
     * -2;//用户名或密码错误
     * -6;//用户已存在
     * -7;//用户不存在
     * -8;//注册失败
     * -9;//登录失败
     * -10;//帐户冻结
     *
     * 1	KErrUnknown	未知错误
     * 2	KErrInvalidRequest	无效请求
     * 3	KErrServerError	服务器错误
     * 4	KErrNotFound	未找到
     * 5	KErrAlreadyExist	已经存在
     * 7	KErrKeyLogin	没有登录
     */
    fun isSuccess(context: Context?, code: Int = 6, @Nullable error: String = "", listener: () -> Unit): Boolean {
        return when (code) {
            NetCode_Success -> {
                true
            }
            HttpCode_Success -> {
                listener.invoke()
                true
            }
            1 -> {
                context?.toast("参数错误")
                false
            }
            2 -> {
                context?.toast("无效请求")
                false
            }
            3 -> {
                context?.toast("服务器错误")
                false
            }
            4 -> {
                context?.toast("未找到")
                false
            }
            5 -> {
                context?.toast("已经存在")
                false
            }
            7 -> {
                context?.toast("已经存在")
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
                context?.toast("没有登录")
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
                    if (context != null)
                        userLogin(context, MyApplication.getSPUtils(context)?.getString(USER_NAME) ?: "", MyApplication.getSPUtils(context)?.getString(USER_PWD) ?: "") {
                            listener.invoke()
                        }
                }
                false
            }
            else -> {
                context?.toast(error)
                false
            }
        }

    }

    private fun changeFun(hashMap: HashMap<String, String>): HashMap<String, String> {
        val params = HashMap<String, String>()
        var param=""
        for (mutableEntry in hashMap) {
            param+=mutableEntry.key+"="+mutableEntry.value+"&"
        }
        params.put("param", encry2String(param.substring(0,param.length-1), ToolApplication.changeKey?.rc4Key ?: ""))
        params.put("token", ToolApplication.changeKey?.token ?: "")
        return params
    }

    /**
     * 交换密钥接口
     *
     * */
    fun changeKey(context: Context?, listener: (l: ChangeKeyBean?) -> Unit) {
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
                        if (isSuccess(context, response.body().getCode(), response.body().messError) {}) {
                            ToolApplication.changeKey = response.body()
                            ToolApplication.changeKey?.rc4Key = RSAUtils.decryptByPrivateKey(response.body().key, privateKey)
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<ChangeKeyBean>) {
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 登录接口
     *@param userNO    账号
     * @param password    密码
     * */
    fun userLogin(context: Context?,
                  userNO: String,
                  password: String,
                  listener: (l: LoginBean?) -> Unit) {
//        userNO	账号	Y	string
//        password	密码	Y	string
        val params = HashMap<String, String>()
        params.put("userNO", userNO)
        params.put("password", password)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<LoginBean>(Urls.userLogin)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<LoginBean>(context) {
                        val function = { userLogin(context,userNO,password,listener) }
                        override fun onSuccess(response: Response<LoginBean>) {
                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<LoginBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 更新数据库在线标志（注销接口）
     * @param IsOnline    在线标志
     * */
    fun updateIsOnline(context: Context?,
                       IsOnline: String,
                       listener: (l: AbsNetBean?) -> Unit) {
//        参数名	参数含义	必选	类型及范围	说明
//        uid	    用户ID	    Y	    Int
//        IsOnline	在线标志	N	    Int	        1.在线0不在线    默认为1
        val params = HashMap<String, String>()
        params.put("uid", MyApplication.loginUserId)
        params.put("IsOnline", IsOnline)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<AbsNetBean>(Urls.updateIsOnline)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<AbsNetBean>(context) {
                        val function = { updateIsOnline(context, IsOnline, listener) }
                        override fun onSuccess(response: Response<AbsNetBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<AbsNetBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 修改密码接口
     *
     * */
    fun changePwd(context: Context?,
                  current_pwd: String,
                  changed_pwd: String,
                  listener: (l: AbsNetBean?) -> Unit) {
//        参数名	    参数含义	必选	类型及范围	说明
//        uid	        用户ID	    Y	    Int
//        current_pwd	原密码	    Y	    String
//        changed_pwd	新密码	    Y	    String
        val params = HashMap<String, String>()
        params.put("uid", MyApplication.loginUserId)
        params.put("current_pwd", current_pwd)
        params.put("changed_pwd", changed_pwd)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<AbsNetBean>(Urls.changePwd)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<AbsNetBean>(context) {
                        val function = { changePwd(context, current_pwd, changed_pwd, listener) }
                        override fun onSuccess(response: Response<AbsNetBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError,function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<AbsNetBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 忘记密码接口
     * @param phone        用户手机号
     * @param password    新密码
     * */
    fun updatePwd(context: Context?, phone: String, password: String, listener: (l: AbsNetBean?) -> Unit) {
//        参数名	参数含义	必选	类型及范围	说明
//        phone	    用户手机号	Y	String
//        password	新密码	    Y	String
        val params = HashMap<String, String>()
        params.put("phone", phone)
        params.put("password", password)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<AbsNetBean>(Urls.updatePwd)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<AbsNetBean>(context) {
                        val function = { updatePwd(context, phone, password, listener) }
                        override fun onSuccess(response: Response<AbsNetBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<AbsNetBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param RoleId    角色编号
     * @param Pid        父角色编号
     * @param comId        顶级单位id
     * @param nameKey    名称查询关键字
     * @param Type        分类
     * @param Level        级别
     * @param nowPage    当前页
     * */
    fun getEnterPriseAllList(context: Context?,
                             RoleId: String,
                             Pid: String = "0",
                             comId: String,
                             nameKey: String = "",
                             Type: String = "",
                             Level: String = "",
                             nowPage: Int = 1,
                             listener: (l: EnterPriseAllListBean?) -> Unit) {
//        参数名	参数含义	    必选	类型及范围	说明
//        RoleId	角色编号	    Y	    int
//        Pid	    父角色编号	    N	    Int
//        comid	    顶级单位id	    Y	    Int
//        nameKey	名称查询关键字	N	    String
//        Type	    分类	        N	    String	    例如：石油化工类
//        Level	    级别	        N	    String	    例如：一级重点单位
//        pageSize	页大小	        N	    Int
//        nowPage	当前页	        N	    Int
        val params = HashMap<String, String>()
        params.put("RoleId", RoleId)
        params.put("Pid", Pid)
        params.put("comid", comId)
        params.put("nameKey", nameKey)
        params.put("Type", Type)
        params.put("Level", Level)
        params.put("pageSize", pageSize.toString())
        params.put("nowPage", nowPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<EnterPriseAllListBean>(Urls.getEnterPriseAllList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<EnterPriseAllListBean>(context) {
                        val function = { getEnterPriseAllList(context, RoleId, Pid, comId, nameKey, Type, Level, nowPage, listener) }
                        override fun onSuccess(response: Response<EnterPriseAllListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError,function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<EnterPriseAllListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param EnterpriseId    角色编号
     * @param comId        顶级单位id
     * */
    fun getDrawFileList(context: Context?,
                        EnterpriseId: String,
                        comId: String,
                        listener: (l: DrawFileListBean?) -> Unit) {
//        参数名	    参数含义	必选	类型及范围	说明
//        EnterpriseId	消防单位id	Y	    int
//        comid	        顶级单位id	Y	    int
        val params = HashMap<String, String>()
        params.put("EnterpriseId", EnterpriseId)
        params.put("comid", comId)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<DrawFileListBean>(Urls.getDrawFileList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<DrawFileListBean>(context) {
                        val function = { getDrawFileList(context, EnterpriseId, comId, listener) }
                        override fun onSuccess(response: Response<DrawFileListBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<DrawFileListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param Id        主键Id
     * */
    fun getEnterPrise(context: Context?,
                      Id: String,
                      listener: (l: EnterPriseBean?) -> Unit) {
//        参数名	参数含义	必选	类型及范围	说明
//        Id	    主键Id	    Y	    int
        val params = HashMap<String, String>()
        params.put("Id", Id)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<EnterPriseBean>(Urls.getEnterPrise)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<EnterPriseBean>(context) {
                        val function = { getEnterPrise(context, Id, listener) }
                        override fun onSuccess(response: Response<EnterPriseBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<EnterPriseBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }


                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param RoleId        角色编号
     * @param Pid        父角色编号
     * @param comId        顶级单位id
     * @param nameKey    姓名查询关键字
     * @param isOnline    人员状态，是否在线
     * @param nowPage    当前页
     * */
    fun getFireManLocAllList(context: Context?,
                             RoleId: String,
                             Pid: String = "0",
                             comId: String,
                             nameKey: String = "",
                             isOnline: String = "1",
                             nowPage: Int = 1,
                             listener: (l: FireMainLocAllListBean?) -> Unit) {
//       参数名	    参数含义	        必选	类型及范围	说明
//       RoleId	    角色编号	        Y	    int
//       Pid	    父角色编号	        N	    Int
//       comid	    顶级单位id	        Y	    int
//       nameKey	姓名查询关键字	    N	    String
//       isOnline	人员状态，是否在线	N	    int	        1.在线0.不在线
//       pageSize	页大小	            N	    Int
//       nowPage	当前页	            N	    Int
        val params = HashMap<String, String>()
        params.put("RoleId", RoleId)
        params.put("Pid", Pid)
        params.put("comid", comId)
        params.put("nameKey", nameKey)
        params.put("isOnline", isOnline)
        params.put("pageSize", pageSize.toString())
        params.put("nowPage", nowPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireMainLocAllListBean>(Urls.getFireManLocAllList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireMainLocAllListBean>(context) {
                        val function = { getFireManLocAllList(context, RoleId, Pid, comId, nameKey, isOnline, nowPage, listener) }
                        override fun onSuccess(response: Response<FireMainLocAllListBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireMainLocAllListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param RoleId        角色编号
     * @param Pid        父角色编号
     * @param comId        顶级单位id
     * @param carType    车辆类型
     * @param carNum        车辆编号
     * @param isOnline    车辆状态，是否在线
     * @param nowPage    当前页
     * */
    fun getFireCarLocAllList(context: Context?,
                             RoleId: String,
                             Pid: String = "0",
                             comId: String,
                             carType: String = "",
                             carNum: String = "",
                             isOnline: String = "1",
                             nowPage: Int = 1,
                             listener: (l: FireCarLocAllListBean?) -> Unit) {
//       参数名	    参数含义	        必选	类型及范围	说明
//       RoleId	    角色编号	        Y	    int
//       Pid	    父角色编号	        N	    Int
//       comid	    顶级单位id	        Y	    int
//       carType	车辆类型	        N	    int	        1.登高车2.指挥车3.泡沫车4.水罐车5.干粉车6.高鹏车 7越野摩托车
//       carNum	    车辆编号	        N	    String
//       isOnline	车辆状态，是否在线	N	    Int	        1.在线0.不在线
//       pageSize	页大小	            N	    Int
//       nowPage	当前页	            N	    Int
        val params = HashMap<String, String>()
        params.put("RoleId", RoleId)
        params.put("Pid", Pid)
        params.put("comid", comId)
        params.put("carType", carType)
        params.put("carNum", carNum)
        params.put("isOnline", isOnline)
        params.put("pageSize", pageSize.toString())
        params.put("nowPage", nowPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireCarLocAllListBean>(Urls.getFireCarLocAllList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireCarLocAllListBean>(context) {
                        val function = { getFireCarLocAllList(context, RoleId, Pid, comId, carType, carNum, isOnline, nowPage, listener) }
                        override fun onSuccess(response: Response<FireCarLocAllListBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireCarLocAllListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防预案、vr列表
     * @param flag            标志
     * @param enterpriseId    消防单位id
     * */
    fun getEnterPriseData(context: Context?,
                          flag: String,
                          enterpriseId: String,
                          listener: (l: EnterPriseDataBean?) -> Unit) {
//       参数名	        参数含义	必选	类型及范围	说明
//       flag	        标志	    Y	    int	        1-电子预案，2-纸质预案，3-VR
//       enterpriseId	消防单位id	Y	    Int
        val params = HashMap<String, String>()
        params.put("flag", flag)
        params.put("enterpriseId", enterpriseId)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<EnterPriseDataBean>(Urls.getEnterPriseData)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<EnterPriseDataBean>(context) {
                        val function = { getEnterPriseData(context, flag, enterpriseId, listener) }
                        override fun onSuccess(response: Response<EnterPriseDataBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<EnterPriseDataBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取数据路线
     * @param RoleId        角色编号
     * */
    fun getPathPlanByRoleId(context: Context?,
                            RoleId: String,
                            enterpriseId: String,
                            listener: (l: PathPlanByRoleIdBean?) -> Unit) {
//       参数名	参数含义	必选	类型及范围	说明
//       RoleId	角色id	    Y	    int
        val params = HashMap<String, String>()
        params.put("RoleId", RoleId)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<PathPlanByRoleIdBean>(Urls.getPathPlanByRoleId)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<PathPlanByRoleIdBean>(context) {
                        val function = { getPathPlanByRoleId(context, RoleId, enterpriseId, listener) }
                        override fun onSuccess(response: Response<PathPlanByRoleIdBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<PathPlanByRoleIdBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param  oid        组织结构id
     * @param  comId        顶级单位ID
     * @param  nowPage    当前页
     * @param  status        处理状态，多个状态用逗号隔开
     * */
    fun getFireInfoList(context: Context?,
                        oid: String,
                        comId: String,
                        status: String = "",
                        nowPage: Int = 1,
                        listener: (l: FireCarLocAllListBean?) -> Unit) {
//       参数名	    参数含义	                    必选	类型及范围	说明
//       oid	    组织结构id	                    Y	    int
//       comId	    顶级单位ID	                    Y	    int
//       pageSize	页大小	                        N	    Int
//       nowPage	当前页	                        N	    Int
//       status	    处理状态，多个状态用逗号隔开	N	    String	    1=未处理  2=出动  3=归队
        val params = HashMap<String, String>()
        params.put("oid", oid)
        params.put("comid", comId)
        params.put("status", status)
        params.put("pageSize", pageSize.toString())
        params.put("nowPage", nowPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireCarLocAllListBean>(Urls.getFireInfoList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireCarLocAllListBean>(context) {
                        val function = { getFireInfoList(context, oid, comId, status, nowPage, listener) }
                        override fun onSuccess(response: Response<FireCarLocAllListBean>) {

                            if (isSuccess(context, response.body().getCode(),response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireCarLocAllListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey,"", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
}