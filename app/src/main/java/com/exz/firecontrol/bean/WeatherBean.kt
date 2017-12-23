package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/23.
 */

class WeatherBean : AbsNetBean {
    /**
     * result : 0
     * humidity : 35%
     * temperature : 6
     * windDirection : 南风
     * windForce : <3级
     * type : 多云
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var humidity: String? = null
    var temperature: String? = null
    var windDirection: String? = null
    var windForce: String? = null
    var type: String? = null
}
