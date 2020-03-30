package p.gordenyou.pdalibrary.net;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import p.gordenyou.pdalibrary.listener.JDBCHelperExecuteListener;
import p.gordenyou.pdalibrary.listener.JDBCHelperQueryListener;

public class JDBCHelper {
    public static String USERNAME = "sa";
    public static String PASSWORD = "lhfz123456";
    private final static String CLASSNAME = "net.sourceforge.jtds.jdbc.Driver";
    public static String URL = "jdbc:jtds:sqlserver://121.35.98.225:10874;DatabaseName=LianHe";

    private static Connection con;
    private static JDBCHelper instance;

    //********所需参数***************
    private String[] params;

    private static int column;
    private static JDBCHelperExecuteListener executeListener;

    private static String queryFlag;
    private static JDBCHelperQueryListener queryListener;
    //********所需参数***************

    /**
     * 获取操作（增加，删除，更新）实例
     *
     * @param params   sql语句
     * @param column   预计影响行数
     * @param listener 回调接口
     * @return JDBCHelper实例
     */
    public static JDBCHelper getExecuteHelper(String[] params, int column, JDBCHelperExecuteListener listener) {
        if (instance == null) {
            synchronized (JDBCHelper.class){
                if(instance == null){
                    instance = new JDBCHelper(params, column, listener, null, null);
                }
            }

        } else {
            instance.setParams(params);
            instance.setColumn(column);
            instance.setExecuteListener(listener);
        }
        return instance;
    }

    /**
     * 获取查询实例
     *
     * @param params    sql语句
     * @param queryFlag 查询标记
     * @param listener  回调接口
     * @return JDBCHelper实例
     */
    public static JDBCHelper getQueryHelper(String[] params, String queryFlag, JDBCHelperQueryListener listener) {
        if (instance == null) {
            synchronized (JDBCHelper.class){
                if(instance == null){
                    instance = new JDBCHelper(params, 0, null, queryFlag, listener);
                }
            }

        } else {
            instance.setParams(params);
            instance.setQueryFlag(queryFlag);
            instance.setQueryListener(listener);
        }
        return instance;
    }

    private JDBCHelper(String[] params, int column, JDBCHelperExecuteListener executeListener, String queryFlag, JDBCHelperQueryListener queryListener) {
        this.params = params;
        JDBCHelper.column = column;
        JDBCHelper.executeListener = executeListener;
        JDBCHelper.queryFlag = queryFlag;
        JDBCHelper.queryListener = queryListener;
    }

    private void setParams(String[] params) {
        this.params = params;
    }

    private void setColumn(int column) {
        JDBCHelper.column = column;
    }

    private void setExecuteListener(JDBCHelperExecuteListener executeListener) {
        JDBCHelper.executeListener = executeListener;
    }

    private void setQueryFlag(String queryFlag) {
        JDBCHelper.queryFlag = queryFlag;
    }

    private void setQueryListener(JDBCHelperQueryListener queryListener) {
        JDBCHelper.queryListener = queryListener;
    }

    public void sqlExecute() {
        new SQLServerExecute().execute(params);
    }

    public void sqlQuery() {
        new SQLServerQuery().execute(params);
    }

    private static class SQLServerExecute extends AsyncTask<String, Integer, Void> {
        boolean result = false;
        String errorMessage = "";

        @Override
        protected Void doInBackground(String... params) {

            try {
                if (con == null || con.isClosed()) {
                    // 使用Class加载驱动程序
                    Class.forName(CLASSNAME);
                    //连接数据库
                    con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("加载驱动程序出错");
                errorMessage = e.getMessage();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                errorMessage = e.getMessage();
            }

            try {
                Thread.sleep(1000);
//                String sql = "Update TCcang set rong = 1.0 where cang = '备件仓'";
                //创建Statement,操作数据
                PreparedStatement state;
                if (con != null && params.length != 0) {
                    for (String param : params) {
                        state = con.prepareStatement(param);
                        if (state != null) {
                            //executeUpdate 返回的是影响的行数
                            con.setAutoCommit(false);
                            if (state.executeUpdate() != column) {
                                result = false;
                                errorMessage = "操作行数不是预计行数";
                                con.rollback();
                                state.close();
                                break;
                            }
                            state.close();
                        }
                    }
                    result = true;
                    con.commit();
                    con.setAutoCommit(true);

                    //回收资源
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            executeListener.result(result, errorMessage);
        }
    }

    private static class SQLServerQuery extends AsyncTask<String, Integer, Void> {
        PreparedStatement state = null;
        ArrayList<HashMap<String, Object>> list;
        String errorMessage = "";

        @Override
        protected Void doInBackground(String... params) {

            try {
                if (con == null || con.isClosed()) {
                    // 使用Class加载驱动程序
                    Class.forName(CLASSNAME);
                    //连接数据库
                    con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("加载驱动程序出错");
                errorMessage = e.getMessage();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                errorMessage = e.getMessage();
            }

            try {
                Thread.sleep(1000);
//                String sql = "Select * from TCcang where wei = '113'";
                if (con != null && params.length != 0) {
                    state = con.prepareStatement(params[0]);
                    if (state != null) {
                        ResultSet resultData = state.executeQuery();
                        list = convertList(resultData);
//                        if (list.size() == 0)
//                            errorMessage = "经查询，没有数据";
                        state.close();
                    }
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (errorMessage.equals("")) {
                queryListener.success(list, queryFlag);
            } else {
                queryListener.fail(errorMessage);
            }
        }
    }

    /**
     * ResultSet 转化为ArrayList<HashMap<String, Object>>
     *
     * @param resultSet 数据源
     * @return ArrayList对象
     * @throws SQLException SQL操作异常
     */
    private static ArrayList<HashMap<String, Object>> convertList(ResultSet resultSet) throws SQLException {

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = resultSet.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (resultSet.next()) {
            HashMap<String, Object> rowData = new HashMap<>();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), resultSet.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }
}
