package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Utils  {
    public static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String convertArrayToString(int[] arr) {
        return Arrays.toString(arr).replace("[", "").replace("]", "").replace(",", "");
    }

    public static int[] parseVector(String body, String paramName) {
        String restOfTheBody = body.substring(body.indexOf(paramName) + paramName.length() + 1);
        String vectorStr = restOfTheBody.substring(restOfTheBody.indexOf("[") + 1, restOfTheBody.indexOf("]"));
        String[] vectorArr = vectorStr.trim().split(", ");
        int[] vector = new int[vectorArr.length];
        for (int i = 0; i < vectorArr.length; i++) {
            vector[i] = Integer.parseInt(vectorArr[i].trim());
        }
        return vector;
    }
        
}
