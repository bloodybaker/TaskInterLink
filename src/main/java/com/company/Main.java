package com.company;

import au.com.bytecode.opencsv.CSVWriter;

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
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("final.csv");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            CSVWriter writer = new CSVWriter(outputStreamWriter);
            for(String date : dates){
                if(date.equals("Date")){
                    dates.set(dates.indexOf(date),"1");
                }
            }
            List<String> raw = new ArrayList<String>();
            Set<String> uniqueDateSet = new HashSet<String>(dates);
            String datesLine[] = new String[uniqueDateSet.size()];
            uniqueDateSet.toArray(datesLine);
            Arrays.sort(datesLine);
            datesLine[0] = "Name / Date";
            writer.writeNext(datesLine);
            String[] users = new String[userData.size()];
            for (int i = 1; i < userData.size(); i++) {
                String s = userData.get(i);
                raw.add(s);
                for (int j = 1; j < datesLine.length; j++) {
                    String dateResult = mainMap.get(s).get(datesLine[j]);
                    if (dateResult == null)
                        raw.add("0");
                    else raw.add(dateResult);
                }
            }

            for(String user : users){
                System.out.println(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
