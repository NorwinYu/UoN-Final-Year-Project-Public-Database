package pl.michal.olszewski.rssaggregator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

class HomeControllerTest {

  private static final String VIEW_BASE_PATH = "/WEB-INF/templates";
  private static final String VIEW_FILENAME_SUFFIX = ".html";

  private MockMvc mockMvc;

  private static ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix(VIEW_BASE_PATH);
    viewResolver.setSuffix(VIEW_FILENAME_SUFFIX);
    return viewResolver;
  }

  @BeforeEach
  void configureSystemUnderTest() {
    mockMvc = MockMvcBuilders.standaloneSetup(new HomeController())
        .setViewResolvers(viewResolver())
        .build();
  }

  @Test
  void shouldReturnHttpStatusCodeOk() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldRenderHomePageView() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(view().name("index"));
  }

  @Test
  void shouldShowIndexTemplate() {
    HomeController homeController = new HomeController();
    assertThat(homeController.mainPage()).isEqualTo("index");
  }
}