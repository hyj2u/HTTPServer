package config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration currentConfiguration;

    private ConfigurationManager() {

    }
    public static ConfigurationManager getInstance(){
        if(configurationManager ==null){
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
    public void loadConfigurationFile(String filePath) {

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
           throw new HttpConfigurationException(e);
        }
        StringBuffer  sb = new StringBuffer();
        int read=0;

        try {
            while((read= fileReader.read()) != -1){
                sb.append((char)read);
            }
        } catch (IOException e) {

            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;

        try {
            conf = Json.parse(sb.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }
        try {
            currentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File, internal", e);
        }
    }
    public Configuration getCurrentConfigutation(){

        if(currentConfiguration ==null){
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return currentConfiguration;
    }

}
