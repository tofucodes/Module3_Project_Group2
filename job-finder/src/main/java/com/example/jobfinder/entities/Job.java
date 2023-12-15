package com.example.jobfinder.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Job title is mandatory")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    @NotBlank(message = "Category is mandatory")
    private String category;

    @Column(name = "salary")
    private double salary;

    @Column(name = "years_of_experience")
    @Range(min = 0, max = 20, message = " Years of experience should be between 0 and 20")
    private int yearsOfExperience;

    @Column(name = "country")
    private String country;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_job", 
        joinColumns = @JoinColumn(name = "job_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")  
    )

    @JsonBackReference
    private Set<User> users;

    public Job() {}


    public Job(String title, String description, String category, 
    double salary, int yearsOfExperience, String country) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.country = country;
    }

}
