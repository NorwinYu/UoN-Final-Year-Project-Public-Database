package pl.michal.olszewski.rssaggregator.item;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReadItemServiceTest {

  private ReadItemService readItemService;

  @Mock
  private ItemRepository itemRepository;

  @BeforeEach
  void setUp() {
    readItemService = new ReadItemService(itemRepository);
    given(itemRepository.findById("itemId")).willReturn(Mono.just(new Item()));
    given(itemRepository.findById("itemId2")).willReturn(Mono.empty());

    given(itemRepository.save(any(Item.class))).willAnswer(i -> {
          Item argument = i.getArgument(0);
          argument.setId(UUID.randomUUID().toString());
          return Mono.just(argument);
        }
    );
  }

  @Test
  void shouldMarkItemAsRead() {
    var readItemDTO = ReadItemDTO.builder().itemId("itemId").read(true).build();

    Mono<Boolean> result = readItemService.processRequest(readItemDTO, "correlationId");

    StepVerifier.create(result)
        .expectNext(true)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldMarkItemAsUnread() {
    var readItemDTO = ReadItemDTO.builder().itemId("itemId").read(false).build();

    Mono<Boolean> result = readItemService.processRequest(readItemDTO, "correlationId");

    StepVerifier.create(result)
        .expectNext(true)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldThrowExceptionWhenItemByIdNotExists() {
    var readItemDTO = ReadItemDTO.builder().itemId("itemId2").read(false).build();

    Mono<Boolean> result = readItemService.processRequest(readItemDTO, "correlationId");

    StepVerifier.create(result)
        .expectError(ItemNotFoundException.class)
        .verify();
  }
}