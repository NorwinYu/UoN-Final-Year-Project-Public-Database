package com.example.codeclan.recruitment.projections;

import com.example.codeclan.recruitment.models.Job;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="EmbeddedJobs", types = Job.class)
public interface EmbeddedJob {

    String getCompanyName();
    String getTitle();
    String getSalary();
    String getLocation();
    String getApplicationUrl();
    String getCompanyCulture();
    String getTechStack();
    String getYourTeam();
    String getResponsibilities();
    String getRequirements();
    String getRole();
    String getSkillLevel();
    String getJobType();


}
