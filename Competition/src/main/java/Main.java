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

            ParticipantRepository repo = new ParticipantDbRepository(prop);
            repo.findAll().forEach(System.out::println);

//            repo.add(new Participant("miiiy3333","parola4","Ion","Marcel"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
