package com.exz.firecontrol.bean

import com.szw.framelibrary.utils.net.AbsNetBean

/**
 * Created by 史忠文
 * on 2017/12/20.
 */

class KnowledgeCategoryListBean: AbsNetBean {
    /**
     * result : 0
     * categoryList : [{"Id":1,"categoryName":"类别一"}]
     * messError : 操作成功
     */

    override  var result=""
    override var messError=""
    var categoryList: List<CategoryListBean>? = null

    class CategoryListBean {
        /**
         * Id : 1
         * categoryName : 类别一
         */

        var id: Int = 0
        var categoryName: String? = null
    }
}
