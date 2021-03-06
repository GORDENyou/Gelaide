package p.gordenyou.pdalibrary.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import p.gordenyou.pdalibrary.R;
import p.gordenyou.pdalibrary.view.NumberView;
import p.gordenyou.pdalibrary.view.ScannerView;

public class CommonMethod {

    public static void showResultDialog(Context context, boolean result, String errorMessage) {
        if (result) {
            new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("提示").setMessage("数据操作成功！").setIcon(R.drawable.ic_check_circle_black_24dp)
                    .setPositiveButton("确定", (dialogInterface, i) -> {
                    }).show();
        } else {
            new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("错误信息").setMessage(errorMessage).setIcon(R.drawable.ic_error_black_24dp)
                    .setPositiveButton("确定", (dialogInterface, i) -> {
                    }).show();
        }
    }

    public static void showDialog(Context context, String message) {
        new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("提示").setMessage(message)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                }).show();
    }

    public static void showRightDialog(Context context, String message) {
        new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("提示").setMessage(message).setIcon(R.drawable.ic_check_circle_black_24dp)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                }).show();
    }

    public static void showErrorDialog(Context context, String message, SureCallback callback) {
        new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("警告").setMessage(message).setIcon(R.drawable.ic_error_black_24dp)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    if (callback != null) {
                        callback.sureCallback();
                    }
                }).show();
    }

    public static void showErrorDialog(Context context, String message, String okMes, SureCallback okCallback, String ngMes, SureCallback ngCallback) {
        new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("警告").setMessage(message).setIcon(R.drawable.ic_error_black_24dp)
                .setPositiveButton(okMes, (dialogInterface, i) -> {
                    if (okCallback != null) {
                        okCallback.sureCallback();
                    }
                }).setNegativeButton(ngMes, (dialog, which) -> {
            if (ngCallback != null) {
                ngCallback.sureCallback();
            }
        }).show();
    }


    /**
     * 判断字符串是否全为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }

    public static String getListTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }

    private final static int PLAYER_OK = 1;
    private final static int PLAYER_NG = 0;

    /**
     * 播放提示音
     *
     * @param context
     * @param sound
     */
    public static void soundPlayer(Context context, int sound) {
        MediaPlayer mediaPlayer = null;
        switch (sound) {
            case 1:
                mediaPlayer = MediaPlayer.create(context, R.raw.scanok);
                mediaPlayer.start();
                break;
            case 0:
                mediaPlayer = MediaPlayer.create(context, R.raw.beep);
                mediaPlayer.start();
                break;
        }
        mediaPlayer.release();
    }

    /**
     * 检查输入
     *
     * @param context
     * @param views
     */
    public static boolean checkScannerView(Context context, LinearLayout[] views) {
        for (LinearLayout view : views) {

            if (view instanceof ScannerView) {
                ScannerView scannerView = (ScannerView) view;
                if (scannerView.getEditText().getText().toString().equals("")) {
                    showErrorDialog(context, scannerView.getTitle().replace(" ", "") + "不能为空！", null);
                    return false;
                }
            } else {
                NumberView numberView = (NumberView) view;
                if (numberView.getEdittext().getText().toString().equals("0.0")) {
                    showErrorDialog(context, numberView.getTitle().replace(" ", "") + "不能为空！", null);
                    return false;
                }
            }

        }
        return true;
    }

    public static void checkNumberView(Context context, NumberView[] views) {
        for (NumberView view : views) {
            String text = view.getTitle();
            if (view.getEdittext().getText().toString().equals("")) {
                showDialog(context, text + "不能为空！");
            }
        }
    }

    public static ArrayList<String> getKey(HashMap<String, String> map, String value) {
        ArrayList<String> keyList = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                keyList.add(key);
            }
        }
        return keyList;
    }

}
