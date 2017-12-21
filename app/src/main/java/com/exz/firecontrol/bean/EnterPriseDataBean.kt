package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class EnterPriseDataBean : AbsNetBean {
    /**
     * result : 0
     * enterpriseData : [{"id":6,"name":"1","longitude":1,"latitude":1,"altitude":1,"flag":1,"description":"1","enterpriseId":1}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var enterpriseData: List<EnterpriseDataBean>? = null

    open class EnterpriseDataBean {
        /**
         * id : 6
         * name : 1
         * longitude : 1
         * latitude : 1
         * altitude : 1
         * flag : 1
         * description : 1
         * enterpriseId : 1
         */

        var id: Int = 0
        var name: String? = null
        var longitude: Double = 0.toDouble()
        var latitude: Double = 0.toDouble()
        var altitude: Double = 0.toDouble()
        var flag: Int = 0
        var description: String? = null
        var enterpriseId: Int = 0
    }
}
