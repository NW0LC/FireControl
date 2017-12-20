package com.exz.firecontrol.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.StairBean
import kotlinx.android.synthetic.main.item_stair.view.*
import org.jetbrains.anko.textColor

/**
 * Created by pc on 2017/12/20.
 */

class StairLisAdapter() : BaseQuickAdapter<StairBean, BaseViewHolder>(R.layout.item_stair, null) {

    override fun convert(helper: BaseViewHolder, item: StairBean) {
        var v = helper.itemView
        v.tv_name.text =item.name
        if(item.check){
            v.tv_name.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,ContextCompat.getDrawable(mContext,R.mipmap.icon_arrow_check),null)
            v.tv_name.textColor=ContextCompat.getColor(mContext,R.color.colorPrimary)
        }else{
            v.tv_name.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null,null)
            v.tv_name.textColor=ContextCompat.getColor(mContext,R.color.MaterialGrey600)
        }
    }
}
