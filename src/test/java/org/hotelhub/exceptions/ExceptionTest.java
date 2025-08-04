package org.hotelhub.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionTest {

    @Test
    void shouldContainCorrectMessage() {
        Long hotelId = 1L;
        HotelNotFoundException exception = new HotelNotFoundException(hotelId);
        assertEquals("Hotel with id: 1 not found!", exception.getMessage());
    }

    @Test
    void shouldHaveNotFoundStatus() {
        HotelNotFoundException exception = new HotelNotFoundException(1L);
        ResponseStatus annotation = exception.getClass()
                .getAnnotation(ResponseStatus.class);

        assertEquals(HttpStatus.NOT_FOUND, annotation.value());
    }
}