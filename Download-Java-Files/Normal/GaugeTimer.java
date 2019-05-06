package pl.michal.olszewski.rssaggregator.config;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.AtomicDouble;
import io.micrometer.core.instrument.MeterRegistry;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GaugeTimer {

  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final Map<String, AtomicDouble> gauges = new HashMap<>();
  private final MeterRegistry registry;

  public GaugeTimer(MeterRegistry registry) {
    this.registry = registry;
  }

  @Around("@annotation(RegistryTimed)")
  public Object logServiceAccess(ProceedingJoinPoint joinPoint) throws Throwable {
    Stopwatch stopwatch = Stopwatch.createStarted();
    Object retVal = joinPoint.proceed();
    logger.debug("Zakonczono {} w czasie {} milisekund", joinPoint.getSignature().toShortString(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    gauges.putIfAbsent(joinPoint.getSignature().toShortString(), new AtomicDouble());
    registry.gauge(joinPoint.getSignature().toShortString(), gauges.get(joinPoint.getSignature().toShortString())).set(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    return retVal;
  }
}
