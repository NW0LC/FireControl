package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/19.
 */

class DrawFileListBean :AbsNetBean{
    /**
     * result : 0
     * DrawingFiles : [{"Id":1,"Name":"工艺流程图","EnterpriseName":"中国石化宿州分公司","Path":"http://139.129.23.185:9866/upload/drawingFile/gongyiliuchengtu.jpg","EnterpriseId":1,"comid":1},{"Id":2,"Name":"接地点分布图","EnterpriseName":"中国石化宿州分公司","Path":"http://139.129.23.185:9866/upload/drawingFile/jiedidianfenbutu.jpg","EnterpriseId":1,"comid":1},{"Id":3,"Name":"配电线路图","EnterpriseName":"中国石化宿州分公司","Path":"http://139.129.23.185:9866/upload/drawingFile/peidianxianlutu.jpg","EnterpriseId":1,"comid":1},{"Id":5,"Name":"消防器材分布图","EnterpriseName":"中国石化宿州分公司","Path":"http://139.129.23.185:9866/upload/drawingFile/xiaofangqicaifenbutu.jpg","EnterpriseId":1,"comid":1},{"Id":6,"Name":"巡检路线路图","EnterpriseName":"中国石化宿州分公司","Path":"http://139.129.23.185:9866/upload/drawingFile/xunjianluxiantu.jpg","EnterpriseId":1,"comid":1},{"Id":7,"Name":"中石化安徽宿州油库灭火作战方案图","EnterpriseName":"中国石化宿州分公司","Path":"http://139.129.23.185:9866/upload/drawingFile/youkumiehuozuozhanfangantu.jpg","EnterpriseId":1,"comid":1},{"Id":8,"Name":"总平面图","EnterpriseName":"中国石化宿州分公司","Path":"http://139.129.23.185:9866/upload/drawingFile/zongpingmiantu.jpg","EnterpriseId":1,"comid":1}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var DrawingFiles: List<DrawingFilesBean>? = null

    open class DrawingFilesBean {
        /**
         * Id : 1
         * Name : 工艺流程图
         * EnterpriseName : 中国石化宿州分公司
         * Path : http://139.129.23.185:9866/upload/drawingFile/gongyiliuchengtu.jpg
         * EnterpriseId : 1
         * comid : 1
         */

        var Id: Int = 0
        var Name: String? = null
        var EnterpriseName: String? = null
        var Path: String? = null
        var EnterpriseId: Int = 0
        var comid: Int = 0
    }
}
