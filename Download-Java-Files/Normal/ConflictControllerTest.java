package propra2.leihOrDie;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ConflictControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    LoanRepository loanRepository;

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
    private Loan testLoan;
    private Buy testBuy;

    @Before
    public void setUp() throws Exception {
        DummyUserGenerator dummyUserGenerator = new DummyUserGenerator();
        testUser1 = dummyUserGenerator.generateUser();
        testUser2 = dummyUserGenerator.generateUser();
        testAdmin = dummyUserGenerator.generateUser();
        testAdmin.setRole("ADMIN");
        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testAdmin);

        Session userSession = new Session("1", testUser1);
        sessionRepository.save(userSession);

        Session adminSession = new Session("2", testAdmin);
        sessionRepository.save(adminSession);

        DummyItemGenerator dummyItemGenerator = new DummyItemGenerator();
        testItem = dummyItemGenerator.generateItem(testUser1);
        itemRepository.save(testItem);

        DummyLoanGenerator dummyLoanGenerator = new DummyLoanGenerator();
        testLoan = dummyLoanGenerator.generateLoan(testItem, testUser2);
        loanRepository.save(testLoan);

        testBuy = new Buy();
        testBuy.setItem(testItem);
        testBuy.setBuyer(testUser2);
        testBuy.setPurchasePrice(10);
        testBuy.setStatus("error");
        buyRepository.save(testBuy);
    }

    @After
    public void tearDown() {
        loanRepository.deleteAll();
        buyRepository.deleteAll();
        itemRepository.deleteAll();
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testOpenConflict() throws Exception {
        testLoan.setState("accepted");
        loanRepository.save(testLoan);

        mvc.perform(post("/conflict/open/" + testLoan.getId())
                .cookie(new Cookie("SessionID", "1")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("conflict", loanRepository.findAll().get(0).getState());
        Assert.assertFalse(itemRepository.findAll().get(0).isAvailability());
    }

    @Test
    public void testSolveConflictLender() throws Exception {
        Account account = new Account();

        when(proPayWrapper
                        .punishAccount(testLoan.getUser().getEmail(), testLoan.getProPayReservationId()))
                .thenReturn(account);

        mvc.perform(post("/conflict/solve/" + testLoan.getId() + "/and/" + testUser1.getUsername())
                .cookie(new Cookie("SessionID", "2")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Konflikt wurde gelöst."));

        Assert.assertEquals("completed", loanRepository.findAll().get(0).getState());
    }

    @Test
    public void testSolveConflictBuyer() throws Exception {
        doNothing().when(proPayWrapper).freeReservationOfUser(testUser2.getUsername(), testLoan.getProPayReservationId());

        mvc.perform(post("/conflict/solve/" + testLoan.getId() + "/and/" + testUser2.getUsername())
                .cookie(new Cookie("SessionID", "2")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Konflikt wurde gelöst."));

        Assert.assertEquals("completed", loanRepository.findAll().get(0).getState());
    }

    @Test
    public void testSolveConficUserNotExisting() throws Exception {
        mvc.perform(post("/conflict/solve/" + testLoan.getId() + "/and/NotHere")
                .cookie(new Cookie("SessionID", "2")))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(content().string("User NotHere existiert nicht."));
    }

    @Test
    public void testSolveLoanError() throws Exception {
        testLoan.setState("error");
        testLoan.setDuration(1);
        loanRepository.save(testLoan);


        testItem.setCost(10);
        itemRepository.save(testItem);

        when(proPayWrapper
                .transferMoney
                        (testLoan.getUser().getEmail(), testLoan.getItem().getUser().getEmail(), 20.0))
                .thenReturn("something");
        when(proPayWrapper.getBalanceOfUser(testLoan.getItem().getUser().getEmail()))
                .thenReturn(20.0);

        doNothing()
                .when(proPayWrapper)
                .freeReservationOfUser(testLoan.getUser().getEmail(), testLoan.getProPayReservationId());

        when(proPayWrapper.getBalanceOfUser(testLoan.getItem().getUser().getEmail()))
                .thenReturn(20.0 + testLoan.getProPayReservationId());


        mvc.perform(post("/conflict/solveerror/loan/" + testLoan.getId())
                .cookie(new Cookie("SessionID", "2")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Überweisung wurde getätigt."));

        Assert.assertEquals("completed", loanRepository.findAll().get(0).getState());
    }

    @Test
    public void testSolveLoanErrorNotExisting() throws Exception {
        testLoan.setState("completed");
        testLoan.setDuration(1);
        loanRepository.save(testLoan);

        testItem.setCost(10);
        itemRepository.save(testItem);

        mvc.perform(post("/conflict/solveerror/loan/" + testLoan.getId())
                .cookie(new Cookie("SessionID", "2")))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(content().string("Es liegt kein Fehler bei dieser Ausleihe vor."));
    }

    @Test
    public void testSolveBuyError() throws Exception {
        when(proPayWrapper
                .transferMoney
                        (testLoan.getUser().getEmail(), testLoan.getItem().getUser().getEmail(), 20.0))
                .thenReturn("something");
        when(proPayWrapper.getBalanceOfUser(testLoan.getItem().getUser().getEmail()))
                .thenReturn(20.0);

        doNothing()
                .when(proPayWrapper)
                .freeReservationOfUser(testLoan.getUser().getEmail(), testLoan.getProPayReservationId());

        when(proPayWrapper.getBalanceOfUser(testLoan.getItem().getUser().getEmail()))
                .thenReturn(20.0 + testLoan.getProPayReservationId());


        mvc.perform(post("/conflict/solveerror/buy/" + testBuy.getId())
                .cookie(new Cookie("SessionID", "2")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Überweisung wurde getätigt."));

        Assert.assertEquals("completed", buyRepository.findAll().get(0).getStatus());
    }
}
