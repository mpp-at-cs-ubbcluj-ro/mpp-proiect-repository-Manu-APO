package start;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@ComponentScan({"competition","repository"})
@SpringBootApplication
public class StartRestServices {
    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class,args);

    }

    @Bean(name="properties")
    public Properties getBdProperties(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.properties"));
        } catch (IOException e) {
            System.err.println("Configuration file bd.properties not found" + e);

        }
        return props;
    }

}
