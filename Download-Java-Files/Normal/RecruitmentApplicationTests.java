package com.example.codeclan.recruitment;

import com.example.codeclan.recruitment.models.Job;
import com.example.codeclan.recruitment.repositories.JobRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecruitmentApplicationTests {

	@Autowired
	JobRepository jobRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void canSaveJob() {
		Job job = new Job("JP Morgan", "declan06023@hotmail.com",
				"011241131", "Joesph", "Software Developer",
				"24,000", "Glasgow", "www.google.com",
				"We are committed to ensuring that all job applicants are treated equally," +
						" without discrimination because of gender, sexual orientation, marital or civil partner status, gender reassignment," +
						" race, colour, nationality, ethnic or national origin, religion or belief, disability or age.", "JavaScript",
				"Your team is looking to have someone who is committed and passionate but also looking to have a good time.",
				"You will be responsible for maintaining and extending the company's core flagship cloud-based product. " +
						"You would be working within the backend team & therefore " +
						"you'll have involvement in all aspects of design and development.",
				"2 years of JavaScript and SQL Knowledge",
				"Software Developer", "Senior", "Full-Time");
		jobRepository.save(job);
	}
}
