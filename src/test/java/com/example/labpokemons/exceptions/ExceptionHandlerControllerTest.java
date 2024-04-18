package com.example.labpokemons.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static com.example.labpokemons.utilities.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {
    private final ExceptionHandlerController exceptionHandlerController = new ExceptionHandlerController();

    @Test
    void BadRequestExceptionTest_ifThrown(){
        BadRequestException e=new BadRequestException(INVALID_INFO_MSG);

        assertNotNull(exceptionHandlerController.badRequestExceptionHandler(e));
        assertEquals(Objects.requireNonNull(exceptionHandlerController.badRequestExceptionHandler(e)
                .getBody()).getMessage(), e.getMessage());
    }

    @Test
    void NotFoundExceptionTest_ifThrown(){
        NotFoundException e=new NotFoundException(NOT_FOUND_MSG);

        assertNotNull(exceptionHandlerController.notFoundExceptionHandler(e));
        assertEquals(Objects.requireNonNull(exceptionHandlerController.notFoundExceptionHandler(e)
                .getBody()).getMessage(), e.getMessage());
    }

    @Test
    void ServerExceptionTest_ifThrown(){
        ServerException e=new ServerException(SERVER_ERROR_MSG);

        assertNotNull(exceptionHandlerController.serverExceptionHandler(e));
        assertEquals(Objects.requireNonNull(exceptionHandlerController.serverExceptionHandler(e)
                .getBody()).getMessage(), e.getMessage());
    }
}
