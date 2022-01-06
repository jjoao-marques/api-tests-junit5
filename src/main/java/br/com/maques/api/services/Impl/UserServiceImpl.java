package br.com.maques.api.services.Impl;

import br.com.maques.api.domain.User;
import br.com.maques.api.repositories.UserRepository;
import br.com.maques.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
