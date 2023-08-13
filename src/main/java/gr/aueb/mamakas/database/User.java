package gr.aueb.mamakas.database;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing a general user retrieved from the database.
 *
 * @author dimitrios.mamakas
 *
 */
@Entity
@Table(name = "users", schema = "public")
@Data
@NoArgsConstructor
public class User implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column(name = "username")
    private String username;

    @Column(name = "salt")
    private String salt;

    @Column(name = "password")
    private String password;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "description")
    private String description;

    /**
     * 2 argument constructor.
     *
     * @param username User's user-name
     * @param password User's password
     */
    public User( final String username, final String password ) {
        this.username = username;
        this.password = password;
    }

}
