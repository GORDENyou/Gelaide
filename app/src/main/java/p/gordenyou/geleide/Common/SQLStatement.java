package p.gordenyou.geleide.Common;

import p.gordenyou.pdalibrary.common.CommonMethod;
import p.gordenyou.pdalibrary.unity.UserInfo;

/**
 * Created by GORDENyou on 2020/2/3.
 * mailbox:1193688859@qq.com
 * have nothing but……
 */
public class SQLStatement {

    public static String[] getLogin(String username, String password) {
        String[] sqls = new String[1];
        sqls[0] = "select * from UbUser where username = '" +
                username + "' and pwd = '" +
                password + "'";
        return sqls;
    }


    public static String[] getGongdan() {
        String[] sqls = new String[1];
        sqls[0] = "select * from Fzhanliao where state='生产'";
        return sqls;
    }

    public static String[] getShebei(String gongdan) {
        String[] sqls = new String[1];
        sqls[0] = "select shebei from Fzhanliao where state='生产' and gongdan = '" +
                gongdan + "'";
        return sqls;
    }

    public static String[] getZhanwei(String gongdan, String shebei) {
        String[] sqls = new String[1];
        sqls[0] = "select zhanwei, wuliao from Fzhanliao where state='生产' and gongdan = '" +
                gongdan + "' and shebei = '" +
                shebei + "'";
        return sqls;
    }

    public static String getInsert(String gongdan, String shebei, String zhanwei, String wuliao) {
        return "insert into Fshangliao (ctime,cman,gongdan,shebei,zhanwei,wuliao) values ('" +
                CommonMethod.getTime() + "','" +
                UserInfo.USERNAME + "','" +
                gongdan + "','" +
                shebei + "','" +
                zhanwei + "','" +
                wuliao + "')";
    }
}
