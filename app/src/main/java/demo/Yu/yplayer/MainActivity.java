package demo.Yu.yplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fragment.FavoriteFragment;
import fragment.HomePageFragment;
import fragment.UserFragment;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private FragmentManager mFragmentManager;
    private BottomNavigationBar bottomNavigationBar;

    private HomePageFragment homePageFragment;
    private FavoriteFragment favoriteFragment;
    private UserFragment userFragment;
    //默认选择第一个fragment
    private int lastSelectedPosition = 0;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationBar = this.findViewById(R.id.bottom_navigation_bar);
        initNavigation();
    }

    /**
     * 初始化底部导航栏
     * **/
    private void initNavigation(){
        //导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setTabSelectedListener(this);//监听切换点击事件
        //bottomNavigationBar.setBarBackgroundColor("#595757");//背景颜色
        //1、BACKGROUND_STYLE_DEFAULT：如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC 。如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
        //2、BACKGROUND_STYLE_STATIC：点击无水波纹效果
        //3、BACKGROUND_STYLE_RIPPLE：点击有水波纹效果
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_48dp,"首页")
                        //选中的颜色
                        .setActiveColor(R.color.colorAccent)
                        //.setActiveColorResource(R.color.colorAccent)
                        //未选中的颜色(默认灰色 可注释)
                        .setInActiveColor("#999999")
                        //未选中时的图片的资源
                        .setInactiveIconResource(R.drawable.ic_home_black_48dp))
                .addItem(new BottomNavigationItem(R.drawable.favorite_heart,"喜爱")
                        .setActiveColor(R.color.colorAccent)
                        .setInActiveColor("#999999")
                        .setInactiveIconResource(R.drawable.favorite_heart))
                .addItem(new BottomNavigationItem(R.drawable.user, "用户")
                        .setActiveColor(R.color.colorAccent)
                        .setInActiveColor("#999999")
                        .setInactiveIconResource(R.drawable.user))
                .setFirstSelectedPosition(lastSelectedPosition)//设置默认选中项
                //.setFab(FloatingActionButton的id)//FloatingActionButton 关联
                .initialise();//注意此句放在最后
        setDefaultFragment();
    }

    /**
     * 设置默认开启的fragment
     * */
    private void setDefaultFragment(){
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        homePageFragment = new HomePageFragment();
        transaction.add(R.id.tb, homePageFragment);
        transaction.commit();
    }

    /**
     * 切换事件
     * **/
    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;//每次点击赋值
        //开启事务
        transaction = mFragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (position){
            case 0:
                if (homePageFragment == null){
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.tb, homePageFragment);
                }else {
                    transaction.show(homePageFragment);
                    // transaction.replace(R.id.tb, homepageFragment);
                }
                break;
            case 1:
                if (favoriteFragment == null){
                    favoriteFragment = new FavoriteFragment();
                    transaction.add(R.id.tb, favoriteFragment);
                }else {
                    transaction.show(favoriteFragment);
                }
                break;
            case 2:
                if (userFragment == null){
                    userFragment = new UserFragment();
                    transaction.add(R.id.tb, userFragment);
                }else {
                    transaction.show(userFragment);
                }
                break;
        }
        //事物提交
        transaction.commit();
    }

    /**
     * 隐藏当前fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction){
        if (homePageFragment != null){
            transaction.hide(homePageFragment);
        }
        if (favoriteFragment != null){
            transaction.hide(favoriteFragment);
        }
        if (userFragment != null){
            transaction.hide(userFragment);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
