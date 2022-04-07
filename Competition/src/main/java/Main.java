import competition.Participant;
import repository.ParticipantDbRepository;
import repository.ParticipantRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        try (InputStream input = new FileInputStream("db.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("jdbc.url"));
            System.out.println(prop.getProperty("jdbc.user"));
            System.out.println(prop.getProperty("jdbc.pass"));

            ParticipantRepository repo = new ParticipantDbRepository(prop);
            repo.findAll().forEach(System.out::println);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}