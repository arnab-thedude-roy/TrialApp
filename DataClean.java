package com.example;

import org.apache.commons.csv.CSVParser;
import java.io.IOException;
import java.util.*;

public class DataClean {
    public static void main(String[] args) throws IOException {
        KYTCData file2017 = new KYTCData();
        file2017.filename = "G:\\Dropbox\\LCMS-BaiSunXuZhang\\data\\Trial_LCMS2.csv";
        file2017.filetype = ".csv";
        KYTCData query = new KYTCData();
        query.filename = "G:\\Dropbox\\LCMS-BaiSunXuZhang\\data\\Trial_VES2.csv";
        query.filetype = ".csv";
        CSVParser parser1 = ReadData.makeParser(file2017, Boolean.TRUE);
        CSVParser parser2 = ReadData.makeParser(query, Boolean.TRUE);
        LinkedList<Map<String, String>> finalarray = ReadData.ParserToArray(parser1);
        LinkedList<Map<String, String>> finalves = ReadData.ParserToArray(parser2);
        LinkedList<Map<String, String>> route = CleaningMethods.RemoveMissingData(finalarray, 0.0);
        LinkedList<Map<String, String>> qroute = CleaningMethods.RemoveMissingData(finalves, 0.0);
        LinkedList<Map<String, List<String>>> unique_routes = CleaningMethods.UniqueIds(route, qroute);
        System.out.println(unique_routes.get(7).get("Query"));
        //System.out.println(unique_routes.size());
        System.out.println(unique_routes.get(7).get("URSID"));
        System.out.println(unique_routes.get(7).get("COVERAGE"));
        //LinkedList<Map<String, List<String>>> final_stat = CalcStat.Stat(unique_routes, "WPC_JD_EXT", 5);
        //System.out.println(final_stat.get(4));
        //WriteData.writeWithCsvMapWriter(final_stat);

    }
}
