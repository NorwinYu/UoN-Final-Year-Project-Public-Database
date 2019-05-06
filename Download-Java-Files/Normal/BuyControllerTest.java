package propra2.leihOrDie;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import propra2.leihOrDie.dataaccess.*;
import propra2.leihOrDie.model.*;
import propra2.leihOrDie.propay.ProPayWrapper;

import javax.servlet.http.Cookie;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BuyControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    BuyRepository buyRepository;

    @MockBean
    ProPayWrapper proPayWrapper;

    private User testUser1;
    private User testUser2;
    private User testAdmin;
    private Item testItem;
    private Buy testBuy;

    @Before
    public void setUp() {
        DummyUserGenerator dummyUserGenerator = new DummyUserGenerator();
        testUser1 = dummyUserGenerator.generateUser();
        testUser2 = dummyUserGenerator.generateUser();
        testAdmin = dummyUserGenerator.generateUser();
        testAdmin.setRole("ADMIN");
        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testAdmin);

        Session userSession1 = new Session("1", testUser1);
        sessionRepository.save(userSession1);

        Session userSession2 = new Session("2", testUser2);
        sessionRepository.save(userSession2);

        Session adminSession = new Session("3", testAdmin);
        sessionRepository.save(adminSession);

        DummyItemGenerator dummyItemGenerator = new DummyItemGenerator();
        testItem = dummyItemGenerator.generateItem(testUser2);
        testItem.setSoldPrice(10);
        itemRepository.save(testItem);

        testBuy = new Buy(testItem, testItem.getSoldPrice(), "pending", testUser1);
        buyRepository.save(testBuy);

    }

    @After
    public void tearDown() {
        buyRepository.deleteAll();
        itemRepository.deleteAll();
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testBuyItem() throws Exception {
        mvc.perform(post("/buy/" + testItem.getId())
                .cookie(new Cookie("SessionID", "2"))
                .param("PurchasePrice", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertFalse(buyRepository.findBuysOfItem(testItem.getId()).get(0).getItem().isAvailability());
    }

    @Test
    public void testItemSale() throws Exception {
        when(proPayWrapper
                .transferMoney
                        (testBuy.getBuyer().getEmail(), testUser2.getEmail(), testBuy.getPurchasePrice()))
                .thenReturn("something");
        when(proPayWrapper.getBalanceOfUser(testUser2.getEmail())).thenReturn((double)testBuy.getPurchasePrice());

        mvc.perform(post("/buy/accept/" + testItem.getId())
                .cookie(new Cookie("SessionID", "2"))
                .param("PurchasePrice", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Erfolgreich verkauft"));


        Assert.assertEquals("completed", buyRepository.findBuysOfItem(testItem.getId()).get(0).getStatus());
        Assert.assertEquals(10.0, proPayWrapper.getBalanceOfUser(testUser2.getEmail()), 0.001);

    }

    @Test
    public void testDeclineItemSale() throws Exception {

        mvc.perform(post("/buy/decline/" + testItem.getId())
                .cookie(new Cookie("SessionID", "2")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("declined", buyRepository.findBuysOfItem(testItem.getId()).get(0).getStatus());
        Assert.assertEquals(buyRepository.findBuysOfItem(testItem.getId()).size(), 1);
    }
}
