package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    private static HttpURLConnection connection;

    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://makeup-api.herokuapp.com/api/v1/products.json?brand=maybelline");
            connection = (HttpURLConnection) url.openConnection();

            // Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if (status > 299) {
                // If status is not successful, use BufferedReader to read error message
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                // If we still have things to read, we want to append them to response content
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                // When reading is complete, close reader
                reader.close();
            } else {
                // If connection is successful
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                // When reading is complete, close reader
                reader.close();
            }
        System.out.println(responseContent.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }
}
