package org.kaccag.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GenericErrorController extends AbstractErrorController {

    private static final String ERROR_PATH = "/error";

    @Autowired
    public GenericErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<?> handleErrors(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        if (status.equals(HttpStatus.NOT_FOUND))
            throw new ResourceNotFoundException("Resource not found");

        DisplayedException error = new DisplayedException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
