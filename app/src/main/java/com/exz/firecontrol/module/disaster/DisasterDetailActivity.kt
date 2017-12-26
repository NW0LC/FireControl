package com.exz.firecontrol.module.disaster

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.TimeUtils
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.RongBean
import com.exz.firecontrol.bean.UserBean
import com.exz.firecontrol.module.MaprTrafficActivity
import com.exz.firecontrol.module.chat.ConversationActivity
import com.exz.firecontrol.module.live.LiveListActivity
import com.exz.firecontrol.module.live.LiveListActivity.Companion.Intent_live_id
import com.exz.firecontrol.module.unit.DrawingsActivity
import com.exz.firecontrol.module.unit.DrawingsActivity.Companion.Intent_getDrawFileList_comId
import com.exz.firecontrol.module.unit.DrawingsActivity.Companion.Intent_getDrawFileList_id
import com.exz.firecontrol.module.unit.EnterPriseDataActivity
import com.exz.firecontrol.module.unit.EnterPriseDataActivity.Companion.Intent_EnterPriseDataActivity_id
import com.exz.firecontrol.module.unit.FirewaterSupplyActivity
import com.exz.firecontrol.module.unit.InfoActivity.Companion.Intent_getEnterPrise_id
import com.exz.firecontrol.pop.SchemePop
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_disaster_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 2017/12/21.
 * 灾情详情
 */

class DisasterDetailActivity : BaseActivity(), View.OnClickListener, RongIM.UserInfoProvider {


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
            if (mPop.data.isNotEmpty())
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
        DataCtrlClass.getRongCloudToken(mContext){
            if (it!=null){
               token1=it.token
            }
        }
        DataCtrlClass.getFireInfoById(mContext, intent.getStringExtra(Intent_DisasterDetail_Id) ?: "") {
            if (it != null) {
                val fireInfoBean = it.fireInfo?.get(0)
                DataCtrlClass.getAdvisePlan(mContext,fireInfoBean?.flag.toString()){
                    if (it!=null){
                        mPop.data=it.advisePlan?.advise?:""
                    }
                }
                DataCtrlClass.getWeather(mContext, fireInfoBean?.cityName ?: "") {
                    if (it != null) {
                        tv_temp.text = String.format(it.temperature + "%s", "°")
                        tv_weather.text = it.type ?: ""
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
        rl_traffic.setOnClickListener(this)
        rl_chat.setOnClickListener(this)
        rl_live.setOnClickListener(this)
        rongList = ArrayList<RongBean>()
        rongList!!.add(RongBean("1", "测试", "https://www.baidu.com/link?url=f_6xwj-7u_yUnlhFMS9mFRT9lHtboSNz9wK4PEKzFLHS9dMVDae8YVzYIPSAeLESfKCmMTb99PJQo0LjNUzlkuxQtEji8mtoCHnODslOOHbjsDBKsWA0aRtIF0aF869JMCZ8J22wddT3b5FzN5Xy_XvPjBPPnB85WLgXT5lAvI95zLltaDGNHQB5WUntqATzOq8h_-OWkooyFER_Jt-VYjhOlhtaljnmghcydf8AldIo1PLFZwAzLYVmoD4ruGQQZTRK5WSeHmsyqg8KCGnbZA13VDbDW84uyxvqG61CO2JatfppC8h2o8mUVC2DWGBrob4wfJSIaXfWQS26mkgKnsfR2hbS5sJDBbiDIAwIihGZXi2DTjXW_1u5pWMsuG7vlTNRh-BZhoiu23ycwSoaNw5F6OCyxRi9ZUEdnBS41I90fnKGAM2fC0TKY0un_o0mkE35nZSFER7nrp5EO-PIIlO3XjCvOIo0OY1aPxZ03jg26R80p6hOP7wSOG_wm3Dw33BIoJTBLSvFeteNAHIR8dpJltCw8jWMEPGG2t5_vw0utoxEorgziVvf4PtokOgN4v9RIHYFgt3z0t4LNxHdy_&wd=&eqid=893d54d300034ee3000000055a4054df"))

    }
    var token1=""
    override fun onClick(p0: View) {
        when (p0) {
            rl_traffic -> {//实时交通
                startActivity(Intent(mContext, MaprTrafficActivity::class.java))
            }
            rl_related_plans -> {//关联预案
                startActivity(Intent(mContext, EnterPriseDataActivity::class.java).putExtra(Intent_EnterPriseDataActivity_id, id))
            }
            rl_plans -> {//图纸资料
                startActivity(Intent(mContext, DrawingsActivity::class.java).putExtra(Intent_getDrawFileList_id, intent.getStringExtra(Intent_getEnterPrise_id) ?: "").putExtra(Intent_getDrawFileList_comId, comId))
            }
            rl_xfsy -> {//消防水源
                startActivity(Intent(mContext, FirewaterSupplyActivity::class.java))
            }
            rl_chat -> {//融云聊天室
                connect(token1)
            }
            rl_live -> {//直播
                startActivity(Intent(mContext, LiveListActivity::class.java).putExtra(Intent_live_id,id))
            }
        }
    }

    companion object {
        var Intent_DisasterDetail_Id = "Intent_DisasterDetail_Id"
    }

    /**
     *
     * 连接服务器，在整个应用程序全局，只需要调用一次，需在 [.init] 之后调用。
     *
     * 如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
    private fun connect(token: String) {

        if (applicationInfo.packageName == getCurProcessName(applicationContext)) {

            RongIM.connect(token, object : RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                override fun onTokenIncorrect() {
                    Log.i("connect", "--onTokenIncorrect")
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                override fun onSuccess(userid: String) {
                    Log.i("connect", "--onSuccess" + userid)
                    RongIM.getInstance()
                            .setCurrentUserInfo(UserInfo(MyApplication.loginUserId, (MyApplication.user as UserBean).RoleName, Uri.parse("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1629089538,987246310&fm=27&gp=0.jpg")))
//                    RongIM.getInstance().startConversation(mContext, Conversation.ConversationType.CHATROOM, "1", "聊天室 I");
                    ConversationActivity.Chat_Class_Name = id
                        RongIM.getInstance().startChatRoomChat(mContext, id, true)

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                override fun onError(errorCode: RongIMClient.ErrorCode) {
                    Log.i("connect", "--onError" + errorCode)
                }
            })
        }
        RongIM.setUserInfoProvider(this, true)
    }

    private var rongList: ArrayList<RongBean>? = null
    override fun getUserInfo(p0: String?): UserInfo? {
        if (rongList != null) {
            for (i in rongList!!) {
                if (i.accountId.equals("110")) {
                    return UserInfo(i.accountId, i.friendName,
                            Uri.parse(i.friendPhoto))
                }
            }
        }
        return null
    }


    fun getCurProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }
}
