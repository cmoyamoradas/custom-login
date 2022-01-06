package com.okta.spring.example.controllers;

import com.okta.spring.example.entities.*;
import com.okta.spring.example.handlers.CustomAccessDeniedHandler;
import com.okta.spring.example.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Autowired(required=true)
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "sign-up";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sign-up";
        }
        OktaUser newUser = userService.create(user, true);
        if (user.isMfa()){
            return "redirect:/factor/" + newUser.getId();
        }
        return "redirect:/login?";
    }

    @GetMapping("/newuser")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new-user";
    }

    @PostMapping(value = "/createuser", params = "submit")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "new-user";
        }
        OktaUser newUser = userService.create(user, false);
        userService.activateUser(newUser.getId());
        return "redirect:/users";
    }
    @PostMapping(value= "/createuser", params = "cancel")
    public String cancelCreateUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        OktaUser user = userService.findUserById(id);
        if (user.getCredentials().getProvider().getType().equals("LDAP")){
            return "non-editable";
        }
        model.addAttribute("user", (OktaEditUser)user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id, @Valid OktaEditUser user, BindingResult result, Model model) {
        userService.update(user,id);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") String id, Model model) {
        OktaUser user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "delete-user";
    }

    @GetMapping("/groups/{id}")
    public String showGroupsForm(@PathVariable("id") String id, Model model) {
        List<OktaGroup> userGroups = userService.findUserGroups(id);
        List<OktaGroup> groups = userService.listGroups();

        groups.removeAll(userGroups);

        model.addAttribute("userGroups", userGroups);
        model.addAttribute("groups", groups);

        return "groups";
    }

    @GetMapping("/addToGroup/{groupId}/{userId}")
    public String addToGroup(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId, Model model) {
        userService.addUserToGroup(groupId, userId);
        return "redirect:/groups/{userId}";
    }

    @GetMapping("/removeFromGroup/{groupId}/{userId}")
    public String removeFromGroup(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId, Model model) {
        userService.removeUserFromGroup(groupId, userId);
        return "redirect:/groups/{userId}";
    }

    @PostMapping(value = "/doDelete/{id}", params = "submit")
    public String deleteUser(@PathVariable("id") String id) {
        userService.delete(id);
        return "redirect:/users";
    }
    @PostMapping(value = "/doDelete/{id}", params = "cancel")
    public String cancelDeleteUser(@PathVariable("id") String id) {
        return "redirect:/users";
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllException(final Throwable th, final Model model) {
        LOG.error("An error has been thrown: " + th.getMessage());
        String errorMessage = (th != null ? th.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
