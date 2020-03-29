package p.gordenyou.geleide.activity;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import p.gordenyou.geleide.R;

/**
 * Created by GORDENyou on 2020/2/2.
 * mailbox:1193688859@qq.com
 * have nothing but……
 */
public abstract class BaseActivity extends AppCompatActivity {
//    public MyViewModel myViewModel;
//    public View.OnClickListener listener;
    protected AlertDialog alertDialog;
    public MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        mediaPlayer = MediaPlayer.create(this, p.gordenyou.pdalibrary.R.raw.beep);
//        scanInterface = new ScanDecode(this);
//        scanInterface.initService("true");
//        scanInterface.getBarCode(new ScanInterface.OnScanListener() {
//            @Override
//            public void getBarcode(String s) {
//                setBarcode(s);
//            }
//
//            @Override
//            public void getBarcodeByte(byte[] bytes) {
//
//            }
//        });

        alertDialog = new AlertDialog.Builder(BaseActivity.this
                , R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("提示").setView(R.layout.dialog_wait).create();

//        listener = view -> {
//            if (getCurrentFocus() != null)
//                getCurrentFocus().clearFocus();
//            logicController();
//        };

        initView();
        dealData();
        logicController();
    }

    protected abstract void initView();

    protected abstract void dealData();

    public abstract void logicController();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
