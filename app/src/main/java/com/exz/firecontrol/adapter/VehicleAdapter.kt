package com.exz.firecontrol.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import kotlinx.android.synthetic.main.item_veicle.view.*


class VehicleAdapter<T : FireCarLocBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_veicle, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        val itemView = helper.itemView
        itemView.tv_title.text = getCarTyeStr(item.carType)
        itemView.tv_num.text = item.carNum
        itemView.tv_unit.text = item.RoleName
        itemView.tv_is_online.delegate.backgroundColor = ContextCompat.getColor(mContext, if (item.IsOnline == "1") R.color.MaterialGreenA400 else R.color.MaterialGrey400)
        itemView.tv_is_online.text = if (item.IsOnline == "1") mContext.getString(R.string.on_line) else mContext.getString(R.string.off_line)


//1.登高车2.指挥车3.泡沫车4.水罐车5.干粉车6.高喷车 7越野摩托车
        when (item.carType) {
            "1" -> itemView.img.setImageResource(R.mipmap.icon_highcar)
            "2" -> itemView.img.setImageResource(R.mipmap.icon_commandcar)
            "3" -> itemView.img.setImageResource(R.mipmap.icon_bubblecar)
            "4" -> itemView.img.setImageResource(R.mipmap.icon_watercar)
            "5" -> itemView.img.setImageResource(R.mipmap.icon_powdercar)
            "6" -> itemView.img.setImageResource(R.mipmap.icon_spray)
            else -> itemView.img.setImageResource(R.mipmap.icon_vehicles)
        }
    }

    companion object {
        //        车辆类型(1.登高车2.指挥车3.泡沫车4.水罐车5.干粉车6.高鹏车 7越野摩托车)
        fun getCarTyeStr(carType: String?): String {
            return when (carType) {
                "1" -> {
                    "登高车"
                }
                "2" -> {
                    "指挥车"
                }
                "3" -> {
                    "泡沫车"
                }
                "4" -> {
                    "水罐车"
                }
                "5" -> {
                    "干粉车"
                }
                "6" -> {
                    "高喷车"
                }
                "7" -> {
                    "越野摩托车"
                }
                else -> {
                    ""
                }
            }
        }
    }
}
