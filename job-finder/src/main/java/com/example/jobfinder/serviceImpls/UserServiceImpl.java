package com.example.jobfinder.serviceImpls;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;
import com.example.jobfinder.exceptions.UserNotFoundException;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.repositories.UserRepository;
import com.example.jobfinder.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JobRepository jobRepository;

    public UserServiceImpl (UserRepository userRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public User createUser(User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }


    @Override
    public User getUser(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return foundUser;
    }

    @Override
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setCountry(user.getCountry());
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Job addJobToUser(Long id, Job job) {
        // find the user by Id
        User selectedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        // Get the set of jobs associated with user
        Set<Job> userJobs = selectedUser.getJobs();

        // add new job to ser of user's job
        userJobs.add(job);
        
        // update the set of jobs in user entity
        selectedUser.setJobs(userJobs);

        job.getUsers().add(selectedUser);

        // save the user 
        // will cascade change to associated jobs
        userRepository.save(selectedUser);
        jobRepository.save(job);

        return job;
    }





    



    
    
}
