package project.ly.com.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by liuyun on 2018/1/19.
 */

public abstract class AbsKeyBoardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private Activity mActivity;

    public AbsKeyBoardChangeListener(@NonNull final Activity activity) {

        this.mActivity = activity;

        activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    @Override public void onGlobalLayout() {

        if (mActivity == null) {
            return;
        }

        Rect r = new Rect();

        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

        int statusBarHeight = getStatusHeight();

        int heightDifference = getScreenHeight() - (r.bottom - r.top);

        if (heightDifference - statusBarHeight == 0) {
            onKeyBoardChanged(false);
        } else {
            onKeyBoardChanged(true);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void release() {
        if (mActivity != null) {
            mActivity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    }

    private int getStatusHeight() {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = mActivity.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private int getScreenHeight() {
        WindowManager wm = (WindowManager) mActivity
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public abstract void onKeyBoardChanged(boolean isChanged);

}
