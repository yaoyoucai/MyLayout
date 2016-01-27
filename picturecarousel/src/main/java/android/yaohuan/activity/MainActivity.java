package android.yaohuan.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private TextView mTitle;
    private List<View> mDatas = new ArrayList<>();
    private String[] mListTitle=new String[]{"标题1","标题2","标题3","标题4","标题5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        //设置默认选中中间的图片,以便能左右滑动
        int item=Integer.MAX_VALUE/2;
        int extras=item%mDatas.size();
        mViewPager.setCurrentItem(item-extras);
    }

    /**
     * 初始化ui控件
     */
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mLinearLayout = (LinearLayout) findViewById(R.id.pointScoll_layout);
        mTitle= (TextView) findViewById(R.id.tv_title);
    }

    /**
     * 初始化数据
     */
    public void initDatas() {
        ImageView view1 = new ImageView(MainActivity.this);
        ImageView view2 = new ImageView(MainActivity.this);
        ImageView view3 = new ImageView(MainActivity.this);
        ImageView view4 = new ImageView(MainActivity.this);
        ImageView view5 = new ImageView(MainActivity.this);

        view1.setImageResource(R.mipmap.a);
        view1.setScaleType(ImageView.ScaleType.FIT_XY);
        view2.setImageResource(R.mipmap.b);
        view2.setScaleType(ImageView.ScaleType.FIT_XY);

        view3.setImageResource(R.mipmap.c);
        view3.setScaleType(ImageView.ScaleType.FIT_XY);

        view4.setImageResource(R.mipmap.d);
        view4.setScaleType(ImageView.ScaleType.FIT_XY);

        view5.setImageResource(R.mipmap.e);
        view5.setScaleType(ImageView.ScaleType.FIT_XY);

        mDatas.add(view1);
        mDatas.add(view2);
        mDatas.add(view3);
        mDatas.add(view4);
        mDatas.add(view5);

        //初始化时设置为第一个标题
        mTitle.setText(mListTitle[0]);

        initPoint();
    }

    /**
     * 初始化页面上的五个点
     */
    public void initPoint() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        for (int i = 0; i < 5; i++) {
            View view = new View(MainActivity.this);
            if (i != 0) {
                //设置每个点之间的间距为10
                params.leftMargin = 10;
                view.setBackgroundResource(R.drawable.point_normal);
            } else {
                //设置第一个点默认选中
                view.setBackgroundResource(R.drawable.point_selected);
            }
            mLinearLayout.addView(view, params);
        }
    }


    /**
     * 自定义adapter类，用于ViewPager实现
     */
    class MyPagerAdapter extends PagerAdapter {

        private static final String TAG ="position" ;

        /**
         * @return 页面的数量
         */
        @Override
        public int getCount() {
//            return mDatas.size();
            return Integer.MAX_VALUE;
        }

        /**
         * 初始化view,并返回标记
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i(TAG,Integer.toString(position));
            position=position%mDatas.size();
            View iv = mDatas.get(position);
            mViewPager.addView(iv);
            return iv;
        }

        /**
         * 销毁view
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            position=position%mDatas.size();
            View view = mDatas.get(position);
            mViewPager.removeView(view);
        }

        /**
         * 标记方法，用来判断缓存标记
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    /**
     * 自定义页面改变的监听器类
     */
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 回凋方法 当页面滑动时调用
         *
         * @param position             页面下标
         * @param positionOffset       页面滑动占整个页面的百分比
         * @param positionOffsetPixels 页面滑动像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //
        }

        /**
         * 当页面选中时调用
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            int childCount = mLinearLayout.getChildCount();
            position=position%mDatas.size();
            for (int i = 0; i < childCount; i++) {
                View view =mLinearLayout.getChildAt(i);
                if (position==i){
                    view.setBackgroundResource(R.drawable.point_selected);
                }else {
                    view.setBackgroundResource(R.drawable.point_normal);
                }
            }
            mTitle.setText(mListTitle[position]);

        }

        /**
         * 、
         * 当页面状态改变时调用
         * 三种状态：
         * ViewPager#SCROLL_STATE_IDLE 闲置状态
         *
         * @param state
         * @see ViewPager#SCROLL_STATE_DRAGGING 滑动状态
         * @see ViewPager#SCROLL_STATE_SETTLING 固定状态
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
