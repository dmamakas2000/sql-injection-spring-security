package gr.aueb.mamakas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gr.aueb.mamakas.authentication.manager.Singleton;
import gr.aueb.mamakas.database.User;
import gr.aueb.mamakas.database.UserRepository;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dimitrios.mamakas
 *
 */
@Service
@Getter
@Setter
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    private String userName;

    private String salt;

    /**
     * Loads a user from the user-name field.
     */
    @Override
    public UserDetails loadUserByUsername( final String username ) throws UsernameNotFoundException {
        final String ip = getClientIP();

        this.setUserName( username );

        if ( this.loginAttemptService.isBlocked( ip ) ) {
            // This IP address is now blocked
            Singleton.getInstance().setBlocked( true );
        }
        else {
            final List<User> allUsers = this.userRepository.findAll();

            for ( final User user : allUsers ) {
                if ( user.getUsername().equals( username ) ) {
                    // Found a user
                    this.salt = user.getSalt();
                    return UserDetailsImpl.build( user );
                }
            }
        }
        // In case no user is found return an empty user just not to throw any exception
        return UserDetailsImpl.build( new User() );
    }

    /**
     * Gets the current machine's IP address.
     *
     * @return IP address of the current machine.
     */
    private String getClientIP() {
        final String xfHeader = this.request.getHeader( "X-Forwarded-For" );
        if ( xfHeader == null ) {
            return this.request.getRemoteAddr();
        }
        return xfHeader.split( "," )[0];
    }

}
