package storage.demo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class StorageApi {

    @PostMapping
    public ResponseEntity store(@RequestBody String state) {
        try {
            storeTextToFile(state);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error storing text to file: " + e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping
    public ResponseEntity get() {
        try {
            String text = getTextFromFile();
            return ResponseEntity.ok().body(text);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving text from file: " + e.getMessage());
        }
    }

    private void storeTextToFile (String state){
        String filepath = "/app/data/log.txt";
        try {
            FileWriter fileWriter = new FileWriter(filepath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(state);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTextFromFile () throws Exception {
        String filepath = "/app/data/log.txt";
        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace(); 
            throw new Error("error reading file");   
        }
    }
    
}
