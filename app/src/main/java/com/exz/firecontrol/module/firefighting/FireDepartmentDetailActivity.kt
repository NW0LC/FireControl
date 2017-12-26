package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.VehicleAdapter
import com.exz.firecontrol.module.MapLocationActivity
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Class_Name
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Latitude
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Longitude
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_fire_department_detail.*
import org.jetbrains.anko.textColor


/**
 * Created by pc on 2017/12/19.
 * 机构详情
 */

class FireDepartmentDetailActivity : BaseActivity(), View.OnClickListener {

    private var phone=""
    private var lon=0.toDouble()
    private var lat=0.toDouble()
    override fun initToolbar(): Boolean {
        mTitle.text = "机构详情"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_fire_department_detail
    }

    override fun init() {
        initView()
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.getOrgDetailById(this, intent.getStringExtra(Intent_getOrgDetailById_Id) ?: "") {
            if (it != null) {
                when (intent.getStringExtra(Intent_Type)) {
                    "1" -> { //1  支队 大队 中队
                        tv_type.text = mContext.getString(R.string.vehicle_type)
                        tv_type.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_vehicle_type), null, null, null)
                        tv_organization_name.text=it.cname?:""
                        tv_leader.text=""
                        tv_person_num.text=it.fireManCount.toString()
                        tv_telephone.text=it.telePhone?:""
                        phone=it.telePhone?:""
                        tv_address.text=it.addr?:""
                        lon=it.lon
                        lat=it.lat
                        llLay.removeAllViews()
                        it.carInfo?.forEach {
                            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            lp.setMargins(0, 0, 0, 10)
                            val textView = TextView(mContext)
                            textView.text = String.format("${VehicleAdapter.getCarTyeStr(it.carType.toString())}(${it.carCount})")
                            textView.textColor = ContextCompat.getColor(mContext, R.color.MaterialBlueGrey800)
                            textView.textSize = 14f
                            textView.gravity = Gravity.END
                            textView.maxLines = 1
                            textView.layoutParams = lp
                            llLay.addView(textView)
                        }
                    }
                    "2" -> {//微型消防站
                        tv_type.text = mContext.getString(R.string.fire_equipment)
                        tv_type.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_fire_equipment), null, null, null)
                        llLay.removeAllViews()
                        for (i in 0..6) {
                            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            lp.setMargins(0, 0, 0, 10)
                            val textView = TextView(mContext)
                            textView.text = "微型消防站(" + i + ")"
                            textView.textColor = ContextCompat.getColor(mContext, R.color.MaterialBlueGrey800)
                            textView.textSize = 14f
                            textView.gravity = Gravity.RIGHT
                            textView.maxLines = 1
                            textView.layoutParams = lp
                            llLay.addView(textView)
                        }
                    }
                }
            }
        }
    }

    private fun initView() {

        tv_telephone.setOnClickListener(this)
        tv_address.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            tv_telephone -> {
                DialogUtils.Call(this, phone)
            }
            tv_address -> {
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(Intent_Longitude,lon).putExtra(Intent_Latitude,lat).putExtra(Intent_Class_Name, "地址"))
            }
        }
    }

    companion object {
        var Intent_getOrgDetailById_Id = "Intent_getOrgDetailById_Id"
        var Intent_Type = "type" // 1  支队 大队 中队 2 微型消防站
    }


}