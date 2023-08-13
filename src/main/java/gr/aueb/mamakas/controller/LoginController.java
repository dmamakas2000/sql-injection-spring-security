package gr.aueb.mamakas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gr.aueb.mamakas.database.UserRepository;
import gr.aueb.mamakas.domain.Form;

/**
 * Application's main controller.
 *
 * @author dimitrios.mamakas
 *
 */
@Controller
public class LoginController {

    @Autowired
    UserRepository repository;

    /**
     * Get mapping for login page.
     *
     * @param model Model.
     * @return Login page.
     */
    @GetMapping("/login")
    public String login( final Model model ) {
        model.addAttribute( "form", new Form() );
        return "login";
    }

    /**
     * GET mapping for failed login error page
     *
     * @param model Model.
     * @return Failed login error page.
     */
    @GetMapping("/login/error")
    public String loginFailed( final Model model ) {
        model.addAttribute( "form", new Form() );
        return "login_fail";
    }

    /**
     * GET mapping for blocked page.
     *
     * @return Blocked page.
     */
    @GetMapping("/login/block")
    public String blockedUser() {
        return "block";
    }

    /**
     * GET mapping for change password page.
     *
     * @return
     */
    @GetMapping("/change/password")
    public String getChangePasswordPage() {
        return "change_password";
    }

    /**
     * GET mapping for home page.
     *
     * @return Home page.
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

}
