package com.example.jobfinder.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.jobfinder.entities.Job;
import com.example.jobfinder.entities.User;
import com.example.jobfinder.services.UserServiceImplTest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
// to mock HTTP requests and responses
@AutoConfigureMockMvc
public class UserControllerTest {

    private final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    // private static Logger staticLogger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // @BeforeAll
    // public void beforeAll(){
    //     staticLogger.info("Before all tests in UserControllerTest class");
    // }    

    @BeforeEach
    public void beforeEach() {
        logger.info("Before each test in UserControllerTest class");
    }
    
    @DisplayName("Get user by Id")
    @Test
    public void getUserByIdTest() throws Exception {

        logger.info("Testing getUserById in Usercontroller Method");

        RequestBuilder request = MockMvcRequestBuilders.get("/users/1");

    mockMvc.perform(request)
    .andExpect(status().isOk())
    // to ensure endpoint is producing the expected content type
    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    // extracts value of id field from the requests'response
    .andExpect(jsonPath("$.id").value(1));
    }

    @DisplayName("Get all users")
    @Test
    public void getAllUsersTest() throws Exception{

        RequestBuilder request = MockMvcRequestBuilders.get("/users");

    mockMvc.perform(request)
    .andExpect(status().isOk())
    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    // because number of users in dataloader is 10
    .andExpect(jsonPath("$.size()").value(10));
    }

    @DisplayName("Valid user input during user creation")
    @Test
    public void validUserCreationTest() throws Exception {

        logger.info("creating valid user to test createUser method in UserController");

        User user = User.builder().firstName("Jimmy").lastName("Yang").email("jmmy@jobfinder.com").country("US").build();

        // converts Java objects to JSON-formatted strings allows us to include them in the body of HTTP requests
        String newUserAsJson = objectMapper.writeValueAsString(user);

        // specifying that input of this is request is of JSON type and content = new user that we just created
        RequestBuilder request = MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(newUserAsJson);

        mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(11))
        .andExpect(jsonPath("$.firstName").value("Jimmy"))
        .andExpect(jsonPath("$.lastName").value("Yang"))
        .andExpect(jsonPath("$.email").value("jmmy@jobfinder.com"))
        .andExpect(jsonPath("$.country").value("US"));  

    }

    @DisplayName("Invalid user input during user creation")
    @Test
    public void invalidUserCreationTest() throws Exception {

        logger.info("testing valid checking working as expected during user creation");


        User invalidUser = User.builder().firstName("Te").lastName("User").email("user").country("US").build();

        String invalidUserAsJson = objectMapper.writeValueAsString(invalidUser);

        RequestBuilder request = MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(invalidUserAsJson);

         mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @DisplayName("Update user")
    @Test
    public void updateUserTest() throws Exception {

        logger.info("testing updateUser method from UserController");

        User updatedUser = User.builder().firstName("Mary").lastName("Kate").email("mary@jobfinder.com").country("Singapore").build();
        
        String updatedUserAsJson = objectMapper.writeValueAsString(updatedUser);

        RequestBuilder request = MockMvcRequestBuilders.put("/users/1").contentType(MediaType.APPLICATION_JSON).content(updatedUserAsJson);

        mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.firstName").value("Mary"))
        .andExpect(jsonPath("$.lastName").value("Kate"))
        .andExpect(jsonPath("$.email").value("mary@jobfinder.com"))
        .andExpect(jsonPath("$.country").value("Singapore"));
    }

    @DisplayName("Add job to user")
    @Test
    public void addJobtoUserTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/users/1/jobs/10");

        mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.title").value("Nurse"))
        .andExpect(jsonPath("$.description").value("Nurses plan and provide medical and nursing care to patients in hospital, at home or in other settings."))
        .andExpect(jsonPath("$.category").value("Professional"))
        .andExpect(jsonPath("$.salary").value(90000.0))
        .andExpect(jsonPath("$.yearsOfExperience").value(4))
        .andExpect(jsonPath("$.country").value("Singapore"));
    }

    @DisplayName("Delete user")
    @Test
    public void deleteUserTest() throws Exception {

        logger.info("Deleting user that was newly created at the start of testing ");

        RequestBuilder request = MockMvcRequestBuilders.delete("/users/11");

        mockMvc.perform(request)
        .andExpect(status().isNoContent());
    }









    }

