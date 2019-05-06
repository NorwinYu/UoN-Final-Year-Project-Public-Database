package dev.mikeburgess.medialib.web;

import dev.mikeburgess.medialib.model.Media;
import dev.mikeburgess.medialib.model.MediaType;
import dev.mikeburgess.medialib.service.MediaService;
import org.springframework.validation.BindingResult;

abstract class AbstractMediaController {

    static final String REDIRECT_MEDIA = "redirect:/media";
    static final String VIEW_MEDIA_EDIT = "media/edit";
    static final String VIEW_MEDIA_LIST = "media/list";

    final MediaService mediaService;

    AbstractMediaController(final MediaService mediaService) {
        this.mediaService = mediaService;
    }

    String save(final Media media, final BindingResult bindingResult) {
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
