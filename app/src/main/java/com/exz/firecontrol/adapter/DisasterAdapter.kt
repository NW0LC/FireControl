package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireInfoListBean

/**
 * Created by pc on 2017/12/18.
 */

class DisasterAdapter<T: FireInfoListBean.FireInfoBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_disaster, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {

    }
}
