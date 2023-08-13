package gr.aueb.mamakas.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import gr.aueb.mamakas.authentication.manager.Singleton;

/**
 * Custom defined failure handler.
 *
 * @author dimitrios.mamakas
 *
 */
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Method triggered on a failed authentication event.
     */
    @Override
    public void onAuthenticationFailure( final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException exception ) throws IOException, ServletException {

        // Check if the IP is blocked
        final boolean isBlocked = Singleton.getInstance().isBlocked();

        String redirectURL = "";

        // Customize redirect URL
        if ( isBlocked ) {
            redirectURL = "/login/block";
        }
        else {
            redirectURL = "/login/error";
        }

        // Redirect the user into the specified URL
        super.setDefaultFailureUrl( redirectURL );

        super.onAuthenticationFailure( request, response, exception );
    }

}