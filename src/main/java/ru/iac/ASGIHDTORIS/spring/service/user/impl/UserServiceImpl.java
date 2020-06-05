package ru.iac.ASGIHDTORIS.spring.service.user.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.iac.ASGIHDTORIS.spring.domain.Role;
import ru.iac.ASGIHDTORIS.spring.domain.User;
import ru.iac.ASGIHDTORIS.spring.repo.UserRepo;
import ru.iac.ASGIHDTORIS.spring.service.user.UserService;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Value("${system.id}")
    private String systemID;

    @Value("${toris.url}")
    private String torisUrl;

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User loginUser(String token) {
        User user = getUserInfo(token);

        return register(user, token);
    }

    @Override
    public User getUserInfo(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> entity = new HttpEntity<>(createHeader(token));

        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject) parser
                    .parse(restTemplate.exchange(torisUrl, HttpMethod.GET, entity, String.class).getBody());

            return createUser((JSONObject) parser.parse(object.get("data").toString()));
        } catch (Exception e) {
            e.printStackTrace();

            return User.createEmptyUser();
        }
    }

    private HttpHeaders createHeader(String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "Token " + token);
        headers.set("SystemID", systemID);

        return headers;
    }

    private User createUser(JSONObject jsonUser) {
        return User
                .builder()
                .fio(jsonUser.get("USER_FIO").toString())
                .secretKey(jsonUser.get("USER_ESOV_UID").toString())
                .build();
    }

    private User register(User user, String token) {
        if (!userRepo.existsBySecretKey(user.getSecretKey())) {
            try {
                user.setRoles(getRoles(token));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            user = userRepo.save(user);
        } else if (userRepo.existsBySecretKey(user.getSecretKey())) {
            user = userRepo.findBySecretKey(user.getSecretKey());
        }

        return user;
    }

    private Set<Role> getRoles(String token) throws ParseException {
        Set<Role> roles = new LinkedHashSet<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> entity = new HttpEntity<>(createHeader(token));

        JSONParser parser = new JSONParser();

        JSONObject object = (JSONObject) parser
                .parse(restTemplate.exchange(torisUrl, HttpMethod.GET, entity, String.class).getBody());
        JSONObject object1 = (JSONObject) parser.parse(object.get("data").toString());
        JSONArray object2 = (JSONArray) parser.parse(object1.get("USER_ROLES").toString());

        for (int i = 0; i < object2.size(); i++) {
            if (object2.get(0).toString().lastIndexOf("role:gihd-uzd:admin") != -1) {
                roles.add(Role.ADMIN);
            }
            if (object2.get(0).toString().lastIndexOf("role:gihd-uzd:user") != -1) {
                roles.add(Role.USER);
            }
        }

        return roles;
    }
}
