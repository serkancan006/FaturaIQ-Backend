package com.example.FaturaIQ.exceptions;

import lombok.Data;

import java.util.*;

@Data
public class ErrorDTO {
    private Date timestamp;
    private int status;
    private String path;
    private Map<String, String> errors = new HashMap<>();

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public void addError(String error, String message) {
        this.errors.put(error, message);
    }
}
