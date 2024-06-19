package net.mamesosu.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Data {

    public static JsonNode getJsonNode(String endpoint) {
        try {
            URL obj = new URL(endpoint);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
