package com.andy.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public abstract class DBUtil {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    //定义数据库账号
    private static final String USERNAME = "andy";
    //定义数据库密码
    private static final String PASSWORD = "andy";
    //访问的地址
    private static final String URL = "jdbc:mysql://localhost:3306/pachong";
    
    // 创建数据源
    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);//注册驱动
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);//定义连接
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }

        return conn;
    }

    // 关闭资源
    public static void close(ResultSet rs, Statement stm, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close(); // 关连接的方法和过去一样,由于数据源返回的Connection对象是代理对象,所以close方法被处理
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // 关闭资源
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            	throw new RuntimeException(e);
            }
        }
    }

    // 万能更新(可以进行添加,更新,删除三种操作)
    public static int update(String sql, Object... params) {
        int result = 0;
        QueryRunner qr = new QueryRunner(); // 是一个线程不安全的类
        Connection conn=getConn();
        try {
            result = qr.update(conn, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            close(conn);
        }
        
        return result;
    }

    // 添加数据,并将生成的自增ID返回
    public static int addWithId(String sql, Object... params) {
        int autoId = 0;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            stm = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                stm.setObject(i + 1, params[i]);
            }

            // 执行添加操作
            stm.executeUpdate();

            // 取出生成的自增ID
            ResultSet rsKey = stm.getGeneratedKeys();
            rsKey.next();
            autoId = rsKey.getInt(1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, stm, conn);
        }
        return autoId;
    }

    // 查询出一个单个的对象
    public static <T> T getSingleObj(String sql, Class<T> clazz,
            Object... params) {
        QueryRunner qr = new QueryRunner();
        T result = null;
        Connection conn=getConn();
        try {
            result = qr.query(conn, sql, new BeanHandler<T>(clazz),
                    params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            close(conn);
        }

        return result;
    }

    // 查询出对象列表(以ArrayList的方式返回),注意,如果没有查询到数据,该方法返回一个空列表,而不是null
    public static <T> List<T> getList(String sql, Class<T> clazz,
            Object... params) {
        List<T> list = new ArrayList<T>();
        QueryRunner qr = new QueryRunner();
        Connection conn =getConn();
        
        try {
            list = qr.query(conn, sql, new BeanListHandler<T>(clazz), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            close(conn);
        }

        return list;
    }

    // 返回Map集合(该方法只将一条数据返回为Map集合,key为字段名称,value为字段值)
    public static Map<String, Object> getMap(String sql, Object... params) {
        Map<String, Object> m = null;
        QueryRunner qr = new QueryRunner();
        Connection conn =getConn();
        
        try {
            m = qr.query(conn, sql, new MapHandler(), params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            close(conn);
        }

        return m;
    }

    // 返回一个List集合,其中每条数据都被封装成了一个Map集合,
    public static List<Map<String, Object>> getMapList(String sql,
            Object... params) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        QueryRunner qr = new QueryRunner();
        Connection conn =getConn();

        try {
            mapList = qr.query(conn, sql, new MapListHandler(),
                    params);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            close(conn);
        }

        return mapList;
    }

    // 返回单行单个数据,该方法可以用来查询记录数(这时请使用Long型进行接收),单个字段值等数据
    public static <T> T getScalar(String sql, Object... obj) {
        T result = null;
        QueryRunner qr = new QueryRunner();
        Connection conn = getConn();
        try {
            result = qr.query(conn, sql, new ScalarHandler<T>(1), obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            close(conn);
        }

        return result;
    }

}