package com.vvs.hypeshop.data;

import com.vvs.hypeshop.Repository.RoleRespository;
import com.vvs.hypeshop.Repository.UserRepository;
import com.vvs.hypeshop.model.Role;
import com.vvs.hypeshop.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRespository roleRespository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRespository.findByName("ROLE_USER").get();
        for (int i=1; i<5; i++){
            String defaultUserName = "user"+i+"@hypeshop.com";
            if(userRepository.existsByEmail(defaultUserName)){
                continue;
            }
            User user = new User();
            user.setEmail(defaultUserName);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setFirstname("the user");
            user.setLastname("hypeshop");
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user "+ i + " created successfully");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRespository.findByName("ROLE_ADMIN").get();
        for (int i=1; i<2; i++){
            String defaultUserName = "admin"+i+"@hypeshop.com";
            if(userRepository.existsByEmail(defaultUserName)){
                continue;
            }
            User user = new User();
            user.setEmail(defaultUserName);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setFirstname("the admin");
            user.setLastname("admin" + i);
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Admin user "+ i + " created successfully");
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles){
        roles.stream()
                .filter(role -> roleRespository.findByName(role).isEmpty())
                .map(Role:: new).forEach(roleRespository::save);

    }
}
