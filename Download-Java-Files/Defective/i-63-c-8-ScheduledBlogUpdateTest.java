package pl.michal.olszewski.rssaggregator.blog;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class ScheduledBlogUpdateTest {

  private ScheduledBlogUpdate scheduledBlogUpdate;
  @Mock
  private BlogReactiveRepository blogRepository;
  @Mock
  private AsyncService asyncService;

  @BeforeEach
  void setUp() {
    scheduledBlogUpdate = new ScheduledBlogUpdate(new UpdateBlogService(blogRepository, asyncService, Executors.newSingleThreadExecutor(), null));
  }

  @Test
  void shouldRunUpdatesForAllBlogs() {
    //given
    given(blogRepository.findAll()).willReturn(Flux.just(new Blog()));
    //when
    scheduledBlogUpdate.runScheduledUpdate();

    verify(blogRepository, times(1)).findAll();
    verify(asyncService, times(1)).updateRssBlogItems(Mockito.eq(new Blog()), Mockito.anyString());
    verifyNoMoreInteractions(blogRepository);
  }

}