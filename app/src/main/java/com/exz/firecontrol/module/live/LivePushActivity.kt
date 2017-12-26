package com.exz.firecontrol.module.live

import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXLivePushConfig
import com.tencent.rtmp.TXLivePusher
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.layout_live_push.*


/**
 * Created by pc on 2017/12/25.
 */

class LivePushActivity : BaseActivity(), View.OnClickListener {


    lateinit var mLivePusher: TXLivePusher
    lateinit var mLivePushConfig: TXLivePushConfig
    private var mVideoPublish: Boolean = false
    private var mFrontCamera = true
    override fun initToolbar(): Boolean {
        mTitle.text="直播"
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
        return R.layout.layout_live_push
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        mLivePusher = TXLivePusher(mContext)
        mLivePushConfig = TXLivePushConfig()
        if (Build.VERSION.SDK_INT > 18) {
            mLivePushConfig.setHardwareAcceleration( TXLiveConstants.ENCODE_VIDEO_HARDWARE)
        }
        mLivePusher.config = mLivePushConfig

        bt_lay.setOnClickListener(this)
        btnPlay.setOnClickListener(this)
        btnCameraChange.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()

        if (mCaptureView != null) {
            mCaptureView.onResume()
        }

        if (mVideoPublish && mLivePusher != null) {
            mLivePusher.resumePusher()
            mLivePusher.resumeBGM()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (mCaptureView != null) {
            mCaptureView.onPause()
        }

        if (mVideoPublish && mLivePusher != null) {
            mLivePusher.pausePusher()
            mLivePusher.pauseBGM()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopPublishRtmp()
        if (mCaptureView != null) {
            mCaptureView.onDestroy()
        }
    }

    private fun startPush() {
        btnPlay.setImageResource(R.mipmap.play_pause)
        mVideoPublish = false
        val rtmpUrl = "rtmp://14124.mpush.live.lecloud.com/live/test1"
        mLivePusher.startPusher(rtmpUrl)
        mLivePusher.startCameraPreview(mCaptureView)
        mCaptureView.visibility = View.VISIBLE
    }

    private fun stopPublishRtmp() {
        btnPlay.setImageResource(R.mipmap.play_start)
        mVideoPublish = true
        mLivePusher.stopBGM()
        mLivePusher.stopCameraPreview(true)
        mLivePusher.stopScreenCapture()
        mLivePusher.setPushListener(null)
        mLivePusher.stopPusher()
        mCaptureView.visibility = View.GONE
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnPlay -> {
                if (mVideoPublish) { //启动播放器
                    startPush()
                }else{
                    stopPublishRtmp()
                }
            }
            btnCameraChange -> {//相机
                mFrontCamera = !mFrontCamera

                if (mLivePusher.isPushing) {
                    mLivePusher.switchCamera()
                }
                mLivePushConfig.setFrontCamera(mFrontCamera)
                btnCameraChange.setImageResource(if (mFrontCamera) R.mipmap.camera_change else R.mipmap.camera_change2)
            }
        }
    }
}
