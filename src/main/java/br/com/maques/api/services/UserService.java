package br.com.maques.api.services;

import br.com.maques.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();
}
