package dev.mikeburgess.medialib.web;

import dev.mikeburgess.medialib.model.Media;
import dev.mikeburgess.medialib.model.MediaType;
import dev.mikeburgess.medialib.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/media")
public class MediaController {

    private static final String REDIRECT_MEDIA = "redirect:/media";
    private static final String VIEW_MEDIA_EDIT = "media/edit";
    private static final String VIEW_MEDIA_LIST = "media/list";

    private final MediaService mediaService;

    @Autowired
    public MediaController(final MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public String list(final Model model, final Pageable pageable) {
        final var mediaList = mediaService.findByPage(pageable);
        model.addAttribute("page", mediaList);
        return VIEW_MEDIA_LIST;
    }

    @GetMapping("/create")
    public String create(final Model model, final Media media) {
        model.addAttribute("media", media);
        return VIEW_MEDIA_EDIT;
    }

    @GetMapping("/update/{mediaId}")
    public String update(final Model model, @PathVariable final Long mediaId) {
        final var media = mediaService.findById(mediaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("media", media);
        return VIEW_MEDIA_EDIT;
    }

    @GetMapping("/delete/{mediaId}")
    public String delete(@PathVariable final Long mediaId) {
        mediaService.deleteById(mediaId);
        return REDIRECT_MEDIA;
    }

    @PostMapping
    public String save(@Valid final Media media, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_MEDIA_EDIT;
        }

        mediaService.save(media);
        return REDIRECT_MEDIA;
    }

    public MediaType[] getMediaTypes() {
        return MediaType.values();
    }
}
