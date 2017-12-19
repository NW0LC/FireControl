package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean
class LoginBean : AbsNetBean {

    /**
     * 参数名	参数含义	必选	类型及范围	说明
       result	结果码	Y	unsigned integer	0成功
       uid	用户id	Y	unsigned integer	成功时返回
       comid	顶级单位id	Y	int
       oid	组织机构id	Y	int
       LoginId	用户名	Y	String
       RoleId	角色id	Y	Int
       RoleName	角色名称	Y	String
       Pid	父角色id	Y	Int
       IsOnline	是否在线	Y	Int
       phone	手机号码	Y	String
       messError	错误消息	Y	String
     */

    var uid: String=""
    var comid: String=""
    var oid: String=""
    var loginId: String=""
    var roleId: String=""
    var roleName: String=""
    var isOnline: String=""
    var phone: String=""
    var pid: String=""
    override var messError: String=""
    override var result=""

}
