package gr.aueb.mamakas.authentication.manager;

import lombok.Getter;
import lombok.Setter;

/**
 * Singleton class holding on important parameters.
 *
 * @author dimitrios.mamakas
 *
 */
@Getter
@Setter
public class Singleton {

    /**
     * Instance attribute.
     */
    private static Singleton INSTANCE;

    /**
     * Blocked attribute. By default is set up to false.
     */
    private boolean isBlocked = false;

    /**
     * Default constructor.
     */
    private Singleton() {
    }

    /**
     * Returns the current instance.
     *
     * @return The instance.
     */
    public static Singleton getInstance() {
        if ( Singleton.INSTANCE == null ) {
            Singleton.INSTANCE = new Singleton();
        }

        return Singleton.INSTANCE;
    }

}
