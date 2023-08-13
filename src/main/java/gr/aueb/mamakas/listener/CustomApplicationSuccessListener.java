package gr.aueb.mamakas.listener;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import gr.aueb.mamakas.authentication.manager.Singleton;
import gr.aueb.mamakas.database.User;
import gr.aueb.mamakas.database.UserInsertRepository;
import gr.aueb.mamakas.service.LoginAttemptService;
import gr.aueb.mamakas.service.UserDetailsImpl;

/**
 * Authentication Success Listener called once a successful authentication connection has been established.
 *
 * @author dimitrios.mamakas
 *
 */
@Component
public class CustomApplicationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    UserInsertRepository userInsertRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    /**
     * Triggers the application event.
     */
    @Override
    public void onApplicationEvent( final AuthenticationSuccessEvent event ) {
        final UserDetailsImpl user = ( UserDetailsImpl ) event.getAuthentication().getPrincipal();
        if ( Singleton.getInstance().isBlocked() == false ) {
            this.userInsertRepository.setSuccessfulLogin( true );
            this.userInsertRepository.insertWithQuery( new User( user.getUsername(), user.getPassword() ) );
        }

        final String xfHeader = this.request.getHeader( "X-Forwarded-For" );

        if ( xfHeader == null ) {
            this.loginAttemptService.loginSucceeded( this.request.getRemoteAddr() );
        }
        else {
            this.loginAttemptService.loginSucceeded( xfHeader.split( "," )[0] );
        }
    }

}
