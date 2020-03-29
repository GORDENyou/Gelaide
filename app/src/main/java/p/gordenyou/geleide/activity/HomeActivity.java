package p.gordenyou.geleide.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import p.gordenyou.geleide.R;
import p.gordenyou.pdalibrary.adapter.FragmentAdapter;
import p.gordenyou.geleide.fragment.MainFragment;


public class HomeActivity extends AppCompatActivity {
    private ViewPager mViewpager;
    //    private BottomNavigationView mBottom;
    private static final String TAG = "HomeActivity";
//    private MenuItem mMenuitem;

    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat.from(HomeActivity.this).areNotificationsEnabled();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.GET_INTENT_FILTERS) { //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        } else {

        }

        mViewpager = findViewById(R.id.main_viewpager);
//        mBottom = findViewById(R.id.main_bottom);
//        BottomNavigationViewHelper.disableShiftMode(mBottom);

        FragmentManager fg = getSupportFragmentManager();
        List<Fragment> list = new ArrayList<>();
        MainFragment mainfragment = new MainFragment();
//        MessageFragment messageFragment = new MessageFragment();
//        PostFragment postFragment = new PostFragment();
//        UserFragment userFragment = new UserFragment();

        list.add(mainfragment);
//        list.add(messageFragment);
//        list.add(postFragment);
//        list.add(userFragment);


        final FragmentAdapter fragmentAdapter = new FragmentAdapter(fg, list);
        mViewpager.setAdapter(fragmentAdapter);

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

//                mMenuitem = mBottom.getMenu().getItem(position);
//                mMenuitem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Log.d(TAG, "onNavigationItemSelected: " + item.getItemId());
//                int fragmentid = 0;
//                switch (item.getItemId()) {
//                    case R.id.item_plan:
//                        fragmentid = 0;
//                        break;
//                    case R.id.item_message:
//                        fragmentid = 1;
//                        break;
//                    case R.id.item_post:
//                        fragmentid = 2;
//                        break;
//                    case R.id.item_user:
//                        fragmentid = 3;
//                        break;
//                }
//                mViewpager.setCurrentItem(fragmentid);
//                return true;
//            }
//        });
    }

//    private void notification(JSONArray array_low) {
////        Notification.Builder builder3 = new Notification.Builder(this);
//        Intent intent3 = new Intent(getBaseContext(), WarmActivity.class);
//        PendingIntent pendingIntent3 = PendingIntent.getActivity(getBaseContext(), 0, intent3, 0);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        //8.0 以后需要加上channelId 才能正常显示
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            String channelId = "default";
//            String channelName = "默认通知";
//            manager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
//        }
//
//        //设置TaskStackBuilder
////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////        stackBuilder.addParentStack(NotificationIntent.class);
////        stackBuilder.addNextIntent(intent);
//
////        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification notification = new NotificationCompat.Builder(this, "default")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("预警信息")
//                .setContentText(array_low.length() + "条库存过低预警信息" + "，点击查看~")
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setContentIntent(pendingIntent3)
//                .build();
//
//        manager.notify(2, notification);
//    }
}
