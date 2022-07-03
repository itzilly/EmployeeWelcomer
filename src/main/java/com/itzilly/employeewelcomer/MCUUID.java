package com.itzilly.employeewelcomer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MCUUID {

    public String uuid;
    public String raw_uuid;
    public String playerName;

    public Boolean isValidPlayer = false;
    public Boolean success = false;

    public MCUUID(String id) {
        if (playerName == null) {
            fetchFromApi(id);
        }
    }

    private void fetchFromApi(String id) {
        try {
            String baseUrl = "https://playerdb.co/api/player/minecraft/";
            URL url = new URL(baseUrl + id);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject data = root.getAsJsonObject();

            success = data.get("success").getAsBoolean();

            if (!(success)) {
                System.out.println("There was an unknown error getting player data!");
            }
            JsonObject metadata = data
                    .getAsJsonObject("data")
                    .getAsJsonObject("player");

            uuid = metadata.get("id").getAsString();
            raw_uuid = metadata.get("raw_id").getAsString();
            playerName = metadata.get("username").getAsString();
            isValidPlayer = true;



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
