package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class EnterPriseBean : AbsNetBean{
    /**
     * result : 0
     * Id : 37
     * RoleId : 16
     * Name : 罗喉寺
     * Longitude : 113.597677
     * Latitude : 39.008648
     * Altitude : 0
     * Type : 人员密集类
     * Level : 一级重点单位
     * Address : 五台山风景区
     * LegalAgent : 李四(13705576654)
     * Artificial : 包居日木图(13856545421)
     * Manager : 李岗(13705576654)
     * Liable : 冯宝林、刘川、李斌
     * FirePump : 4个,15L/S,60米
     * Hydrant : 20个
     * WaterTank : 2000立方米*2
     * FireHose : 20米*100根
     * FoamReserve : 11吨
     * FoamType : 二氧化碳
     * FireWaterType : 地下深井
     * FFES : 液上喷射
     * OilTankType : 固定顶+内浮顶
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var id: Int = 0
    var roleId: Int = 0
    var name: String? = null
    var longitude: Double = 0.toDouble()
    var latitude: Double = 0.toDouble()
    var altitude: Int = 0
    var type: String? = null
    var level: String? = null
    var address: String? = null
    var legalAgent: String? = null
    var artificial: String? = null
    var manager: String? = null
    var liable: String? = null
    var firePump: String? = null
    var hydrant: String? = null
    var waterTank: String? = null
    var fireHose: String? = null
    var foamReserve: String? = null
    var foamType: String? = null
    var fireWaterType: String? = null
    var ffes: String? = null
    var oilTankType: String? = null
}
