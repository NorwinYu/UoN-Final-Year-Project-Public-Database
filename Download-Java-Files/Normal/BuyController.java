package propra2.leihOrDie.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propra2.leihOrDie.dataaccess.*;
import propra2.leihOrDie.form.BuyForm;
import propra2.leihOrDie.model.Buy;
import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.Transaction;
import propra2.leihOrDie.model.User;
import propra2.leihOrDie.propay.ProPayWrapper;
import propra2.leihOrDie.response.ResponseBuilder;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BuyController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BuyRepository buyRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    TransactionRepository transactionRepository;

    private ResponseBuilder responseBuilder = new ResponseBuilder();
    @Autowired
    private ProPayWrapper proPayWrapper;

    @GetMapping("/reloadbuyitems")
    public String reloadBuyItems(Model model, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        User user = sessionRepository.findUserBySessionCookie(sessionId);
        List<Item> items = itemRepository.findItemsOfUser(user.getUsername());
        List<Buy> buys = new ArrayList<>();

        for (Item item: items) {
            Buy pendingBuy = getPendingBuyOfItem(item);

            if (pendingBuy != null ) {
                buys.add(pendingBuy);
            }
        }
        model.addAttribute("buys", buys);
        model.addAttribute("mypurchases", buyRepository.findBuysOfUser(user.getUsername()));
        return "buy-snippet";
    }


    @GetMapping("/myaccount/buy")
    public String showBuyService(Model model, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        User user = sessionRepository.findUserBySessionCookie(sessionId);
        List<Item> items = itemRepository.findItemsOfUser(user.getUsername());
        List<Buy> buys = new ArrayList<>();

        for (Item item: items) {
            Buy pendingBuy = getPendingBuyOfItem(item);

            if (pendingBuy != null ) {
                buys.add(pendingBuy);
            }
        }
        model.addAttribute("buys", buys);
        model.addAttribute("mypurchases", buyRepository.findBuysOfUser(user.getUsername()));
        return "user-shop";
    }

    @PostMapping("/buy/{itemId}")
    public ResponseEntity buyItem(Model model, @PathVariable Long itemId, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        Item item = itemRepository.findById(itemId).get();
        User user = sessionRepository.findUserBySessionCookie(sessionId);
        Buy buy = new Buy(item, item.getSoldPrice(), "pending", user);

        buyRepository.save(buy);
        item.setAvailability(false);
        itemRepository.save(item);

        return responseBuilder.createSuccessResponse("Kaufanfrage wurde gestellt");
    }

    @PostMapping("/buy/accept/{itemId}")
    public ResponseEntity itemSale(Model model, BuyForm form, @PathVariable Long itemId, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        Item item = itemRepository.findById(itemId).get();
        User user = sessionRepository.findUserBySessionCookie(sessionId);
        Buy buy = getPendingBuyOfItem(item);

        if (!isAuthorized(user, item)) {
            return responseBuilder.createUnauthorizedResponse();
        }

        try {
            proPayWrapper.transferMoney(buy.getBuyer().getEmail(), user.getEmail(), buy.getPurchasePrice());
            Transaction transaction = new Transaction(buy.getBuyer(), user, buy.getPurchasePrice(), "Kauf von " + item.getName());
            buy.setStatus("completed");
            buyRepository.save(buy);
            transactionRepository.save(transaction);
        } catch (Exception e) {
            buy.setStatus("error");
            return responseBuilder.createProPayErrorResponse(user, buy);
        }

        return responseBuilder.createSuccessResponse("Erfolgreich verkauft");
    }

    @PostMapping("/buy/decline/{itemId}")
    public ResponseEntity declineItemSale(Model model, BuyForm form, @PathVariable Long itemId, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        Item item = itemRepository.findById(itemId).get();
        Buy buy = getPendingBuyOfItem(item);

        if (!isAuthorized(sessionRepository.findUserBySessionCookie(sessionId), item)) {
            return responseBuilder.createUnauthorizedResponse();
        }

        buy.setStatus("declined");

        buyRepository.save(buy);

        return responseBuilder.createSuccessResponse("Verkauf erfolgreich abgelehnt");
    }

    private Buy getPendingBuyOfItem(Item item) {
        List<Buy> buys = buyRepository.findBuysOfItem(item.getId());

        Buy buy = null;
        for (Buy b: buys) {
            if (b.getStatus().equals("pending")) {
                buy = b;
            }
        }

        return buy;
    }

    private boolean isAuthorized(User user, Item item) {
        return user.getUsername().equals(item.getUser().getUsername());
    }
}
