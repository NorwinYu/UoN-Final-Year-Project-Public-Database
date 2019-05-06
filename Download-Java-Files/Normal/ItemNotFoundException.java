package pl.michal.olszewski.rssaggregator.item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ItemNotFoundException extends RuntimeException {

  ItemNotFoundException(String name, String correlationID) {
    super("Nie znaleziono wpisu na blogu = " + name + " correlationID = " + correlationID);
  }
}
