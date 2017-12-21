package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireInfoListBean


class DisasterAdapter<T: FireInfoListBean.FireInfoBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_disaster, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        val itemView = helper.itemView
//        itemView.tv_title.text=item.name
//        itemView.tv_subTitle.text=String.format(mContext.getString(R.string.main_disaster_create_time),item.createDate)
    }
}
