package com.vvs.hypeshop.service.user;

import com.vvs.hypeshop.dto.UserDto;
import com.vvs.hypeshop.model.User;
import com.vvs.hypeshop.request.CreateUserRequest;
import com.vvs.hypeshop.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertToUserDto(User user);

    User getAuthenticatedUser();
}
