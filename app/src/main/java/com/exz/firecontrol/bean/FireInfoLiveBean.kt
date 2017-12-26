package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/26.
 */

class FireInfoLiveBean : AbsNetBean {
    /**
     * messError : 操作成功
     * liveList : [{"id":7,"fiId":1,"livePath":"rtmp://139.129.23.185:1935/live/fire_20171225163813","addTime":"2017-12-25 16:38:13"},{"id":6,"fiId":1,"livePath":"rtmp://139.129.23.185:1935/live/fire_Mon Dec 25 16:35:10 CST 2017","addTime":"2017-12-25 16:35:19"},{"id":5,"fiId":1,"livePath":"rtmp://139.129.23.185:1935/live/fire_Mon Dec 25 16:34:52 CST 2017","addTime":"2017-12-25 16:34:52"}]
     */

    override  var result=""
    override var messError=""
    var liveList: List<LiveListBean>? = null

    open class LiveListBean {
        /**
         * id : 7
         * fiId : 1
         * livePath : rtmp://139.129.23.185:1935/live/fire_20171225163813
         * addTime : 2017-12-25 16:38:13
         */

        var id: Int = 0
        var fiId: Int = 0
        var livePath: String? = null
        var addTime: String? = null
    }
}
