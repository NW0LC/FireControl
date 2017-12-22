package com.exz.firecontrol.bean

import com.exz.firecontrol.adapter.FireCarLocBean
import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class FireCarLocAllListBean : AbsNetBean {
    /**
     * result : 0
     * carCount : 1
     * FireCarLocs : [{"RoleId":16,"RoleName":"五台山大队","id":7,"deviceCode":"B97F11847A955308","carNum":"WDAYHCAA3G0084283","GPSDate":"","carspeed":"0.000000","speed":"0.000000","carLevel":"-125","oilmass":"0.000000","voltage":"28.100000","volume":"0.000000","engineTemp":"0.000000","enginePRESS":"0.000000","coolantTemp":"0.000000","Temp":"0.000000","lon":113.593252,"lat":39.008648,"height":0,"angle":"0.000000","IsOnline":1,"bVolume":"0.0","airPRESS":"0.0","waterTemp":"0.0","totalKM":"0.0","overOilmass":"0.0","pumpSpeed":"0.0","pumpOutPressure":"0.0","pumpInPressure":"0.0","waterLevel":"0.0","PTO":"1","pumpWorkTime":"0.0","waterGun":"0","sprayGun":"0","foamGun":"0","foamSpanner":"0","catchMent":"0","waterTrap":"0","spanner":"0","rubberHammer":"0","upSpanner":"0","belowSpanner":"0","fireLadder":"0","diffPort":"0","protectBridge":"0","hoseBandage":"0","hoseHook":"0","fireAx":"0","lights":"0","SCBA":"0","handPump":"0","suctionPipe":"0","waterFilter":"0","videoPath":"","oilPressure":"0.0","inCar":"1","aVolume":"0.0","carType":2}]
     * messError : 操作成功
     */

    override var result = ""
    override var messError = ""
    var carCount: Int = 0
    var FireCarLocs: List<FireCarLocBean>? = null

}
