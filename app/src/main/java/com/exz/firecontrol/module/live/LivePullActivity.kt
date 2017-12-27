package com.exz.firecontrol.module.live

import android.support.v4.content.ContextCompat
import com.exz.firecontrol.R
import com.exz.firecontrol.module.live.LiveListActivity.Companion.Intent_live_url
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import com.tencent.rtmp.TXLivePlayer
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.layout_live_pull.*

/**
 * Created by pc on 2017/12/25.
 */

class LivePullActivity : BaseActivity() {


    lateinit var mLivePlayer: TXLivePlayer
    override fun initToolbar(): Boolean {
        mTitle.text="播放"
        StatusBarUtil.immersive(this)
        blurView.setOverlayColor(ContextCompat.getColor(mContext, R.color.transparen))
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.layout_live_pull
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        mLivePlayer = TXLivePlayer(mContext)
        mLivePlayer.setPlayerView(mPlayerView)
        val rtmpUrl = intent.getStringExtra(Intent_live_url)?:""
        mLivePlayer.startPlay(rtmpUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP)
    }


    override fun onResume() {
        super.onResume()

        if (mPlayerView != null) {
            mPlayerView.onResume()
        }

    }

    public override fun onStop() {
        super.onStop()
        if (mPlayerView != null) {
            mPlayerView.onPause()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true)
        }
        if (mPlayerView != null) {
            mPlayerView.onDestroy()
        }
    }


    private fun stopPublishRtmp() {
    }


}