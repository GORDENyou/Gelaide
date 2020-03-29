package p.gordenyou.geleide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import p.gordenyou.geleide.R;
import p.gordenyou.geleide.databinding.ActivityHome2Binding;
import p.gordenyou.pdalibrary.adapter.GridAdapter;
import p.gordenyou.pdalibrary.unity.FunList;

public class HomeActivity2 extends AppCompatActivity {

    ActivityHome2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home2);

        binding.headtitle.getButtonLeft().setOnClickListener(v ->
                new AlertDialog.Builder(HomeActivity2.this, p.gordenyou.pdalibrary.R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("提示").setMessage("退出登陆？")
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            startActivity(new Intent(HomeActivity2.this, LoginActivity.class));
                            finish();
                        }).setNegativeButton("取消", null).show());

        Intent intent = getIntent();
        String[] funs = intent.getStringArrayExtra("FuncName");
//        String[] funs = new String[]{"原料入库","原料退货","原料领料","生产上料","余料称重","原料退料","成品入库","成品出库"
//                ,"待处理品退货","换标入库","待处理品再加工","待处理品作废"};

        ArrayList<FunList> funLists_yuanliao = new ArrayList<>();
        ArrayList<FunList> funLists_chengpin = new ArrayList<>();
        ArrayList<FunList> funLists_daichuli = new ArrayList<>();

        if (funs != null) {
            for (String fun : funs) {
                switch (fun) {
                    case "原料入库":
                        funLists_yuanliao.add(new FunList(0, R.drawable.ruku, "原料入库"));
                        break;
                    case "原料退货":
                        funLists_yuanliao.add(new FunList(0, R.drawable.tuihuo, "退货"));
                        break;
                    case "原料领料":
                        funLists_yuanliao.add(new FunList(0, R.drawable.lingliao, "领料"));
                        break;
                    case "生产上料":
                        funLists_yuanliao.add(new FunList(0, R.drawable.shangliao, "生产上料"));
                        break;
                    case "余料称重":
                        funLists_yuanliao.add(new FunList(0, R.drawable.chengzhong, "余料称重"));
                        break;
                    case "原料退料":
                        funLists_yuanliao.add(new FunList(0, R.drawable.tuiliao, "退料"));
                        break;
                    case "半成品入库":
                        funLists_yuanliao.add(new FunList(0, R.drawable.tuiliao, "半成品入库"));
                        break;

                    case "成品入库":
                        funLists_chengpin.add(new FunList(0, R.drawable.ruku, "成品入库"));
                        break;
                    case "成品出库":
                        funLists_chengpin.add(new FunList(0, R.drawable.chuku, "销售出库"));
                        break;

                    case "成品退货":
                        funLists_daichuli.add(new FunList(0, R.drawable.tuihuo, "成品退货"));
                        break;
                    case "退货换标":
                        funLists_daichuli.add(new FunList(0, R.drawable.ruku, "换标入库"));
                        break;
                    case "退货加工":
                        funLists_daichuli.add(new FunList(0, R.drawable.zaijiagong, "再加工"));
                        break;
                    case "退货作废":
                        funLists_daichuli.add(new FunList(0, R.drawable.zuofei, "作废"));
                        break;

                }
            }
        }


        GridAdapter gridAdapter_yuanliao = new GridAdapter(this, funLists_yuanliao);
        gridAdapter_yuanliao.setClickListener((text) -> {
            switch (text) {

            }
        });
        binding.gridYuanliao.setGridAdapter(gridAdapter_yuanliao);


        GridAdapter gridAdapter_chengpin = new GridAdapter(this, funLists_chengpin);
        gridAdapter_chengpin.setClickListener((text) -> {
            switch (text) {
            }
        });
        binding.gridChengpin.setGridAdapter(gridAdapter_chengpin);


        GridAdapter gridAdapter_daichuli = new GridAdapter(this, funLists_daichuli);
        gridAdapter_daichuli.setClickListener((text) -> {
            switch (text) {

            }
        });
        binding.gridDaichuli.setGridAdapter(gridAdapter_daichuli);

    }

    /**
     * 返回键监听
     * Return key listening
     */
    private long mkeyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.ACTION_DOWN:
                if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                    mkeyTime = System.currentTimeMillis();
                    boolean cn = "CN".equals(getApplicationContext().getResources().getConfiguration().locale.getCountry());
                    Toast.makeText(HomeActivity2.this, "再次点击返回退出", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
