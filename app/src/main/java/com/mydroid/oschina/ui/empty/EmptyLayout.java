package com.mydroid.oschina.ui.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mydroid.oschina.R;

/**
 *  qll : 空视图或者错误视图
 */


public class EmptyLayout extends LinearLayout implements
        android.view.View.OnClickListener {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;

    private ProgressBar animProgress;
    private boolean clickEnable = true;
    private final Context context;
    public ImageView img;
    private android.view.View.OnClickListener listener;
    private int mErrorState;
    private RelativeLayout mLayout;
    private String strNoDataContent = "";
    private TextView tv;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.loading_error_layout, null);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);
        mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);
        animProgress = (ProgressBar) view.findViewById(R.id.animProgress);
        setBackgroundColor(-1);
        setOnClickListener(this);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
        addView(view);
        changeErrorLayoutBgMode(context);
    }

    public void changeErrorLayoutBgMode(Context context1) {
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            if (listener != null)
                listener.onClick(v);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onSkinChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onSkinChanged() {
    }

    public void setDayNight(boolean flag) {}

    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    /**
     * 新添设置背景
     */
    public void setErrorImag(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                    tv.setText("内容加载失败\\r\\n点击重新加载");
                    img.setBackgroundResource(R.drawable.pagefailed_bg);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                animProgress.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                tv.setText("加载中...");
                clickEnable = false;
                break;
            case NODATA:
                mErrorState = NODATA;
                img.setBackgroundResource(R.drawable.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                img.setBackgroundResource(R.drawable.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (!strNoDataContent.equals(""))
            tv.setText(strNoDataContent);
        else
            tv.setText("暂无内容");
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}