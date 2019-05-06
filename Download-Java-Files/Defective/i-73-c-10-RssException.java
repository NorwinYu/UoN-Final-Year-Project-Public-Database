package pl.michal.olszewski.rssaggregator.blog;

class RssException extends RuntimeException {

  RssException(String message, String correlationID, Throwable cause) {
    super("Wystąpił błąd przy pobieraniu informacji z bloga " + message + " correlationID = " + correlationID, cause);
  }
}
