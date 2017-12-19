package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class OrgListByOidBean : AbsNetBean {
    /**
     * result : 0
     * OrganizationList : [{"id":1,"cname":"消防总局","addr":"北京","telePhone":"00000000000","lon":117,"lat":34,"placeName":"北京市","fid":0,"levels":0},{"id":2,"cname":"山西省消防总队","addr":"太原","telePhone":"00000000000","lon":116.94233,"lat":33.65309,"placeName":"山西省","fid":1,"levels":1},{"id":6,"cname":"安徽省消防总队","addr":"合肥","telePhone":"00000000000","fid":1,"levels":1},{"id":13,"cname":"天津总队","fid":1,"levels":1},{"id":14,"cname":"广东省消防总队","fid":1,"levels":1},{"id":16,"cname":"浙江省消防总队","fid":1,"levels":1},{"id":19,"cname":"江苏省消防总队","fid":1,"levels":1},{"id":3,"cname":"忻州市消防支队","addr":"忻州","telePhone":"18260053246","lon":117,"lat":34,"placeName":"忻州市","fid":2,"levels":2},{"id":7,"cname":"宿州市消防支队","addr":"宿州","fid":6,"levels":2},{"id":15,"cname":"广州市消防支队","fid":14,"levels":2},{"id":18,"cname":"太原市消防支队","fid":2,"levels":2},{"id":20,"cname":"徐州支队","fid":19,"levels":2},{"id":4,"cname":"五台山消防大队","addr":"五台山","telePhone":"18260053246","lon":117,"lat":34,"placeName":"五台县","fid":3,"levels":3},{"id":8,"cname":"埇桥区消防大队","fid":7,"levels":3},{"id":9,"cname":"开发区中队","fid":7,"levels":3},{"id":10,"cname":"砀山大队","fid":7,"levels":3},{"id":11,"cname":"灵璧大队","fid":7,"levels":3},{"id":12,"cname":"萧县大队","fid":7,"levels":3},{"id":5,"cname":"五台山消防中队","addr":"五台山","telePhone":"00000000000","lon":0,"lat":0,"placeName":"台怀镇","fid":4,"levels":4}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var organizationList: List<OrganizationListBean>? = null

    class OrganizationListBean {
        /**
         * id : 1
         * cname : 消防总局
         * addr : 北京
         * telePhone : 00000000000
         * lon : 117
         * lat : 34
         * placeName : 北京市
         * fid : 0
         * levels : 0
         */

        var id: Int = 0
        var cname: String? = null
        var addr: String? = null
        var telePhone: String? = null
        var lon: Int = 0
        var lat: Int = 0
        var placeName: String? = null
        var fid: Int = 0
        var levels: Int = 0
    }
}
