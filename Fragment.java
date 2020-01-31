package demo.login.com.myapplication.;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import androidx.appcompat.app.AppCompatActivity;
import demo.Yu.yplayer.fragment.AFragment;
import demo.Yu.yplayer.fragment.BFragment;
import demo.Yu.yplayer.fragment.CFragment;
import demo.Yu.yplayer.fragment.DFragment;

public class Fragment extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private FragmentManager mFragmentManager;
    private BottomNavigationBar bottomNavigationBar;

    private AFragment aFragment;
    private BFragment bFragment;
    private CFragment cFragment;
    private DFragment dFragment;

    private FragmentTransaction transaction;
    private int lastSelectedPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);

        bottomNavigationBar = this.findViewById(R.id.bottom_navigation_bar);
        initNavigation();
    }

    private void initNavigation(){
        //导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setTabSelectedListener(this);//监听切换点击事件
        //bottomNavigationBar.setBarBackgroundColor("#595757");//背景颜色
        //1、BACKGROUND_STYLE_DEFAULT：如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC 。如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
        //2、BACKGROUND_STYLE_STATIC：点击无水波纹效果
        //3、BACKGROUND_STYLE_RIPPLE：点击有水波纹效果
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //需要添加的item数
        bottomNavigationBar
            //选中时的图片的资源、文字
            .addItem(new BottomNavigationItem(R.drawable.ic_home_black_48dp,"首页")
                //选中的颜色
                .setActiveColor(R.color.colorAccent)
                //.setActiveColorResource(R.color.colorAccent)
                //未选中的颜色(默认灰色 可注释)
                .setInActiveColor("#999999")
                //未选中时的图片的资源
                .setInactiveIconResource(R.drawable.ic_home_black_48dp))
            .addItem(new BottomNavigationItem(R.drawable.favorite_heart, "影视")
                .setActiveColor(R.color.colorAccent)
                .setInActiveColor("#999999")
                .setInactiveIconResource(R.drawable.favorite_heart))
            .addItem(new BottomNavigationItem(R.drawable.backup, "text")
                .setActiveColor(R.color.colorAccent)
                .setInActiveColor("#999999")
                .setInactiveIconResource(R.drawable.backup))
            .addItem(new BottomNavigationItem(R.drawable.user, "我的")
                .setActiveColor(R.color.colorAccent)
                .setInActiveColor("#999999")
                .setInactiveIconResource(R.drawable.user))
            .setFirstSelectedPosition(lastSelectedPosition)
            //.setFab(FloatingActionButton的id)//FloatingActionButton 关联
            .initialise();//注意此句放在最后
        setDefultFragment();
    }

    private void setDefultFragment(){
        mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        aFragment = new AFragment();
        transaction.add(R.id.tb, aFragment);
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if (aFragment != null){
            transaction.hide(aFragment);
        }
        if (bFragment != null){
            transaction.hide(bFragment);
        }
        if (cFragment != null){
            transaction.hide(cFragment);
        }
        if (dFragment != null){
            transaction.hide(dFragment);
        }
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;//每次点击赋值
        transaction = mFragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (position){
            case 0:
                if (aFragment == null){
                    aFragment = new AFragment();
                    transaction.add(R.id.tb, aFragment);
                }else {
                    transaction.show(aFragment);
                }
                // transaction.replace(R.id.tb, firstFragment);
                break;
            case 1:
                if (bFragment == null) {
                    bFragment = new BFragment();
                    transaction.add(R.id.tb, bFragment);
                } else {
                    transaction.show(bFragment);
                }
                break;
            case 2:
                if (cFragment == null) {
                    cFragment = new CFragment();
                    transaction.add(R.id.tb, cFragment);
                } else {
                    transaction.show(cFragment);
                }
                break;
            case 3:
                if (dFragment == null) {
                    dFragment = new DFragment();
                    transaction.add(R.id.tb, dFragment);
                } else {
                    transaction.show(dFragment);
                }
                break;
        }
        //事物提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
