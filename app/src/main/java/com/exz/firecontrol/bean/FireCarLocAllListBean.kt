package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class FireCarLocAllListBean:AbsNetBean {
    /**
     * result : 0
     * carCount : 1
     * FireCarLocs : [{"RoleId":16,"RoleName":"五台山大队","id":7,"deviceCode":"B97F11847A955308","carNum":"WDAYHCAA3G0084283","GPSDate":"","carspeed":"0.000000","speed":"0.000000","carLevel":"-125","oilmass":"0.000000","voltage":"28.100000","volume":"0.000000","engineTemp":"0.000000","enginePRESS":"0.000000","coolantTemp":"0.000000","Temp":"0.000000","lon":113.593252,"lat":39.008648,"height":0,"angle":"0.000000","IsOnline":1,"bVolume":"0.0","airPRESS":"0.0","waterTemp":"0.0","totalKM":"0.0","overOilmass":"0.0","pumpSpeed":"0.0","pumpOutPressure":"0.0","pumpInPressure":"0.0","waterLevel":"0.0","PTO":"1","pumpWorkTime":"0.0","waterGun":"0","sprayGun":"0","foamGun":"0","foamSpanner":"0","catchMent":"0","waterTrap":"0","spanner":"0","rubberHammer":"0","upSpanner":"0","belowSpanner":"0","fireLadder":"0","diffPort":"0","protectBridge":"0","hoseBandage":"0","hoseHook":"0","fireAx":"0","lights":"0","SCBA":"0","handPump":"0","suctionPipe":"0","waterFilter":"0","videoPath":"","oilPressure":"0.0","inCar":"1","aVolume":"0.0","carType":2}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var carCount: Int = 0
    var fireCarLocs: List<FireCarLocBean>? = null

    class FireCarLocBean {
        /**
         * RoleId : 16
         * RoleName : 五台山大队
         * id : 7
         * deviceCode : B97F11847A955308
         * carNum : WDAYHCAA3G0084283
         * GPSDate :
         * carspeed : 0.000000
         * speed : 0.000000
         * carLevel : -125
         * oilmass : 0.000000
         * voltage : 28.100000
         * volume : 0.000000
         * engineTemp : 0.000000
         * enginePRESS : 0.000000
         * coolantTemp : 0.000000
         * Temp : 0.000000
         * lon : 113.593252
         * lat : 39.008648
         * height : 0
         * angle : 0.000000
         * IsOnline : 1
         * bVolume : 0.0
         * airPRESS : 0.0
         * waterTemp : 0.0
         * totalKM : 0.0
         * overOilmass : 0.0
         * pumpSpeed : 0.0
         * pumpOutPressure : 0.0
         * pumpInPressure : 0.0
         * waterLevel : 0.0
         * PTO : 1
         * pumpWorkTime : 0.0
         * waterGun : 0
         * sprayGun : 0
         * foamGun : 0
         * foamSpanner : 0
         * catchMent : 0
         * waterTrap : 0
         * spanner : 0
         * rubberHammer : 0
         * upSpanner : 0
         * belowSpanner : 0
         * fireLadder : 0
         * diffPort : 0
         * protectBridge : 0
         * hoseBandage : 0
         * hoseHook : 0
         * fireAx : 0
         * lights : 0
         * SCBA : 0
         * handPump : 0
         * suctionPipe : 0
         * waterFilter : 0
         * videoPath :
         * oilPressure : 0.0
         * inCar : 1
         * aVolume : 0.0
         * carType : 2
         */

        var roleId: Int = 0
        var roleName: String? = null
        var id: Int = 0
        var deviceCode: String? = null
        var carNum: String? = null
        var gpsDate: String? = null
        var carspeed: String? = null
        var speed: String? = null
        var carLevel: String? = null
        var oilmass: String? = null
        var voltage: String? = null
        var volume: String? = null
        var engineTemp: String? = null
        var enginePRESS: String? = null
        var coolantTemp: String? = null
        var temp: String? = null
        var lon: Double = 0.toDouble()
        var lat: Double = 0.toDouble()
        var height: Int = 0
        var angle: String? = null
        var isOnline: Int = 0
        var bVolume: String? = null
        var airPRESS: String? = null
        var waterTemp: String? = null
        var totalKM: String? = null
        var overOilmass: String? = null
        var pumpSpeed: String? = null
        var pumpOutPressure: String? = null
        var pumpInPressure: String? = null
        var waterLevel: String? = null
        var pto: String? = null
        var pumpWorkTime: String? = null
        var waterGun: String? = null
        var sprayGun: String? = null
        var foamGun: String? = null
        var foamSpanner: String? = null
        var catchMent: String? = null
        var waterTrap: String? = null
        var spanner: String? = null
        var rubberHammer: String? = null
        var upSpanner: String? = null
        var belowSpanner: String? = null
        var fireLadder: String? = null
        var diffPort: String? = null
        var protectBridge: String? = null
        var hoseBandage: String? = null
        var hoseHook: String? = null
        var fireAx: String? = null
        var lights: String? = null
        var SCBA: String? = null
        var handPump: String? = null
        var suctionPipe: String? = null
        var waterFilter: String? = null
        var videoPath: String? = null
        var oilPressure: String? = null
        var inCar: String? = null
        var aVolume: String? = null
        var carType: Int = 0
    }
}
