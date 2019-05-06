package org.orcid.core.utils.v3.identifiers.normalizers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class BibcodeNormalizer implements Normalizer {

    private static final List<String> canHandle = Lists.newArrayList("bibcode");
    private static final Pattern pattern = Pattern.compile("(?:^|[Bb][Ii][Bb][Bc][Oo][Dd][Ee]:?\\s*|[^a-zA-Z0-9\\.])([0-9]{4}[\\.a-zA-Z0-9]{15})(?:$|[^a-zA-Z0-9\\.])");
    
    @Override
    public List<String> canHandle() {
        return canHandle;
    }

    @Override
    public String normalise(String apiTypeName, String value) {
        if (!canHandle.contains(apiTypeName))
            return value;
        Matcher m = pattern.matcher(value);
        if (m.find()){
            String n = m.group(1);
            if (n != null){
                return n;
            }
        }
        return "";
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
