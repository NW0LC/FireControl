package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireInfoListBean
import kotlinx.android.synthetic.main.item_disaster.view.*
import org.jetbrains.anko.imageResource


class DisasterAdapter<T: FireInfoListBean.FireInfoBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_disaster, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        val itemView = helper.itemView
        itemView.tv_title.text=item.name
        itemView.tv_subTitle.text=String.format(mContext.getString(R.string.main_disaster_create_time),item.createDate)
        itemView.iv_state.imageResource= when (item.status) {
            1 -> R.mipmap.icon_disaster_state1
            2 -> R.mipmap.icon_disaster_state2
            3 -> R.mipmap.icon_disaster_state3
            else -> {
                R.mipmap.icon_disaster_state1
            }
        }
    }
}
