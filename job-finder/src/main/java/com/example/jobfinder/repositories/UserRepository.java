package com.example.jobfinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobfinder.entities.User;

public interface UserRepository extends JpaRepository <User, Long> {

    
} 
