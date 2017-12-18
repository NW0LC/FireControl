package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by pc on 2017/12/18.
 */

class LoginBean : AbsNetBean {

    /**
     * result : 0
     * uid : 1
     * LoginId : hhadmin
     * RoleId : 4
     * RoleName : 管理员
     * Pid : -1
     * messError : 成功
     */

    var uid: String=""
    var loginId: String=""
    var roleId: String=""
    var roleName: String=""
    var isOnline: String=""
    var pid: String=""
    override var messError: String=""
    override var result=""

}
