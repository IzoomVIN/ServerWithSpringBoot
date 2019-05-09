package server.SourceFiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.DTO.SuicideCountRow;
import server.DTO.SuicideStatisticsRow;

import java.io.IOException;
import java.util.List;

public class DataConverter {
    public static String JsonToGetData(DBHandler dbh){
        List<SuicideStatisticsRow> data = dbh.getAllRowsFromTable();
        String result;

        result = toJSON(data);

        if (result != null){
            return result;
        }else{
            return toJSON("db is empty");
        }
    }

    public static String getJSONSuicideCountFromYear(DBHandler dbh, String year) {
        List<SuicideCountRow> data = dbh.getAllSuicideFromYear(
                Integer.valueOf(year)
        );
        String result;

        result = toJSON(data);

        if (result != null){
            return result;
        }else{
            return "hmmm...";
        }
    }

    public static String toJSON(Object data){
        ObjectMapper mapper = new ObjectMapper();
        String result = null;

        try {
            result = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static SuicideStatisticsRow fromJSON(String row){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readerFor(SuicideStatisticsRow.class).readValue(row);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String rowAdapter(DBHandler dbh, String type, SuicideStatisticsRow row) {
        boolean checkFlag = checkOnPrimaryKey(dbh,row);
        if(!checkFlag && type.equals("add")){
            dbh.setInformationToTable(row);
            return "ok";
        }else if (checkFlag && type.equals("update")){
            dbh.updateInformationToTable(row);
            return "ok";
        }
        if(checkFlag){
            return "Element is exist";
        }else{
            return "Element is not exist";
        }
    }

    public static String deleteRow(DBHandler dbh,
                                   String country, int year, String sex, String age){
        if (
                checkOnPrimaryKey(
                        dbh,
                        new SuicideStatisticsRow(
                                country, year, sex, age, 0, 0, 0
                        )
                )
        ){
            dbh.deleteRowByKey(country, year, sex, age);
            return "ok";
        }
        return "Element is not exist";
    }

    public static boolean checkOnYear(DBHandler dbh, String typeId) {
        for(Integer year: dbh.getAllYear()){
            if (
                    Integer.valueOf(typeId).equals(year)
            ){
                return true;
            }
        }
        return false;
    }

    private static boolean checkOnPrimaryKey(DBHandler dbh, SuicideStatisticsRow row) {
        for(SuicideStatisticsRow dataRow: dbh.getAllRowsFromTable()){
            if(     row.getCountry().equals(dataRow.getCountry()) &&
                    row.getAge().equals(dataRow.getAge()) &&
                    row.getSex().equals(dataRow.getSex()) &&
                    (row.getYear() == dataRow.getYear())
            ){
                return true;
            }
        }
        return false;
    }
}
