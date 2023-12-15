package com.example.jobfinder.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;
import com.example.jobfinder.exceptions.JobAlreadySavedException;
import com.example.jobfinder.exceptions.UserNotFoundException;
import com.example.jobfinder.repositories.JobRepository;
import com.example.jobfinder.repositories.UserRepository;
import com.example.jobfinder.serviceImpls.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    UserServiceImpl userService;

    // @BeforeAll
    // public void beforeAll(){
    //     staticLogger.info("Before all tests in UserControllerTest class");
    // } 

    @BeforeEach
    public void beforeEach() {
        logger.info("Before each test in UserServiceImplTest class");
    }
    
    @DisplayName("Create User")
    @Test
    public void createCustomerTest() {
        
        logger.info("Testing createCustomer method");
        User user = User.builder().firstName("Trevor").lastName("Noah").email("trevor@jobfinder.com").country("South Africa").build();
        logger.info("user: " + user);
        
        when((userRepository.save(user))).thenReturn(user);

        logger.info("Execute & Assert");

        User savedUser = userService.createUser(user);
        logger.info("savedUser: " + savedUser);

        assertEquals(user, savedUser, "The saved user should be the same as the new customer");

        verify(userRepository, times(1)).save(user);
    }

    @DisplayName("Get User by Id")
    @Test 
    public void getUserTest(){
        logger.info("Testing getUser method");
        User user = User.builder().firstName("John").lastName("Mulaney").email("john@jobfinder.com").country("US").build();
        logger.info("user: " + user);

        Long userId = 1L;

        // return the user we have created above when trying to find user with userId 
        when((userRepository.findById(userId))).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUser(userId);
        logger.info("retrievedUser: " + retrievedUser);

        assertEquals(user, retrievedUser);
    }

    @DisplayName("Test UserNotFound Exception")
    @Test
    public void testGetUserNotFound(){
        Long userId = 1L;
        // instead of returning user, don't return anything
        when((userRepository.findById(userId))).thenReturn(Optional.empty());

        // to check that exception is being thrown
        assertThrows(UserNotFoundException.class,() -> userService.getUser(userId));
    }

    @DisplayName("Delete user")
    @Test
    public void deleteUserTest() throws Exception {

        Long userId = 1L;

        userService.deleteUser(userId);

        // checks that deleteById method is called once with the argument userId
        verify(userRepository, Mockito.times(1)).deleteById(userId);

    }

    @DisplayName("Update user")
    @Test
    public void updateUserTest() throws Exception {
        
        Long userId = 1L;

        User existingUser = User.builder().firstName("Dave").lastName("Matthews").email("save@jobfinder.com").country("US").build();
        User updatedUser = User.builder().firstName("John").lastName("Tan").email("john@jobfinder.com").country("Indonesia").build();

        logger.info("updatedUser: " + updatedUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn((updatedUser));

        User result = userService.updateUser(userId, updatedUser);

        logger.info("result: " + result);

        assertEquals(updatedUser.getFirstName(), result.getFirstName(), "The updated first name should be John");
        assertEquals(updatedUser.getLastName(), result.getLastName(), "The updated last name should be Matthews");
        assertEquals(updatedUser.getEmail(), result.getEmail(), "The updated email should be john@jobfinder.com");
        assertEquals(updatedUser.getCountry(), result.getCountry(), "The updated country should be Indonesia");
        
        // verify(userRepository, times(1)).findById(userId);
        // verify(userRepository, times(1)).save(updatedUser);

    }


    @DisplayName("Add job to user")
    @Test
    void AddJobToUserTest(){

        logger.info("Testing addJobtoUser method");

        Long userId = 1L;
        Long jobId = 2L;

        User user = User.builder().firstName("John").lastName("Mulaney").email("john@jobfinder.com").country("US").build();
        user.setId(userId);

        Job job = new Job("Analyst", "test", "Technical", 45000, 2, "United Kingdom");
        job.setId(jobId);

        // mocking behaviour of User Repo findById
        when((userRepository.findById(userId))).thenReturn(Optional.of(user));

        // mocking behaviour of Job Repo findById
        when((jobRepository.findById(jobId))).thenReturn(Optional.of(job));
       
        userService.addJobToUser(userId, jobId);

        // verify that user added to job
        verify(jobRepository, times(1)).save(job);
        assertEquals(1, job.getUsers().size(), "job should have one user saved to it");
        assertTrue(job.getUsers().contains(user));

    }

    @DisplayName("Test JobAlreadySavedException Exception")
    @Test
    public void testJobAlreadySavedException(){
        Long userId = 1L;
        Long jobId = 2L;

        User user = User.builder().firstName("John").lastName("Mulaney").email("john@jobfinder.com").country("US").build();
        user.setId(userId);

        Job job = new Job("Analyst", "test", "Technical", 45000, 2, "United Kingdom");
        job.setId(jobId);

        when((userRepository.findById(userId))).thenReturn(Optional.of(user));

        when((jobRepository.findById(jobId))).thenReturn(Optional.of(job));

        Set<Job> userJobs = new HashSet<>();
        userJobs.add(job);
        user.setJobs(userJobs);

        // to check that exception is being thrown
        assertThrows(JobAlreadySavedException.class,() -> userService.addJobToUser(userId, jobId));

        verify(jobRepository, never()).save(any(Job.class));

    }

}


