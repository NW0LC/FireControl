package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/26.
 */

class AdvisePlanBean: AbsNetBean {
    /**
     * messError : 操作成功
     * advisePlan : {"id":1,"flag":1,"advise":"建议用干粉灭火器救火"}
     */

    override  var result=""
    override var messError=""
    var advisePlan: AdvisePlanBean? = null

    class AdvisePlanBean {
        /**
         * id : 1
         * flag : 1
         * advise : 建议用干粉灭火器救火
         */

        var id: Int = 0
        var flag: Int = 0
        var advise: String? = null
    }
}
