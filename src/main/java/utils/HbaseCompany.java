package utils;

import java.sql.*;

/**
 * @jiguiyang
 *
 * phenoix 操作HBASE
 */
public class HbaseCompany {

    private static final String PHOENIXDRIVER = "jdbc:phoenix:172.16.236.91,172.16.236.92,172.16.236.93:2181:/hbase-unsecure";
    /*
    Hbase phenoix 连接
     */
    public static Connection getConnection(){
        try {
            Connection connection = null;
            connection= DriverManager.getConnection(PHOENIXDRIVER);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
