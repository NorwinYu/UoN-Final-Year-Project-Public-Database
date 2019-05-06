package propra2.leihOrDie;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import propra2.leihOrDie.dataaccess.ItemRepository;
import propra2.leihOrDie.dataaccess.PictureRepository;
import propra2.leihOrDie.dataaccess.UserRepository;
import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.Picture;
import propra2.leihOrDie.model.User;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PictureRepositoryTest {
    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @After
    public void deleteAllContent() {
        pictureRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSaveOnePicture() {
        User user = (new DummyUserGenerator()).generateUser();
        Item item = new Item("testitem", "das ist ein test", 200, 10,
                true, 20, user.getAddress(), user, 0);
        Picture pic = new Picture(item);

        userRepository.save(user);
        itemRepository.save(item);
        pictureRepository.save(pic);
        List<Picture> pictureList = pictureRepository.findAll();

        Assert.assertEquals(1, pictureList.size());

    }

    @Test
    public void testFindPicturesOfItem() {
        User user = (new DummyUserGenerator()).generateUser();
        Item item = (new DummyItemGenerator()).generateItem(user);
        Item item2 = (new DummyItemGenerator()).generateAnotherItem(user);

        userRepository.save(user);
        itemRepository.save(item);
        itemRepository.save(item2);

        for(int i = 0; i < 5; i++) {
            pictureRepository.save(new Picture(item));
        }

        for(int i = 0; i < 3; i++) {
            pictureRepository.save(new Picture(item2));
        }

        List<Picture> pictureListItem1 = pictureRepository.findPicturesOfItem(item.getId());
        List<Picture> pictureListItem2 = pictureRepository.findPicturesOfItem(item2.getId());

        Assert.assertEquals(5, pictureListItem1.size());
        Assert.assertEquals(3, pictureListItem2.size());

    }
}
