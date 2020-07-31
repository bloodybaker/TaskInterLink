package com.company;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        BufferedReader bufferedReader = null;
        String line = "";
        try {
            URL url = new URL("https://img.in6k.com/screens/acme_worksheet.csv");
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(data[0] + " " + data[1] + " " + data[2]);
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
    }
}
