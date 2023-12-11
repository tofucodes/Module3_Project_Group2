package com.example.jobfinder.exceptions;

public class JobAlreadySavedException extends RuntimeException {
    public JobAlreadySavedException(Long id) {
        super("Job with id:" + id + " is already saved.");
    }
}
