package com.example;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CleaningMethods {
    public static LinkedList<Map<String,List<String>>> UniqueIds(LinkedList<Map<String,String>> finalarray1, LinkedList<Map<String,String>> vesarray)  {
        Set<String> head= finalarray1.get(0).keySet();
        String ID_head = null;
        for (String temp : head){
            if (temp.contains("UNIQUE")){
                ID_head = temp;
            }
        }
        ArrayList<String> Q_IDs = new ArrayList<>();
        vesarray.iterator().forEachRemaining((s) -> Q_IDs.add(s.get("Route")));
        Set<String> setString1 = new HashSet<>(Q_IDs);
        Q_IDs.clear();
        Q_IDs.addAll(setString1);
        String finalID_head = ID_head;
        ArrayList<String> All_IDs = new ArrayList<>();
        finalarray1.iterator().forEachRemaining((r) -> All_IDs.add(r.get(finalID_head)));
        Set<String> setString = new HashSet<>(All_IDs);
        All_IDs.clear();
        All_IDs.addAll(setString);
        LinkedList<Map<String,List<String>>> id_alldata = new LinkedList<>();
        for(int num = 0; num < All_IDs.size(); num++){
            String id = All_IDs.get(num);
            Map<String, List<String>> mergedMap = new HashMap<>();
            LinkedList<Map<String,String>> id_list= new LinkedList<>();
            for(int num1 = 0; num1 < finalarray1.size(); num1++){
                Map<String,String> r = finalarray1.get(num1);
                if (r.get("RT_NE_UNIQUE").equals(id)){
                    id_list.add(r);
                }
            }
            List<Map.Entry<String, String>> list = new ArrayList<>();
            for(int num2 = 0; num2 < id_list.size(); num2++){
                list.addAll(id_list.get(num2).entrySet());
            }
            list.stream()
                    .forEach(entry -> {
                        String key = entry.getKey();
                        List<String> value = new ArrayList<>(Collections.singleton(entry.getValue()));
                        mergedMap.computeIfAbsent(key, K -> {
                            return value;
                        });

                        mergedMap.computeIfPresent(key, (K, V) -> {
                            return mergeList(value, V);
                        });

                    });
            Map<String, List<String>> mergedMap2 = new HashMap<>();
            for (Map.Entry<String,List<String>> entry : mergedMap.entrySet()){
                List<String> Value = entry.getValue();
                Value.remove(Value.size()-1);
                Collections.reverse(Value);
                mergedMap2.put(entry.getKey(), Value);
            }
            id_alldata.add(mergedMap2);
        }
        LinkedList<Map<String,List<String>>> q_alldata = new LinkedList<>();
        for(int num = 0; num < Q_IDs.size(); num++){
            String id = Q_IDs.get(num);
            Map<String, List<String>> mergedMap = new HashMap<>();
            LinkedList<Map<String,String>> id_list= new LinkedList<>();
            for(int num1 = 0; num1 < vesarray.size(); num1++){
                Map<String,String> r = vesarray.get(num1);
                if (r.get("Route").equals(id)){
                    id_list.add(r);
                }
            }
            List<Map.Entry<String, String>> list = new ArrayList<>();
            for(int num2 = 0; num2 < id_list.size(); num2++){
                list.addAll(id_list.get(num2).entrySet());
            }
            list.stream()
                    .forEach(entry -> {
                        String key = entry.getKey();
                        List<String> value = new ArrayList<>(Collections.singleton(entry.getValue()));
                        mergedMap.computeIfAbsent(key, K -> {
                            return value;
                        });

                        mergedMap.computeIfPresent(key, (K, V) -> {
                            return mergeList(value, V);
                        });

                    });
            Map<String, List<String>> mergedMap2 = new HashMap<>();
            for (Map.Entry<String,List<String>> entry : mergedMap.entrySet()){
                List<String> Value = entry.getValue();
                Value.remove(Value.size()-1);
                Collections.reverse(Value);
                mergedMap2.put(entry.getKey(), Value);
            }
            q_alldata.add(mergedMap2);
        }
        LinkedList<Map<String,List<String>>> URSID_list = new LinkedList<>();
        for (int num1 = 0; num1 < q_alldata.size(); num1++){
            Map<String,List<String>> map1 = q_alldata.get(num1);
            Set<String> directions = new HashSet<>(map1.get("Direction.1"));
            String R_ID = map1.get("Route").get(0);
            //System.out.println(R_ID);
            Map<String,List<String>> map1_data = new HashMap<>();
            for (int n = 0; n < id_alldata.size(); n++){
                Map<String, List<String>> map11 = id_alldata.get(n);
                if (map11.get("RT_NE_UNIQUE").get(0).equals(R_ID)){
                    map1_data = map11;
                }
            }
            for(Iterator<String> num2 = directions.iterator(); num2.hasNext();){
                //For each direction making a new map for both data and query
                Map<String,List<String>> map2_data = new HashMap<>();
                Map<String,List<String>> map2 = new HashMap<>();
                String d = num2.next();
                List<Integer> indices_d = FindIndices(map1_data.get("HPMS_DIRECTION"),d);
                for (Map.Entry<String,List<String>> entry : map1_data.entrySet()){
                    List<String> Value = entry.getValue();
                    List<String> Value_f = indices_d.stream()
                            .map(Value::get)
                            .collect(Collectors.toList());
                    map2_data.put(entry.getKey(), Value_f);
                }
                if (map2_data.get("SESSION_NAME").size() > 0 ){
                    Set<String> sessions = new HashSet<>(map2_data.get("SESSION_NAME"));
                    List<Integer> indices_dq = FindIndices(map1.get("Direction.1"),d);
                    for (Map.Entry<String,List<String>> entry : map1.entrySet()){
                        List<String> Value = entry.getValue();
                        List<String> Value_f = indices_dq.stream()
                                .map(Value::get)
                                .collect(Collectors.toList());
                        map2.put(entry.getKey(), Value_f);
                    }
                    List<String> q_start = new ArrayList<>(map2.get("Start.MP"));
                    List<String> q_end = new ArrayList<>(map2.get("End.MP"));
                    Iterator<String> num3 = q_start.iterator(); Iterator<String> num4 = q_end.iterator();
                    while ( num3.hasNext() && num4.hasNext()){
                        String b = num3.next();
                        String e = num4.next();
                        if (sessions.size() > 1){
                            List<String> sessions_data = new ArrayList<>();
                            for(Iterator<String> num5 = sessions_data.iterator(); num5.hasNext();){
                                String s = num5.next();
                                List<String> All_sessions = map2_data.get("SESSION_NAME");
                                List<String> All_BeginMP = map2_data.get("BEGIN_MP");
                                List<String> All_EndMP = map2_data.get("END_MP");
                                List<List<String>> cover_ind = FindIndices2(All_sessions,All_BeginMP,All_EndMP,s,Double.parseDouble(b), Double.parseDouble(e));
                                if (cover_ind != null){
                                    List<Integer> indices_s = new ArrayList<>();
                                    for(String ind : cover_ind.get(0)) indices_s.add(Integer.valueOf(ind));
                                    Double cover = Double.parseDouble(cover_ind.get(1).get(0));
                                    if (cover > 0.3){
                                        Map<String, List<String>> final_data = new HashMap<>();
                                        for (Map.Entry<String,List<String>> entry : map2_data.entrySet()){
                                            List<String> Value = entry.getValue();
                                            List<String> Value_f = indices_s.stream()
                                                    .map(Value::get)
                                                    .collect(Collectors.toList());
                                            final_data.put(entry.getKey(), Value_f);
                                        }
                                        final_data.put("URSID", Collections.singletonList(R_ID + "-" + d + "-" + s + "-"
                                                + map2_data.get("BEGIN_MP").get(indices_s.get(0)) + "-" + map2_data.get("END_MP").get(indices_s.get(indices_s.size() - 1))));
                                        final_data.put("COVERAGE", Collections.singletonList(Double.toString(cover)));
                                        final_data.put("Query", Collections.singletonList(R_ID + "-" + d + "-" + "-" + b + "-"
                                                + e));
                                        URSID_list.add(final_data);
                                    }
                                }
                            }
                        }else{
                            List<String> sessions_data = new ArrayList<>();
                            sessions_data.addAll(sessions);
                            List<String> All_sessions = map2_data.get("SESSION_NAME");
                            List<String> All_BeginMP = map2_data.get("BEGIN_MP");
                            List<String> All_EndMP = map2_data.get("END_MP");
                            List<List<String>> cover_ind = FindIndices2(All_sessions,All_BeginMP,All_EndMP,sessions_data.get(0),Double.parseDouble(b), Double.parseDouble(e));
                            List<Integer> indices_s = new ArrayList<>();
                            for(String ind : cover_ind.get(0)) indices_s.add(Integer.valueOf(ind));
                            Double cover = Double.parseDouble(cover_ind.get(1).get(0));
                            Map<String, List<String>> final_data = new HashMap<>();
                            for (Map.Entry<String,List<String>> entry : map2_data.entrySet()){
                                List<String> Value = entry.getValue();
                                List<String> Value_f = indices_s.stream()
                                        .map(Value::get)
                                        .collect(Collectors.toList());
                                final_data.put(entry.getKey(), Value_f);
                            }
                            final_data.put("URSID", Collections.singletonList(R_ID + "-" + d + "-" + "-" + sessions_data.get(0) + "-"
                                    + map2_data.get("BEGIN_MP").get(indices_s.get(0)) + "-" + map2_data.get("END_MP").get(indices_s.get(indices_s.size() - 1))));
                            final_data.put("COVERAGE", Collections.singletonList(Double.toString(cover)));
                            final_data.put("Query", Collections.singletonList(R_ID + "-" + d + "-" + "-" + b + "-"
                                    + e));
                            URSID_list.add(final_data);
                        }
                    }
                }
            }
        }
        return URSID_list;
    }


    public static LinkedList<Map<String,String>> RemoveMissingData(LinkedList<Map<String,String>> finalarray2, Double e) throws IOException {
        LinkedList<Map<String,String>> res = new LinkedList<>(finalarray2);
        LinkedList<Map<String,String>> res_rem = new LinkedList<>();
        for(int num=0; num<res.size(); num++)
        {
            Map<String,String> r = res.get(num);
            Set<String> keys = r.keySet();
            List<String> keys_list = new LinkedList<>();
            keys_list.addAll(keys);
            List<String> values = new LinkedList(r.values());
            Map<String, String> r1 = new HashMap<>();
            for (int num1 =0; num1 < values.size(); num1++){
                if (values.get(num1).equals("null") || values.get(num1).equals("") || values.get(num1).equals("NA")){
                    values.set(num1, Double.toString(e));
                }
                r1.put(keys_list.get(num1), values.get(num1));
            }
            res_rem.add(r1);
        }
        return res_rem;
    }

    private static List<String> mergeList(List<String> item1, List<String> item2) {
        List<String> set1 = new ArrayList<>();
        set1.addAll(item1);
        set1.addAll(item2);
        return new ArrayList<>(set1);
    }

    private static <String> List<String> removeDuplicates(List<String> list)
    {
        List<String> newList = new ArrayList<String>();
        for (String element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    private static List<Integer> FindIndices(List<String> list, String elem){
        List<Integer> indices = new ArrayList<>();
        for (int i =0; i < list.size(); i++){
            if(elem.equals(list.get(i))){
                indices.add(i);
            }
        }
        return indices;
    }

    private static List<List<String>> FindIndices2(List<String> list, List<String> list1, List<String> list2, String elem, Double elem1, Double elem2){
        List<Integer> indices = new ArrayList<>();
        List<Double> list1_d = new LinkedList<>();
        List<Double> list2_d = new LinkedList<>();
        for (int obs = 0; obs < list1.size(); obs++){
            list1_d.add(Double.parseDouble(list1.get(obs)));
            list2_d.add(Double.parseDouble(list2.get(obs)));
        }
        for (int i =0; i < list.size(); i++){
            if(elem.equals(list.get(i)) && list1_d.get(i) >= elem1 && list2_d.get(i) <= elem2){
                indices.add(i);
            }
        }
        if (indices.size() > 0){
            Double Length = list2_d.get(indices.get(indices.size() - 1)) - list1_d.get(indices.get(0));
            Double coverage = Length/(elem2 - elem1);
            String coverage1 = Double.toString(coverage);
            List<String> C = Collections.singletonList(coverage1);
            List<String> ind = new LinkedList<>();
            for (Integer myInt : indices) {
                ind.add(String.valueOf(myInt));
            }
            List<List<String>> results = new LinkedList<>();
            results.add(ind);
            results.add(C);
            return results;
        }else{
            return null;
        }
    }

}



