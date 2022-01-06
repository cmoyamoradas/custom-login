package com.okta.spring.example.services;

import com.okta.spring.example.entities.*;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    OktaUser create(User user, boolean activate);
    OktaUser update(OktaEditUser user, String id);
    List<OktaUser> listUsers();
    OktaUser findUserById(String id);
    void delete(String id);
    List<OktaGroup> findUserGroups(String id);
    List<OktaGroup> listGroups();
    void addUserToGroup(String groupId, String userId);
    void removeUserFromGroup(String groupId, String userId);
    OktaFactorRes enrollMFA(String userId);
    void activateTOTP(String userId, String factorId, TOTP totp);
    List<OktaEnrolledFactors> listEnrolledFactors(String userId);
    FactorResult verifyTOTP(String userId, String factorId, TOTP totp);
    void activateUser(String userId);
}
