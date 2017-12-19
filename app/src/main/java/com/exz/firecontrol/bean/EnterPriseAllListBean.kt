package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class EnterPriseAllListBean :AbsNetBean {
    /**
     * result : 0
     * EnterpriseInfos : [{"Id":14,"Name":"天津油库","Type":"石油化工类","Level":"一级重点单位","Longitude":117.37856,"Latitude":38.82623,"Altitude":0,"RoleId":21,"ZipFileName":"tjykData.zip","cityName":"天津市"}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""

    var enterpriseInfos: List<EnterpriseInfoBean>? = null

    class EnterpriseInfoBean {
        /**
         * Id : 14
         * Name : 天津油库
         * Type : 石油化工类
         * Level : 一级重点单位
         * Longitude : 117.37856
         * Latitude : 38.82623
         * Altitude : 0
         * RoleId : 21
         * ZipFileName : tjykData.zip
         * cityName : 天津市
         */

        var id: Int = 0
        var name: String? = null
        var type: String? = null
        var level: String? = null
        var longitude: Double = 0.toDouble()
        var latitude: Double = 0.toDouble()
        var altitude: Int = 0
        var roleId: Int = 0
        var zipFileName: String? = null
        var cityName: String? = null
    }
}
