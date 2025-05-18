package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /**
     * Handles GET requests to /login and shows the login form.
     *
     * @param model  Spring Model to pass attributes to the view
     * @param error  Optional query parameter indicating login error
     * @param logout Optional query parameter indicating successful logout
     * @return The login view name
     */
    @GetMapping("/login")
    public String showLoginForm(Model model,
                                 @RequestParam(value = "error", required = false) String error,
                                 @RequestParam(value = "logout", required = false) String logout) {

        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }

        return "login"; // Returns login.html (Thymeleaf or other template engine)
    }


}
