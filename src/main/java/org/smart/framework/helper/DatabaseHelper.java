package org.smart.framework.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart.framework.util.MyThreadLocal;

import java.sql.Connection;

public final class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final MyThreadLocal<Connection> CONNECTION_HOLDER = new MyThreadLocal<>();

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final BasicDataSource DATA_SOURCE = new BasicDataSource();

    static {
       DATA_SOURCE.setDriverClassName(ConfigHelper.getJdbcDriver());
       DATA_SOURCE.setUrl(ConfigHelper.getJdbcUrl());
       DATA_SOURCE.setUsername(ConfigHelper.getJdbcUsername());
       DATA_SOURCE.setPassword(ConfigHelper.getJdbcPassword());
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection == null){
            try{
                connection = DATA_SOURCE.getConnection();
            }catch (Exception e){
                LOGGER.error("获取数据库连接失败", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    public static void beginTransaction(){
        Connection connection = getConnection();
        if (connection != null){
            try{
                connection.setAutoCommit(false);
            }catch (Exception e){
                LOGGER.error("开启事务失败", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
    }

    public static void commitTransaction(){
        Connection connection = getConnection();
        if (connection != null){
            try{
                connection.commit();
                connection.close();
            }catch (Exception e){
                LOGGER.error("提交事务失败", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static void rollbackTransaction(){
        Connection connection = getConnection();
        if (connection != null){
            try{
                connection.rollback();
                connection.close();
            }catch (Exception e){
                LOGGER.error("回滚事务失败", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
