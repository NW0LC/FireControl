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
        var param = ""
        for (mutableEntry in hashMap) {
            param += mutableEntry.key + "=" + mutableEntry.value + "&"
        }
        params.put("param", encry2String(param.substring(0, param.length - 1), ToolApplication.changeKey?.rc4Key ?: ""))
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
                  listener: (l: UserBean?) -> Unit) {
//        userNO	账号	Y	string
//        password	密码	Y	string
        val params = HashMap<String, String>()
        params.put("userNO", userNO)
        params.put("password", password)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<UserBean>(Urls.userLogin)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<UserBean>(context) {
                        val function = { userLogin(context, userNO, password, listener) }
                        override fun onSuccess(response: Response<UserBean>) {
                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<UserBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
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

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<AbsNetBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
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
                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                            context?.toast(response.body().messError)
                        }

                        override fun onError(response: Response<AbsNetBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
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

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<AbsNetBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param nameKey    名称查询关键字
     * @param Type        分类
     * @param Level        级别
     * @param currentPage    当前页
     * */
    fun getEnterPriseAllList(context: Context?,
                             nameKey: String = "",
                             Type: String = "",
                             Level: String = "",
                             currentPage: Int = 1,
                             listener: (l: EnterPriseAllListBean?) -> Unit) {
//        参数名	参数含义	    必选	类型及范围	说明
//        RoleId	角色编号	    Y	    int
//        Pid	    父角色编号	    N	    Int
//        comid	    顶级单位id	    Y	    Int
//        nameKey	名称查询关键字	N	    String
//        Type	    分类	        N	    String	    例如：石油化工类
//        Level	    级别	        N	    String	    例如：一级重点单位
//        fetch_count	页大小	        N	    Int
//        start_postion	当前页	        N	    Int
        val params = HashMap<String, String>()
        params.put("RoleId", (MyApplication.user as UserBean).RoleId)
        params.put("Pid", (MyApplication.user as UserBean).Pid)
        params.put("comid", (MyApplication.user as UserBean).comid)
        params.put("nameKey", nameKey)
        params.put("Type", Type)
        params.put("Level", Level)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<EnterPriseAllListBean>(Urls.getEnterPriseAllListByPage)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<EnterPriseAllListBean>(context) {
                        val function = { getEnterPriseAllList(context,nameKey, Type, Level, currentPage, listener) }
                        override fun onSuccess(response: Response<EnterPriseAllListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<EnterPriseAllListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
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
        params.put("comid",comId )
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<DrawFileListBean>(Urls.getDrawFileList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<DrawFileListBean>(context) {
                        val function = { getDrawFileList(context, EnterpriseId,comId,listener) }
                        override fun onSuccess(response: Response<DrawFileListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<DrawFileListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 根据id获取消防单位基本信息
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

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<EnterPriseBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }


                    })
        }
    }

    /**
     * 获取消防单位列表
     * @param nameKey    姓名查询关键字
     * @param isOnline    人员状态，是否在线
     * @param currentPage    当前页
     * */
    fun getFireManAllByPage(context: Context?,
                             nameKey: String = "",
                             isOnline: String = "1",
                             currentPage: Int = 1,
                             listener: (l: FireMainLocAllListBean?) -> Unit) {
//       参数名	    参数含义	        必选	类型及范围	说明
//       RoleId	    角色编号	        Y	    int
//       Pid	    父角色编号	        N	    Int
//       comid	    顶级单位id	        Y	    int
//       nameKey	姓名查询关键字	    N	    String
//       isOnline	人员状态，是否在线	N	    int	        1.在线0.不在线
//       fetch_count	页大小	            N	    Int
//       start_postion	当前页	            N	    Int
        val params = HashMap<String, String>()
        params.put("RoleId", (MyApplication.user as UserBean).RoleId)
        params.put("Pid", (MyApplication.user as UserBean).Pid)
        params.put("comid", (MyApplication.user as UserBean).comid)
        params.put("nameKey", nameKey)
        params.put("isOnline", isOnline)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireMainLocAllListBean>(Urls.getFireManAllByPage)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireMainLocAllListBean>(context) {
                        val function = { getFireManAllByPage(context,nameKey, isOnline, currentPage, listener) }
                        override fun onSuccess(response: Response<FireMainLocAllListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireMainLocAllListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取消防车列表分页（分页）
     * @param carType    车辆类型
     * @param carNum        车辆编号
     * @param isOnline    车辆状态，是否在线
     * @param currentPage    当前页
     * */
    fun getFireCarListByPage(context: Context?,
                             carType: String = "",
                             carNum: String = "",
                             isOnline: String = "1",
                             currentPage: Int = 1,
                             listener: (l: FireCarLocAllListBean?) -> Unit) {
//       参数名	    参数含义	        必选	类型及范围	说明
//       RoleId	    角色编号	        Y	    int
//       Pid	    父角色编号	        N	    Int
//       comid	    顶级单位id	        Y	    int
//       carType	车辆类型	        N	    int	        1.登高车2.指挥车3.泡沫车4.水罐车5.干粉车6.高鹏车 7越野摩托车
//       carNum	    车辆编号	        N	    String
//       isOnline	车辆状态，是否在线	N	    Int	        1.在线0.不在线
//       fetch_count	页大小	            N	    Int
//       start_postion	当前页	            N	    Int
        val params = HashMap<String, String>()
        params.put("RoleId", (MyApplication.user as UserBean).RoleId)
        params.put("Pid", (MyApplication.user as UserBean).Pid)
        params.put("comid", (MyApplication.user as UserBean).comid)
        params.put("carType", carType)
        params.put("carNum", carNum)
        params.put("isOnline", isOnline)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireCarLocAllListBean>(Urls.getFireCarListByPage)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireCarLocAllListBean>(context) {
                        val function = { getFireCarListByPage(context,carType, carNum, isOnline, currentPage, listener) }
                        override fun onSuccess(response: Response<FireCarLocAllListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireCarLocAllListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
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

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<EnterPriseDataBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
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
                        val function = { getPathPlanByRoleId(context, RoleId,  listener) }
                        override fun onSuccess(response: Response<PathPlanByRoleIdBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<PathPlanByRoleIdBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 获取灾情列表
     * @param  currentPage    当前页
     * @param  status        处理状态，多个状态用逗号隔开
     * */
    fun getFireInfoListByPage(context: Context?,
                        status: String = "",
                        currentPage: Int = 1,
                        listener: (l: FireInfoListBean?) -> Unit) {
//       参数名	        参数含义	                    必选	类型及范围	说明
//       oid	        组织结构id	                    Y	    int
//       comId	        顶级单位ID	                    Y	    int
//       fetch_count	页大小	                        N	    Int
//       start_postion	当前页	                        N	    Int
//       status	        处理状态，多个状态用逗号隔开	N	    String	    1=未处理  2=出动  3=归队
        val params = HashMap<String, String>()
        params.put("oid", (MyApplication.user as UserBean).oid)
        params.put("comId",(MyApplication.user as UserBean).comid)
        params.put("status", status)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireInfoListBean>(Urls.getFireInfoListByPage)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireInfoListBean>(context) {
                        val function = { getFireInfoListByPage(context, status, currentPage, listener) }
                        override fun onSuccess(response: Response<FireInfoListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireInfoListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }

    /**
     * 根据id获取灾情详情
     * @param id        灾情id
     * */
    fun getFireInfoById(context: Context?,
                        id: String,
                            listener: (l: FireInfoListBean?) -> Unit) {
//      参数名	参数含义	必选	类型及范围	说明
//      id	灾情id	Y	int
        val params = HashMap<String, String>()
        params.put("id", id)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireInfoListBean>(Urls.getFireInfoById)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireInfoListBean>(context) {
                        val function = { getFireInfoById(context, id,  listener) }
                        override fun onSuccess(response: Response<FireInfoListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireInfoListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 获取组织机构列表
     * @param  oid	    组织机构id
     * @param  nameKey	组织机构名称关键字
     * @param  currentPage	当前页
     * */
    fun getOrgListByPage(context: Context?,
                        oid: String,
                        nameKey: String="",
                        currentPage: Int = 1,
                        listener: (l: OrgListByPageBean?) -> Unit) {
//       参数名	    参数含义	        必选	类型及范围	说明
//       oid	    组织机构id	        Y	    int	        1=消防机构  17=测试   30=环保保护部
//       namekey	组织机构名称关键字	N	    String
//       fetch_count	页大小	            N	    Int
//       start_postion	当前页	            N	    Int
        val params = HashMap<String, String>()
        params.put("oid", oid)
        params.put("namekey", nameKey)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<OrgListByPageBean>(Urls.getOrgListByPage)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<OrgListByPageBean>(context) {
                        val function = { getOrgListByPage(context, oid, nameKey,  currentPage, listener) }
                        override fun onSuccess(response: Response<OrgListByPageBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<OrgListByPageBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 获取消防大数据信息
     * @param flag		                Y	    int	        1.消防水源2.医院3.水务公司4.摄像机6.微型消防站
     * @param comId	    顶级单位列表	Y	    int
     * @param currentPage	当前页	        N	    Int
     * */
    fun getFireDataList(context: Context?,
                        flag: String,
                        comId: String,
                        currentPage: Int = 1,
                        listener: (l: FireDataListBean?) -> Unit) {
//       参数名	    参数含义	    必选	类型及范围	说明
//       flag		                Y	    int	        1.消防水源2.医院3.水务公司4.摄像机6.微型消防站
//       comid	    顶级单位列表	Y	    int
//       fetch_count	页大小	        N	    Int
//       start_postion	当前页	        N	    Int
        val params = HashMap<String, String>()
        params.put("flag", flag)
        params.put("comid", comId)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireDataListBean>(Urls.getFireDataList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireDataListBean>(context) {
                        val function = { getFireDataList(context, flag, comId,  currentPage, listener) }
                        override fun onSuccess(response: Response<FireDataListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireDataListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 根据id获取消防员信息
     * @param id        消防员id
     * */
    fun getFireManById(context: Context?,
                        id: String,
                        listener: (l: FireMainIdBean?) -> Unit) {
//      参数名	参数含义	必选	类型及范围	说明
//      Id	    消防员id	Y	    int
        val params = HashMap<String, String>()
        params.put("Id", id)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireMainIdBean>(Urls.getFireManById)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireMainIdBean>(context) {
                        val function = { getFireManById(context, id,  listener) }
                        override fun onSuccess(response: Response<FireMainIdBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireMainIdBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 根据id获取消防车信息
     * @param id        消防车id
     * */
    fun getFireCarById(context: Context?,
                        id: String,
                        listener: (l: FireCarBean?) -> Unit) {
//      参数名	参数含义	必选	类型及范围	说明
//      Id	    消防车id	Y	    int
        val params = HashMap<String, String>()
        params.put("Id", id)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireCarBean>(Urls.getFireCarById)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireCarBean>(context) {
                        val function = { getFireCarById(context, id,  listener) }
                        override fun onSuccess(response: Response<FireCarBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireCarBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 获取组织机构详情信息
     * @param id        组织机构id
     * */
    fun getOrgDetailById(context: Context?,
                        id: String,
                        listener: (l: OrganizationBean?) -> Unit) {
//      参数名	参数含义	必选	类型及范围	说明
//      oid	    组织机构id	Y	    Int
        val params = HashMap<String, String>()
        params.put("oid", id)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<OrganizationBean>(Urls.getOrgDetailById)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<OrganizationBean>(context) {
                        val function = { getOrgDetailById(context, id,  listener) }
                        override fun onSuccess(response: Response<OrganizationBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<OrganizationBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 获取消防大数据详情信息
     * @param id        组织机构id
     * */
    fun getFireDataById(context: Context?,
                        id: String,
                        listener: (l: FireDataListBean.FireDataBean?) -> Unit) {
//      参数名	参数含义	必选	类型及范围	说明
//      Id	    组织机构id	Y	    int
        val params = HashMap<String, String>()
        params.put("Id", id)
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FireDataListBean.FireDataBean>(Urls.getFireDataById)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FireDataListBean.FireDataBean>(context) {
                        val function = { getFireDataById(context, id,  listener) }
                        override fun onSuccess(response: Response<FireDataListBean.FireDataBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FireDataListBean.FireDataBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 获取下一级组织机构列表
     * @param oid	    组织机构id
     * @param currentPage	当前页
     * */
    fun findLowerLevel(context: Context?,
                       oid: String,
                        currentPage: Int = 1,
                        listener: (l: FindLowerLevelBean?) -> Unit) {
//      参数名	参数含义	必选	类型及范围	说明
//      oid	    组织机构id	Y	    Int
//      fetch_count页大小	    N	    Int
//      start_postion	当前页	    N	    Int
//
        val params = HashMap<String, String>()
        params.put("oid", oid)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<FindLowerLevelBean>(Urls.findLowerLevel)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<FindLowerLevelBean>(context) {
                        val function = { findLowerLevel(context,oid,currentPage, listener) }
                        override fun onSuccess(response: Response<FindLowerLevelBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<FindLowerLevelBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 获取消防知识库类别列表
     * @param nameKey	    类别名称查询关键字
     * @param currentPage	当前页
     * */
    fun getKnowledgeCategoryList(context: Context?,
                                 nameKey: String="",
                        currentPage: Int = 1,
                        listener: (l: KnowledgeCategoryListBean?) -> Unit) {
//      参数名	参数含义	        必选	类型及范围	说明
//      nameKey	类别名称查询关键字	N	    String
//      fetch_count页大小	            N	    Int
//      start_postion	当前页	            N	    Int
//
        val params = HashMap<String, String>()
        params.put("nameKey", nameKey)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<KnowledgeCategoryListBean>(Urls.getKnowledgeCategoryList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<KnowledgeCategoryListBean>(context) {
                        val function = { getKnowledgeCategoryList(context,nameKey,currentPage, listener) }
                        override fun onSuccess(response: Response<KnowledgeCategoryListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<KnowledgeCategoryListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
    /**
     * 获取消防知识库类别列表
     * @param id	类别id
     * @param currentPage	当前页
     * */
    fun getKnowledgeInfoList(context: Context?,
                             id: String="",
                        currentPage: Int = 1,
                        listener: (l: KnowledgeInfoListBean?) -> Unit) {
//      参数名	参数含义	必选	类型及范围	说明
//      id	类别id	Y	Int
//      start_postion	搜索数据的起始位置，不传设为0	N	Int
//      fetch_count	当前页，不传设为10	N	Int
//
        val params = HashMap<String, String>()
        params.put("id", id)
        params.put("fetch_count", pageSize.toString())
        params.put("start_postion", currentPage.toString())
        isSuccess(context, if (ToolApplication.changeKey == null) NetCode_NoKey else HttpCode_Success) {
            OkGo.post<KnowledgeInfoListBean>(Urls.getKnowledgeInfoList)
                    .params(changeFun(params))
                    .tag(this)
                    .execute(object : DialogCallback<KnowledgeInfoListBean>(context) {
                        val function = { getKnowledgeInfoList(context,id,currentPage, listener) }
                        override fun onSuccess(response: Response<KnowledgeInfoListBean>) {

                            if (isSuccess(context, response.body().getCode(), response.body().messError, function)) {
                                listener.invoke(response.body())
                            } else {
                                listener.invoke(null)
                            }
                        }

                        override fun onError(response: Response<KnowledgeInfoListBean>) {
                            if (response.code() == HttpCode_Error_Key)
                                isSuccess(context, NetCode_NoKey, "", function)
                            else
                                listener.invoke(null)
                        }

                    })
        }
    }
}