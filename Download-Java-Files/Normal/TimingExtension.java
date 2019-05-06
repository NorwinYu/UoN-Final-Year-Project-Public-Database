package pl.michal.olszewski.rssaggregator.extenstions;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

@Slf4j
class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

  @Override
  public void beforeTestExecution(ExtensionContext context) {
    getStore(context).put(context.getRequiredTestMethod(), System.currentTimeMillis());
  }

  @Override
  public void afterTestExecution(ExtensionContext context) {
    Method testMethod = context.getRequiredTestMethod();
    long start = getStore(context).remove(testMethod, long.class);
    long duration = System.currentTimeMillis() - start;

    log.info(String.format("Method [%s] took %s ms.", testMethod.getName(), duration));
  }

  private Store getStore(ExtensionContext context) {
    return context.getStore(Namespace.create(getClass(), context));
  }

}