package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2018/1/10.
 */
class FireDataEntity: AbsNetBean {
    /**
     * id		            Y	int
     * name	            名称	Y	String
     * longitude            	经度	Y	Double
     * Latitude	            纬度	Y	Double
     * altitude	            高度	Y	Double
     * description	            描述	Y	String
     * city	                县市	Y	String
     * address	            地址	Y	String
     * artificial	            联系人	Y	String
     * liable	            责任人	Y	String
     * phone	            电话	Y	String
     * flag	                标志	Y	int	1.消防水源2.医院3.水务公司4.摄像机6.微型消防站
     * comid	            顶级单位id		int
     * firedatacol	            flag=6微型消防站消防人员数量	Y	String
     */
    override  var result=""
    override var messError=""
    var id		     : Int = 0
    var name	             : String? = null
    var longitude         : Double = 0.toDouble()
    var latitude	         : Double = 0.toDouble()
    var altitude	         : Double = 0.toDouble()
    var description	     : String? = null
    var city	             : String? = null
    var address	         : String? = null
    var artificial	         : String? = null
    var liable	         : String? = null
    var phone	         : String? = null
    var flag	             : Int = 0
    var comid	         : String? = null
    var firedatacol	         : String? = null
}
