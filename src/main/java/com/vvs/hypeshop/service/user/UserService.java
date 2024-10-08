package com.vvs.hypeshop.service.user;

import com.vvs.hypeshop.Repository.UserRepository;
import com.vvs.hypeshop.dto.UserDto;
import com.vvs.hypeshop.exceptions.AlreadyExistsException;
import com.vvs.hypeshop.exceptions.ResourceNotFoundException;
import com.vvs.hypeshop.model.User;
import com.vvs.hypeshop.request.CreateUserRequest;
import com.vvs.hypeshop.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstname(request.getFirstname());
                    user.setLastname(request.getLastname());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops"+ request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {

        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstname(request.getFirstname());
            existingUser.setLastname(request.getLastname());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });

    }
@Override
public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
