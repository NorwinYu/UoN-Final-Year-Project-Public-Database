package propra2.leihOrDie.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import propra2.leihOrDie.dataaccess.ItemRepository;
import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.User;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("")
    public String homepage(){
        return "homepage";
    }

}
