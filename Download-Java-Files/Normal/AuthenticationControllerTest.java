package propra2.leihOrDie;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import propra2.leihOrDie.dataaccess.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testNewUserRegistration() throws Exception {
        mvc.perform(post("/registration")
                    .param("username", "max123")
                    .param("email", "maxmustar@gmail.com")
                    .param("password", "123456789123")
                    .param("street", "Universitaetstrasse")
                    .param("postcode", "123456")
                    .param("houseNumber", "1")
                    .param("city", "Duesseldorf"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        Assert.assertEquals(userRepository.count(), 1);
    }
}