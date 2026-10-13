package utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;




public final class ConfigReader {

    private static final String fileName = "configs/config.properties";
    private static final Properties properties = new Properties();

    private ConfigReader() {
    } 

    static {
        loadProperties();
    }

    private static void loadProperties(){

        try(InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)){
                
                if (input == null) {

                throw new RuntimeException("Configuration file not found: "+ fileName);
            }

            properties.load(input);
            
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration file: "+ fileName, e);
        }
    }

    public static String get(String key){

        String value = properties.getProperty(key);

        if (value == null || value.isBlank()){
            throw new RuntimeException("Missing entry in Config file"+key);
        }

        return value.trim();
    }

    public static int getInt(String key){

        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException  e) {
            throw new RuntimeException("Missing entry in Config file"+key+ " *must be an integer", e);
        }
         
    }

    public static boolean getBoolean(String key){
        
        String value = get(key);
        if (!value.equalsIgnoreCase("true")  && !value.equalsIgnoreCase("false")) {

            throw new RuntimeException("Configuration property '" +key +"' must be true or false");
        }

        return  Boolean.parseBoolean(value);
    }

}
