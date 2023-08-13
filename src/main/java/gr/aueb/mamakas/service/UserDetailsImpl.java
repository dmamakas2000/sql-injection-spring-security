package gr.aueb.mamakas.service;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gr.aueb.mamakas.database.User;
import lombok.Getter;

/**
 * Implementation of UserDetails interface.
 *
 * @author dimitrios.mamakas
 *
 */
@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String salt;

    @JsonIgnore
    private String password;

    private String lastModified;

    private String description;

    /**
     * Main constructor.
     *
     * @param id User's id.
     * @param username User's user-name.
     * @param salt User's salt.
     * @param password User's password.
     * @param lastModified User's last modified field.
     * @param description User's description.
     */
    public UserDetailsImpl( final Long id, final String username, final String salt, final String password, final String lastModified,
            final String description ) {
        this.id = id;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.lastModified = lastModified;
        this.description = description;
    }

    /**
     * Builds a user.
     *
     * @param user User to build
     * @return UserDetailsImpl instance.
     */
    public static UserDetailsImpl build( final User user ) {
        return new UserDetailsImpl( user.getId(), user.getUsername(), user.getSalt(), user.getPassword(), user.getLastModified(),
                user.getDescription() );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        final UserDetailsImpl user = ( UserDetailsImpl ) o;
        return Objects.equals( this.id, user.id );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

}