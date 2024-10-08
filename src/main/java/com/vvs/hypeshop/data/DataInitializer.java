package com.vvs.hypeshop.data;

import com.vvs.hypeshop.Repository.UserRepository;
import com.vvs.hypeshop.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        for (int i=1; i<5; i++){
            String defaultUserName = "user"+i+"@hypeshop.com";
            if(userRepository.existsByEmail(defaultUserName)){
                continue;
            }
            User user = new User();
            user.setEmail(defaultUserName);
            user.setPassword("password");
            user.setFirstname("the user");
            user.setLastname("hypeshop");
            userRepository.save(user);
            System.out.println("Default user "+ i + " created successfully");
        }
    }
}
