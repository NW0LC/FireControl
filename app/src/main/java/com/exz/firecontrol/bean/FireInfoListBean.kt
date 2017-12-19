package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class FireInfoListBean : AbsNetBean {
    /**
     * result : 0
     * fireInfoList : [{"id":11,"name":"徐州市公安总局3","status":4,"fightId":4,"fightname":"铜山中队5","createDate":"2017-11-29 08:32:47","startDate":"2017-11-16 14:00:01","endDate":"2017-11-24 14:00:04","enterpriseId":14,"telePhone":"18260053244","flag":1,"psnNum":5,"comid":1,"cityName":"天津市"},{"id":2,"name":"宿州消防支队","status":2,"fightId":2,"fightname":"铜山中队2","createDate":"2017-11-22 08:32:47","startDate":"2017-11-16 13:58:49","endDate":"2017-11-17 13:58:52","enterpriseId":14,"telePhone":"18260053244","flag":2,"psnNum":2,"comid":1,"cityName":"天津市"}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    private val fireInfoList: List<FireInfoBean>? = null


    class FireInfoBean {
        /**
         * id : 11
         * name : 徐州市公安总局3
         * status : 4
         * fightId : 4
         * fightname : 铜山中队5
         * createDate : 2017-11-29 08:32:47
         * startDate : 2017-11-16 14:00:01
         * endDate : 2017-11-24 14:00:04
         * enterpriseId : 14
         * telePhone : 18260053244
         * flag : 1
         * psnNum : 5
         * comid : 1
         * cityName : 天津市
         */

        var id: Int = 0
        var name: String? = null
        var status: Int = 0
        var fightId: Int = 0
        var fightname: String? = null
        var createDate: String? = null
        var startDate: String? = null
        var endDate: String? = null
        var enterpriseId: Int = 0
        var telePhone: String? = null
        var flag: Int = 0
        var psnNum: Int = 0
        var comid: Int = 0
        var cityName: String? = null
    }
}
