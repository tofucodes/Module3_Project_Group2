package com.example.jobfinder.entities;

import java.util.HashSet;
import java.util.Set;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotBlank(message = "First name is manadatory")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @Column(name = "country")
    private String country;

    @ManyToMany(mappedBy = "users")
    private Set<Job> jobs;

    // @ManyToMany(mappedBy = "users")
    // private Set<Job> jobs = new HashSet<>();

    public User(){
    }

    public User (String firstName){
        this.firstName = firstName;

    }

    public User(String firstName, String lastName, String email, String country){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
    }
    
}
