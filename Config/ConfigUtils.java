package lib.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.Filesystem;


public class ConfigUtils{
    private ObjectMapper mapper;

    public ConfigUtils(){
        this(new ObjectMapper());
    }

    public ConfigUtils(ObjectMapper mapper){
        this.mapper = mapper;
    }

    public <T> T readFromClassPath(Class<T> clazz, String resource)throws IOException{
        try (BufferedReader br =
        new BufferedReader(
            new FileReader(
                new File(Filesystem.getDeployDirectory(), "resources/" + resource + ".json")))) {
      StringBuilder fileContentBuilder = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        
        fileContentBuilder.append(line);
      }

      String fileContent = fileContentBuilder.toString();
    //   JSONObject json = (JSONObject) new JSONParser().parse(fileContent);
        return mapper.readValue(fileContent, clazz);
        // PathPlanner.loadPath(resource, null);
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to read values", e);
      }
    }
}