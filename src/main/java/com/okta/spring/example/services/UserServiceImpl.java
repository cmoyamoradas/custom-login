package com.okta.spring.example.services;


import com.okta.spring.example.HostedLoginCodeFlowExampleApplication;
import com.okta.spring.example.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    WebClient.Builder apiClientBuilder;
    public static final String BASE_URL = System.getProperty("baseUrl");
    public static final String AUTHORIZATION = System.getProperty("authorization");

    @Autowired
    public UserServiceImpl(WebClient.Builder apiClientBuilder){
       this.apiClientBuilder = apiClientBuilder;
       this.apiClientBuilder.baseUrl(BASE_URL)
                .defaultHeader("Authorization", "SSWS " + AUTHORIZATION);
    }

    public OktaUser create(User user, boolean activate){
        OktaNewUser oktaUser = new OktaNewUser(
                new OktaNewUser.Profile(
                        user.getFirstName(),user.getLastName(),user.getEmail(),user.getEmail()),
                new OktaNewUser.Credentials(
                        new OktaNewUser.Credentials.Password(
                                user.getPassword())));

        OktaStagedUser newUser = this.apiClientBuilder.build()
                .post()
                .uri("/api/v1/users?activate=" + activate)
                .body(Mono.just(oktaUser), OktaNewUser.class)
                .retrieve()
                .bodyToMono(OktaStagedUser.class)
                .block();

        return newUser;
    }

    @Override
    public OktaUser update(OktaEditUser user, String id) {
        user.getProfile().setLogin(user.getProfile().getEmail());
        return this.apiClientBuilder.build()
                .post()
                //.uri("https://engd15iw3fyet74.m.pipedream.net")
                .uri("/api/v1/users/" + id)
                .body(Mono.just(user), OktaEditUser.class)
                .retrieve()
                .bodyToMono(OktaUser.class)
                .block();
    }

    public List<OktaUser> listUsers(){

        return this.apiClientBuilder.build()
                .get()
                .uri("/api/v1/users?limit=20")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OktaUser>>() {})
                .block();
    }

    @Override
    public OktaUser findUserById(String id) {
        return this.apiClientBuilder.build()
                .get()
                .uri("/api/v1/users/" + id)
                .retrieve()
                .bodyToMono(OktaUser.class)
                .block();
    }

    @Override
    public void delete(String id) {
        this.apiClientBuilder.build()
                .delete()
                //.uri("https://engd15iw3fyet74.m.pipedream.net")
                .uri("/api/v1/users/" + id)
                .retrieve().bodyToMono(Void.class).block();
    }

    @Override
    public List<OktaGroup> findUserGroups(String id){
        List<OktaGroup> groups = this.apiClientBuilder.build()
                .get()
                .uri("/api/v1/users/" + id + "/groups")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OktaGroup>>() {})
                .block();
        return groups;
    }

    @Override
    public List<OktaGroup> listGroups(){
        return this.apiClientBuilder.build()
                .get()
                .uri("/api/v1/groups?filter=type eq \"OKTA_GROUP\"")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OktaGroup>>() {})
                .block();
    }

    @Override
    public void addUserToGroup(String groupId, String userId) {
        this.apiClientBuilder.build()
                .put()
                .uri("/api/v1/groups/" + groupId + "/users/" + userId)
                .retrieve().bodyToMono(Void.class).block();
    }

    @Override
    public void removeUserFromGroup(String groupId, String userId) {
        this.apiClientBuilder.build()
                .delete()
                .uri("/api/v1/groups/" + groupId + "/users/" + userId)
                .retrieve().bodyToMono(Void.class).block();
    }

    @Override
    public OktaFactorRes enrollMFA(String userId) {
        OktaFactorReq req = new OktaFactorReq("token:software:totp","OKTA");
        OktaFactorRes res = this.apiClientBuilder.build()
                .post()
                .uri("/api/v1/users/" + userId + "/factors")
                .body(Mono.just(req), OktaFactorReq.class)
                .retrieve()
                .bodyToMono(OktaFactorRes.class)
                .block();
        String href = res.get_embedded().getActivation().get_links().getQrcode().getHref();
        logger.info("QR Code for user with ID " + userId + ": " + href);
        return res;
    }

    @Override
    public void activateTOTP(String userId, String factorId, TOTP totp) {
        this.apiClientBuilder.build()
                .post()
                .uri("/api/v1/users/" + userId + "/factors/" + factorId + "/lifecycle/activate")
                .body(Mono.just(totp), TOTP.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public List<OktaEnrolledFactors> listEnrolledFactors(String userId) {
        return this.apiClientBuilder.build()
                .get()
                .uri("api/v1/users/" + userId + "/factors")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OktaEnrolledFactors>>() {})
                .block();
    }

    @Override
    public FactorResult verifyTOTP(String userId, String factorId, TOTP totp) {
        return this.apiClientBuilder.build()
                .post()
                .uri("/api/v1/users/" + userId + "/factors/" + factorId + "/verify")
                .body(Mono.just(totp), TOTP.class)
                .retrieve()
                .bodyToMono(FactorResult.class)
                .block();
    }

    @Override
    public void activateUser(String userId) {
        this.apiClientBuilder.build()
                .post()
                .uri("/api/v1/users/" + userId + "/lifecycle/activate")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
