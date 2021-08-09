package com.sdet.automationFramework.utilities;

import java.sql.*;
import java.util.HashMap;

public class HiveDatabaseManager {

    private static String driverClass = "org.apache.hive.jdbc.HiveDriver";

    public Statement statement;

    /**
     * Constructor to instantiate HIVE DataBase
     * @param url
     * @param userName
     * @param password
     * @throws SQLException
     */
    public HiveDatabaseManager(String url, String userName, String password) throws SQLException {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException exception) {

            exception.printStackTrace();
            System.exit(1);
        }
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();

        this.statement = statement;
    }

    /**
     * Function to set HIVE DataBase
     * @param statement
     * @throws SQLException
     */
    public void setHiveDataBaseAndAddJAR(Statement statement) throws SQLException {

        statement.execute("set hive.limit.optimize.enable=true");
        statement.execute("SET mapred.job.queue.name=oysterplus");
        statement.execute("SET mapred.job.queue.name=dev");
        statement.execute("add jar /opt/mapr/hive/hive/lib/hive-contrib-2.3.3-mapr-1901.jar");
    }

    /**
     * Function to execute single HIVE Query
     * @param statement
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(Statement statement, String query) throws SQLException {

        ResultSet resultSet = statement.executeQuery(query);

        return resultSet;
    }

    /**
     * Function to get attribute value
     * @param table
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    public String getAttributeValueFromTable(String table, ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getString( table+ "."+columnName);
    }

    /**
     * Function to get all Attributes with their values
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public HashMap<String,String> getAllAttributesAndValues(ResultSet resultSet) throws SQLException {

        HashMap<String, String> attributeValuePair = new  HashMap<String, String>();

        ResultSetMetaData metaData = resultSet.getMetaData();
        while (resultSet.next()) {
                int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++)
                {
                    String columnName = metaData.getColumnName(i);
                    int type = metaData.getColumnType(i);
                    if (type == Types.VARCHAR || type == Types.CHAR) {
                        //System.out.print(metaData.getColumnName(i) + ": "+resultSet.getString(columnName)+"\n");

                        attributeValuePair.put(columnName,resultSet.getString(columnName));
                    }
                    if (type==Types.INTEGER){

                        //System.out.print(resultSet.getInt(columnName));

                        attributeValuePair.put(columnName,resultSet.getString(columnName));
                    }
                    // ....
                    // save columnName and name
                }
            //System.out.println("Column Value-> " + resultSet.getString("model_info_view.market"));
        }
        return attributeValuePair;
    }


}
