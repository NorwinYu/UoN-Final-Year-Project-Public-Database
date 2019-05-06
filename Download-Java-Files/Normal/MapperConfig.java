package at.jugger.tracker.config;

import org.mapstruct.InjectionStrategy;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MapperConfig {
}
