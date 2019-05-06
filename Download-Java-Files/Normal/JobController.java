package com.example.codeclan.recruitment.controllers;


import com.example.codeclan.recruitment.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/")
public class JobController {

    @Autowired
    JobRepository jobRepository;

}
