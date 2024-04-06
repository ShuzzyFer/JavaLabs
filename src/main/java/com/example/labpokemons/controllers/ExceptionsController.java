package com.example.labpokemons.controllers;

import com.example.labpokemons.exceptions.BadRequestException;
import org.springframework.web.bind.annotation.*;

import static com.example.labpokemons.utilities.Constants.INVALID_INFO_MSG;
@RestController
public class ExceptionsController {
    @GetMapping("/{aboba}")
    public void getException(@PathVariable String aboba) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }
    @GetMapping("/{aboba}/{aboba2}")
    public void getExceptionExtern(@PathVariable String aboba, @PathVariable String aboba2) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }
    @PostMapping("/{aboba}")
    public void postException(@PathVariable String aboba) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }
    @PostMapping("/{aboba}/{aboba2}")
    public void postExceptionExtern(@PathVariable String aboba, @PathVariable String aboba2) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }

    @PutMapping("/{aboba}")
    public void putException(@PathVariable String aboba) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }
    @PutMapping("/{aboba}/{aboba2}")
    public void putExceptionExtern(@PathVariable String aboba, @PathVariable String aboba2) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }
    @DeleteMapping("/{aboba}")
    public void deleteException(@PathVariable String aboba) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }
    @DeleteMapping("/{aboba}/{aboba2}")
    public void deleteExceptionExtern(@PathVariable String aboba, @PathVariable String aboba2) {
        throw new BadRequestException(INVALID_INFO_MSG);
    }
}
