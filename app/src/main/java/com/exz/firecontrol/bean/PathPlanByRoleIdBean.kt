package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class PathPlanByRoleIdBean : AbsNetBean {
    /**
     * result : 0
     * pathPlan : [{"id":2,"roleId":1,"name":"1","inputPath":"1","outPath":"1"},{"id":1,"roleId":1,"name":"1","inputPath":"1","outPath":"1"}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var pathPlan: List<PathPlanBean>? = null

    class PathPlanBean {
        /**
         * id : 2
         * roleId : 1
         * name : 1
         * inputPath : 1
         * outPath : 1
         */

        var id: Int = 0
        var roleId: Int = 0
        var name: String? = null
        var inputPath: String? = null
        var outPath: String? = null
    }
}
