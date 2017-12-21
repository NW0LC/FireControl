package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class FireMainLocAllListBean :AbsNetBean{
    /**
     * result : 0
     * fireManCount : 3
     * FireManLocs : [{"RoleId":27,"RoleName":"五台山中队","id":33,"deviceCode":"a00000595af3a0","psnCode":"","GPSDate":"2017-07-13 09:22:52","direction":"209.000000","speed":"0.000000","distance":"-1","Temp":"0","falldwon":"255","staticflag":"255","flag":"255","Battery":"85.000000","lon":117.141451,"lat":34.205883,"height":0,"IsOnline":1,"Name":"李炳杰","PCNumber":"13546776303","SSLOCID":12,"userHead":"http://139.129.23.185:9866/upload/fireManHead/liBinJie.png","comid":1},{"RoleId":27,"RoleName":"五台山中队","id":34,"deviceCode":"355457087915701","psnCode":"","GPSDate":"2017-07-17 14:13:01","direction":"0.000000","speed":"0.000000","distance":"-1","Temp":"0","falldwon":"255","staticflag":"255","flag":"255","Battery":"23.000000","lon":113.599952,"lat":39.008548,"height":0,"IsOnline":1,"Name":"马权瑞","PCNumber":"15110523119","SSLOCID":13,"userHead":"http://139.129.23.185:9866/upload/fireManHead/maQuanRui.png","comid":1},{"RoleId":27,"RoleName":"五台山中队","id":36,"deviceCode":"865586025870801","psnCode":"","GPSDate":"2017-07-13 09:24:42","direction":"167.000000","speed":"0.000000","distance":"-1","Temp":"0","falldwon":"255","staticflag":"255","flag":"255","Battery":"63.000000","lon":117.280034,"lat":34.297684,"height":0,"IsOnline":1,"Name":"王宁","PCNumber":"18735342119","SSLOCID":15,"userHead":"http://139.129.23.185:9866/upload/fireManHead/wangNing.png","comid":1}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var fireManCount: Int = 0
    var fireManLocs: List<FireManLocBean>? = null

    class FireManLocBean {
        /**
         * RoleId : 27
         * RoleName : 五台山中队
         * id : 33
         * deviceCode : a00000595af3a0
         * psnCode :
         * GPSDate : 2017-07-13 09:22:52
         * direction : 209.000000
         * speed : 0.000000
         * distance : -1
         * Temp : 0
         * falldwon : 255
         * staticflag : 255
         * flag : 255
         * Battery : 85.000000
         * lon : 117.141451
         * lat : 34.205883
         * height : 0
         * IsOnline : 1
         * Name : 李炳杰
         * PCNumber : 13546776303
         * SSLOCID : 12
         * userHead : http://139.129.23.185:9866/upload/fireManHead/liBinJie.png
         * comid : 1
         */

        var roleId: Int = 0
        var roleName: String? = null
        var id: Int = 0
        var deviceCode: String? = null
        var psnCode: String? = null
        var GPSDate: String? = null
        var direction: String? = null
        var speed: String? = null
        var distance: String? = null
        var Temp: String? = null
        var falldwon: String? = null
        var staticflag: String? = null
        var flag: String? = null
        var Battery: String? = null
        var lon: Double = 0.toDouble()
        var lat: Double = 0.toDouble()
        var height: Int = 0
        var IsOnline: Int = 0
        var Name: String? = null
        var PCNumber: String? = null
        var SSLOCID: Int = 0
        var userHead: String? = null
        var comid: Int = 0
    }
}
