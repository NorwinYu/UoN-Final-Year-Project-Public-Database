package propra2.leihOrDie;

import org.junit.Assert;
import org.junit.Test;
import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.User;

public class ItemTest {

    @Test
    public void testPicture() {
        User user = (new DummyUserGenerator()).generateUser();

        Item item = new Item("testitem", "das ist ein test", 200, 10,
                true, 20, user.getAddress(), user, 0);

        Assert.assertEquals(item.getName(), "testitem");
        Assert.assertEquals(item.getDescription(), "das ist ein test");
        Assert.assertEquals(item.getCost(), 200);
        Assert.assertEquals(item.getDeposit(), 10);
        Assert.assertEquals(item.getAvailableTime(), 20);
        Assert.assertTrue(item.isAvailability());
    }

}
