package br.com.maques.api.services.Impl;

import br.com.maques.api.Utils.MessageUtil;
import br.com.maques.api.domain.User;
import br.com.maques.api.repositories.UserRepository;
import br.com.maques.api.services.UserService;
import br.com.maques.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtil.OBJECT_NOT_FOUND));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
