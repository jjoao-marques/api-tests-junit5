package br.com.maques.api.services.Impl;

import br.com.maques.api.Utils.MessageUtil;
import br.com.maques.api.domain.User;
import br.com.maques.api.domain.dto.UserDTO;
import br.com.maques.api.repositories.UserRepository;
import br.com.maques.api.services.UserService;
import br.com.maques.api.services.exceptions.DataIntegrityViolationException;
import br.com.maques.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtil.OBJECT_NOT_FOUND));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(mapper.map(obj, User.class));
    }

    private void findByEmail(UserDTO objDto) {
        Optional<User>  user = userRepository.findByEmail(objDto.getEmail());
        if(user.isPresent()) {
            throw  new DataIntegrityViolationException(MessageUtil.EMAIL_ALREADY_EXISTS);
        }
    }
}
