package com.ahmedteleb.requestchat.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ahmedteleb.requestchat.R;

public class SnapTabsView extends FrameLayout implements ViewPager.OnPageChangeListener
{
    ImageView myCenterImage;
    ImageView myBottomImage;
    ImageView myStartImage;
    ImageView myEndImage;
    View indicator;

    public SnapTabsView(@NonNull Context context) {
        super(context);
        init(context);

    }

    public SnapTabsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public SnapTabsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public SnapTabsView(Context context, AttributeSet attrs, int defStyle, int defStyleRes)

    {
        super(context,attrs,defStyle,defStyleRes);
        init(context);

    }

    private void init(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.view_snap_tab,this,true);

        myBottomImage = findViewById(R.id.viewSnap_bottom_image);
        myCenterImage = findViewById(R.id.viewSnap_center_image);
        myEndImage = findViewById(R.id.viewSnap_end_image);
        myStartImage = findViewById(R.id.viewSnap_start_image);
        indicator = findViewById(R.id.viewSnap_indicator);

    }
 ///////////////////////////////////
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
