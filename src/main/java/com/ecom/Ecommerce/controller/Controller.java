package com.ecom.Ecommerce.controller;

import com.ecom.Ecommerce.dto.UserRequest;
import com.ecom.Ecommerce.dto.UserResponse;
import com.ecom.Ecommerce.entity.User;
import com.ecom.Ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final UserService UserService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> fetchAllUser()
    {
        return new ResponseEntity<>(UserService.fetchAllUser(), HttpStatus.OK);
    }

    @PostMapping("/api/users")
    public String insertUserDetail(@RequestBody UserRequest request)
    {
        UserService.addUserDetail(request);
        return "user added successfully";
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
    {
        return new ResponseEntity<>(UserService.fetchUserById(id), HttpStatus.OK);
    }

    @PutMapping("/api/users/{id}")
//    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public String updateUser(@RequestBody UserRequest request, @PathVariable Long id)
    {
        return UserService.updateCredentialsOfUser(id, request);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable Long id)
    {
        UserService.deleteUser(id);

        return new ResponseEntity<>("User and their associated address deleted successfully.", HttpStatus.OK);
    }
}
