package server.SourceFiles;


import org.sqlite.JDBC;
import server.DTO.SuicideCountRow;
import server.DTO.SuicideStatisticsRow;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHandler {

    // Constant of connect address
    private static final String CONNECT_ADDRESS = "jdbc:sqlite:src/main/java/server/DB/SuicideDB.db";
    //Object of connect with DB
    private Connection connection;

    /**Use scheme of "Only class instance" for optimization of the memory*/
    private static DBHandler instance = null;
    public static synchronized DBHandler getInstance() {
        if (instance == null){
            try {
                instance = new DBHandler();
            }catch (SQLException e){
                e.getCause();
            }
        }
        return instance;
    }

    /**Build of this class (class initialised from getInstance*/
    private DBHandler() throws SQLException{
        //Registering driver for work with DB
        DriverManager.registerDriver(new JDBC());
        //Completed connect with DB
        this.connection = DriverManager.getConnection(CONNECT_ADDRESS);
    }

    List<SuicideStatisticsRow> getAllRowsFromTable(){
        // Statement is use for completing SQL search
        try(Statement statement = this.connection.createStatement()){
            List<SuicideStatisticsRow> resultList = new ArrayList<>();
            // from resultSet we take rows of result SQL search
            // in ResultSet we give SQL code
            ResultSet resultSet = statement.executeQuery("SELECT*FROM suicide;");
            //get all rows from result set and give result to resultList
            while (resultSet.next()){
                resultList.add(
                        new SuicideStatisticsRow(
                                resultSet.getString("Country"),
                                resultSet.getInt("Year"),
                                resultSet.getString("Sex"),
                                resultSet.getString("Age"),
                                resultSet.getInt("Suicides_Count"),
                                resultSet.getInt("Population"),
                                resultSet.getInt("Suicides_to_100_K_Population")));
            }
            return resultList;
        }catch(SQLException e){
            return Collections.emptyList();
        }
    }

    List<Integer> getAllYear(){
        // Statement is use for completing SQL search
        try(Statement statement = this.connection.createStatement()){
            List<Integer> resultList = new ArrayList<>();
            // from resultSet we take rows of result SQL search
            // in ResultSet we give SQL code
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT Year FROM suicide;");
            //get all rows from result set and give result to resultList
            while (resultSet.next()){
                resultList.add(
                        resultSet.getInt("Year")
                );
            }
            return resultList;
        }catch(SQLException e){
            return Collections.emptyList();
        }
    }

    List<SuicideCountRow> getAllSuicideFromYear(int year){
        // Statement is use for completing SQL search
        try(Statement statement = this.connection.createStatement()){
            List<SuicideCountRow> resultList = new ArrayList<>();
            // from resultSet we take rows of result SQL search
            // in ResultSet we give SQL code
            String sql = "SELECT Year, Country, SUM(Suicides_Count) AS Suicides_Count " +
                    "FROM " +
                    "(" +
                    "SELECT Year, Country, Suicides_Count " +
                    String.format("FROM suicide WHERE Year=%d", year) +
                    ") " +
                    "GROUP BY Country;";
            ResultSet resultSet = statement.executeQuery(sql);
            //get all rows from result set and give result to resultList
            while (resultSet.next()){
                resultList.add(
                        new SuicideCountRow(
                                resultSet.getInt("Year"),
                                resultSet.getString("Country"),
                                resultSet.getInt("Suicides_Count")
                        )
                );
            }
            return resultList;
        }catch(SQLException e){
            return Collections.emptyList();
        }
    }

    void setInformationToTable(SuicideStatisticsRow row){
        try(PreparedStatement statement = this.connection.prepareStatement("INSERT INTO suicide " +
                "('Country', 'Year', 'Sex', 'Age', 'Suicides_Count', " +
                "'Population', 'Suicides_to_100_K_Population') " +
                "VALUES(?,?,?,?,?,?,?)")){
            statement.setObject(1, row.getCountry());
            statement.setObject(2, row.getYear());
            statement.setObject(3, row.getSex());
            statement.setObject(4, row.getAge());
            statement.setObject(5, row.getSuicidesCount());
            statement.setObject(6, row.getPopulation());
            statement.setObject(7, row.getSuicidesTo100KPopulation());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    void updateInformationToTable(SuicideStatisticsRow row) {
        String sql = "UPDATE suicide " +
                "SET Suicides_Count = ?, Population = ?, Suicides_to_100_K_Population = ? " +
                "WHERE Country = ? AND Year = ? AND Sex = ? AND Age = ?;";
        try(PreparedStatement statement = this.connection.prepareStatement(sql)){
            statement.setObject(1, row.getSuicidesCount());
            statement.setObject(2, row.getPopulation());
            statement.setObject(3, row.getSuicidesTo100KPopulation());
            statement.setObject(4, row.getCountry());
            statement.setObject(5, row.getYear());
            statement.setObject(6, row.getSex());
            statement.setObject(7, row.getAge());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    void deleteRowByKey(String country, int year, String sex, String age) {
        String sql = "DELETE FROM suicide " +
                "WHERE Country = ? AND " +
                "Year = ? AND " +
                "Sex = ? AND " +
                "Age = ?;";
        // Statement is use for completing SQL search
        try(PreparedStatement statement = this.connection.prepareStatement(sql)){
            statement.setObject(1, country);
            statement.setObject(2, year);
            statement.setObject(3, sex);
            statement.setObject(4, age);

            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public SuicideStatisticsRow getOneRowByKey(String country, int year, String sex, String age){
        // Statement is use for completing SQL search
        try(Statement statement = this.connection.createStatement()){
            SuicideStatisticsRow resultRow = new SuicideStatisticsRow();
            // from resultSet we take rows of result SQL search
            // in ResultSet we give SQL code
            ResultSet resultSet = statement.executeQuery("SELECT*FROM suicide " +
                    String.format("WHERE Country = '%s' AND ", country) +
                    String.format("Year = '%d' AND ", year) +
                    String.format("Sex = '%s' AND ", sex) +
                    String.format("Age = '%s';", age));
            //get all rows from result set and give result to resultList
            while (resultSet.next()){
                resultRow.setCountry(
                        resultSet.getString("Country")
                );
                resultRow.setYear(
                        resultSet.getInt("Year")
                );
                resultRow.setSex(
                        resultSet.getString("Sex")
                );
                resultRow.setAge(
                        resultSet.getString("Age")
                );
                resultRow.setSuicidesCount(
                        resultSet.getInt("Suicides_Count")
                );
                resultRow.setPopulation(
                        resultSet.getInt("Population")
                );
                resultRow.setSuicidesTo100KPopulation(
                        resultSet.getInt("Suicides_to_100_K_Population")
                );
            }
            return resultRow;
        }catch(SQLException e){
            return null;
        }
    }
}
