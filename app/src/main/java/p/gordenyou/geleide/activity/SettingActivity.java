package p.gordenyou.geleide.activity;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import p.gordenyou.geleide.R;
import p.gordenyou.pdalibrary.common.CommonMethod;
import p.gordenyou.pdalibrary.net.JDBCHelper;
import p.gordenyou.pdalibrary.view.ScannerView;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ip)
    ScannerView ip;
    @BindView(R.id.database)
    ScannerView database;
    @BindView(R.id.username)
    ScannerView username;
    @BindView(R.id.password)
    ScannerView password;
    @BindView(R.id.save)
    Button save;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void dealData() {

    }

    @Override
    public void logicController() {
        save.setOnClickListener((view) -> {
            if (CommonMethod.checkScannerView(SettingActivity.this, new LinearLayout[]{ip, database, username, password})) {
                SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String Url = "jdbc:jtds:sqlserver://" +
                        ip.getText() + ";DatabaseName=" +
                        database.getText();
                editor.putString("url", Url);
                editor.putString("user", username.getText());
                editor.putString("password", password.getText());
                editor.apply();

                JDBCHelper.URL = Url;
                JDBCHelper.USERNAME = username.getText();
                JDBCHelper.PASSWORD = password.getText();

                CommonMethod.showRightDialog(SettingActivity.this, "配置成功！配置已生效。");
            }
        });
    }
}
