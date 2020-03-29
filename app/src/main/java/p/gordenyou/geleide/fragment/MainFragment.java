package p.gordenyou.geleide.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p.gordenyou.geleide.R;
import p.gordenyou.pdalibrary.common.CommonMethod;
import p.gordenyou.pdalibrary.listener.JDBCHelperQueryListener;
import p.gordenyou.pdalibrary.net.JDBCHelper;
import p.gordenyou.pdalibrary.unity.SystemUser;
import p.gordenyou.pdalibrary.unity.UserInfo;


public class MainFragment extends Fragment {

//    @BindView(R.id.fragment_user)
//    TextView user;
//    @BindView(R.id.fragment_username)
//    TextView username;
//    @BindView(R.id.fragment_companycode)
//    TextView companycode;


    private List<Map<String, Object>> m_ltMenuList;
    private String[] m_sMenuText;
    private int[] m_nMenuIcon;


    private String st_usercardid, st_userid, st_username, st_companycode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
//        ButterKnife.bind(this, view);

        initViews();
//        setUserinfo();
        InitMenuList(SystemUser.GetFunList().trim());
        GridView m_gvGridView = view.findViewById(R.id.fragment_gvMentItem);
        //新建List
        m_ltMenuList = new ArrayList<>();
        //获取数据
        GetMenuItem();
        //新建适配器
        String[] sFrom = {"ivMenuIcon", "tvMenuText"};
        int[] nTo = {R.id.ivMenuIcon, R.id.tvMenuText};
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        SimpleAdapter m_saSimpleAdapter = new SimpleAdapter(getContext(), m_ltMenuList, R.layout.itemmenu, sFrom, nTo);
//        }
        //配置适配器
        m_gvGridView.setAdapter(m_saSimpleAdapter);
        m_gvGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),m_sMenuText[position],Toast.LENGTH_LONG).show();
                OnMenuItemClick(m_sMenuText[position]);
            }
        });

        return view;
    }

//    private void setUserinfo() {
//        user.setText(st_userid);
//        username.setText(st_username);
//        companycode.setText("公司代码:" + st_companycode);
//    }

    private void initViews() {
        st_userid = UserInfo.USERID + "";
        st_username = UserInfo.USERNAME;
        st_usercardid = UserInfo.USERCARDID;
        st_companycode = UserInfo.COMPANYCODE;
    }

    private void InitMenuList(String StrList) {
        String[] FunStr = new String[21];
        for (int i = 0; i < 21; i++) {
            FunStr[i] = "00" + (7001 + i);
        }
//        String FunStr[] = StrList.split(",");
        m_sMenuText = new String[FunStr.length];
        m_nMenuIcon = new int[FunStr.length];

        for (int i = 0; i < FunStr.length; i++) {
            if (FunStr[i].trim().length() == 6) {
                GetFunImageId(FunStr[i].trim());
//                m_sMenuText[i] = FunList.GetFunName();
//                m_nMenuIcon[i] = FunList.GetImagetId();
            }
        }
    }

    public void GetMenuItem() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ivMenuIcon", m_nMenuIcon[i]);
            map.put("tvMenuText", m_sMenuText[i]);
            m_ltMenuList.add(map);
        }
    }

    private void GetFunImageId(String FunId) {
//        switch (FunId) {
//            case "007001":
//                FunList.SetFunId(FunId);
//                FunList.SetImageId(R.drawable.kucun);
//                FunList.SetFunName("库存统计查询");
//                break;
//            case "007002":
//                FunList.SetFunId(FunId);
//                FunList.SetImageId(R.drawable.ruku);
//                FunList.SetFunName("入库统计");
//                break;
//            case "007003":
//                FunList.SetFunId(FunId);
//                FunList.SetImageId(R.drawable.warm);
//                FunList.SetFunName("预警");
//                break;
//            case "007004":
//                FunList.SetFunId(FunId);
//                FunList.SetImageId(R.drawable.plan);
//                FunList.SetFunName("计划");
//                break;
//            case "007005":
//                FunList.SetFunId(FunId);
//                FunList.SetImageId(R.drawable.database);
//                FunList.SetFunName("数据库概况");
//                break;
//        }
    }

    public void OnMenuItemClick(String sMenuItem) {
        Intent intent = new Intent();
        Log.i("MainActivity", sMenuItem);
        switch (sMenuItem) {
            case "库存统计查询":
//                intent.setClass(getActivity(), MaterielsActivity.class);
//                intent.putExtra("userid", st_userid);
//                startActivity(intent);
                JDBCHelper jdbcHelper1 = JDBCHelper.getExecuteHelper(null, 1
                        , (result, errorMessage) -> CommonMethod.showResultDialog(getContext(), result, errorMessage));
                jdbcHelper1.sqlExecute();
                break;
            case "入库统计":
//                intent.setClass(getActivity(), InStockActivity.class);
//                startActivity(intent);
                JDBCHelper jdbcHelper = JDBCHelper.getQueryHelper(null, null, new JDBCHelperQueryListener() {
                    @Override
                    public void success(ArrayList<HashMap<String, Object>> result, String queryFlag) {
                        Toast.makeText(getContext(), result.get(0).get("wei").toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(String error) {
                        CommonMethod.showDialog(getContext(), error);
                    }
                });
                jdbcHelper.sqlQuery();
                break;
            case "预警":
//                intent.setClass(getActivity(), WarmActivity.class);
                startActivity(intent);
                break;
            case "计划":
//                intent.setClass(getActivity(), PlanActivity.class);
                startActivity(intent);
                break;
            case "数据库概况":
//                intent.setClass(getActivity(), DatabaseActivity.class);
                startActivity(intent);
                break;
//            case "资产盘点":
//                intent.setClass(getActivity(), Activity_ZCPD.class);
//                startActivity(intent);
//                break;
//            case "采购入库":
//                intent.setClass(getActivity(), Activity_JSRK_James.class);
//                startActivity(intent);
//                break;
//            case "紧急发料":
//                intent.setClass(getActivity(), Activity_JJFL.class);
//                startActivity(intent);
//                break;
//            case "产品序号绑定":
//                intent.setClass(getActivity(), Activity_XHBD.class);
//                startActivity(intent);
//                break;
////            case "自制件入库接收确认":
////                intent.setClass(getActivity(), Activity_ZZJRKJSQR.class);
////                startActivity(intent);
////                break;
////            case "自制件退库":
////                intent.setClass(getActivity(), Activity_ZZJTK.class);
////                startActivity(intent);
////                break;
////            case "转仓出库":
////                intent.setClass(getActivity(), Activity_ZCCK.class);
////                startActivity(intent);
////                break;
////            case "转仓接收":
////                intent.setClass(getActivity(), Activity_ZCJS.class);
////                startActivity(intent);
////                break;
////            case "库存盘点":
////                intent.setClass(getActivity(), Activity_PD.class);
////                startActivity(intent);
////                break;
//            case "差异调整":
//                break;
//            case "移位":
//                intent.setClass(getActivity(), Activity_YW.class);
//                startActivity(intent);
//                break;
////            case "装配物料接收确认":
////                intent.setClass(getActivity(), Activity_ZPWLJSQR.class);
////                startActivity(intent);
////                break;
////            case "装配退料":
////                intent.setClass(getActivity(), Activity_ZPTL.class);
////                startActivity(intent);
////                break;
////            case "自制件入库":
////                Intent intent1 = new Intent(getActivity(), Activity_ZZJRK.class);
////                startActivity(intent1);
////                break;
//            case "自制件退库接收确认":
//                break;
//
        }
    }
}
