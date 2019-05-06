package dev.mikeburgess.medialib.web;

import dev.mikeburgess.medialib.model.Media;
import dev.mikeburgess.medialib.service.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
class EditMediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MediaService mediaService;

    private Media media;

    @BeforeEach
    void beforeEach() {
        media = Media.builder().title("Title").build();
    }

    @Test
    void givenValidId_whenUpdate_thenShowEditPageWithMedia() throws Exception {
        // given
        var mediaId = 1L;
        given(mediaService.findById(any())).willReturn(Optional.of(media));

        // when
        var response = mockMvc.perform(get("/media/{mediaId}/update", mediaId));

        // then
        response.andExpect(view().name("media/edit"))
                .andExpect(model().attribute("media", is(media)))
                .andExpect(model().hasNoErrors());
        then(mediaService).should().findById(mediaId);
    }

    @Test
    void givenNonexistentId_whenUpdate_thenThrowNotFound() throws Exception {
        // given
        var mediaId = 0L;
        given(mediaService.findById(any())).willReturn(Optional.empty());

        // when
        var response = mockMvc.perform(get("/media/{mediaId}/update", mediaId));

        // then
        response.andExpect(status().isNotFound());
        then(mediaService).should().findById(mediaId);
    }

    @Test
    void givenValidData_whenPostUpdate_thenSaveAndRedirectToListPage() throws Exception {
        // given
        var mediaId = 1L;
        var type = "MOVIE";
        var title = "Title";
        var releaseDate = "2019-01-01";

        // when
        var response = mockMvc.perform(post("/media/{mediaId}/update", mediaId)
                .param("type", type)
                .param("title", title)
                .param("releaseDate", releaseDate));

        // then
        response.andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/media"));
        then(mediaService).should().save(any(Media.class));
    }

    @Test
    void givenInvalidData_whenPostUpdate_thenShowEditPageWithErrors() throws Exception {
        // given
        var mediaId = 1L;
        var type = "";
        var title = "";
        var releaseDate = "invalid";

        // when
        var response = mockMvc.perform(post("/media/{mediaId}/update", mediaId)
                .param("type", type)
                .param("title", title)
                .param("releaseDate", releaseDate));

        // then
        response.andExpect(model().attributeExists("media"))
                .andExpect(model().attributeHasFieldErrors("media", "type"))
                .andExpect(model().attributeHasFieldErrors("media", "title"))
                .andExpect(model().attributeHasFieldErrors("media", "releaseDate"))
                .andExpect(view().name("media/edit"));
        then(mediaService).should(never()).save(any());
    }

    @Test
    void whenDelete_thenDeleteAndRedirectToListPage() throws Exception {
        // given
        var mediaId = 1L;

        // when
        var response = mockMvc.perform(get("/media/{mediaId}/delete", mediaId));

        // then
        response.andExpect(redirectedUrl("/media"));
        then(mediaService).should().deleteById(mediaId);
    }
}
