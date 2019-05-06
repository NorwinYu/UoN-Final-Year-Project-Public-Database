package org.orcid.core.utils.v3.identifiers.normalizers;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.orcid.core.manager.IdentifierTypeManager;
import org.orcid.pojo.IdentifierType;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class CaseSensitiveNormalizer implements Normalizer{

    @Resource
    IdentifierTypeManager idman;
    
    @Override
    public List<String> canHandle() {
        return CAN_HANDLE_EVERYTHING;
    }

    @Override
    public String normalise(String apiTypeName, String value) {
        IdentifierType t = idman.fetchIdentifierTypesByAPITypeName(Locale.ENGLISH).get(apiTypeName);
        if (t != null && !t.getCaseSensitive()){
            return value.toLowerCase();
        }
        return value;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


}
