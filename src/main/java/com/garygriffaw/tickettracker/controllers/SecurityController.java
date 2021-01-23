package com.garygriffaw.tickettracker.controllers;

import com.garygriffaw.tickettracker.converters.UserAccountConverter;
import com.garygriffaw.tickettracker.dto.PasswordDto;
import com.garygriffaw.tickettracker.dto.ProfileDto;
import com.garygriffaw.tickettracker.dto.RegisterDto;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class SecurityController {

    @Autowired
    UserAccountConverter userAccountConverter;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    HomeController homeController;

    // ---------------------------------------------------------------------------------------------------------------
    // User Registration
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/register")
    public String processRegisterUrl(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("registerDto", registerDto);

        return "security/register";
    }

    @PostMapping("/register/save")
    public String saveRegistration(Model model, @Valid RegisterDto registerDto, Errors errors) {
        if(errors.hasErrors())
            return "security/register";

        UserAccount userAccount = userAccountConverter.registerDtoToEntity(registerDto);
        userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
        userAccountService.save(userAccount);

        return "redirect:/";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // User Profile Update
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/profile")
    public String processProfileUrl(Model model, Principal principal) {
        UserAccount userAccount = userAccountService.findById(principal.getName());
        ProfileDto profileDto = userAccountConverter.entityToProfileDto(userAccount);
        model.addAttribute("profileDto", profileDto);

        return "security/profile";
    }

    @PostMapping(value="/profile/save", params={"save=Save"})
    public String saveProfile(Model model, @Valid ProfileDto profileDto, Errors errors) {
        if(errors.hasErrors()) {
            return "security/profile";
        }

        UserAccount userAccount = userAccountConverter.profileDtoToEntity(profileDto);
        userAccountService.save(userAccount);

        return "redirect:/";
    }

    @PostMapping(value="/profile/save", params={"cancel=Cancel"})
    public String cancelSaveProfile(Model model, Principal principal) {
        return homeController.displayHomeList(model, principal);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // User Password Update
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/password")
    public String processPasswordUrl(Model model, Principal principal) {
        UserAccount userAccount = userAccountService.findById(principal.getName());
        PasswordDto passwordDto = userAccountConverter.entityToPasswordDto(userAccount);
        model.addAttribute("passwordDto", passwordDto);

        return "security/password";
    }

    @PostMapping(value="/password/save", params={"save=Save"})
    public String savePassword(Model model, @Valid PasswordDto passwordDto, Errors errors) {
        if(errors.hasErrors()) {
            return "security/password";
        }

        UserAccount userAccount = userAccountConverter.passwordDtoToEntity(passwordDto);
        userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
        userAccountService.save(userAccount);

        return "redirect:/profile";
    }

    @PostMapping(value="/password/save", params={"cancel=Cancel"})
    public String cancelSavePassword(Model model) {
        return "redirect:/profile";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // User Logout
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/logout")
    public String processLogoutUrl(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
