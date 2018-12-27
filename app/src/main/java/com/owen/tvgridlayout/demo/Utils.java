package com.owen.tvgridlayout.demo;

import android.content.Context;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2018/12/19
 */
public class Utils {

    //将像素转换为px
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //将px转换为dp
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
