package br.com.maques.api.services.Impl;

import br.com.maques.api.Utils.MessageUtil;
import br.com.maques.api.domain.User;
import br.com.maques.api.domain.dto.UserDTO;
import br.com.maques.api.repositories.UserRepository;
import br.com.maques.api.services.exceptions.DataIntegrityViolationException;
import br.com.maques.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    public static final Long ID = 1L;
    public static final String NAME = "João";
    public static final String EMAIL = "joao@mail.com";
    public static final String PASSWORD = "123";
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);

        User response = service.findById(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(ID, response.getId());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(
                new ObjectNotFoundException(MessageUtil.OBJECT_NOT_FOUND));

        try {
            service.findById(ID);
        }catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals(MessageUtil.OBJECT_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(User.class, response.get(0).getClass());

        Assertions.assertEquals(ID, response.get(0).getId());
        Assertions.assertEquals(NAME, response.get(0).getName());
        Assertions.assertEquals(PASSWORD, response.get(0).getPassword());
    }

    @Test
    void whenCreateThenReturnSucess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User response = service.create(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2L);
            service.create(userDTO);
        }catch (Exception e) {
            Assertions.assertEquals(DataIntegrityViolationException.class, e.getClass());
            Assertions.assertEquals(MessageUtil.EMAIL_ALREADY_EXISTS, e.getMessage());
        }


    }

    @Test
    void whenUpdateThenReturnSucess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User response = service.update(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2L);
            service.update(userDTO);
        }catch (Exception e) {
            Assertions.assertEquals(DataIntegrityViolationException.class, e.getClass());
            Assertions.assertEquals(MessageUtil.EMAIL_ALREADY_EXISTS, e.getMessage());
        }


    }

    @Test
    void deleteWithSuccess() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());
        service.delete(ID);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}