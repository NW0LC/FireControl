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
        v.tv_title.text=item.cname
        v.tv_location.text=item.addr
        v.tv_people.text=String.format(mContext.getString(R.string.people),item.fireManCount)
        when (item.levels) {
            0 -> {//总局
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_zong)
                v.tv_more.text="查看省总队"
            }
            1 -> {//1省总队
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_zong    )
                v.tv_more.text="查看各市支队"
            }
            2 -> {//各市支队
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_zhi)
                v.tv_more.text="查看市辖区大队"
            }
            3 -> {//市辖区大队
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_big)
                v.tv_more.text="查看各中队"
            }
            4 -> {//中队
                v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_mid)
                v.tv_more.visibility= View.GONE
            }
        }
        helper.addOnClickListener(R.id.tv_see_details)
        helper.addOnClickListener(R.id.tv_more)
    }
}
