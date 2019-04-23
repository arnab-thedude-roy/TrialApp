package com.example;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class ReadData {
    public static CSVParser makeParser(KYTCData file1, final boolean header)
            throws IOException {
        File inFile = new File(file1.filename);
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        if (header) {
            return new CSVParser(reader, CSVFormat.RFC4180.withFirstRecordAsHeader());
        } else {
            return new CSVParser(reader, CSVFormat.RFC4180);
        }
    }

    public static LinkedList<Map<String,String>> ParserToArray(CSVParser csvParser){
        LinkedList<Map<String, String>> results = new LinkedList<>();
        csvParser.iterator().forEachRemaining(
                (r) -> results.add(r.toMap())
        );
        return results;
    }
}
