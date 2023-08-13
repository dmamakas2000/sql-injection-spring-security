package gr.aueb.mamakas.listener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import gr.aueb.mamakas.authentication.manager.Singleton;
import gr.aueb.mamakas.database.User;
import gr.aueb.mamakas.database.UserInsertRepository;
import gr.aueb.mamakas.service.LoginAttemptService;

/**
 * Authentication Failure Listener called once a failed authentication connection has been established.
 *
 * @author dimitrios.mamakas
 *
 */
@Component
public class CustomApplicationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

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
    public void onApplicationEvent( final AuthenticationFailureBadCredentialsEvent event ) {
        final Object userName = event.getAuthentication().getPrincipal();
        final Object credentials = event.getAuthentication().getCredentials();

        if ( Singleton.getInstance().isBlocked() == false ) {
            this.userInsertRepository.setSuccessfulLogin( false );
            this.userInsertRepository.insertWithQuery( new User( ( String ) userName, generateMD5Hash( ( String ) credentials ) ) );
        }

        final String xfHeader = this.request.getHeader( "X-Forwarded-For" );

        if ( xfHeader == null ) {
            this.loginAttemptService.loginFailed( this.request.getRemoteAddr() );
        }
        else {
            this.loginAttemptService.loginFailed( xfHeader.split( "," )[0] );
        }
    }

    /**
     * Generates a MD5 hash value.
     *
     * @param field String to generate hash value
     * @return
     */
    private String generateMD5Hash( final String field ) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance( "MD5" );
            md.update( field.getBytes() );
            final byte[] digest = md.digest();
            final String hashedField = DatatypeConverter.printHexBinary( digest ).toLowerCase();
            return hashedField;
        }
        catch ( final NoSuchAlgorithmException e ) {
            e.printStackTrace();
            return "";
        }
    }

}