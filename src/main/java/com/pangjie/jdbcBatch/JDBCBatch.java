package com.pangjie.jdbcBatch;

import java.sql.*;
import java.util.UUID;

public class JDBCBatch {

    public static void main(String[] args) {
        String url = "jdbc:mysql://rm-m5esk36093uv9v848bo.mysql.rds.aliyuncs.com:3306/emporium_admin_test?" +
                "serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&" +
                "rewriteBatchedStatements=true";
        String username = "emroot";
        String password = "em@ROOT130";

        //定义连接、statement对象
        Connection conn = null;
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
//        PreparedStatement pstm2 = null;
        try {
            //加载jdbc驱动
            Class.forName("com.mysql.jdbc.Driver");
            //连接mysql
            conn = DriverManager.getConnection(url, username, password);
            //将自动提交关闭
            conn.setAutoCommit(false);

            String select = "select user_id, user_integral_1, user_integral_2 from user_info";

            Statement stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(select);
            //编写sql
            String sql = "update user_info set user_integral_1 = ? and user_integral_2 = ? where user_id = ?";
            String sql2 = "insert into user_integral_log (log_id,type_id,log_num,log_remarks,user_id) values (?,?,?,?,?)";
            //预编译sql
            pstm = conn.prepareStatement(sql);
            pstm2 = conn.prepareStatement(sql2);

            int temp = 0;
            //开始计时
            long bTime1 = System.currentTimeMillis();
            while (resultSet.next()) {
                long bTime = System.currentTimeMillis();

//                设置参数
                pstm.setInt(1, resultSet.getInt("user_integral_2"));
                pstm.setInt(2, resultSet.getInt("user_integral_1"));
                pstm.setString(3,resultSet.getString("user_id"));
                //添加到同一个批处理中
                pstm.addBatch();

                String x = UUID.randomUUID().toString().replace("-","");
                pstm2.setString(1, x);
                pstm2.setString(2, "0");
                pstm2.setInt(3, resultSet.getInt("user_integral_1"));
                pstm2.setString(4, "2023年度积分清零:" + resultSet.getInt("user_integral_1"));
                pstm2.setString(5, resultSet.getString("user_id"));

                pstm2.addBatch();
                ++temp;

                //10000个一组
                if (temp % 10000 == 0 || resultSet.isLast()) {
                    //执行批处理
                    pstm.executeBatch();
                    pstm2.executeBatch();
                    //提交事务
                    conn.commit();
                    //关闭分段计时
                    long eTime = System.currentTimeMillis();
                    System.out.println("成功插入"+ temp+"条数据耗时：" + (eTime - bTime1)/1000);
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
