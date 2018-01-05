package com.exz.firecontrol.module.unit

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.EnterPriseDataAdapter
import com.exz.firecontrol.bean.EnterPriseDataBean
import com.exz.firecontrol.widget.MyWebActivity
import com.exz.firecontrol.widget.MyWebActivity.Intent_Title
import com.exz.firecontrol.widget.MyWebActivity.Intent_Url
import com.exz.firecontrol.widget.PDFActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_enter_prise_data.*

/**
 * Created by pc on 2017/12/20.
 * 消防预案、vr列表
 */

class EnterPriseDataActivity : BaseActivity() {
    private lateinit var mEAdapter: EnterPriseDataAdapter<EnterPriseDataBean.EnterpriseDataBean>
    private lateinit var mPAdapter: EnterPriseDataAdapter<EnterPriseDataBean.EnterpriseDataBean>
    private lateinit var mVRAdapter: EnterPriseDataAdapter<EnterPriseDataBean.EnterpriseDataBean>
    override fun initToolbar(): Boolean {
        mTitle.text = "救援预案"
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
        return R.layout.activity_enter_prise_data
    }

    override fun init() {
        initRecycler()
    }

    private fun initRecycler() {
        mEAdapter = EnterPriseDataAdapter()
        mEAdapter.bindToRecyclerView(mERecyclerView)
        mERecyclerView.layoutManager = LinearLayoutManager(mContext)
        mERecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        val onItemClick: OnItemClickListener = object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {

                val enterpriseDataBean = adapter.data[position] as EnterPriseDataBean.EnterpriseDataBean
                val intent = if (enterpriseDataBean.flag==2) {
                    Intent(this@EnterPriseDataActivity, PDFActivity::class.java)
                }else
                    Intent(this@EnterPriseDataActivity, MyWebActivity::class.java)
                intent.putExtra(Intent_Url, enterpriseDataBean.description)
                intent.putExtra(Intent_Title, enterpriseDataBean.name)
                startActivity(intent)
            }
        }
        mERecyclerView.addOnItemTouchListener(onItemClick)
        mPAdapter = EnterPriseDataAdapter()
        mPAdapter.bindToRecyclerView(mPRecyclerView)
        mPRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mPRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        mPRecyclerView.addOnItemTouchListener(onItemClick)
        mVRAdapter = EnterPriseDataAdapter()
        mVRAdapter.bindToRecyclerView(mVRRecyclerView)
        mVRRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mVRRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        mVRRecyclerView.addOnItemTouchListener(onItemClick)
        DataCtrlClass.getEnterPriseData(this, "1", intent.getStringExtra(Intent_EnterPriseDataActivity_id) ?: "") {
            if (it != null)
                mEAdapter.setNewData(it.enterpriseData)
            tv_EPlan.visibility = if (mEAdapter.data.size == 0) View.GONE else View.VISIBLE
        }
        DataCtrlClass.getEnterPriseData(this, "2", intent.getStringExtra(Intent_EnterPriseDataActivity_id) ?: "") {
            if (it != null)
                mPAdapter.setNewData(it.enterpriseData)
            tv_Paper.visibility = if (mPAdapter.data.size == 0) View.GONE else View.VISIBLE
        }
        DataCtrlClass.getEnterPriseData(this, "3", intent.getStringExtra(Intent_EnterPriseDataActivity_id) ?: "") {
            if (it != null)
                mVRAdapter.setNewData(it.enterpriseData)

            tv_VR.visibility = if (mVRAdapter.data.size == 0) View.GONE else View.VISIBLE

        }
    }

    companion object {
        val Intent_EnterPriseDataActivity_id = "Intent_getDrawFileList_id"
    }

}
