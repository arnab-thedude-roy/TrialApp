package com.example;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CalcStat {

    public static LinkedList<Map<String, List<String>>> Stat(LinkedList<Map<String,List<String>>> finalarray4, String VES, int min_len) throws IOException {

        String VESfactor = VES + "_LCMS.txt";
        File file = new File("G:\\Dropbox\\LCMS-BaiSunXuZhang\\" + VESfactor);
        LinkedList<String> LCMSfactors;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            LCMSfactors = new LinkedList<>();
            String st;
            while ((st = br.readLine()) != null)
                LCMSfactors.add(st);
        }
        LinkedList<Map<String,List<String>>> res = new LinkedList<>();
        finalarray4.iterator().forEachRemaining((r) -> res.add(LCMSfactors.stream().filter(r::containsKey).collect(Collectors.toMap(Function.identity(), r::get))));
        ArrayList<String> Stat = new ArrayList<>(Arrays.asList("Percentile10","Percentile20","Percentile30",
                "Percentile40","Percentile50","Percentile60","Percentile70","Percentile80","Percentile90", "STD", "SKEWNESS", "MAX", "MIN"));

        ArrayList<Integer> Perc = new ArrayList<>(Arrays.asList(10,20,30,40,50,60,70,80,90));
        LinkedList<Map<String, List<String>>> Stat_All_URSID = new LinkedList<>();
        LinkedList<Map<String,List<String>>> all_data = new LinkedList<>();
        for (int i = 0; i < finalarray4.size(); i++){
            List<String> obs = finalarray4.get(i).get(LCMSfactors.get(1));
            if (obs.size() >= min_len){
                all_data.add(finalarray4.get(i));
            }
        }
        System.out.println(all_data.size());
        for(int num = 0; num < Stat.size(); num++){
           Map<String, List<String>> stat_map = new HashMap<>();
           ArrayList<List<String>> listOfFactors= new ArrayList<>();
           for (int num0 = 0; num0 < LCMSfactors.size(); num0++){
               LinkedList<String> list = new LinkedList<>();
               listOfFactors.add(list);
           }
           if (num < 9){
               if(num == 0 || num == 8){
                   for (int num1 = 0; num1 < all_data.size(); num1++){
                       Map<String,List<String>> map_ursid = all_data.get(num1);
                       listOfFactors.get(0).add(map_ursid.get("URSID").get(0));

                       for (int num2 = 1; num2 < LCMSfactors.size(); num2++){
                           List<String> factor_obs = map_ursid.get(LCMSfactors.get(num2));
                           List<Double> factor_obs_d = new LinkedList<>();
                           for (int obs = 0; obs < factor_obs.size(); obs++){
                               factor_obs_d.add(Double.parseDouble(factor_obs.get(obs)));
                           }
                           double perc_10 = Percentile(factor_obs_d,Perc.get(num));
                           listOfFactors.get(num2).add(Double.toString(PercentileCheck(factor_obs_d,perc_10,Perc.get(num))));
                       }
                   }
               }else{
                   for (int num1 = 0; num1 < all_data.size(); num1++){
                       Map<String,List<String>> map_ursid = all_data.get(num1);
                       listOfFactors.get(0).add(map_ursid.get("URSID").get(0));
                       for (int num2 = 1; num2 < LCMSfactors.size(); num2++){
                           List<String> factor_obs = map_ursid.get(LCMSfactors.get(num2));
                           List<Double> factor_obs_d = new LinkedList<>();
                           for (int obs = 0; obs < factor_obs.size(); obs++){
                               factor_obs_d.add(Double.parseDouble(factor_obs.get(obs)));
                           }
                           listOfFactors.get(num2).add(Double.toString(Percentile(factor_obs_d,Perc.get(num))));
                       }
                   }
               }
           }else if (num == 9){
               for (int num1 = 0; num1 < all_data.size(); num1++){
                   Map<String,List<String>> map_ursid = all_data.get(num1);
                   listOfFactors.get(0).add(map_ursid.get("URSID").get(0));

                   for (int num2 = 1; num2 < LCMSfactors.size(); num2++){
                       List<String> factor_obs = map_ursid.get(LCMSfactors.get(num2));
                       List<Double> factor_obs_d = new LinkedList<>();
                       for (int obs = 0; obs < factor_obs.size(); obs++){
                           factor_obs_d.add(Double.parseDouble(factor_obs.get(obs)));
                       }
                       listOfFactors.get(num2).add(Double.toString(StdDeviation(factor_obs_d)));
                   }
               }
           }else if (num == 10){
               for (int num1 = 0; num1 < all_data.size(); num1++){
                   Map<String,List<String>> map_ursid = all_data.get(num1);
                   listOfFactors.get(0).add(map_ursid.get("URSID").get(0));

                   for (int num2 = 1; num2 < LCMSfactors.size(); num2++){
                       List<String> factor_obs = map_ursid.get(LCMSfactors.get(num2));
                       List<Double> factor_obs_d = new LinkedList<>();
                       for (int obs = 0; obs < factor_obs.size(); obs++){
                           factor_obs_d.add(Double.parseDouble(factor_obs.get(obs)));
                       }
                       listOfFactors.get(num2).add(Double.toString(Skewness(factor_obs_d)));
                   }
               }
           }else if (num == 11){
               for (int num1 = 0; num1 < all_data.size(); num1++){
                   Map<String,List<String>> map_ursid = all_data.get(num1);
                   listOfFactors.get(0).add(map_ursid.get("URSID").get(0));

                   for (int num2 = 1; num2 < LCMSfactors.size(); num2++){
                       List<String> factor_obs = map_ursid.get(LCMSfactors.get(num2));
                       List<Double> factor_obs_d = new LinkedList<>();
                       for (int obs = 0; obs < factor_obs.size(); obs++){
                           factor_obs_d.add(Double.parseDouble(factor_obs.get(obs)));
                       }
                       Collections.sort(factor_obs_d);
                       listOfFactors.get(num2).add(Double.toString(factor_obs_d.get(factor_obs_d.size()-1)));
                   }
               }
           }else {
               for (int num1 = 0; num1 < all_data.size(); num1++){
                   Map<String,List<String>> map_ursid = all_data.get(num1);
                   listOfFactors.get(0).add(map_ursid.get("URSID").get(0));

                   for (int num2 = 1; num2 < LCMSfactors.size(); num2++){
                       List<String> factor_obs = map_ursid.get(LCMSfactors.get(num2));
                       List<Double> factor_obs_d = new LinkedList<>();
                       for (int obs = 0; obs < factor_obs.size(); obs++){
                           factor_obs_d.add(Double.parseDouble(factor_obs.get(obs)));
                       }
                       Collections.sort(factor_obs_d);
                       listOfFactors.get(num2).add(Double.toString(factor_obs_d.get(0)));
                   }
               }
           }
           for (int num3 = 0; num3 < LCMSfactors.size(); num3++){
               stat_map.put(LCMSfactors.get(num3),listOfFactors.get(num3));
           }
           Stat_All_URSID.add(stat_map);
        }
        return Stat_All_URSID;
    }

    public static double Percentile(List<Double> list1, Integer p){
        Collections.sort(list1);
        Double ind = (list1.size()*p)/100 + 0.5;
        double percentile;
        if ((ind == Math.floor(ind)) && !Double.isInfinite(ind)) {
            int ind1 = (int)Math.round(ind);
            percentile = list1.get(ind1);
        }else{
            int k = (int)Math.floor(ind);
            double f = Math.abs(ind - k);
            percentile = (1-f)*(list1.get(k)) + f*(list1.get(k));
        }

        return percentile;
    }

    public static double PercentileCheck(List<Double> list1, double perc_value, int perc){
        List<Integer> check = new LinkedList<>();
        for (double num : list1){
            if (perc == 10){
                if (num < perc_value){
                    check.add(1);
                }else{
                    check.add(0);
                }
            }else{
                if (num > perc_value){
                   check.add(1);
                }else{
                   check.add(0);
                }
            }
        }
        List<Integer> check_ind = new LinkedList<>();
        for (int num = 0; num < check.size(); num++){
            if (num > 0){
                if (check.get(num) == 1 && check.get(num-1) == 1){
                    check_ind.add(num);
                    check_ind.add(num - 1);
                }
            }
        }

        double percentile;
        Set<Integer> check_ind_set = new HashSet<>(check_ind);
        check_ind.clear();
        check_ind.addAll(check_ind_set);
        if (check_ind.size() > 1){
            Collections.sort(check_ind, Collections.reverseOrder());
            for (int num1 : check_ind){
                list1.remove(num1);
            }
            percentile = Percentile(list1,perc);
        }else{
            percentile = Percentile(list1,perc);
        }

        return percentile;
    }

    public static double StdDeviation(List<Double> list1){
        double sum = 0.0;
        double std_dev = 0.0;
        for(double num : list1){
            sum += num;
        }
        double mean = sum/list1.size();
        for (double num : list1){
            std_dev += Math.pow(num - mean, 2);
        }

        return Math.sqrt(std_dev/list1.size());
    }

    public static double Skewness(List<Double> list1){
        double sum = 0.0;
        double skew = 0.0;
        for(double num : list1){
            sum += num;
        }
        double mean = sum/list1.size();
        double std_dev = StdDeviation(list1);
        for (double num : list1){
            skew += num - Math.pow(mean,3);
        }

        return skew/(Math.pow(std_dev,3)*list1.size());
    }

}
