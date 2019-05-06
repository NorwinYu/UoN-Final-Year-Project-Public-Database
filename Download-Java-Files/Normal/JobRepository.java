package com.example.codeclan.recruitment.repositories;

import com.example.codeclan.recruitment.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface JobRepository extends JpaRepository<Job, Long>, JobRepositoryCustom{
}
