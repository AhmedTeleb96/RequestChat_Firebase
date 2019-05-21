package com.ahmedteleb.requestchat.View;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ahmedteleb.requestchat.R;

public class SnapTabsView extends FrameLayout implements ViewPager.OnPageChangeListener
{
    private ImageView myCenterImage;
    private ImageView myBottomImage;
    private ImageView myStartImage;
    private ImageView myEndImage;
    private View indicator;
    private ArgbEvaluator myArgbEvaluator;
    private int centerColor , sideColor;
    private int endViewsTranslationX;
    private int indicatorTranslationX;
    private int centerTranslationY;

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

        centerColor = ContextCompat.getColor(getContext(),R.color.white);
        sideColor = ContextCompat.getColor(getContext(),R.color.light_gray);

        myArgbEvaluator = new ArgbEvaluator();

        indicatorTranslationX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,getResources().getDisplayMetrics());

        myBottomImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                endViewsTranslationX = (int) ((myBottomImage.getX()-myStartImage.getX()) - indicatorTranslationX );
                myBottomImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                centerTranslationY = getHeight() - myBottomImage.getBottom();
            }
        });


    }
 ///////////////////////////////////
    public void setUpWithViewPager(final ViewPager viewPager)
    {
        viewPager.addOnPageChangeListener(this);

        myStartImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(0);
            }
        });

        myEndImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != 2)
                    viewPager.setCurrentItem(2);
            }
        });
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (position == 0)
        {
            setColor(1-positionOffset);

            moveViews(1-positionOffset);

            moveAndScaleCenter(1-positionOffset );

            indicator.setTranslationX((positionOffset-1)* indicatorTranslationX);

        }else if (position == 1)
        {
            setColor(positionOffset);

            moveViews(positionOffset);

            moveAndScaleCenter(positionOffset );

            indicator.setTranslationX(positionOffset* indicatorTranslationX);


        }
    }

    private void moveAndScaleCenter(float fractionFromCenter)
    {
        float scale = 0.7f + ((1-fractionFromCenter) * 0.3f);

        myCenterImage.setScaleX(scale);
        myCenterImage.setScaleY(scale);

        int translation = (int) (fractionFromCenter*centerTranslationY);

        myCenterImage.setTranslationY(translation);
        myBottomImage.setTranslationY(translation);

        myBottomImage.setAlpha(1-fractionFromCenter);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void moveViews(float fractionFromCenter)
    {
        myStartImage.setTranslationX(fractionFromCenter * endViewsTranslationX);
        myEndImage.setTranslationX(-fractionFromCenter * endViewsTranslationX);
        indicator.setAlpha(fractionFromCenter);
        indicator.setScaleX(fractionFromCenter);

    }

    private void setColor(float fractionFromCenter)
    {
       int color = (int) myArgbEvaluator.evaluate(fractionFromCenter , centerColor , sideColor);

        myStartImage.setColorFilter(color);
        myEndImage.setColorFilter(color);
        myCenterImage.setColorFilter(color);
        myBottomImage.setColorFilter(color);

    }
}
