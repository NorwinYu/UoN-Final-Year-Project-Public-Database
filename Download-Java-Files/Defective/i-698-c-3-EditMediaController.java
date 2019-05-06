package dev.mikeburgess.medialib.web;

import dev.mikeburgess.medialib.model.Media;
import dev.mikeburgess.medialib.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/media/{mediaId}")
public class EditMediaController extends AbstractMediaController {

    @Autowired
    public EditMediaController(final MediaService mediaService) {
        super(mediaService);
    }

    @GetMapping("/update")
    public String update(final Model model, @PathVariable final Long mediaId) {
        final var media = mediaService.findById(mediaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("media", media);
        model.addAttribute("actionUri", "/media/" + mediaId + "/update");
        return VIEW_MEDIA_EDIT;
    }

    @PostMapping("/update")
    public String postUpdate(@Valid final Media media, final BindingResult bindingResult) {
        return save(media, bindingResult);
    }

    @GetMapping("/delete")
    public String delete(@PathVariable final Long mediaId) {
        mediaService.deleteById(mediaId);
        return REDIRECT_MEDIA;
    }
}
