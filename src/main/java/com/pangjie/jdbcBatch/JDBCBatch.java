package com.pangjie.jdbcBatch;

import com.pangjie.jpa.entity.UserInfo;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JDBCBatch {

    public static void jdbcBatch(List<UserInfo> list) {
        int begin = 2;
        int end = begin + 30997;
        String url = "jdbc:mysql://81.68.124.103:3306/test?" +
                "serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&" +
                "rewriteBatchedStatements=true";
        String username = "root";
        String password = "lilishop";

        //定义连接、statement对象
        Connection conn = null;
        PreparedStatement pstm = null;
//        PreparedStatement pstm2 = null;
        try {
            //加载jdbc驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接mysql
            conn = DriverManager.getConnection(url, username, password);
            //将自动提交关闭
            conn.setAutoCommit(false);

            String select = "select id, pass_word from sys_user_info";

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(select);
            resultSet.next();

            //编写sql
            String sql = "update sys_user_info set user_name = ? where id = ?";
            //预编译sql
            pstm = conn.prepareStatement(sql);
//            pstm2 = conn.prepareStatement(sql2);
            //开始总计时
            long bTime1 = System.currentTimeMillis();
            Timestamp from = Timestamp.from(Instant.now());

            for (int i = 0; i < list.size(); i++) {
                //开启分段计时，计1W数据耗时
                long bTime = System.currentTimeMillis();
                //开始循环
                pstm.setString(1, list.get(i).getPassWord());
                pstm.setInt(2, list.get(i).getId());
                //添加到同一个批处理中
                pstm.addBatch();
                //输出
                if (i % 1000 == 0 || i == list.size()) {
                    //执行批处理
                    pstm.executeBatch();
                    //提交事务
                    conn.commit();
                    //关闭分段计时
                    long eTime = System.currentTimeMillis();
                    System.out.println("成功插入1000条数据耗时：" + (eTime - bTime));
                }
            }
            //关闭总计时
            long eTime1 = System.currentTimeMillis();
            //输出
            System.out.println("共耗时：" + (eTime1 - bTime1));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://81.68.124.103:3306/test?" +
                "serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&" +
                "rewriteBatchedStatements=true";
        String username = "root";
        String password = "lilishop";

        //定义连接、statement对象
        Connection conn = null;
        PreparedStatement pstm = null;
//        PreparedStatement pstm2 = null;
        try {
            //加载jdbc驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接mysql
            conn = DriverManager.getConnection(url, username, password);
            //将自动提交关闭
            conn.setAutoCommit(false);

            String select = "select id, pass_word from sys_user_info";

            Statement stmt = conn.createStatement();



            ResultSet resultSet = stmt.executeQuery(select);

            ArrayList<UserInfo> list = new ArrayList<>();
            list.ensureCapacity(4100);
            while (resultSet.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt("id"));
                userInfo.setUserName(resultSet.getString("pass_word"));
                list.add(userInfo);
            }


            //编写sql
            String sql = "update sys_user_info set user_name = ? where id = ?";
            //预编译sql
            pstm = conn.prepareStatement(sql);
//            pstm2 = conn.prepareStatement(sql2);
            //开始总计时
            long bTime1 = System.currentTimeMillis();
            Timestamp from = Timestamp.from(Instant.now());

            for (int i = 0; i < list.size(); i++) {
                //开启分段计时，计1W数据耗时
                long bTime = System.currentTimeMillis();
                //开始循环
                pstm.setString(1, list.get(i).getPassWord());
                pstm.setInt(2, list.get(i).getId());
                //添加到同一个批处理中
                pstm.addBatch();
                //输出
                if (i % 1000 == 0 || i == list.size()) {
                    //执行批处理
                    pstm.executeBatch();
                    //提交事务
                    conn.commit();
                    //关闭分段计时
                    long eTime = System.currentTimeMillis();
                    System.out.println("成功插入1000条数据耗时：" + (eTime - bTime));
                }
            }
            //关闭总计时
            long eTime1 = System.currentTimeMillis();
            //输出
            System.out.println("共耗时：" + (eTime1 - bTime1));


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
