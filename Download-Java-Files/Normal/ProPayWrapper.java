package propra2.leihOrDie.propay;


import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import propra2.leihOrDie.model.Account;
import propra2.leihOrDie.model.Reservation;

@Service
public class ProPayWrapper {
    public RestTemplate createRestTemplate() {
        HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientRequestFactory.setReadTimeout(3000);
        clientRequestFactory.setConnectTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(clientRequestFactory);

        return restTemplate;
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    public double getBalanceOfUser(String username) {
        try {
            return getAccountOfUser(username).getAmount();
        } catch (Exception e) {
            return - 1;
        }
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    public void createUser(String username) {
        try {
            getAccountOfUser(username);
        } catch (Exception e) {
            return;
        }
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    public Account raiseBalanceOfUser(String username, double amount) {
        RestTemplate rt = new RestTemplate();
        String url = "http://propay:8888/account/" + username;
        MultiValueMap<String, Object> postParams = new LinkedMultiValueMap<>();
        postParams.add("amount", amount);
        ResponseEntity<Account> result = rt.postForEntity(url, postParams, Account.class);

        return result.getBody();
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    public String transferMoney(String senderUsername, String recipientUsername,
                                        double amount) throws Exception {
        RestTemplate rt = new RestTemplate();
        String url = "http://propay:8888/account/" + senderUsername + "/transfer/" + recipientUsername;
        MultiValueMap<String, Object> postParams = new LinkedMultiValueMap<>();
        postParams.add("amount", amount);
        ResponseEntity<String> result = rt.postForEntity(url, postParams, String.class);

        return result.getBody();
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    public Account punishAccount(String username, Long reservationId) throws Exception {
        RestTemplate rt = new RestTemplate();
        String url = "http://propay:8888/reservation/punish/" + username;
        MultiValueMap<String, Object> postParams = new LinkedMultiValueMap<>();
        postParams.add("reservationId", reservationId);

        return rt.postForEntity(url, postParams, Account.class).getBody();
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    public void freeReservationOfUser(String username, Long reservationId) throws Exception {
        RestTemplate rt = new RestTemplate();
        String url = "http://propay:8888/reservation/release/" + username;
        MultiValueMap<String, Object> postParams = new LinkedMultiValueMap<>();
        postParams.add("reservationId", reservationId);
        ResponseEntity<Account> result = rt.postForEntity(url, postParams, Account.class);

        //return result.getBody();
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    public Reservation reserve(String senderUsername, String recipientUsername,
                                      double amount) throws Exception {
        RestTemplate rt = new RestTemplate();
        String url = "http://propay:8888/reservation/reserve/" + senderUsername + "/" + recipientUsername;
        MultiValueMap<String, Object> postParams = new LinkedMultiValueMap<>();
        postParams.add("amount", amount);
        ResponseEntity<Reservation> result = rt.postForEntity(url, postParams, Reservation.class);

        return result.getBody();
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay=5000),value = Exception.class)
    private Account getAccountOfUser(String username) throws Exception {
        RestTemplate rt = new RestTemplate();
        String url = "http://propay:8888/account/" + username;
        ResponseEntity<Account> result = rt.getForEntity(url, Account.class);

        return result.getBody();
    }
}
