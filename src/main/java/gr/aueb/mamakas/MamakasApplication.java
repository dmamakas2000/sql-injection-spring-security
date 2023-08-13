package gr.aueb.mamakas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring's Application class.
 *
 * @author dimitrios.mamakas
 *
 */
@SpringBootApplication
public class MamakasApplication {

    /**
     * Main method starting the application.
     *
     * @param args Arguments parsed.
     */
    public static void main( final String[] args ) {
        SpringApplication.run( MamakasApplication.class, args );
    }

}
