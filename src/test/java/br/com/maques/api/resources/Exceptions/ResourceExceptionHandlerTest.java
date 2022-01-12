package br.com.maques.api.resources.Exceptions;

import br.com.maques.api.Utils.MessageUtil;
import br.com.maques.api.services.exceptions.DataIntegrityViolationException;
import br.com.maques.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = resourceExceptionHandler.ObjectNotFound(
                new ObjectNotFoundException(MessageUtil.OBJECT_NOT_FOUND), new MockHttpServletRequest());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(StandardError.class, response.getBody().getClass());
        Assertions.assertEquals(MessageUtil.OBJECT_NOT_FOUND, response.getBody().getError());
        Assertions.assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void whenEmailAlreadyExistsThen() {
        ResponseEntity<StandardError> response = resourceExceptionHandler.EmailAlreadyExists(
                new DataIntegrityViolationException( MessageUtil.EMAIL_ALREADY_EXISTS), new MockHttpServletRequest());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(StandardError.class, response.getBody().getClass());
        Assertions.assertEquals(MessageUtil.EMAIL_ALREADY_EXISTS, response.getBody().getError());
        Assertions.assertEquals(400, response.getBody().getStatus());
    }
}