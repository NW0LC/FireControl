package com.exz.firecontrol.module.vehicle

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.VehicleAdapter
import com.exz.firecontrol.bean.StairBean
import com.exz.firecontrol.bean.VehicleBean
import com.exz.firecontrol.pop.StairPop
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import com.szw.framelibrary.view.DrawableCenterButton
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.activity_vehicle.*
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow

/**
 * Created by pc on 2017/12/20.
 * 消防车辆
 */

class VehicleActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private lateinit var mPopCarState: StairPop//车辆类型
    private lateinit var mPopCarType: StairPop//车辆状态
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: VehicleAdapter
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        edTitle.hint = "搜索车辆编号"
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_vehicle
    }

    override fun init() {
        super.init()
        initView()
        initPop()
        initRecycler()
    }



    var dataState = ArrayList<StairBean>()
    var dataType = ArrayList<StairBean>()
    private fun initView() {
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        edTitle.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // do something
                val searchContent = edTitle.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(searchContent)) {

                }
                return@OnEditorActionListener true
            }
            false
        })
        rb1.setOnClickListener(this)
        rb2.setOnClickListener(this)
    }
    private fun initPop() {
        dataState.add(StairBean("0", "测试数据", false))
        dataState.add(StairBean("1", "接口返回", false))
        dataType.add(StairBean("1", "全部", false))
        dataType.add(StairBean("2", "在线", false))
        dataType.add(StairBean("3", "离线", false))
        mPopCarState = StairPop(mContext, {
            if (it != null) {
                setGaryOrblue(rb1, true, it.name)
            }
        })
        mPopCarType = StairPop(mContext, {
            if (it != null) {
                setGaryOrblue(rb2, true, it.name)
            }
        })
        mPopCarState.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        mPopCarState.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        mPopCarState.data = dataState
        mPopCarType.data = dataType
    }
    private fun setGaryOrblue(rb: DrawableCenterButton, check: Boolean, name: String) {
        if (!TextUtils.isEmpty(name)) rb.text = name
        if (check) {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.blue_arrow), null)
            rb.textColor = ContextCompat.getColor(mContext, R.color.colorPrimary)

        } else {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.gray_arrow), null)
            rb.textColor = ContextCompat.getColor(mContext, R.color.MaterialGrey600)
        }
    }

    private var data = ArrayList<VehicleBean>()
    private fun initRecycler() {
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        mAdapter = VehicleAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext,VehicleDetailActivity::class.java))
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        refreshLayout.autoRefresh()
    }

    override fun onClick(p0: View) {
        when (p0) {
            rb1 -> {
                mPopCarState.showPopupWindow(radioGroup)
            }
            rb2 -> {
                mPopCarType.showPopupWindow(radioGroup)
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        refreshLayout?.finishRefresh()
        currentPage = 1
        refreshState = Constants.RefreshState.STATE_REFRESH

    }

    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
    }
}
