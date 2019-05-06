package pl.michal.olszewski.rssaggregator.blog;

class UpdateTimeoutException extends RuntimeException {

  UpdateTimeoutException(String name, String correlationId) {
    super("Timeout aktualizacji bloga = " + name + " correlationId = " + correlationId);
  }

}
