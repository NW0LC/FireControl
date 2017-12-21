package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/21.
 */

class KnowledgeInfoListBean : AbsNetBean {
    /**
     * result : 0
     * knowledgeList : [{"Id":3,"title":"消防知识主题3","content":"内容3"}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var knowledgeList: List<KnowledgeListBean>? = null

    open class KnowledgeListBean {
        /**
         * id : 3
         * title : 消防知识主题3
         * content : 内容3
         */

        var id: Int = 0
        var title: String? = null
        var content: String? = null
    }
}
