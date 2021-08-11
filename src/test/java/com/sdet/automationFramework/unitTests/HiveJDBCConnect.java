package com.sdet.automationFramework.unitTests;

import java.sql.*;

public class HiveJDBCConnect {
    public static void main(String[] args) {

        Connection con = null;

        try {
            String conStr = "jdbc:hive2://139.61.38.36:10000/default";
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            con = DriverManager.getConnection(conStr, " "," ");
            Statement statement = con.createStatement();

            statement.execute("set hive.limit.optimize.enable=true");
            statement.execute("SET mapred.job.queue.name=oysterplus");
            statement.execute("SET mapred.job.queue.name=dev");
            statement.execute("add jar /opt/mapr/hive/hive/lib/hive-contrib-2.3.3-mapr-1901.jar");

            ResultSet resultSet = statement.executeQuery(
                    "SELECT * from table_name limit 1");
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
             /*   int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++)
                {
                    String columnName = metaData.getColumnName(i);
                    int type = metaData.getColumnType(i);
                    if (type == Types.VARCHAR || type == Types.CHAR) {
                        System.out.print(
                                metaData.getColumnName(i) + ": "+resultSet.getString(columnName)+"\n");
                        String name= resultSet.getString(columnName);
                    }
                    if (type==Types.INTEGER){
                        System.out.print(resultSet.getInt(columnName));
                        int age= resultSet.getInt(columnName);
                    }
                    // ....
                    // save columnName and name
                }*/
                System.out.println("Column Value-> " + resultSet.getString(" "));
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception ex) {
            }
        }
    }
}
