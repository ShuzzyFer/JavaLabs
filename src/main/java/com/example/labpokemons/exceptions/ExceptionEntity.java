package com.example.labpokemons.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Schema(description = "Exception")
public class ExceptionEntity {
    @Schema(description = "timestamp")
    private Date timestamp;
    @Schema(description = "Message")
    private String message;

    public ExceptionEntity(final Date tmstmp, final String msg) {
        this.timestamp = tmstmp;
        this.message = msg;
    }
}
