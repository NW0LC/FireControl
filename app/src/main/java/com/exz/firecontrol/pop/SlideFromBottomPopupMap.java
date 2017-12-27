package com.exz.firecontrol.pop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.exz.firecontrol.R;

import razerdp.basepopup.BasePopupWindow;

public class SlideFromBottomPopupMap extends BasePopupWindow implements View.OnClickListener {

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SlideFromBottomPopupMap(Activity context) {
        super(context);
        findViewById(R.id.tx_1).setOnClickListener(this);
        findViewById(R.id.tx_2).setOnClickListener(this);
        findViewById(R.id.tx_3).setOnClickListener(this);
    }

    @Override
    protected Animation initShowAnimation() {
        Animation shakeAnimate = new TranslateAnimation(0, 0, 600, 0);
        shakeAnimate.setDuration(300);
        return shakeAnimate;
    }

    @Override
    protected Animation initExitAnimation() {
        Animation shakeAnimate = new TranslateAnimation(0, 0, 0, 600);
        shakeAnimate.setDuration(300);
        return shakeAnimate;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }


    @Override
    public View onCreatePopupView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.popup_slide_from_bottom_map, null);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.animationView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tx_3) {
            dismiss();
        }
        if (onClickListener != null)
            onClickListener.onClick(v);
    }
}
