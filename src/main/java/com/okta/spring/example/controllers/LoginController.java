/*
 * Copyright 2017 Okta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.okta.spring.example.controllers;

import com.okta.spring.example.OktaAuthenticationToken;
import com.okta.spring.example.entities.*;
import com.okta.spring.example.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired(required=true)
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(OktaLogin oktaLogin) {
        return "login";
    }

    @GetMapping("/mfa")
    public String showMfaForm(Authentication authentication, Model model){
        model.addAttribute("totp", new TOTP());
        if (authentication instanceof OktaAuthenticationToken){
            String userId = ((OktaAuthenticationToken) authentication).getId();
            List<OktaEnrolledFactors> enrolledFactors = userService.listEnrolledFactors(userId);

            List<GrantedAuthority> authorities;

            if (enrolledFactors.isEmpty()){
                authorities = new ArrayList<GrantedAuthority>(getAuthorites(userId));
                Authentication newAuth = new OktaAuthenticationToken(authentication.getPrincipal(),
                        authentication.getCredentials(), authorities, userId);
                SecurityContextHolder.getContext().setAuthentication(newAuth);
                return "redirect:/users";
            }

            authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH"));
            Authentication newAuth = new OktaAuthenticationToken(authentication.getPrincipal(),
                    authentication.getCredentials(), authorities, userId);
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            String factorId = enrolledFactors.get(0).getId();

            model.addAttribute("userId", userId);
            model.addAttribute("factorId", factorId);
        }

        return "mfa";
    }

    @PostMapping("/mfa/{userId}/{factorId}")
    public String verifyTOTP(@PathVariable("userId") String userId, @PathVariable("factorId") String factorId,
                               @Valid @ModelAttribute("totp") TOTP totp, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "mfa";
        }
        FactorResult factorResult = userService.verifyTOTP(userId, factorId, totp);

        logger.info("Factor result: " + factorResult.getFactorResult());

        OktaAuthenticationToken auth = (OktaAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(getAuthorites(userId));
        Authentication newAuth = new OktaAuthenticationToken(auth.getPrincipal(),
                auth.getCredentials(), authorities, userId);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return ("redirect:/users");

    }

    @GetMapping("/factor/{id}")
    public String factor(@PathVariable("id") String id, Model model) {
        OktaFactorRes factor = userService.enrollMFA(id);
        model.addAttribute("qrcode",factor.get_embedded().getActivation().get_links().getQrcode().getHref());
        model.addAttribute("factorId", factor.getId());
        model.addAttribute("id", id);

        return "factor";
    }

    @GetMapping("/totp/{userId}/{factorId}")
    public String showTOTP(@PathVariable("userId") String userId, @PathVariable("factorId") String factorId, Model model) {
        model.addAttribute("totp", new TOTP());
        model.addAttribute("userId", userId);
        model.addAttribute("factorId", factorId);
        return "totp";
    }

    @PostMapping("/activate-totp/{userId}/{factorId}")
    public String activateTOTP(@PathVariable("userId") String userId, @PathVariable("factorId") String factorId,
                               @Valid @ModelAttribute("totp") TOTP totp, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "totp";
        }
        userService.activateTOTP(userId, factorId, totp);

        return "redirect:/login?";
    }

    @PostMapping("/post-logout")
    public String logout(Model model) {
        return "redirect:/logout";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "error/403";
    }

    private List<GrantedAuthority> getAuthorites (String id){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        List<OktaGroup> groups = userService.findUserGroups(id);

        if (groups!= null){
            for (OktaGroup group : groups) {
                switch(group.getProfile().getName()) {
                    case "demo_local":
                        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_LOCAL"));
                        break;
                    case "demo_users":
                        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                        break;
                    case "demo_admins":
                        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        break;
                    default:
                        ;
                }
            }
        }

        return grantedAuthorities;

    }

}