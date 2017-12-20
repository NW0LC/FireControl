package com.exz.firecontrol.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.OrganizationBean
import kotlinx.android.synthetic.main.item_firefighting.view.*
import kotlinx.android.synthetic.main.lay_firefighting.view.*


class FirefightingAdapter<T: OrganizationBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_firefighting, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item:T) {
        val v = helper.itemView
        when (item.levels) {
            1 -> {//微型消防站
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_stand)
                v.tv_more.visibility=View.GONE
            }
            2 -> {//大队
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_big)
                v.tv_more.visibility=View.VISIBLE
            }
            3 -> {//中队
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_mid)
                v.tv_more.visibility=View.GONE
            }
        }
        helper.addOnClickListener(R.id.tv_see_details)
        helper.addOnClickListener(R.id.tv_more)
    }
}
