package com.xing.shoppingcart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<FruitBean> dataList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private ImageView imageView;
    private ViewGroup animationLayout;

    private int[] cartLocation = new int[2];
    private ImageView cartImgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

        setListener();


    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecyclerAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        animationLayout = createAnimationLayout(this);
        imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.shape_yellow_dot);

        cartImgView = findViewById(R.id.tv_cart);

        cartImgView.getLocationInWindow(cartLocation);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private ViewGroup createAnimationLayout(Context context) {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(linearLayout);
        return linearLayout;
    }

    float[] currentPos = new float[2];


    private void playAnimation(PointF startPoint, PointF endPoint) {
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.quadTo(startPoint.x - 100, startPoint.y - 200, endPoint.x, endPoint.y);
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                pathMeasure.getPosTan(value, currentPos, null);
                imageView.setTranslationX(currentPos[0]);
                imageView.setTranslationY(currentPos[1]);

            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationLayout.removeAllViews();
            }
        });
        valueAnimator.start();

    }

    private void setListener() {
        adapter.setOnItemButtonClickListener(new RecyclerAdapter.OnItemButtonClickListener() {
            @Override
            public void onItemButtonClick(View view, int position, int[] locations) {
                Log.d(TAG, "onItemButtonClick: position = " + position);
                animationLayout.removeAllViews();
                animationLayout.addView(imageView);
                cartImgView.getLocationInWindow(cartLocation);
                playAnimation(new PointF(locations[0] + view.getWidth() / 2, locations[1]),
                        new PointF(cartLocation[0] + cartImgView.getWidth() / 2f, cartLocation[1]));

            }
        });


    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dataList.add(new FruitBean(R.mipmap.ic_launcher, "柚子", "甜蜜的柚子,甜过初恋"));
        }
    }


}
