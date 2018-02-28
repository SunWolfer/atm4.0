package com.feicui.com.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.feicui.com.exception.AtmException;

/**
 * 用于连接数据库的工具类
 * 
 * @author Benzolamps
 *
 */
public class DatabaseUtils {

    private static Connection connection;

    private static boolean inited = false;

    private static void init() throws ClassNotFoundException, IOException, SQLException {
        if (inited) {
            return;
        }
        
        inited = true;
        
        Class.forName("com.mysql.jdbc.Driver");
		
    }

    public DatabaseUtils() throws ClassNotFoundException, IOException, SQLException {
        
    	super();
        DatabaseUtils.init();
    }

    /**
     * 根据对象类型执行查询语句, 并返回指定类型对象的List
     * @param clazz 对象的类型
     * @param sql   SQL语句
     * @param args  参数列表
     * @param <T>   对象的类型
     * @return 指定类型对象的List
     */
    public <T> List<T> queryData(Class<T> clazz, String sql, String... args)
        throws SQLException, ReflectiveOperationException {
    	
        connect();

        List<T> list = new ArrayList<>();
        
        ResultSet resultSet = generateStatement(sql, args).executeQuery();
       
        while (resultSet.next()) {
            T t = getCurrentResultData(clazz, resultSet);
            list.add(t);
        }

        return list;
    }

    /***
     * 执行SQL查询语句, 并返回查询结果
     * @param sql  SQL语句
     * @param args 参数列表
     * @return 包含列名与数据的Map的List
     */
    public List<Map<String, String>> queryData(String sql, String... args)
        throws SQLException {
    	
        List<Map<String, String>> list = new ArrayList<>();
        
        connect();

        ResultSet resultSet = generateStatement(sql, args).executeQuery();
        
        ResultSetMetaData metaData = resultSet.getMetaData();
        
        while (resultSet.next()) {
        	
            Map<String, String> map = new LinkedHashMap<>();
            
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
            	
                map.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            
            list.add(map);
        }

        disconnect();

        return list;
    }

    /***
     * 执行SQL插入, 删除, 更新语句, 返回受影响的行数
     * @param sql  SQL语句
     * @param args 参数列表
     * @return 更新受影响的行数
     */
    public int updateData(String sql, String... args) throws SQLException {
        
    	connect();
        PreparedStatement statement = generateStatement(sql, args);

        try {
        	
            return statement.executeUpdate();
        } catch (SQLException e) {
        	
            throw e;
        } finally {
        	
            disconnect();
        }
    }

    private <T> T getCurrentResultData(Class<T> clazz, ResultSet resultSet)
        throws SQLException, ReflectiveOperationException {
    	
        T t = clazz.getDeclaredConstructor().newInstance(); // 创建T对象
        
        // 通过这个对象可以获取结果集的列信息
        ResultSetMetaData metaData = resultSet.getMetaData();
        
        for (int i = 0; i < metaData.getColumnCount(); i++) {
        	
        	//获取列名字
            String column = metaData.getColumnName(i + 1);
            
            //获取当前行当前列转换成对象
            Object value = resultSet.getObject(i + 1);
            
            //获取当前行当前列数据的类名
            String type = metaData.getColumnClassName(i + 1);
            
            //根据列名生成set方法名
            String methodName = "set" + CommonUtils.underliningToPascal(column);

            Method method = null;
            try { // 判断是否含有与列名匹配的对象成员变量
            	
                method = clazz.getMethod(methodName, Class.forName(type));
            } catch (NoSuchMethodException e) {
            	
            	System.out.println(column);
                e.printStackTrace();
                continue; // 没找到, 判断下一个
            }
            
            method.invoke(t, value);
        }

        return t;
    }

    private PreparedStatement generateStatement(String sql, String ... args)
        throws SQLException {
        
    	PreparedStatement statement = null;
    	
        int count = CommonUtils.getCharacterCount(sql, '?');

        if (args.length != count) {
        	
            String str = "SQL语句参数个数错误, 应该为" + count + ", 得到" + args.length;
            throw new AtmException(str);
        }

        statement = connection.prepareStatement(sql);
        
        for (int i = 0; i < count; i++) {
        	
            statement.setString(i+1, args[i]);
        }

        return statement;
    }

    private void connect() throws SQLException{
    	
    	connection = DriverManager.getConnection("jdbc:mysql://192.168.2.232:3306/lalala?useSSL=false", 
				"root", "123456");
    }

    private void disconnect() throws SQLException {
    	
        if (connection == null || connection.isClosed()) {
        	
            return;
        }
        connection.close();
    }
}
