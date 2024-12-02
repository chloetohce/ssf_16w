package sg.edu.nus.iss.ssf_16w.restapi.service;

import java.io.IOException;
import java.io.StringReader;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssf_16w.model.BoardGame;
import sg.edu.nus.iss.ssf_16w.restapi.repository.BoardAPIRepository;
import sg.edu.nus.iss.ssf_16w.utilities.Constant;

@Service
public class BoardAPIService {
    @Autowired
    private BoardAPIRepository boardAPIRepository;

    public ResponseEntity<JsonObject> add(MultipartFile file) throws IOException {
        JsonReader reader = Json.createReader(file.getInputStream());
        JsonArray dataArray = reader.readArray();
        
        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject obj = dataArray.get(i).asJsonObject();
            int id = obj.getInt("gid");
            BoardGame bg = new BoardGame(
                id,
                obj.getString("name"),
                obj.getInt("year"),
                obj.getInt("ranking"),
                obj.getInt("users_rated"),
                obj.getString("url"),
                obj.getString("image")
            );

            boardAPIRepository.add(Constant.KEY_BOARDGAME, bg.toString());
        }

        JsonObject jsonObj = Json.createObjectBuilder()
                .add("insert_count", 1)
                .add("id", Constant.KEY_BOARDGAME)
                .build();
        
        return ResponseEntity.ok().body(jsonObj);
    }

    public ResponseEntity<JsonObject> getById(int id) {
        String rawData = boardAPIRepository.getById(Constant.KEY_BOARDGAME, id);
        if (rawData != null) {
            String[] data = rawData.split(",");

            JsonObject boardgame = Json.createObjectBuilder()
                    .add("gid", Integer.parseInt(data[0]))
                    .add("name", data[1])
                    .add("year", Integer.parseInt(data[2]))
                    .add("ranking", Integer.parseInt(data[3]))
                    .add("users_rated", Integer.parseInt(data[4]))
                    .add("url", data[5])
                    .add("image", data[6])
                    .build();

            return ResponseEntity.ok(boardgame);
        }

        JsonObject error = Json.createObjectBuilder()
                .add("id", id)
                .add("message", "Boardgame with associated id not found.")
                .build();
        
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(404));
    }

    public ResponseEntity<JsonObject> updateBoard(String entity, boolean upsert) {
        JsonArray arr = Json.createReader(new StringReader(entity)).readArray();
        int counter = 0;
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.getJsonObject(i);
            BoardGame bg = new BoardGame(
                    obj.getInt("gid"),
                    obj.getString("name"),
                    obj.getInt("year"),
                    obj.getInt("ranking"),
                    obj.getInt("users_rated"),
                    obj.getString("url"),
                    obj.getString("image"));
            boolean result = boardAPIRepository.put(Constant.KEY_BOARDGAME, bg.toString(), upsert);
            if (result) {
                counter++;
            }
        }

        JsonObject response = Json.createObjectBuilder()
                .add("update_count", counter)
                .add("id", Constant.KEY_BOARDGAME)
                .build();
        if (counter == 0) {
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }

        return ResponseEntity.ok(response);
        
    }
    
}
