package com.example;

public class KYTCData {
    protected static String name = "KYTCData";
    public String filename = "filename";
    public String filepath = "filepath";
    public String filetype = "csv";

    public KYTCData(){
    }

    public KYTCData(String name, String filename, String filetype){
        this.setName(name);
        this.filename = filename;
        this.filetype = filetype;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}

