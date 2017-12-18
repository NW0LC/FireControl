package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/18.
 */

class ChangeKeyBean(override var messError: String, override var result: String) :AbsNetBean{
    var key=""
    var token=""
    var rc4Key=""
}
