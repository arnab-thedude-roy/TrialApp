package com.example;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WriteData {
    public static void writeWithCsvMapWriter(LinkedList<Map<String, List<String>>> final_stats) throws IOException {

        ICsvMapWriter mapWriter = null;
        final String[] header = new String[] {"URSID","Query","COVERAGE","ZN2_LONG_CRACK_EXT", "ZN3_LONG_CRACK_EXT", "ZN4_LONG_CRACK_EXT", "FAT_CRK_TYPEA_LOW",
                "FAT_CRK_TYPEA_MED", "FAT_CRK_TYPEA_HIGH", "FAT_CRK_TYPEB_EXT", "FAT_CRK_TYPEB_AREA_EXT", "FAT_CRK_TYPEC_EXT",
                "FAT_CRK_TYPEC_AREA_EXT",  "NON_WHEEL_LONG_LOW", "NON_WHEEL_LONG_MED", "NON_WHEEL_LONG_HIGH"};
        try {
            for (int i =0; i < final_stats.size(); i++){
                Map<String, List<String>> map = final_stats.get(i);
                mapWriter = new CsvMapWriter(new FileWriter("G:\\Dropbox\\LCMS-BaiSunXuZhang\\data\\Example" + i + ".csv"),
                        CsvPreference.STANDARD_PREFERENCE);
                final CellProcessor[] processors = getProcessors(map);
                mapWriter.writeHeader(header);
                for (int num = 0; num < map.get("URSID").size(); num++){
                    Map<String,String> temp_map = new HashMap<>();
                    temp_map.put(header[0], map.get(header[0]).get(num));
                    temp_map.put(header[1], map.get(header[1]).get(num));
                    temp_map.put(header[2], map.get(header[2]).get(num));
                    temp_map.put(header[3], map.get(header[3]).get(num));
                    temp_map.put(header[4], map.get(header[4]).get(num));
                    temp_map.put(header[5], map.get(header[5]).get(num));
                    temp_map.put(header[6], map.get(header[6]).get(num));
                    temp_map.put(header[7], map.get(header[7]).get(num));
                    temp_map.put(header[8], map.get(header[8]).get(num));
                    temp_map.put(header[9], map.get(header[9]).get(num));
                    temp_map.put(header[10], map.get(header[10]).get(num));
                    temp_map.put(header[11], map.get(header[11]).get(num));
                    temp_map.put(header[12], map.get(header[12]).get(num));
                    temp_map.put(header[13], map.get(header[13]).get(num));
                    temp_map.put(header[14], map.get(header[14]).get(num));
                    temp_map.put(header[15], map.get(header[15]).get(num));
                    mapWriter.write(temp_map, header, processors);
                }


            }
        }
        finally {
            if( mapWriter != null ) {
                mapWriter.close();
            }
        }
    }

    private static CellProcessor[] getProcessors(Map<String, List<String>> map1) {

        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull()

        };

        return processors;
    }
}
