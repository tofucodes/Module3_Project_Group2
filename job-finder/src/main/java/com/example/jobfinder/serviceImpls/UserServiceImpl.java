package com.example.jobfinder.serviceImpls;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;
import com.example.jobfinder.exceptions.JobNotFoundException;
import com.example.jobfinder.exceptions.UserNotFoundException;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.repositories.UserRepository;
import com.example.jobfinder.services.UserService;


@Service
@Transactional   
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        logger.info("addJobToUser method being called");
        // find the user by Id
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        Set <Job> userJobs = user.getJobs();

        // to initialise the collections if they are null
        // to avoid NullPointerExceptions
        if (job.getUsers() == null) {
        job.setUsers(new HashSet<>());}

        if(userJobs == null){
            user.setJobs(new HashSet<>());
        } else if (!userJobs.contains(job)){
            userJobs.add(job);
            user.setJobs(userJobs);
            job.getUsers().add(user);
            userRepository.save(user);
            jobRepository.save(job);
        } 
    
        logger.info("userRepository: " + userRepository.findAll());
        logger.info("jobRepository: " + jobRepository.findAll());

        return job;
    }




    
}
