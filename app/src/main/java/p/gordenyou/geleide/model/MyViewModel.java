package p.gordenyou.geleide.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import p.gordenyou.pdalibrary.common.CommonMethod;
import p.gordenyou.pdalibrary.unity.UserInfo;

/**
 * Created by GORDENyou on 2020/2/2.
 * mailbox:1193688859@qq.com
 * have nothing but……
 */
public class MyViewModel extends ViewModel {
    private MutableLiveData<String> barcode;
    private MutableLiveData<String> liaohao;//料号
    private MutableLiveData<String> testLiaohao;//料号
    private MutableLiveData<Double> result;//数量（改变后）
    private MutableLiveData<Double> number;
    private MutableLiveData<String> kuwei;
    private MutableLiveData<String> xinKuwei;
    private MutableLiveData<String> danhao;//入库单号
    private MutableLiveData<String> shengchandan;//生产单
    private MutableLiveData<String> shebeihao;//设备号
    private MutableLiveData<String> gongyi;//工艺
    private MutableLiveData<String> chengpinhao;//成品号
    private MutableLiveData<String> rtime;//入库时间

    private MutableLiveData<String> username;
    private MutableLiveData<String> password;

    private static String[] sqls;

    public MutableLiveData<String> getBarcode() {
        if (barcode == null) {
            barcode = new MutableLiveData<>();
            barcode.setValue("");
        }
        return barcode;
    }

    public MutableLiveData<String> getLiaohao() {
        if (liaohao == null) {
            liaohao = new MutableLiveData<>();
            liaohao.setValue("");
        }
        return liaohao;
    }

    public MutableLiveData<String> getTestLiaohao() {
        if (testLiaohao == null) {
            testLiaohao = new MutableLiveData<>();
            testLiaohao.setValue("");
        }
        return testLiaohao;
    }

    public MutableLiveData<Double> getResult() {
        if (result == null) {
            result = new MutableLiveData<>();
            result.setValue(null);
        }
        return result;
    }

    public MutableLiveData<Double> getNumber() {
        if (number == null) {
            number = new MutableLiveData<>();
            number.setValue(null);
        }
        return number;
    }

    public MutableLiveData<String> getKuwei() {
        if (kuwei == null) {
            kuwei = new MutableLiveData<>();
            kuwei.setValue("");
        }
        return kuwei;
    }

    public MutableLiveData<String> getXinKuwei() {
        if (xinKuwei == null) {
            xinKuwei = new MutableLiveData<>();
            xinKuwei.setValue("");
        }
        return xinKuwei;
    }

    public String getDanhao(String front) {
        return front + CommonMethod.getListTime();
    }

    public MutableLiveData<String> getDanhaoInstance() {
        if (danhao == null) {
            danhao = new MutableLiveData<>();
            danhao.setValue("");
        }
        return danhao;
    }

    public MutableLiveData<String> getShengchandan() {
        if (shengchandan == null) {
            shengchandan = new MutableLiveData<>();
            shengchandan.setValue("");
        }
        return shengchandan;
    }

    public MutableLiveData<String> getShebeihao() {
        if (shebeihao == null) {
            shebeihao = new MutableLiveData<>();
            shebeihao.setValue("");
        }
        return shebeihao;
    }

    public MutableLiveData<String> getGongyi() {
        if (gongyi == null) {
            gongyi = new MutableLiveData<>();
            gongyi.setValue("");
        }
        return gongyi;
    }

    public MutableLiveData<String> getChengpinhao() {
        if (chengpinhao == null) {
            chengpinhao = new MutableLiveData<>();
            chengpinhao.setValue("");
        }
        return chengpinhao;
    }

    public MutableLiveData<String> getRtime() {
        if (rtime == null) {
            rtime = new MutableLiveData<>();
            rtime.setValue("");
        }
        return rtime;
    }

    public MutableLiveData<String> getUsername() {
        if (username == null) {
            username = new MutableLiveData<>();
            username.setValue("");
        }
        return username;
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<>();
            password.setValue("");
        }
        return password;
    }

    public void setBarcode(String barcode) {
        this.barcode.setValue(barcode);
    }

    public String[] sqlLogin() {
        sqls = new String[1];
        sqls[0] = "select uu.userID,uu.username,uu.Xingming,pp.FuncName from UbUser uu left join UbPower pp on uu.userID=pp.UserID where uu.username='" +
                getUsername().getValue() + "' and uu.pwd='" +
                getPassword().getValue() + "'";
        return sqls;
    }

    public String[] sqlBarcode() {
        sqls = new String[1];
        sqls[0] = "select * from TCcunliao where bar = '" +
                getBarcode().getValue() + "'";
        return sqls;
    }

    public String[] sqlCPBarcode() {
        sqls = new String[1];
        sqls[0] = "select * from TCcunpin where bar = '" +
                getBarcode().getValue() + "' and cang='成品仓'";
        return sqls;
    }

    public String[] sqlDCLBarcodeCP() {
        sqls = new String[1];
        sqls[0] = "select * from TCmidpin where bar = '" +
                getBarcode().getValue() + "' and lei='成品出库'";
        return sqls;
    }

    public String[] sqlDCLBarcodeDCL() {
        sqls = new String[1];
        sqls[0] = "select * from TCcunpin where bar = '" +
                getBarcode().getValue() + "' and cang ='待处理仓'";
        return sqls;
    }

    public String[] sqlGongdan() {
        sqls = new String[1];
        sqls[0] = "select * from TCgongdan where gongdan = '" +
                getShengchandan().getValue() + "'";
        return sqls;
    }

    public String[] sqlCPGongdan() {
        sqls = new String[1];
        sqls[0] = "select * from TCgongdan where gongdan = '" +
                getShengchandan().getValue() + "'";
        return sqls;
    }

    public String[] sqlXSGongdan() {
        sqls = new String[1];
        sqls[0] = "select * from TCxiaoshou where xsdan = '" +
                getShengchandan().getValue() + "'";
        return sqls;
    }

    public String[] yl_Ruku() {
        sqls = new String[2];
        sqls[0] = "insert into TCcunliao (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'原料仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" + //todo 这里有点问题 数量是否需要计算
                getKuwei().getValue() + "')";

        sqls[1] = "insert into TCmidliao (ctime,rtime,lei,cman,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),CONVERT(varchar(24), GETDATE(), 20),'原料入库','" +
                UserInfo.USERNAME + "','" + //todo 登陆界面，用户名来源
                getDanhao("YLRK") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] yl_BCPRuku() {
        sqls = new String[2];
        sqls[0] = "insert into TCcunliao (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'半成品仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" + //todo 这里有点问题 数量是否需要计算
                getKuwei().getValue() + "')";

        sqls[1] = "insert into TCmidliao (ctime,rtime,lei,cman,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),CONVERT(varchar(24), GETDATE(), 20),'半成品入库','" +
                UserInfo.USERNAME + "','" + //todo 登陆界面，用户名来源
                getDanhao("BCRK") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] yl_Tuihuo() {
        sqls = new String[2];
        sqls[0] = "delete from TCcunliao where bar='" +
                getBarcode().getValue() + "'";
        sqls[1] = "insert into TCmidliao (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'原料退货','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("YLTH") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] yl_Lingliao() {
        sqls = new String[3];
        sqls[0] = "delete from TCcunliao where bar='" +
                getBarcode().getValue() + "'";
        sqls[1] = "insert into TCmidliao (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'原料领料','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("YLLL") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        sqls[2] = "insert into TCcunliao (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'生产临时仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] yl_Shangliao() {
        sqls = new String[3];
//        sqls[0] = "select liao from TCgongdan where Gongdan='" +
//                getShengchandan().getValue() + "' and liao='" +
//                getLiaohao().getValue() + "'";
        sqls[0] = "delete from TCcunliao where bar='" +
                getBarcode().getValue() + "'";
        sqls[1] = "update TCgongdan set ling=ling+" +
                getNumber().getValue() + " where Gongdan='" +
                getShengchandan().getValue() + "' and liao='" +
                getLiaohao().getValue() + "'";
        sqls[2] = "insert into TCliucheng (ctime,cman,gongdan,bar,shebei,gongyi) values (CONVERT(varchar(24), GETDATE(), 20),'" +
                UserInfo.USERNAME + "','" +
//                getRtime().getValue() + "','" +
                getShengchandan().getValue() + "','" +
                getBarcode().getValue() + "','" +
                getShebeihao().getValue() + "','" +
                getGongyi().getValue() + "')";
        return sqls;
    }

    public String[] yl_Chengzhong() {
        sqls = new String[1];
        sqls[0] = "insert into TCcunliao (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'生产临时仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "')";
        return sqls;
    }

    public String[] yl_Tuiliao() {
        sqls = new String[3];
        sqls[0] = "delete from TCcunliao where bar='" +
                getBarcode().getValue() + "'";
        sqls[1] = "insert into TCcunliao (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'原料仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getXinKuwei().getValue() + "')";
        sqls[2] = "insert into TCmidliao (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'原料退料','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("YLTL") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] cp_Ruku() {
        sqls = new String[2];
        sqls[0] = "insert into TCcunpin (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'成品仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "')";
        sqls[1] = "insert into TCmidpin (ctime,rtime,lei,cman,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),CONVERT(varchar(24), GETDATE(), 20),'成品入库','" +
                UserInfo.USERNAME + "','" +
                getDanhao("CPRK") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] cp_Chuku() {
        sqls = new String[2];
        sqls[0] = "delete from TCcunpin where bar='" +
                getBarcode().getValue() + "' and cang = '成品仓'";
        sqls[1] = "insert into TCmidpin (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'成品出库','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("CPCK") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] dcl_Tuihuo() {
        sqls = new String[2];
        sqls[0] = "insert into TCcunpin (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'待处理仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "')";
        sqls[1] = "insert into TCmidpin (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'待处理入库','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("CDRK") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] dcl_Ruku() {
        sqls = new String[3];
        sqls[0] = "delete from TCcunpin where bar='" +
                getBarcode().getValue() + "' and cang='待处理仓'";
        sqls[1] = "insert into TCmidpin (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'待处理换标','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("CDHB") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        sqls[2] = "insert into TCcunpin (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'成品仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }

    public String[] dcl_Zaijiagong() {
        sqls = new String[3];
        sqls[0] = "delete from TCcunpin where bar='" +
                getBarcode().getValue() + "' and cang='待处理仓'";
        sqls[1] = "insert into TCmidpin (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'待处理加工','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("CDZC") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        sqls[2] = "insert into TCcunpin (rtime,cang,bar,liaohao,shu,wei) values (CONVERT(varchar(24), GETDATE(), 20),'生产临时仓','" +
                getBarcode().getValue() + "','" +
                getLiaohao().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "')";
        return sqls;
    }

    public String[] dcl_zuofei() {
        sqls = new String[2];
        sqls[0] = "delete from TCcunpin where bar='" +
                getBarcode().getValue() + "' and cang='待处理仓'";
        sqls[1] = "insert into TCmidpin (ctime,lei,cman,rtime,danju,bar,shu,wei,weito) values (CONVERT(varchar(24), GETDATE(), 20),'待处理作废','" +
                UserInfo.USERNAME + "','" +
                getRtime().getValue() + "','" +
                getDanhao("CDZF") + "','" +
                getBarcode().getValue() + "'," +
                getNumber().getValue() + ",'" +
                getKuwei().getValue() + "','" +
                getXinKuwei().getValue() + "')";
        return sqls;
    }
}
