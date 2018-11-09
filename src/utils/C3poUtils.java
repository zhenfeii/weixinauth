package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 连接池的意义很重大：
 * 1、不必每次去查询一条数据就去创建一次连接。
 * 2、大大提高连接效率
 * Created by Administrator on  2018/7/20
 */
@WebListener
public class C3poUtils {

    private static ComboPooledDataSource dataSource = null;

    //只被创建一次
    static {
        dataSource = new ComboPooledDataSource();
        try {
            Properties properties = getProperties();

            dataSource.setDriverClass(properties.getProperty("driverclass"));
            dataSource.setJdbcUrl(properties.getProperty("url"));
            dataSource.setUser(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setInitialPoolSize(Integer.parseInt(properties.getProperty("initialSize")));
            dataSource.setMaxStatements(Integer.parseInt(properties.getProperty("MaxStatements")));
            dataSource.setMaxStatementsPerConnection(Integer.parseInt(properties.getProperty("MaxStatementsPerConnection")));
            dataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty("MaxPoolSize")));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从配置文件中读取信息
     * @return
     */
    private static Properties getProperties() {
        Properties prop = new Properties();
        try {
            InputStream is = C3poUtils.class.getClassLoader().getResourceAsStream("db.properties");
            prop.load(is);
            if (is != null) is.close();
        } catch (Exception e) {
        }
        return prop;
    }

    public static PreparedStatement getPreparedStatement(String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            ps = dataSource.getConnection().prepareStatement(sql);
            int i = 1;
            if (args != null & args.length > 0) {
                for (Object object : args) {
                    ps.setObject(i, object);
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    /**
     * 执行
     *
     * @param sql
     * @param args
     */
    public static void execute(String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            ps = dataSource.getConnection().prepareStatement(sql);
            int i = 1;
            if (args != null & args.length > 0) {
                for (Object object : args) {
                    ps.setObject(i, object);
                    i++;
                }
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     *
     * @param sql
     */
    public static ResultSet executeQuery(String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            ps = dataSource.getConnection().prepareStatement(sql);
            int i = 1;
            if (args != null & args.length > 0) {
                for (Object object : args) {
                    ps.setObject(i, object);
                    i++;
                }
            }
            ResultSet resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}