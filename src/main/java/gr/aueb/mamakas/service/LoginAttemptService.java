package gr.aueb.mamakas.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Service handling login attempts.
 *
 * @author dimitrios.mamakas
 *
 */
@Service
public class LoginAttemptService {

    /**
     * Maximum allowed failed login attempts.
     */
    private final int MAX_ATTEMPT = 2;

    /**
     * Cache memory.
     */
    private LoadingCache<String,Integer> attemptsCache;

    /**
     * Default constructor
     */
    public LoginAttemptService() {
        super();
        this.attemptsCache = CacheBuilder.newBuilder().expireAfterWrite( 1, TimeUnit.DAYS ).build( new CacheLoader<String,Integer>() {
            @Override
            public Integer load( final String key ) {
                return 0;
            }
        } );
    }

    /**
     * Method handling the case of an successful login attempt.
     *
     * @param key
     */
    public void loginSucceeded( final String key ) {
        this.attemptsCache.invalidate( key );
    }

    /**
     * Method handling the case of a failed login attempt.
     *
     * @param key
     */
    public void loginFailed( final String key ) {
        int attempts = 0;
        try {
            attempts = this.attemptsCache.get( key );
        }
        catch ( final ExecutionException e ) {
            attempts = 0;
        }
        attempts++;
        this.attemptsCache.put( key, attempts );
    }

    /**
     * Checks if the IP is blocked.
     *
     * @param key IP address to check.
     * @return Boolean variable indicating the result.
     */
    public boolean isBlocked( final String key ) {
        try {
            return this.attemptsCache.get( key ) >= this.MAX_ATTEMPT;
        }
        catch ( final ExecutionException e ) {
            return false;
        }
    }

}
