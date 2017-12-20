package com.exz.firecontrol.bean

/**
 * Created by 史忠文
 * on 2017/12/20.
 */
open class OrganizationBean{
    var id: Int = 0
    var cname: String? = null
    var addr: String? = null
    var telePhone: String? = null
    var lon: Double = 0.toDouble()
    var lat: Double = 0.toDouble()
    var placeName: String? = null
    var fid: Int = 0
    var levels: Int = 0
    var fireManCount: Int = 0
    var carInfo: List<OrgListByPageBean.CarInfoBean>? = null
}