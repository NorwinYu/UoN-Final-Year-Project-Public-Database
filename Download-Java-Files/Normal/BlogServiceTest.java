package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.michal.olszewski.rssaggregator.item.Item;
import pl.michal.olszewski.rssaggregator.item.ItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BlogServiceTest {

  private BlogService blogService;

  @Mock
  private BlogReactiveRepository blogRepository;

  @Mock
  private MongoTemplate mongoTemplate;

  @BeforeEach
  void setUp() {
    given(blogRepository.save(any(Blog.class))).willAnswer(i -> {
          Blog argument = i.getArgument(0);
          argument.setId(UUID.randomUUID().toString());
          return Mono.just(argument);
        }
    );
    given(mongoTemplate.save(any(Item.class))).willAnswer(i -> Mono.just(i.getArgument(0)));
    blogService = new BlogService(blogRepository, mongoTemplate, Caffeine.newBuilder().build());
    blogService.evictBlogCache();
  }

  @Test
  void shouldCreateBlogFromDTO() {
    //given
    given(blogRepository.findByFeedURL("feedUrl1")).willReturn(Mono.empty());
    BlogDTO blogDTO = BlogDTO.builder()
        .feedURL("feedUrl1")
        .name("test")
        .build();

    //when
    Mono<Blog> blog = blogService.getBlogOrCreate(blogDTO, "correlationId");

    //then
    assertThat(blog).isNotNull();
  }

  @Test
  void shouldNotTryCreatingBlogWhenExist() {
    //given
    given(blogRepository.findByFeedURL("nazwa")).willReturn(Mono.just(new Blog()));

    BlogDTO blogDTO = BlogDTO.builder()
        .feedURL("nazwa")
        .build();

    //when
    Mono<Blog> blog = blogService.getBlogOrCreate(blogDTO, "correlationId");

    //then
    verify(blogRepository, times(1)).findByFeedURL("nazwa");
    assertThat(blog).isNotNull();
  }

  @Test
  void shouldCreateBlogWithCorrectProperties() {
    //given
    Instant now = Instant.now();
    BlogDTO blogDTO = BlogDTO.builder()
        .name("nazwa1")
        .description("desc")
        .feedURL("feedUrl3")
        .link("blogUrl1")
        .publishedDate(now)
        .build();
    given(blogRepository.findByFeedURL("feedUrl3")).willReturn(Mono.empty());

    //when
    Mono<Blog> blog = blogService.getBlogOrCreate(blogDTO, "correlationId");

    //then
    StepVerifier.create(blog)
        .assertNext(v -> assertAll(
            () -> assertThat(v).isNotNull(),
            () -> assertThat(v.getDescription()).isEqualTo("desc"),
            () -> assertThat(v.getName()).isEqualTo("nazwa1"),
            () -> assertThat(v.getFeedURL()).isEqualTo("feedUrl3"),
            () -> assertThat(v.getBlogURL()).isEqualTo("blogUrl1"),
            () -> assertThat(v.getPublishedDate()).isAfterOrEqualTo(now).isBeforeOrEqualTo(now)
        ))
        .expectComplete()
        .verify();

  }

  @Test
  void shouldPersistBlogOnCreate() {
    //given
    given(blogRepository.findByFeedURL("feedUrl5")).willReturn(Mono.empty());
    BlogDTO blogDTO = BlogDTO.builder()
        .feedURL("feedUrl5")
        .build();

    //when
    Blog blog = blogService.getBlogOrCreate(blogDTO, "correlationId").block();

    //then
    assertThat(blog).isNotNull();
    verify(blogRepository, times(1)).save(blog);
  }

  @Test
  void shouldNotCreateBlogWhenThrowException() {
    //given
    BlogDTO blogDTO = BlogDTO.builder().feedURL("feedUrl9").build();
    given(blogRepository.findByFeedURL("feedUrl9")).willReturn(Mono.empty());
    Mockito.doThrow(new DuplicateKeyException("Blog o podanym url juz istnieje"))
        .when(blogRepository).save(any());

    //when
    //then
    assertThatThrownBy(() -> blogService.getBlogOrCreate(blogDTO, "correlationId").block())
        .isNotNull()
        .hasMessage("Blog o podanym url juz istnieje");
  }

  @Test
  void shouldCreateBlogWith2Items() {
    //given
    given(blogRepository.findByFeedURL("feedUrl2")).willReturn(Mono.empty());
    BlogDTO blogDTO = BlogDTO.builder()
        .feedURL("feedUrl2")
        .item(ItemDTO.builder().title("title1").build())
        .item(ItemDTO.builder().title("title2").build())
        .build();

    //when
    Mono<Blog> blog = blogService.getBlogOrCreate(blogDTO, "correlationId");

    //then
    StepVerifier.create(blog)
        .assertNext(v -> {
          assertThat(v).isNotNull();
          assertThat(v.getItems()).isNotEmpty().hasSize(2);
        })
        .expectComplete()
        .verify();
  }

  @Test
  void shouldCreateItemsWithCorrectProperties() {
    Instant now = Instant.now();
    List<ItemDTO> itemsList = IntStream.rangeClosed(1, 2)
        .mapToObj(v -> ItemDTO.builder().author("autor").date(now).description("desc").title(v + "").link("link" + v).build()) //TODO przerobic linie
        .collect(Collectors.toList());
    BlogDTO blogDTO = BlogDTO.builder().feedURL("feedUrl4").itemsList(itemsList).build();
    given(blogRepository.findByFeedURL("feedUrl4")).willReturn(Mono.empty());

    //when
    Mono<Blog> blog = blogService.getBlogOrCreate(blogDTO, "correlationId");

    //then
    StepVerifier.create(blog)
        .assertNext(v -> {
          assertThat(v.getItems()).isNotEmpty().hasSize(2);
          for (Item item : v.getItems()) {
            assertThat(item.getAuthor()).isEqualTo("autor");
            assertThat(item.getDate()).isBeforeOrEqualTo(now).isAfterOrEqualTo(now);
            assertThat(item.getTitle()).isNotNull().isNotEmpty();
            assertThat(item.getLink()).isEqualTo("link" + item.getTitle());
          }
        })
        .expectComplete()
        .verify();
  }

  @Test
  void shouldUpdateBlogWhenNewItemAdd() {
    //given
    Blog blog = Blog.builder().id(UUID.randomUUID().toString()).blogURL("url").name("url").build();

    List<ItemDTO> itemsList = IntStream.rangeClosed(1, 1)
        .mapToObj(v -> ItemDTO.builder().date(Instant.now()).author("autor").description("desc").title(v + "").link("link" + v).build()) //przerobic linie
        .collect(Collectors.toList());
    BlogDTO blogDTO = BlogDTO.builder()
        .name("url")
        .feedURL("url")
        .itemsList(itemsList)
        .build();
    given(blogRepository.findByFeedURL("url")).willReturn(Mono.just(blog));

    //when
    Mono<Blog> updateBlog = blogService.updateBlog(blogDTO, "correlationID");

    //then
    StepVerifier.create(updateBlog)
        .assertNext(v -> assertAll(
            () -> assertThat(v).isEqualToIgnoringGivenFields(blog, "items"),
            () -> assertThat(v.getItems()).isNotEmpty().hasSize(1)
        ))
        .expectComplete()
        .verify();

  }

  @Test
  void shouldAddItemForBlogWhichHaveOneItem() {
    //given
    Blog blog = Blog.builder()
        .id(UUID.randomUUID().toString())
        .blogURL("url")
        .name("url")
        .item(new Item(ItemDTO.builder().title("title").build()))
        .build();

    BlogDTO blogDTO = BlogDTO.builder()
        .name("url")
        .feedURL("url")
        .item(ItemDTO.builder().author("autor").description("desc").date(Instant.now()).title("2").link("link2").build())
        .build();
    given(blogRepository.findByFeedURL("url")).willReturn(Mono.just(blog));

    //when
    Mono<Blog> updateBlog = blogService.updateBlog(blogDTO, "correlationID");

    //then
    StepVerifier.create(updateBlog)
        .assertNext(v -> assertAll(
            () -> assertThat(v).isEqualToIgnoringGivenFields(blog, "items"),
            () -> assertThat(v.getItems()).isNotEmpty().hasSize(2)
        ))
        .expectComplete()
        .verify();
  }

  @Test
    //TODO zmniejszyc liczbe linii w sekcji given
  void shouldNotAddItemWhenIsTheSame() {
    //given
    ItemDTO itemDTO = ItemDTO.builder()
        .title("title")
        .date(Instant.now())
        .build();
    Blog blog = Blog.builder()
        .id(UUID.randomUUID().toString())
        .blogURL("url")
        .name("url")
        .item(new Item(itemDTO))
        .build();

    BlogDTO blogDTO = BlogDTO.builder()
        .name("url")
        .feedURL("url")
        .itemsList(Arrays.asList(itemDTO, itemDTO))
        .build();
    given(blogRepository.findByFeedURL("url")).willReturn(Mono.just(blog));

    //when
    Mono<Blog> updateBlog = blogService.updateBlog(blogDTO, "correlationID");

    //then
    StepVerifier.create(updateBlog)
        .assertNext(v -> assertThat(v).isEqualToComparingFieldByField(blog))
        .expectComplete()
        .verify();
  }

  @Test
  void shouldNotUpdateBlogWhenNothingChanged() {
    //given
    Blog blog = Blog.builder().id(UUID.randomUUID().toString()).blogURL("url").name("url").build();

    BlogDTO blogDTO = BlogDTO.builder()
        .name("url")
        .feedURL("url")
        .build();
    given(blogRepository.findByFeedURL("url")).willReturn(Mono.just(blog));

    //when
    Mono<Blog> updateBlog = blogService.updateBlog(blogDTO, "correlationID");

    //then
    StepVerifier.create(updateBlog)
        .assertNext(v -> assertThat(v).isEqualToComparingFieldByField(blog))
        .expectComplete()
        .verify();
  }

  @Test
  void shouldUpdateBlogWhenDescriptionChanged() {
    //given
    Blog blog = Blog.builder().id(UUID.randomUUID().toString()).blogURL("url").name("url").build();

    BlogDTO blogDTO = BlogDTO.builder()
        .feedURL("url")
        .description("desc")
        .name("url")
        .build();
    given(blogRepository.findByFeedURL("url")).willReturn(Mono.just(blog));

    //when
    Mono<Blog> updateBlog = blogService.updateBlog(blogDTO, "correlationID");

    //then
    StepVerifier.create(updateBlog)
        .assertNext(v -> assertAll(
            () -> assertThat(v).isEqualToIgnoringGivenFields(blog, "description"),
            () -> assertThat(v.getDescription()).isEqualTo("desc")
        ))
        .expectComplete()
        .verify();
  }

  @Test
  void shouldDeleteBlogById() {
    given(blogRepository.delete(any())).willReturn(Mono.empty());
    given(blogRepository.findById("1")).willReturn(Mono.just(Blog.builder().build()));

    StepVerifier.create(blogService.deleteBlog("1", "correlationID"))
        .expectComplete()
        .verify();
  }

  @Test
  void shouldThrowExceptionOnDeleteWhenBlogNotExist() {
    given(blogRepository.findById("1")).willReturn(Mono.empty());

    assertThatThrownBy(() -> blogService.deleteBlog("1", "correlationID").block()).isNotNull().hasMessage("Nie znaleziono bloga = 1 correlationID = correlationID");
  }

  @Test
  void shouldGetBlogDTOById() {
    //given
    given(blogRepository.findById("1")).willReturn(Mono.just(Blog.builder().build()));

    //when
    Mono<BlogAggregationDTO> blogById = blogService.getBlogDTOById("1", "correlationID");

    //then
    assertThat(blogById).isNotNull();
  }

  @Test
  void shouldThrownExceptionWhenBlogDTOByIdNotExist() {
    //given
    given(blogRepository.findById("1")).willReturn(Mono.empty());

    //expect
    StepVerifier.create(blogService.getBlogDTOById("1", "correlationID"))
        .expectErrorMessage("Nie znaleziono bloga = 1 correlationID = correlationID")
        .verify();
  }

  @Test
  void shouldGetEmptyBlogsDTOs() {
    //given
    given(blogRepository.getBlogsWithCount()).willReturn(Flux.empty());

    //when
    Flux<BlogAggregationDTO> blogs = blogService.getAllBlogDTOs("correlationId");

    //then
    StepVerifier.create(blogs)
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldGetAllBlogDTOs() {
    //given
    Blog blog = Blog.builder().id("id").build();

    given(blogRepository.getBlogsWithCount()).willReturn(Flux.just(new BlogAggregationDTO(blog)));

    //when
    Flux<BlogAggregationDTO> blogs = blogService.getAllBlogDTOs("correlationId");

    //then
    StepVerifier.create(blogs)
        .expectNextCount(1)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldChangeActivityBlogWhenWeTryDeleteBlogWithItems() {
    Blog blog = Blog.builder()
        .item(new Item(ItemDTO.builder().link("test").build()))
        .build();
    given(blogRepository.findById("1")).willReturn(Mono.just(blog));

    //when
    blogService.deleteBlog("1", "correlationID").block();

    //then
    assertThat(blog.isActive()).isFalse();
  }

  @Test
  void shouldThrownExceptionWhenGetItemsForNotExistingBlog() {
    //given
    given(blogRepository.findById("id")).willReturn(Mono.empty());

    //when
    StepVerifier.create(blogService.getBlogItemsForBlog("id", "correlationID"))
        .expectErrorMessage("Nie znaleziono bloga = id correlationID = correlationID")
        .verify();
  }

  @Test
  void shouldReturnBlogItemsForBlog() {
    //given
    Blog blog = Blog.builder()
        .blogURL("url")
        .name("url")
        .item(new Item(ItemDTO.builder().title("title").build()))
        .build();

    given(blogRepository.findById("id")).willReturn(Mono.just(blog));

    //when
    StepVerifier.create(blogService.getBlogItemsForBlog("id", "correlationID"))
        .expectNextCount(1)
        .verifyComplete();
  }

}
