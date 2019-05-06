package org.orcid.integration.blackbox.api.v3.rc1.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.orcid.integration.api.pub.PublicV3ApiClientImpl;
import org.orcid.integration.blackbox.api.v3.rc1.BlackBoxBaseV3_0_rc1;
import org.orcid.integration.blackbox.api.v3.rc1.MemberV3Rc1ApiClientImpl;
import org.orcid.jaxb.model.message.ScopePathType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.jersey.api.client.ClientResponse;

/**
 * 
 * @author Angel Montenegro
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class EmailTest extends BlackBoxBaseV3_0_rc1 {
    
    @Resource(name = "memberV3_0_rc1ApiClient")
    private MemberV3Rc1ApiClientImpl memberV3Rc1ApiClient;
    
    @Resource(name = "publicV3_0_rc1ApiClient")
    private PublicV3ApiClientImpl publicV3ApiClientImpl;
    
    private String limitedEmailValue = "limited@test.orcid.org";

    @Before
    public void setUpData() {
        signout();
        signin();
        showAccountSettingsPage();
        openEditEmailsSectionOnAccountSettingsPage();
        updatePrimaryEmailVisibility(org.orcid.jaxb.model.v3.rc1.common.Visibility.PUBLIC.name());
        removePopOver();
        try {
            updateEmailVisibility(limitedEmailValue, org.orcid.jaxb.model.v3.rc1.common.Visibility.LIMITED.name());
        } catch (Exception e) {
            addEmail(limitedEmailValue, org.orcid.jaxb.model.v3.rc1.common.Visibility.LIMITED.name());
        }                
    }

    /**
     * PRECONDITIONS: The primary email must be public
     */
    @Test
    public void testGetWithPublicAPI() {
        ClientResponse getAllResponse = publicV3ApiClientImpl.viewEmailXML(getUser1OrcidId());
        assertNotNull(getAllResponse);
        org.orcid.jaxb.model.v3.rc1.record.Emails emails = getAllResponse.getEntity(org.orcid.jaxb.model.v3.rc1.record.Emails.class);
        assertListContainsEmail(getUser1UserName(), org.orcid.jaxb.model.v3.rc1.common.Visibility.PUBLIC, emails);
    }

    /**
     * PRECONDITIONS: The primary email must be public The user must have a
     * limited email limited@email.com
     * 
     * @throws JSONException
     * @throws InterruptedException
     */
    @Test
    public void testGetWithMembersAPI() throws InterruptedException, JSONException {
        String accessToken = getAccessToken();
        ClientResponse getAllResponse = memberV3Rc1ApiClient.getEmails(getUser1OrcidId(), accessToken);
        assertNotNull(getAllResponse);
        org.orcid.jaxb.model.v3.rc1.record.Emails emails = getAllResponse.getEntity(org.orcid.jaxb.model.v3.rc1.record.Emails.class);
        assertListContainsEmail(getUser1UserName(), org.orcid.jaxb.model.v3.rc1.common.Visibility.PUBLIC, emails);
        assertListContainsEmail("limited@test.orcid.org", org.orcid.jaxb.model.v3.rc1.common.Visibility.LIMITED, emails);
    }

    public static void assertListContainsEmail(String emailString, org.orcid.jaxb.model.v3.rc1.common.Visibility visibility,
            org.orcid.jaxb.model.v3.rc1.record.Emails emails) {
        assertNotNull(emails);
        assertNotNull(emails.getEmails());
        assertFalse(emails.getEmails().isEmpty());
        for (org.orcid.jaxb.model.v3.rc1.record.Email email : emails.getEmails()) {
            if (email.getEmail().equals(emailString)) {
                assertEquals(visibility, email.getVisibility());
                return;
            }
        }
        fail();
    }

    public String getAccessToken() throws InterruptedException, JSONException {
        List<String> scopes = getScopes(ScopePathType.READ_LIMITED);
        return getAccessToken(scopes);
    }
}
