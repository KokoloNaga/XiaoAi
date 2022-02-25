package com.example.xiaoai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaoai.main.DiscussActivity;
import com.example.xiaoai.main.RecommendActivity;
import com.example.xiaoai.main.SearchActivity;
import com.example.xiaoai.main.UserActivity;

public class MainActivity extends AppCompatActivity {
    private Button mBtnExit;
    private TextView mTvDiscuss,mTvSearch,mTvRecommend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvDiscuss = (TextView) findViewById(R.id.tv_discuss);
        mTvRecommend = (TextView) findViewById(R.id.tv_recommend_main);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mBtnExit = (Button)findViewById(R.id.btn_exit);
        setListener();

    }

    private void setListener(){
        OnClick onClick = new OnClick();

        mTvSearch.setOnClickListener(onClick);
        mTvRecommend.setOnClickListener(onClick);
        mTvDiscuss.setOnClickListener(onClick);
        mBtnExit.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case R.id.tv_discuss:
                    intent = new Intent(MainActivity.this, DiscussActivity.class);
                    break;
                case R.id.tv_recommend_main:
                    intent = new Intent(MainActivity.this, RecommendActivity.class);
                    break;
                case R.id.tv_search:
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    break;
                case R.id.btn_exit:
                    SharedPreferences sharedPreferences = getSharedPreferences("now", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id","");
                    editor.apply();
                    intent = new Intent(MainActivity.this,LoginActivity.class);
                    finish();
                    break;

            }
            startActivity(intent);
        }
    }
}