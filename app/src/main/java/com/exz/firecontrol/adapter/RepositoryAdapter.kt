package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.RepositoryBean

/**
 * Created by pc on 2017/12/20.
 */

class RepositoryAdapter : BaseQuickAdapter<RepositoryBean, BaseViewHolder>(R.layout.item_repository, null) {

    override fun convert(helper: BaseViewHolder, item: RepositoryBean) {
        var v = helper.itemView
    }
}
