package p.gordenyou.geleide.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import p.gordenyou.geleide.Common.SQLStatement;
import p.gordenyou.geleide.R;
import p.gordenyou.pdalibrary.common.CommonMethod;
import p.gordenyou.pdalibrary.listener.JDBCHelperQueryListener;
import p.gordenyou.pdalibrary.net.JDBCHelper;
import p.gordenyou.pdalibrary.view.ScannerView;
import p.gordenyou.pdalibrary.view.SpinnerView;
import p.gordenyou.pdalibrary.view.TextshowView;

public class MainActivity extends BaseActivity implements View.OnKeyListener {

    @BindView(R.id.gengXin)
    Button gengXin;
    @BindView(R.id.shangChuan)
    Button shangChuan;

    @BindView(R.id.gongDan)
    SpinnerView gongDan;
    @BindView(R.id.sheBei)
    SpinnerView sheBei;

    @BindView(R.id.zhanWei)
    ScannerView zhanWei;
    @BindView(R.id.wuLiao)
    ScannerView wuLiao;

    @BindView(R.id.num_zhanWei)
    TextshowView numZhanWei;
    @BindView(R.id.num_yiSao)
    TextshowView numYiSao;
    @BindView(R.id.zhengQue)
    TextshowView zhengQue;

    @BindView(R.id.chaZhao)
    CheckBox chaZhao;
    @BindView(R.id.shunXu)
    CheckBox shunXu;
    @BindView(R.id.shiShi)
    CheckBox shiShi;

    private long firstClick;

    private static ArrayList<HashMap<String, Object>> data;
    private LinkedHashSet<String> list_gongDan = new LinkedHashSet<>();
    private LinkedHashSet<String> list_sheBei = new LinkedHashSet<>();
    private LinkedHashMap<String, String> map_shebei = new LinkedHashMap<>();

    private int num_zhanwei;
    private int num_yisao = 0;
    private HashSet<String> set_yisao = new HashSet<>();

    private String testWuliao;

    private ArrayList<String> sqls = new ArrayList<>();

    private int preGongdan;
    private int preShebei;

    private boolean isChazhao = false;
    private boolean isShunxu = false;
    private boolean isShishi = false;
    private Object[] list_key;
    private int i; //记录当前站位key的位置

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void dealData() {
        getData();
    }

    private void getData() {
        alertDialog.show();
        JDBCHelper.getQueryHelper(SQLStatement.getGongdan(), "data", new JDBCHelperQueryListener() {

            @Override
            public void success(ArrayList<HashMap<String, Object>> result, String queryFlag) {
                alertDialog.dismiss();
                data = result;

                Iterator<HashMap<String, Object>> iterator = result.iterator();
                list_gongDan.clear();
                preGongdan = 0;
                preShebei = 0;
                list_gongDan.add("请选择工单");
                while (iterator.hasNext()) {
                    list_gongDan.add(iterator.next().get("gongdan").toString());
                }

                gongDan.setSpinnerList(MainActivity.this, list_gongDan.toArray());
                list_sheBei.clear();
                list_sheBei.add("请选择设备");
                sheBei.setSpinnerList(MainActivity.this, list_sheBei.toArray());

                zhanWei.setText("");
                numZhanWei.setText("");
                numYiSao.setText("");
                zhengQue.setText("");

                shunXu.setVisibility(View.INVISIBLE);
                chaZhao.setVisibility(View.INVISIBLE);

                num_yisao = 0;
            }

            @Override
            public void fail(String error) {
                alertDialog.dismiss();
                CommonMethod.showErrorDialog(MainActivity.this, "工单更新失败，请检查网络" + error, null);
            }
        }).sqlQuery();


    }

    @Override
    public void logicController() {

        gengXin.setOnClickListener((view) -> {
            if (num_yisao == 0) {
                getData();
            } else {
                CommonMethod.showErrorDialog(MainActivity.this, "当前设备还有 " +
                        (num_zhanwei - num_yisao) + " 个站位没有扫描!确认刷新工单？", "继续扫描", null , "刷新工单", this::getData);
            }
        });

        shangChuan.setOnClickListener((view) -> saveData());

        gongDan.getSpinner().setSelection(0, true);
        gongDan.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    if (num_yisao == 0) {
                        changeGongdan(position);
                    } else {
                        if (preGongdan != position) {
                            CommonMethod.showErrorDialog(MainActivity.this, "当前设备还有 " +
                                    (num_zhanwei - num_yisao) + " 个站位没有扫描！确认切换工单？", "继续扫描", () -> gongDan.getSpinner().setSelection(preGongdan), "切换工单", () -> {
                                changeGongdan(position);
                                num_yisao = 0;
                                numYiSao.setText(String.valueOf(num_yisao));
                            });
                        }
                    }
                } else {
                    if (preGongdan != 0) {
                        gongDan.getSpinner().setSelection(preGongdan);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sheBei.getSpinner().setSelection(0, true);
        sheBei.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (gongDan.getSpinner().getSelectedItemId() != 0) {
                    if (position != 0) {
                        if (num_yisao == 0) {
                            changeShebei(position);

                        } else {
                            if (preShebei != position || preGongdan != gongDan.getSpinner().getSelectedItemId()) {
                                CommonMethod.showErrorDialog(MainActivity.this, "当前设备还有 " +
                                                (num_zhanwei - num_yisao) + " 个站位没有扫描！确认切换设备？"
                                        , "继续扫描", () -> sheBei.getSpinner().setSelection(preShebei), "切换设备", () -> {
                                            changeShebei(position);
                                            num_yisao = 0;
                                            numYiSao.setText(String.valueOf(num_yisao));
                                        });
                            }

                        }
                    } else {
                        if (preShebei != 0) {
                            sheBei.getSpinner().setSelection(preShebei);
                        }
                    }
                } else {
                    if (position != 0) {
                        CommonMethod.showErrorDialog(MainActivity.this, "请先选择工单！", null);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        zhanWei.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (sheBei.getSpinner().getSelectedItemId() != 0) {
                if (!zhanWei.getText().isEmpty()) {
                    if (map_shebei.get(zhanWei.getText()) != null) {
                        testWuliao = map_shebei.get(zhanWei.getText());
                        zhengQue.setText(testWuliao);
                        wuLiao.getEditText().requestFocus();
                    } else {
                        CommonMethod.showErrorDialog(MainActivity.this, "站位不存在！", () -> {
                            zhanWei.setText("");
                            zhanWei.getEditText().requestFocus();
                        });
                    }
                }
            } else {
                CommonMethod.showErrorDialog(MainActivity.this, "请先选择设备编号！", () -> {
                    zhanWei.setText("");
                    zhanWei.getEditText().requestFocus();
                });
            }
            return true;
        });

        wuLiao.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (!wuLiao.getText().isEmpty()) {
                if (isChazhao) {
                    if (gongDan.getSpinner().getSelectedItemId() != 0) {
                        if (sheBei.getSpinner().getSelectedItemId() != 0) {
                            if (CommonMethod.getKey(map_shebei, wuLiao.getText()).size() != 0) {
                                CommonMethod.showDialog(MainActivity.this, "条码: \n" +
                                        wuLiao.getText() + "\n对应站位为： "
                                        + getZhanwei() );
                                wuLiao.setText("");
                            } else {

                                CommonMethod.showErrorDialog(MainActivity.this, "经查询此条码没有对应站位！", () -> {
                                    wuLiao.setText("");
                                    wuLiao.getEditText().requestFocus();
                                });
                            }
                        } else {
                            CommonMethod.showErrorDialog(MainActivity.this, "请先选择设备！", () -> {
                                wuLiao.setText("");
                            });
                        }

                    } else {
                        CommonMethod.showErrorDialog(MainActivity.this, "请先选择工单！", () -> {
                            wuLiao.setText("");
                        });
                    }

                } else {
                    if (!zhanWei.getText().isEmpty()) {
                        if (wuLiao.getText().equals(testWuliao)) {

                            if (!set_yisao.contains(zhanWei.getText())) {
                                num_yisao++;
                                numYiSao.setText(String.valueOf(num_yisao));
                                set_yisao.add(zhanWei.getText());

                                //实时上传
                                if (isShishi) {
                                    JDBCHelper.getExecuteHelper(new String[]{SQLStatement.getInsert(gongDan.getText(), sheBei.getText(), zhanWei.getText(), wuLiao.getText())}, 1, (result, errorMessage) -> {
                                        if (result) {
                                            Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            saveSqls();
                                        }
                                    }).sqlExecute();
                                } else {
                                    saveSqls();
                                }
                            }

                            zhanWei.setText("");
                            wuLiao.setText("");
                            zhengQue.setText("");
                            zhanWei.getEditText().requestFocus();

                            if (isChazhao) {
                                chaZhao.setFocusable(false);
                            } else {
                                chaZhao.setVisibility(View.INVISIBLE);
                            }

                            if (isShunxu && i < list_key.length) {
                                shunXu.setEnabled(false);
                                zhanWei.setText(list_key[i].toString());
                                zhengQue.setText(map_shebei.get(list_key[i].toString()));
                                testWuliao = map_shebei.get(list_key[i].toString());
                                wuLiao.getEditText().requestFocus();
                                i++;
                            } else {
                                //隐藏 依次扫描
                                shunXu.setVisibility(View.INVISIBLE);
                            }

                            if (num_yisao == num_zhanwei) {
                                CommonMethod.showRightDialog(MainActivity.this, "当前设备扫描完毕，请切换设备或生产单!");
                                preGongdan = 0;
                                preShebei = 0;
                                Iterator<HashMap<String, Object>> iterator = data.iterator();
                                list_gongDan.clear();
                                list_gongDan.add("请选择工单");
                                while (iterator.hasNext()) {
                                    list_gongDan.add(iterator.next().get("gongdan").toString());
                                }

                                gongDan.setSpinnerList(MainActivity.this, list_gongDan.toArray());
                                list_sheBei.clear();
                                list_sheBei.add("请选择设备");
                                sheBei.setSpinnerList(MainActivity.this, list_sheBei.toArray());

//                        zhanWei.setText("");
//                        wuLiao.setText("");

                                num_zhanwei = 0;
                                num_yisao = 0;
                                numZhanWei.setText(String.valueOf(num_zhanwei));
                                numYiSao.setText(String.valueOf(num_yisao));

                                resetCheck();

                                chaZhao.setVisibility(View.INVISIBLE);
                                shunXu.setVisibility(View.INVISIBLE);

//                                shunXu.setChecked(false);

                            }
                        } else {
                            CommonMethod.showErrorDialog(MainActivity.this, "上料错误！", () -> {
                                wuLiao.setText("");
                                wuLiao.getEditText().requestFocus();
                            });
                        }
                    } else {
                        CommonMethod.showErrorDialog(MainActivity.this, "请先扫描站位！", () -> {
                            zhanWei.getEditText().requestFocus();
                            wuLiao.setText("");
                        });
                    }
                }

            }
            return true;
        });

        zhanWei.getEditText().setOnKeyListener(this);
        wuLiao.getEditText().setOnKeyListener(this);

        chaZhao.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isChazhao = true;

                shunXu.setVisibility(View.INVISIBLE);

                zhanWei.getEditText().setFocusable(false);
                wuLiao.getEditText().requestFocus();
            } else {
                isChazhao = false;
                shunXu.setVisibility(View.VISIBLE);

                zhanWei.getEditText().setFocusable(true);
                zhanWei.getEditText().setFocusableInTouchMode(true);
                zhanWei.getEditText().requestFocus();
            }
        });

        shunXu.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (shunXu.isChecked()) {
                isShunxu = true;

                chaZhao.setVisibility(View.INVISIBLE);

                zhanWei.getEditText().setFocusable(false);
                list_key = map_shebei.keySet().toArray();
                i = 0;
                zhanWei.setText(list_key[i].toString());
                zhengQue.setText(map_shebei.get(list_key[i].toString()));
                testWuliao = map_shebei.get(list_key[i].toString());
                i++;
                wuLiao.getEditText().requestFocus();

            } else {
                isShunxu = false;
                chaZhao.setVisibility(View.VISIBLE);

                zhanWei.getEditText().setFocusable(true);
                zhanWei.getEditText().setFocusableInTouchMode(true);
                zhanWei.getEditText().requestFocus();
                zhanWei.setText("");
                zhengQue.setText("");
            }
        });

        shiShi.setOnCheckedChangeListener((buttonView, isChecked) -> isShishi = isChecked);
    }

    private String getZhanwei() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String i: CommonMethod.getKey(map_shebei, wuLiao.getText())) {
            stringBuilder.append("\n");
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

    private void changeGongdan(int position) {
        Iterator<HashMap<String, Object>> iterator = data.iterator();
        list_sheBei.clear();
        list_sheBei.add("请选择设备");
        while (iterator.hasNext()) {
            HashMap<String, Object> temp = iterator.next();
            if (temp.get("gongdan").toString().equals(gongDan.getText())) {
                list_sheBei.add(temp.get("shebei").toString());
            }
        }

        sheBei.setSpinnerList(MainActivity.this, list_sheBei.toArray());

        //更新统计数据
        num_zhanwei = 0;
        numZhanWei.setText(String.valueOf(num_zhanwei));
        numYiSao.setText(String.valueOf(num_yisao));

        zhanWei.setText("");
        wuLiao.setText("");
        numZhanWei.setText("");
        numYiSao.setText("");
        zhengQue.setText("");

        preGongdan = position;
//                        sheBei.getSpinner().setSelection(0);
        preShebei = 0;


        resetCheck();
        chaZhao.setVisibility(View.INVISIBLE);
        shunXu.setVisibility(View.INVISIBLE);
    }

    private void changeShebei(int position) {
        Iterator<HashMap<String, Object>> iterator = data.iterator();
        map_shebei.clear();
        while (iterator.hasNext()) {
            HashMap<String, Object> temp = iterator.next();
            if (temp.get("gongdan").toString().equals(gongDan.getText()) &&
                    temp.get("shebei").toString().equals(sheBei.getText())) {
                map_shebei.put(temp.get("zhanwei").toString(), temp.get("wuliao").toString());
            }
        }

        num_zhanwei = map_shebei.size();
        //置空检查
        set_yisao = new HashSet<>();

        numZhanWei.setText(String.valueOf(num_zhanwei));
        numYiSao.setText(String.valueOf(num_yisao));

        preShebei = position;
//                            preGongdan = (int) gongDan.getSpinner().getSelectedItemId();

        //打开选择权限
        resetCheck();

        zhanWei.setText("");
        wuLiao.setText("");
        zhanWei.getEditText().requestFocus();
    }


    private void resetCheck() {
        chaZhao.setVisibility(View.VISIBLE);
        shunXu.setVisibility(View.VISIBLE);
        shunXu.setEnabled(true);
        chaZhao.setFocusable(true);
        chaZhao.setChecked(false);
        shunXu.setChecked(false);
    }

    private void saveSqls() {
        sqls.add(SQLStatement.getInsert(gongDan.getText(), sheBei.getText(), zhanWei.getText(), wuLiao.getText()));
    }

    //上传数据按钮
    private void saveData() {
        if (sqls.size() != 0) {
            if (num_yisao == 0) {
                excuteDate();
            } else {
                CommonMethod.showErrorDialog(MainActivity.this, "当前设备还有 " +
                                (num_zhanwei - num_yisao) + " 个站位没有扫描！"
                        , "继续扫描", null, "确认上传", () -> {
                            excuteDate();
                            getData();
                        });
            }
        } else {
            CommonMethod.showDialog(MainActivity.this, "数据已全部上传！");
        }
    }

    private void excuteDate() {
        alertDialog.show();
        String[] str_sqls = sqls.toArray(new String[0]);
        JDBCHelper.getExecuteHelper(str_sqls, 1, (result, errorMessage) -> {
            alertDialog.dismiss();
            if (result) {
                CommonMethod.showRightDialog(MainActivity.this, "数据上传成功！");
                sqls.clear();
            } else {
                CommonMethod.showErrorDialog(MainActivity.this, "数据上传失败！请检查网络！\n" + "错误信息：" + errorMessage, null);
            }
        }).sqlExecute();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - firstClick > 2000) {
                firstClick = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                if (sqls.isEmpty()) {
                    System.exit(0);
                } else {
                    CommonMethod.showErrorDialog(MainActivity.this, "还有" +
                            sqls.size() + "条数据未上传！请上传扫描数据！", null);
                }

            }
            return true;
        }
        return false;
    }

    //处理监听事件处理两次的问题
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            Log.e("MainActivity", "onKey: 按下回车键");
            return true;
        }
        return false;
    }


    private class SBInfo {
        String zhanwei;
        String wuliao;

        public SBInfo(String zhanwei, String wuliao) {
            this.zhanwei = zhanwei;
            this.wuliao = wuliao;
        }

        public String getZhanwei() {
            return zhanwei;
        }

        public String getWuliao() {
            return wuliao;
        }
    }
}
