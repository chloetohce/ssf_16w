package sg.edu.nus.iss.ssf_16w.restapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.ssf_16w.restapi.service.BoardAPIService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/boardgame")
public class BoardAPIController {
    @Autowired
    private BoardAPIService boardAPIService;

    @PostMapping()
    public ResponseEntity<JsonObject> uploadFile(@RequestParam("file-upload") MultipartFile file) throws IOException {
        return boardAPIService.add(file);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<JsonObject> getBoard(@PathVariable("board-id") String boardId) {
        return boardAPIService.getById(Integer.parseInt(boardId));
    }
    
    @PostMapping("/{board-id}")
    public ResponseEntity<JsonObject> updateBoard(@RequestBody String entity, @RequestParam(name = "upsert", required = false) String upsert)  {
        boolean boolUpsert = false;
        if (upsert != null && upsert.equals("true")) {
            boolUpsert = true;
        }
        
        return boardAPIService.updateBoard(entity, boolUpsert);
    }
    
    
}
