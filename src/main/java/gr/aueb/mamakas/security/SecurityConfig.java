package gr.aueb.mamakas.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import gr.aueb.mamakas.handler.LoginFailureHandler;
import gr.aueb.mamakas.handler.LoginSuccessHandler;
import gr.aueb.mamakas.service.CustomUserDetailService;

/**
 * Spring Security configuration class.
 *
 * @author dimitrios.mamakas
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailService userDetailsService;

    @Autowired
    private LoginFailureHandler failureHandler;

    @Autowired
    private LoginSuccessHandler successHandler;

    /**
     * Spring Security authentication configuration.
     */
    @Override
    protected void configure( final AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( this.userDetailsService ).passwordEncoder( passwordEncoder() );
    }

    /**
     * Spring Security HTTP requests configuration.
     */
    @Override
    protected void configure( final HttpSecurity http ) throws Exception {
        http.authorizeRequests().antMatchers( "/login", "/login/error", "/login/block" ).permitAll().anyRequest().authenticated().and()
                .formLogin().permitAll().loginPage( "/login" ).successHandler( this.successHandler )
                .failureHandler( this.failureHandler ).and().logout().logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
                .logoutSuccessUrl( "/login" );
    }

    /**
     * Encodes and checks for passwords.
     *
     * @return PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode( final CharSequence charSequence ) {
                return getMd5( charSequence.toString() );
            }

            @Override
            public boolean matches( final CharSequence charSequence, final String s ) {
                return getMd5( charSequence.toString() ).equals( s );
            }
        };
    }

    /**
     * Generates MD5 hashed strings.
     *
     * @param input String to generate MD5.
     * @return Hashed String.
     */
    public String getMd5( String input ) {
        try {
            final String un = this.userDetailsService.getUserName();

            String salt = "";

            if ( !Objects.isNull( un ) ) {
                salt = this.userDetailsService.getSalt();
            }

            input = input + salt;

            final MessageDigest md = MessageDigest.getInstance( "MD5" );
            final byte[] messageDigest = md.digest( input.getBytes() );
            final BigInteger no = new BigInteger( 1, messageDigest );
            String hashtext = no.toString( 16 );

            while ( hashtext.length() < 32 ) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch ( final NoSuchAlgorithmException e ) {
            e.printStackTrace();
            return null;
        }
    }

}
