package gov.usgs.wma.mlrnotification.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import gov.usgs.wma.mlrnotification.GlobalDefaultExceptionHandler;

@Component
public class StandardErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);

        if(errorAttributes.containsKey("message")) {
            errorAttributes.put(GlobalDefaultExceptionHandler.ERROR_MESSAGE_KEY, errorAttributes.get("message"));
        } else if(errorAttributes.containsKey("error")) {
            errorAttributes.put(GlobalDefaultExceptionHandler.ERROR_MESSAGE_KEY, errorAttributes.get("error"));
        }

        return errorAttributes;
    }
}