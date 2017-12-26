package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/26.
 */

class LivePathBean :AbsNetBean{
    /**
     * messError : 操作成功
     * livePath : [{"id":7,"livePath":"rtmp://139.129.23.185:1935/live/fire_20171225163813"}]
     */

    override  var result=""
    override var messError=""
    var livePath: List<FireInfoLiveBean.LiveListBean>? = null

}
