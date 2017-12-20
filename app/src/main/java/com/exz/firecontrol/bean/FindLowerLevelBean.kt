package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/20.
 */

class FindLowerLevelBean : AbsNetBean {
    /**
     * result : 0
     * lowerOrgList : [{"id":5,"cname":"五台山消防中队","addr":"五台山","telePhone":"00000000000","lon":113.5743472,"lat":38.9862497,"placeName":"台怀镇","fid":4,"levels":4,"fireManCount":32}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var lowerOrgList: List<OrganizationBean>? = null

}
