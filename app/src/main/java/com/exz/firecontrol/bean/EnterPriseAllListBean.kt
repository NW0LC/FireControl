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

    var EnterpriseInfos: List<EnterpriseInfoBean>? = null

    open class EnterpriseInfoBean {
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

        var Id=""
        var Name: String? = null
        var Type: String? = null
        var Level: String? = null
        var Longitude: Double = 0.toDouble()
        var Latitude: Double = 0.toDouble()
        var Altitude: Int = 0
        var RoleId: Int = 0
        var ZipFileName: String? = null
        var cityName: String? = null
    }
}
