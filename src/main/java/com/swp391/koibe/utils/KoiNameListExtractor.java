package com.swp391.koibe.utils;

import java.io.*;
import java.util.*;

public class KoiNameListExtractor {

    public static void main(String[] args) {
        String csvFile = "docs/koi_data.csv";
        List<String> koiNames = getKoiNames(csvFile);

        // Print the koi names
        koiNames.forEach(System.out::println);
    }

    public static List<String> getKoiNames(String csvFile) {
        Set<String> koiSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by comma and extract the first element (koi name)
                String[] fields = line.split(",", -1);
                if (fields.length > 0) {
                    koiSet.add(fields[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       return new ArrayList<>(koiSet);
    }

}
