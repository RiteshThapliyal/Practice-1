package com.ecom.Ecommerce;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class serviceConfig {

    private final repository repository;
//    private List<User> userList = new ArrayList<>();
    private Long generatedId = 1L;

    public List<User> fetchAllUser()
    {
        return repository.findAll();
    }

    public void addUserDetail(User user)
    {
//        Random random = new Random();
//
//        User userDetail = new User(random.nextLong(1000), user.getName(), user.getEmail());
//        user.setId(generatedId++);
        repository.save(user);
    }

    public User fetchUserById(Long id) {
//        for (User user : userList)
//        {
//            if (user.getId().equals(id))
//            {
//                return user;
//            }
//        }
//        return null;

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst()
//                .orElse(null);
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error while fetching id"));
    }

    public String updateCredentialsOfUser(Long id, User user) {
//        for (User u : userList)
//        {
//            if (u.getId().equals(id))
//                u.setName(user.getName());
//            u.setEmail(user.getEmail());
//                return "Update Successfully";
//        }
//        return null;

        return repository.findById(id)
                .map(existingUser -> {existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    repository.save(existingUser);
                    return "Values Updated";
                }).orElse("Values not Updated");
    }
}
