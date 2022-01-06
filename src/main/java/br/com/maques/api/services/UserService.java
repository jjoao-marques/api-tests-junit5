package br.com.maques.api.services;

import br.com.maques.api.domain.User;

public interface UserService {

    User findById(Long id);
}
