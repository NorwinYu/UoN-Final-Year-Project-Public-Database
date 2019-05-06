package dev.mikeburgess.medialib.web;

import dev.mikeburgess.medialib.model.Media;
import dev.mikeburgess.medialib.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/media")
public class ListMediaController extends AbstractMediaController {

    @Autowired
    public ListMediaController(final MediaService mediaService) {
        super(mediaService);
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
        model.addAttribute("actionUri", "/media/create");
        return VIEW_MEDIA_EDIT;
    }

    @PostMapping("/create")
    public String postCreate(@Valid final Media media, final BindingResult bindingResult) {
        return save(media, bindingResult);
    }
}
