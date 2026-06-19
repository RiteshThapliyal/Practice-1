package com.ecom.Ecommerce.service;

import com.ecom.Ecommerce.dto.AddressDTO;
import com.ecom.Ecommerce.dto.UserRequest;
import com.ecom.Ecommerce.dto.UserResponse;
import com.ecom.Ecommerce.entity.Address;
import com.ecom.Ecommerce.entity.User;
import com.ecom.Ecommerce.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Repository repository;
//    private List<User> userList = new ArrayList<>();
    private Long generatedId = 1L;

    public List<UserResponse> fetchAllUser()
    {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        if (user.getAddress() != null)
        {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipCode(user.getAddress().getZipCode());

            response.setAddress(addressDTO);
        }

        return response;
    }

    public void addUserDetail(UserRequest request)
    {
        User user = new User();
        updateUserFromRequest(user, request);
        repository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if (request.getAddress() != null)
        {
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setState(request.getAddress().getState());
            address.setCity(request.getAddress().getCity());
            address.setCountry(request.getAddress().getCountry());
            address.setZipCode(request.getAddress().getZipCode());
            user.setAddress(address);
            address.setUser(user);
        }
    }

    public UserResponse fetchUserById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Error while fetching id"));
    }

    public String updateCredentialsOfUser(Long id, UserRequest request) {
        return repository.findById(id)
                .map(existingUser -> {existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    existingUser.setEmail(request.getEmail());
                    repository.save(existingUser);
                    return "Values Updated";
                }).orElse("Values not Updated");
    }

    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        repository.delete(user);
    }
}
