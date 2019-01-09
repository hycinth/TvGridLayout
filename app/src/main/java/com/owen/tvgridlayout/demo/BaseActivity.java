package com.owen.tvgridlayout.demo;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.owen.focus.AbsFocusBorder;
import com.owen.focus.FocusBorder;
import com.owen.tvgridlayout.TvGridLayout;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2018/12/27
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected FocusBorder mFocusBorder;
    protected void initFocusBorder(AbsFocusBorder.Mode mode) {
        /** 颜色焦点框 */
        mFocusBorder = new FocusBorder.Builder().asColor()
                //阴影宽度(方法shadowWidth(18f)也可以设置阴影宽度)
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 20f)
                //阴影颜色
                .shadowColor(Color.parseColor("#3FBB66"))
                //边框宽度(方法borderWidth(2f)也可以设置边框宽度)
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3f)
                //边框颜色
                .borderColor(Color.parseColor("#00FF00"))
                //padding值
                .padding(2f)
                //动画时长
                .animDuration(300)
                //不要闪光效果动画
//                .noShimmer()
                //闪光颜色
                .shimmerColor(Color.parseColor("#66FFFFFF"))
                //闪光动画时长
                .shimmerDuration(1000)
                //不要呼吸灯效果
//                .noBreathing()
                //呼吸灯效果时长
                .breathingDuration(3000)
                //边框动画模式
                .animMode(mode)
                .build(this);

        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Nullable
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                return focus(oldFocus, newFocus);
            }
        });
    }

    protected FocusBorder.Options focus(View oldFocus, View newFocus) {
        if(newFocus instanceof TvGridLayout)
            return null;
        return FocusBorder.OptionsFactory.get(1.1f, 1.1f);
    }
}
