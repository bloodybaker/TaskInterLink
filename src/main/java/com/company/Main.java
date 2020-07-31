package com.company;

import javax.xml.namespace.QName;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> userData = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> time = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        String line = "";
        try {
            URL url = new URL("https://img.in6k.com/screens/acme_worksheet.csv");
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(data[0] + " " + data[1] + " " + data[2]);
                userData.add(data[0]);
                dates.add(data[1]);
                time.add(data[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String,Map<String, String>> mainMap = new HashMap<String, Map<String, String>>();
        for (int i = 1; i < userData.size(); i++) {
            Map<String, String > timeOfWork = mainMap.get(userData.get(i));
            if(timeOfWork != null){
                timeOfWork.put(dates.get(i),time.get(i));
                mainMap.put(userData.get(i),timeOfWork);

            }else {
                Map<String, String> workPerDate = new HashMap<String, String>();
                workPerDate.put(dates.get(i), time.get(i));
                mainMap.put(userData.get(i), workPerDate);
            }
        }
    }
}
