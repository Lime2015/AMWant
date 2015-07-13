package com.lime.amwant.frame;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.kakao.widget.LoginButton;
import com.lime.amwant.R;

/**
 * Created by Administrator on 2015-07-13.
 */
public class MyLoginButton extends LoginButton {
    public MyLoginButton(Context context) {
        super(context);
    }

    public MyLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       setBackground(getResources().getDrawable(R.drawable.kakao_login));
    }
}
