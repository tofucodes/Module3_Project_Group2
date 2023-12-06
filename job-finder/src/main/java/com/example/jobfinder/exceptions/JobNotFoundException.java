package com.example.jobfinder.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(Long id) {
        super("Could not find job with id: " + id + ".");
    }
}
