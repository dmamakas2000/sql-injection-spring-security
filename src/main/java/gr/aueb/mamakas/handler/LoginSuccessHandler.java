package gr.aueb.mamakas.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import gr.aueb.mamakas.database.UserInsertRepository;
import gr.aueb.mamakas.service.UserDetailsImpl;

/**
 * Custom defined success handler.
 *
 * @author dimitrios.mamakas
 *
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserInsertRepository userInsertRepository;

    /**
     * Days which the password lasts.
     */
    private static int DAYS = 10;

    /**
     * Method triggered on a successful authentication event.
     */
    @Override
    public void onAuthenticationSuccess( final javax.servlet.http.HttpServletRequest request,
            final javax.servlet.http.HttpServletResponse response, final Authentication authentication )
            throws java.io.IOException, javax.servlet.ServletException {

        // Get the last_modified field
        final UserDetailsImpl user = ( UserDetailsImpl ) authentication.getPrincipal();

        final String lastModifiedField = user.getLastModified();

        final String dateField = lastModifiedField.substring( 0, 10 ).replace( "-", "/" );

        try {
            final Date dateFieldFromDatabase = new SimpleDateFormat( "yyyy/MM/dd" ).parse( dateField );
            final String dateNow = new SimpleDateFormat( "yyyy/MM/dd" ).format( new Date() );
            final Date dateFieldNow = new SimpleDateFormat( "yyyy/MM/dd" ).parse( dateNow );

            final int diff = dateFieldNow.compareTo( dateFieldFromDatabase );

            String redirectURL = "";

            if ( diff > LoginSuccessHandler.DAYS ) {
                redirectURL = "/change/password";
            }
            else {
                redirectURL = "/";
            }

            super.setDefaultTargetUrl( redirectURL );
            super.onAuthenticationSuccess( request, response, authentication );
        }
        catch ( final ParseException e ) {
            e.printStackTrace();
            super.setDefaultTargetUrl( "/" );
            super.onAuthenticationSuccess( request, response, authentication );
        }
    }

}
