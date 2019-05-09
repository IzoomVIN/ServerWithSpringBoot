package server.controllers;

import org.springframework.web.bind.annotation.*;
import server.DTO.SuicideStatisticsRow;
import server.SourceFiles.DBHandler;

import static server.SourceFiles.DataConverter.*;

@RestController
@RequestMapping("/api")
public class DataController {
    private DBHandler dbh = DBHandler.getInstance();

    @GetMapping(path = "/get/{typeId}")
    public String getData(@PathVariable String typeId){
        if ("all".equals(typeId)) {
            return JsonToGetData(dbh);
        }

        if(checkOnYear(dbh, typeId)){
            return getJSONSuicideCountFromYear(dbh, typeId);
        }else{
            return toJSON("I don't know what you want!");
        }
    }

    @GetMapping(path = "/row/get")
    public String getDataRow(
            @RequestParam String country,
            @RequestParam int year,
            @RequestParam String sex,
            @RequestParam String age
    ){
        return toJSON(
                dbh.getOneRowByKey(country, year, sex, age)
        );
    }

    @PostMapping(path = "/row/{operationId}")
    public String workWithRow(@PathVariable String operationId,@RequestBody String rowJSON){
        SuicideStatisticsRow row = fromJSON(rowJSON);
        if (row == null){
            return String.format("false!!!, %s", rowJSON);
        }
        return rowAdapter(dbh, operationId, row);
    }

    @DeleteMapping(path = "/row/delete")
    public String deleteDataRow(
            @RequestParam String country,
            @RequestParam int year,
            @RequestParam String sex,
            @RequestParam String age
    ){
        return deleteRow(dbh, country, year, sex, age);
    }
}
