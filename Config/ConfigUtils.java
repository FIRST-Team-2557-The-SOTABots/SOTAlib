package SOTAlib.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Maps json config files to config Objects
 */
public class ConfigUtils {
    private ObjectMapper mapper;

    /**
     * Creates a new ConfigUtils object with default ObjectMapper
     */
    public ConfigUtils() {
        this(new ObjectMapper());
    }

    /**
     * Creates a new ConfigUtils object with specific ObjectMapper
     *
     * Don't use unless you know what you're doing.
     */
    public ConfigUtils(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * @param <T> The type of Config Object you're mapping to (ex: MotorControllerConfig, EncoderConfig, etc.)
     *
     * @param clazz object class to map to (ex: MotorControllerConfig.class or MyCustomConfigObject.class)
     *
     * @param resource path to your json config file. The method automatically assumes your resorce is stored in a subdirectory of deploy/resources and ends in .json. Therefore the only information specificed in this String is the filename with extension omitted. If you have subdirectories for organization you must incude the subdirectory name first. (ex: to map from deploy/resources/ExampleSubdirectory/MyConfig.json the resource parameter should be "ExampleSubdirectory/MyConfig". To map from deploy/resources/UnorganizedConfig.json the resource parameter should be "UnorganizedConfig")
     *
     * @throws IOException throws if it fails to read the values. Stack trace is printed, check the filenames, good luck. :)
     */
    public <T> T readFromClassPath(Class<T> clazz, String resource) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new FileReader(
                        new File(Filesystem.getDeployDirectory(), "resources/" + resource + ".json")))) {
            StringBuilder fileContentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {

                fileContentBuilder.append(line);
            }

            String fileContent = fileContentBuilder.toString();
            // JSONObject json = (JSONObject) new JSONParser().parse(fileContent);
            return mapper.readValue(fileContent, clazz);
            // PathPlanner.loadPath(resource, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to read values", e);
        }
    }
}

