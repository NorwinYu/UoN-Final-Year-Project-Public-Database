package propra2.leihOrDie;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import propra2.leihOrDie.dataaccess.ItemRepository;
import propra2.leihOrDie.dataaccess.PictureRepository;
import propra2.leihOrDie.dataaccess.SessionRepository;
import propra2.leihOrDie.dataaccess.UserRepository;
import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.Session;
import propra2.leihOrDie.model.User;
import javax.servlet.http.Cookie;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ItemControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    PictureRepository pictureRepository;

    private User user;
    private Item item1;
    private Item item2;

    @Before
    public void setUp() {
        DummyUserGenerator dummyUserGenerator = new DummyUserGenerator();
        user = dummyUserGenerator.generateUser();
        userRepository.save(user);
        sessionRepository.save(new Session("1", user));

        DummyItemGenerator dummyItemGenerator = new DummyItemGenerator();
        item1 = dummyItemGenerator.generateItem(user);
        item2 = dummyItemGenerator.generateAnotherItem(user);
        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @After
    public void tearDown() {
        pictureRepository.deleteAll();
        itemRepository.deleteAll();
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreateItem() throws Exception {
        mvc.perform(post("/item/create")
                .cookie(new Cookie("SessionID", "1"))
                .param("name", "Teller")
                .param("description", "Rotes Uhralter Teller (Jahr 10000000000 BC)")
                .param("cost", "99999")
                .param("deposit", "1000")
                .param("availableTime", "5"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/item/" + (item2.getId()+1) + "/uploadphoto"));

        Assert.assertEquals(itemRepository.count(), 3);
        Assert.assertEquals(itemRepository.findById(item2.getId()+1).get().getName(), "Teller");
    }

    @Test
    public void testShowItem() throws Exception {
        mvc.perform(get("/borrowall/" + item1.getId() + "/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("description", item1.getDescription()))
                .andExpect(model().attribute("location", item1.getUser().getAddress().getCity()))
                .andExpect(model().attribute("availableTime", item1.getAvailableTime()))
                .andExpect(model().attribute("deposit", item1.getDeposit()))
                .andExpect(model().attribute("cost", item1.getCost()))
                .andExpect(model().attribute("username", item1.getUser().getUsername()));
    }

    @Test
    public void testListAllItems() throws Exception {
        mvc.perform(get("/borrowall/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("items", hasSize(2)))
                .andExpect(model().attribute("items", hasItem(
                        allOf(
                                hasProperty("id", is(item1.getId())),
                                hasProperty("cost", is(item1.getCost())),
                                hasProperty("deposit", is(item1.getDeposit())),
                                hasProperty("description", is(item1.getDescription()))))))
                .andExpect(model().attribute("items", hasItem(
                        allOf(
                                hasProperty("id", is(item2.getId())),
                                hasProperty("cost", is(item2.getCost())),
                                hasProperty("deposit", is(item2.getDeposit())),
                                hasProperty("description", is(item2.getDescription()))))));
    }

}
