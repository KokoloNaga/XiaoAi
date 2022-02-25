package com.example.xiaoai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.EdgeEffectCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FirstLauncherActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private int[] images=new int[]{R.mipmap.bg_launcher_one,R.mipmap.bg_launcher_two,R.mipmap.bg_launcher_three};
    private List<View> mImageViews = new ArrayList<>();
    private List<ImageView> tips = new ArrayList<>();
    private ViewGroup group;

    private EdgeEffectCompat rightEdge;

    private TextView tvGotoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launcher);

        group = (ViewGroup) findViewById(R.id.viewGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tvGotoMain = (TextView) findViewById(R.id.tv_gotomain);

        try{
            Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
            if(rightEdgeField != null){
                rightEdgeField.setAccessible(true);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[i]);
            mImageViews.add(imageView);
        }

        for (int i = 0; i < images.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            if (i == 0) {
                imageView.setBackgroundResource(R.mipmap.icon_first_launcher_page_select_one);
            } else {
                imageView.setBackgroundResource(R.mipmap.icon_first_launcher_page_normal);
            }
            tips.add(imageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            group.addView(imageView,layoutParams);
        }

        viewPager.setAdapter(new PreviewImageAdapter());
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        tvGotoMain.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_gotomain:
                    gotoMain();
                    break;
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == images.length - 1) {
                tvGotoMain.setVisibility(View.VISIBLE);
            } else {
                tvGotoMain.setVisibility(View.INVISIBLE);
            }
            setImageBackground(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if ( rightEdge != null && !rightEdge.isFinished()) {
                gotoMain();
            }
        }
    };

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.size(); i++) {
            if (i == selectItems) {
                if(i==0){
                    tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_select_one);
                }else if(i==1){
                    tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_select_two);
                }else if(i==2){
                    tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_select_three);
                }else {
                    tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_normal);
                }
            }
        }
    }

    public class PreviewImageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (position < mImageViews.size()) {
                ((ViewPager)container).removeView(mImageViews.get(position));
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager)container).addView(mImageViews.get(position));
            return mImageViews.get(position);
        }
    }

    private void gotoMain(){
        setFirstLauncherBoolean();
        Intent intent = new Intent(FirstLauncherActivity.this,LoginActivity.class);
        //
        SharedPreferences now = getSharedPreferences("now",Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = now.edit();
        mEditor.putString("id","");
        mEditor.apply();

        startActivity(intent);
        finish();
    }

    public void setFirstLauncherBoolean(){
        SharedPreferences sp = getSharedPreferences("ansen", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(LauncherActivity.FIRST_LAUNCHER,true);
        edit.apply();
    }

}