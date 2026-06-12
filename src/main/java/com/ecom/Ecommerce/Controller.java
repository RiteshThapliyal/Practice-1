package com.ecom.Ecommerce;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final serviceConfig serviceConfig;

    @GetMapping("/api/users")
    public List<User> fetchAllUser()
    {
        return serviceConfig.fetchAllUser();
    }

    @PostMapping("/api/users")
    public String insertUserDetail(@RequestBody User user)
    {
        serviceConfig.addUserDetail(user);
        return "user added successfully";
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable Long id)
    {
        return serviceConfig.fetchUserById(id);
    }

    @PutMapping("/api/users/{id}")
//    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public String updateUser(@RequestBody User user, @PathVariable Long id)
    {
        return serviceConfig.updateCredentialsOfUser(id, user);
    }
}
