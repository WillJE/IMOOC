package com.write;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class readFile {

    public static void main(String[] args) {
        System.out.println(read("20180927132612.json"));
    }

    public static String read(String path){
        String laststr="";
        File file=new File(path);
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader(file));
            String tempString=null;
            while((tempString=reader.readLine())!=null){
                laststr=laststr+tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(IOException el){
                }
            }
        }
        return laststr;
    }
}
