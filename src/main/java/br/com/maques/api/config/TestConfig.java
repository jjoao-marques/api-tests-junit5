package br.com.maques.api.config;

import br.com.maques.api.domain.User;
import br.com.maques.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
public class TestConfig {


    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "João Marques", "joao@mail.com", "123");
        User u2 = new User(null, "Taiane", "taiane@mail.com", "321");

        userRepository.saveAll(List.of(u1, u2));
    }
}
