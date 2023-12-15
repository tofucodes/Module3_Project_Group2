package com.example.jobfinder.controllers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.jobfinder.entities.Job;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Get Job by Id")
    @Test
    public void getJobByIdTest() throws Exception {
        // Step 1: Build a GET request to /job/1
        RequestBuilder request = MockMvcRequestBuilders.get("/jobs/1");

        // Step 2: Perform the request, get the response and assert
        mockMvc.perform(request)
                // Assert that the status code is 200
                .andExpect(status().isOk())
                // Assert that the content type is JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Assert that the id returned is 1
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    public void getAllJobsTest() throws Exception {
        // Step 1: Build a GET request to /Jobs
        RequestBuilder request = MockMvcRequestBuilders.get("/jobs");

        // Step 2: Perform the request, get the response and assert
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(10));
    }

    @Test
public void validJobCreationTest() throws Exception {
	// Step 1: Create a Job object
	 Job job = Job.builder().title("Product Manager").description("This is a job description").category("Manager")
        .salary(12000).yearsOfExperience(7).country("United Kingdom").build();

	// Step 2: Convert the Java object to JSON using ObjectMapper
	String newJobAsJSON = objectMapper.writeValueAsString(job);

	// Step 3: Build the request
	RequestBuilder request = MockMvcRequestBuilders.post("/jobs")
			.contentType(MediaType.APPLICATION_JSON)
			.content(newJobAsJSON);

	// Step 4: Perform the request and get the response and assert
	mockMvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(11))
			.andExpect(jsonPath("$.title").value("Product Manager"))
			.andExpect(jsonPath("$.category").value("Manager"));
}

@Test
public void invalidJobCreationTest() throws Exception {
    // Step 1: Create a Job object with invalid fields
     Job invalidJob = Job.builder().title("").description("").category("Executive")
        .salary(14000).yearsOfExperience(5).country("China").build();

    // Step 2: Convert the Java object to JSON
    String invalidJobAsJSON = objectMapper.writeValueAsString(invalidJob);

    // Step 3: Build the request
    RequestBuilder request = MockMvcRequestBuilders.post("/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidJobAsJSON);

    // Step 4: Perform the request and get the response
    mockMvc.perform(request)
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
}
}
