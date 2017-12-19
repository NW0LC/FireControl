package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class FireMainIdBean : AbsNetBean {
    /**
     * result : 0
     * FireManInfo : {"id":33,"roleId":27,"name":"李炳杰","pcnumber":"13546776303","oid":5,"comid":1,"userHead":"http://139.129.23.185:9866/upload/fireManHead/liBinJie.png"}
     * messError : 操作成功
     */
    override  var result=""
    override var messError=""
    var fireManInfo: FireMainLocAllListBean.FireManLocBean? = null

}
