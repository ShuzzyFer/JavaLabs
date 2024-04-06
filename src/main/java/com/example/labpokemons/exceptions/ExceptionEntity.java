package com.example.labpokemons.exceptions;

import java.util.Date;

public class ExceptionEntity {
    private Date timestamp;
    private String message;

    public ExceptionEntity(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
