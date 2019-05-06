package propra2.leihOrDie;

import org.junit.Test;
import org.junit.Assert;
import propra2.leihOrDie.model.Address;
import propra2.leihOrDie.model.User;

public class UserTest {
    @Test
    public void testCreateUser() throws Exception {
        Address address = new Address(12345, "Teststr", 1, "Kuhlstadt");

        String password = "qwerty";

        User user = new User("testuser", "test@email.de", password, "USER", address);

        Assert.assertEquals("testuser", user.getUsername());
        Assert.assertEquals(address, user.getAddress());
        Assert.assertNotEquals("qwerty", user.getPassword());
        Assert.assertEquals("test@email.de", user.getEmail());
        Assert.assertTrue(user.verifyPassword("qwerty"));
        Assert.assertFalse(user.verifyPassword("qwertz"));
    }
}
