package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class FireDataListBean: AbsNetBean {
    /**
     * result : 0
     * FireDatas : [{"Id":59,"Name":"路况1","Longitude":113.58493042,"Latitude":38.99285507,"Altitude":0,"Description":"rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov","flag":4},{"Id":60,"Name":"路况2","Longitude":113.58105469,"Latitude":38.99053955,"Altitude":0,"Description":"rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov","flag":4},{"Id":109,"Name":"zlltest","Longitude":117,"Latitude":38,"Altitude":100,"Description":"rtmp://139.129.23.185:1935/live/livestream","flag":4},{"Id":110,"Name":"zlltest_1","Longitude":117,"Latitude":38.1,"Altitude":100,"Description":"rtmp://139.129.23.185:1935/live/livestream","flag":4},{"Id":111,"Name":"zlltest_2","Longitude":117,"Latitude":38.2,"Altitude":100,"Description":"rtmp://139.129.23.185:1935/live/livestream","flag":4},{"Id":112,"Name":"zlltest_3","Longitude":117,"Latitude":38.21,"Altitude":100,"Description":"rtmp://139.129.23.185:1935/live/livestream","flag":4},{"Id":113,"Name":"zlltest_4","Longitude":117,"Latitude":38.211,"Altitude":100,"Description":"rtmp://139.129.23.185:1935/live/livestream","flag":4}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var fireDatas: List<FireDataBean>? = null

    class FireDataBean : AbsNetBean {
        /**
         * Id		            Y	int
         * Name	            名称	Y	String
         * Longitude            	经度	Y	Double
         * Latitude	            纬度	Y	Double
         * Altitude	            高度	Y	Double
         * Description	            描述	Y	String
         * city	                县市	Y	String
         * Address	            地址	Y	String
         * Artificial	            联系人	Y	String
         * Liable	            责任人	Y	String
         * phone	            电话	Y	String
         * flag	                标志	Y	int	1.消防水源2.医院3.水务公司4.摄像机6.微型消防站
         * comid	            顶级单位id		int
         * firedatacol	            flag=6微型消防站消防人员数量	Y	String
         */
        override  var result=""
        override var messError=""
        var Id		     : Int = 0
        var Name	             : String? = null
        var Longitude         : Double = 0.toDouble()
        var Latitude	         : Double = 0.toDouble()
        var Altitude	         : Double = 0.toDouble()
        var Description	     : String? = null
        var city	             : String? = null
        var Address	         : String? = null
        var Artificial	         : String? = null
        var Liable	         : String? = null
        var phone	         : String? = null
        var flag	             : Int = 0
        var comid	         : String? = null
    }
}
