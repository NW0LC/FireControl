package com.exz.firecontrol.module.disaster

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.TimeUtils
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.module.unit.DrawingsActivity
import com.exz.firecontrol.module.unit.DrawingsActivity.Companion.Intent_getDrawFileList_comId
import com.exz.firecontrol.module.unit.DrawingsActivity.Companion.Intent_getDrawFileList_id
import com.exz.firecontrol.module.unit.EnterPriseDataActivity
import com.exz.firecontrol.module.unit.EnterPriseDataActivity.Companion.Intent_EnterPriseDataActivity_id
import com.exz.firecontrol.module.unit.FirewaterSupplyActivity
import com.exz.firecontrol.module.unit.InfoActivity.Companion.Intent_getEnterPrise_id
import com.exz.firecontrol.pop.SchemePop
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_disaster_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 2017/12/21.
 * 灾情详情
 */

class DisasterDetailActivity : BaseActivity(), View.OnClickListener {


    private lateinit var mPop: SchemePop
    override fun initToolbar(): Boolean {
        mTitle.text = "灾情详情"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(mContext, scrollView)
        StatusBarUtil.setMargin(mContext, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.inflateMenu(R.menu.menu_person)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "建议方案"
        actionView.movementMethod = ScrollingMovementMethod.getInstance()
        actionView.setOnClickListener {
            mPop.showPopupWindow()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_disaster_detail
    }

    override fun init() {

        initView()
        iniData()
    }

    private var comId = ""
    private var id = ""
    private fun iniData() {

        DataCtrlClass.getFireInfoById(mContext, intent.getStringExtra(Intent_DisasterDetail_Id) ?: "") {
            if (it != null) {
                val fireInfoBean = it.fireInfo?.get(0)
                DataCtrlClass.getWeather(mContext, fireInfoBean?.cityName ?: "") {
                    if (it!=null) {
                        tv_temp.text = String.format(it.temperature + "%s", "°")
                        tv_weather.text = it.type?:""
                        tv_windy.text = String.format((it.windDirection ?: "") + (it.windForce ?: ""))
                        tv_date.text = String.format("${TimeUtils.date2String(TimeUtils.getNowDate(), SimpleDateFormat("MM-dd", Locale.CHINA))} ${TimeUtils.getChineseWeek(TimeUtils.getNowDate())}")
                    }
                }
                comId = fireInfoBean?.comid.toString()
                id = fireInfoBean?.id.toString()
                tv_location.text = fireInfoBean?.cityName ?: ""
                tv_disaster_name.text = fireInfoBean?.name ?: ""
                tv_current_state.text = when (fireInfoBean?.status) {
                    1 -> {
                        "待处理"
                    }
                    2 -> {
                        "正在处理"
                    }
                    else -> {
                        "已结束"
                    }
                }
                tv_trapped_person_num.text = fireInfoBean?.psnNum.toString()
                tv_alarm_people.text = fireInfoBean?.alarmName ?: ""
                tv_alarm_date.text = fireInfoBean?.createDate ?: ""
                tv_out_date.text = fireInfoBean?.startDate ?: ""
                tv_back_date.text = fireInfoBean?.endDate ?: ""
                lay_out.visibility = if ((fireInfoBean?.startDate ?: "").isEmpty()) View.GONE else View.VISIBLE
                lay_back.visibility = if ((fireInfoBean?.endDate ?: "").isEmpty()) View.GONE else View.VISIBLE
            }

        }
    }

    private fun initView() {
        mPop = SchemePop(mContext)
        rl_related_plans.setOnClickListener(this)
        rl_plans.setOnClickListener(this)
        rl_xfsy.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            rl_related_plans -> {//关联预案
                startActivity(Intent(mContext, EnterPriseDataActivity::class.java).putExtra(Intent_EnterPriseDataActivity_id, id))
            }
            rl_plans -> {//图纸资料
                startActivity(Intent(mContext, DrawingsActivity::class.java).putExtra(Intent_getDrawFileList_id, intent.getStringExtra(Intent_getEnterPrise_id) ?: "").putExtra(Intent_getDrawFileList_comId, comId))
            }
            rl_xfsy -> {//消防水源
                startActivity(Intent(mContext, FirewaterSupplyActivity::class.java))
            }
        }
    }

    companion object {
        var Intent_DisasterDetail_Id = "Intent_DisasterDetail_Id"
    }
}
