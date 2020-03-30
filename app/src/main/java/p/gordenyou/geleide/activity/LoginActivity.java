package p.gordenyou.geleide.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import p.gordenyou.geleide.Common.SQLStatement;
import p.gordenyou.geleide.R;
import p.gordenyou.pdalibrary.common.CommonMethod;
import p.gordenyou.pdalibrary.listener.JDBCHelperQueryListener;
import p.gordenyou.pdalibrary.net.JDBCHelper;
import p.gordenyou.pdalibrary.unity.UserInfo;
import p.gordenyou.pdalibrary.view.ScannerView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    ScannerView username;
    @BindView(R.id.password)
    ScannerView password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.setting)
    Button setting;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        password.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());

        SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userName = pref.getString("userName", "");
        username.setText(userName);


        String url = pref.getString("url", "");
        if (!url.isEmpty()) {
            String user = pref.getString("user", "");
            String password = pref.getString("password", "");
            JDBCHelper.URL = url;
            JDBCHelper.USERNAME = user;
            JDBCHelper.PASSWORD = password;
        }
    }

    @Override
    protected void dealData() {

    }

    @Override
    public void logicController() {
        login.setOnClickListener(v -> {
            login();
        });

        setting.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SettingActivity.class));
        });
    }

    private void login() {
        if (CommonMethod.checkScannerView(LoginActivity.this, new LinearLayout[]{username, password})) {
            alertDialog.show();
//            SystemClock.sleep(1000);
            JDBCHelper.getQueryHelper(SQLStatement.getLogin(username.getText(), password.getText()), "login", new JDBCHelperQueryListener() {
                @Override
                public void success(ArrayList<HashMap<String, Object>> result, String queryFlag) {
                    alertDialog.cancel();
                    switch (queryFlag) {
                        case "login":
                            if (result.size() == 1) {
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                                Iterator<HashMap<String, Object>> iterator = result.iterator();
                                UserInfo.USERNAME = iterator.next().get("username").toString();


                                SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("userName", UserInfo.USERNAME);
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                CommonMethod.showErrorDialog(LoginActivity.this, "登陆失败，请检查用户名和密码", null);
                            }
                    }
                }

                @Override
                public void fail(String error) {
                    alertDialog.cancel();
                    CommonMethod.showErrorDialog(LoginActivity.this, "登陆失败：" + error, null);
                }
            }).sqlQuery();
        }
    }

}
