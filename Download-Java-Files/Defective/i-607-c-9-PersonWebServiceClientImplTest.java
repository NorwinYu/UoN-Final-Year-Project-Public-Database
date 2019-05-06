package edu.uw.edm.pws.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.edm.pws.exception.BadPersonRequestException;
import edu.uw.edm.pws.exception.BadSearchPersonRequestException;
import edu.uw.edm.pws.exception.NoSuchPersonException;
import edu.uw.edm.pws.exception.PWSException;
import edu.uw.edm.pws.model.Person;
import edu.uw.edm.pws.model.search.PersonSearchModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author Maxime Deravet Date: 10/25/17
 */
@RunWith(SpringRunner.class)
public class PersonWebServiceClientImplTest {

    private MockRestServiceServer server;


    private PersonWebServiceClientImpl pws;


    @Before
    public void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        server = MockRestServiceServer.bindTo(restTemplate).build();

        pws = new PersonWebServiceClientImpl(restTemplate, "http://gws.com/");

    }

    @Test(expected = PWSException.class)
    public void getPersonExceptionTest() throws PWSException {
        this.server.expect(requestTo("http://gws.com/identity/v2/person/my-reg-id/full.json"))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE).body("{\n" +
                        "    \"InnerExceptions\": null,\n" +
                        "    \"StackTrace\": null,\n" +
                        "    \"StatusCode\": \"503\",\n" +
                        "    \"StatusDescription\": \"'bla'\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON));
        try {
            pws.getPersonByRegId("my-reg-id");
        } catch (PWSException e) {
            assertThat(e, is(instanceOf(PWSException.class)));
            assertThat(e.getPwsError().getStatusCode(), is(equalTo("503")));
            throw e;
        }

    }

    @Test(expected = NoSuchPersonException.class)
    public void noSuchPersonPersonExceptionTest() throws PWSException {
        this.server.expect(requestTo("http://gws.com/identity/v2/person/my-reg-id/full.json"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND).body("{\n" +
                        "    \"InnerExceptions\": null,\n" +
                        "    \"StackTrace\": null,\n" +
                        "    \"StatusCode\": \"404\",\n" +
                        "    \"StatusDescription\": \"No person found.\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON));
        try {
            pws.getPersonByRegId("my-reg-id");
        } catch (PWSException e) {
            assertThat(e, is(instanceOf(NoSuchPersonException.class)));
            assertThat(e.getPwsError().getStatusCode(), is(equalTo("404")));
            throw e;
        }

    }


    @Test
    public void fieldsArentDuplicatedTest() throws PWSException, JsonProcessingException {
        this.server.expect(requestTo("http://gws.com/identity/v2/person/my-reg-id/full.json")).andRespond(withSuccess(TEST_RESPONSE_GET, MediaType.APPLICATION_JSON));
        final Person personByRegId = pws.getPersonByRegId("my-reg-id");

        final String json = new ObjectMapper().writeValueAsString(personByRegId);
        Configuration jsonPathConf = Configuration
                .defaultConfiguration()
                .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);

        final DocumentContext parse = JsonPath.using(jsonPathConf).parse(json);
        assertThat(parse.read("$.uwnetID"), nullValue());
        assertThat(parse.read("$.UWNetID"), is(equalTo("bsimps")));


    }


    @Test
    public void getPersonTest() throws PWSException {
        this.server.expect(requestTo("http://gws.com/identity/v2/person/my-reg-id/full.json")).andRespond(withSuccess(TEST_RESPONSE_GET, MediaType.APPLICATION_JSON));
        final Person personByRegId = pws.getPersonByRegId("my-reg-id");

        assertThat(personByRegId.getDisplayName(), is(equalTo("BART SIMPSON")));
        assertThat(personByRegId.getPersonAffiliations().getAlumPersonAffiliations().getDevelopmentId(), is(equalTo("9999980050")));
        assertThat(personByRegId.getPersonAffiliations().getEmployeePersonAffiliation().getEmployeeId(), is(equalTo("000210480")));
        assertThat(personByRegId.getPersonAffiliations().getStudentPersonAffiliation().getStudentNumber(), is(equalTo("3090370")));

    }

    @Test(expected = BadPersonRequestException.class)
    public void badSearchRequestExceptionTest() throws PWSException {
        this.server.expect(requestTo(startsWith("http://gws.com/identity/v2/person")))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST).body("{\n" +
                        "    \"InnerExceptions\": null,\n" +
                        "    \"StackTrace\": null,\n" +
                        "    \"StatusCode\": \"400\",\n" +
                        "    \"StatusDescription\": \"Invalid studentNumber\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON));

        try {
            pws.getPersonByRegId("my-reg-id");
        } catch (PWSException e) {
            assertThat(e, is(instanceOf(BadPersonRequestException.class)));
            assertThat(e.getPwsError().getStatusCode(), is(equalTo("400")));
            assertThat(e.getPwsError().getStatusDescription(), is(equalTo("Invalid studentNumber")));
            throw e;
        }

    }


    @Test
    public void searchPersonTest() throws PWSException {

        final PersonSearchModel searchModel = PersonSearchModel.builder()
                .lastName("SIMPS*")
                .pageSize(20)
                .build();

        this.server.expect(requestTo(startsWith("http://gws.com/identity/v2/person")))
                .andExpect(queryParam("verbose", "true"))
                .andExpect(queryParam("last_name", "SIMPS*"))
                .andExpect(queryParam("page_size", "20"))
                .andExpect(queryParam("page_start", "1"))

                .andRespond(withSuccess(TEST_SEARCHRESPONSE_GET, MediaType.APPLICATION_JSON));
        pws.searchPerson(searchModel);

    }

    private static final String TEST_SEARCHRESPONSE_GET = "{\n" +
            "    \"Current\": {\n" +
            "        \"Address\": null,\n" +
            "        \"ChangedSinceDate\": null,\n" +
            "        \"Department\": null,\n" +
            "        \"DevelopmentID\": null,\n" +
            "        \"EduPersonAffiliationAffiliate\": false,\n" +
            "        \"EduPersonAffiliationAlum\": false,\n" +
            "        \"EduPersonAffiliationEmployee\": false,\n" +
            "        \"EduPersonAffiliationFaculty\": false,\n" +
            "        \"EduPersonAffiliationMember\": false,\n" +
            "        \"EduPersonAffiliationStaff\": false,\n" +
            "        \"EduPersonAffiliationStudent\": false,\n" +
            "        \"Email\": null,\n" +
            "        \"EmployeeID\": null,\n" +
            "        \"FirstName\": null,\n" +
            "        \"HomeDepartment\": null,\n" +
            "        \"Href\": \"/identity/v2/person.xhtml?uwregid=&uwnetid=&employee_id=&student_number=&student_system_key=&development_id=&last_name=SIMPS*&first_name=&edupersonaffiliation_student=&edupersonaffiliation_staff=&edupersonaffiliation_faculty=&edupersonaffiliation_employee=false&edupersonaffiliation_member=&edupersonaffiliation_alum=&edupersonaffiliation_affiliate=&changed_since_date=&phone_number=&mail_stop=&home_dept=&department=&address=&title=&email=&verbose=true&page_size=10&page_start=1\",\n" +
            "        \"LastName\": \"SIMPS*\",\n" +
            "        \"MailStop\": null,\n" +
            "        \"PageSize\": \"10\",\n" +
            "        \"PageStart\": \"1\",\n" +
            "        \"PhoneNumber\": null,\n" +
            "        \"StudentNumber\": null,\n" +
            "        \"StudentSystemKey\": null,\n" +
            "        \"Title\": null,\n" +
            "        \"UWNetID\": null,\n" +
            "        \"UWRegID\": null,\n" +
            "        \"Verbose\": true\n" +
            "    },\n" +
            "    \"Next\": null,\n" +
            "    \"PageSize\": \"10\",\n" +
            "    \"PageStart\": \"1\",\n" +
            "    \"Persons\": [\n" +
            "        {\n" +
            "            \"DisplayName\": \"BART SIMPSON\",\n" +
            "            \"EduPersonAffiliations\": [\n" +
            "                \"member\",\n" +
            "                \"student\",\n" +
            "                \"alum\",\n" +
            "                \"staff\",\n" +
            "                \"employee\"\n" +
            "            ],\n" +
            "            \"IsTestEntity\": true,\n" +
            "            \"PreferredFirstName\": null,\n" +
            "            \"PreferredMiddleName\": null,\n" +
            "            \"PreferredSurname\": null,\n" +
            "            \"PriorUWNetIDs\": [],\n" +
            "            \"PriorUWRegIDs\": [\n" +
            "                \"C18452A204E54F8E8F1DAE1E72B679B2\"\n" +
            "            ],\n" +
            "            \"RegisteredFirstMiddleName\": \"BART\",\n" +
            "            \"RegisteredName\": \"BART SIMPSON\",\n" +
            "            \"RegisteredSurname\": \"SIMPSON\",\n" +
            "            \"RepositoryTimeStamp\": \"1/3/2017 9:29:12 AM\",\n" +
            "            \"UIDNumber\": null,\n" +
            "            \"UWNetID\": null,\n" +
            "            \"UWRegID\": \"ADA12DA10F7649B2A8861B14633F0A0A\",\n" +
            "            \"WhitepagesPublish\": false,\n" +
            "            \"PersonAffiliations\": {\n" +
            "                \"AlumPersonAffiliation\": {\n" +
            "                    \"DevelopmentID\": \"9999980050\"\n" +
            "                },\n" +
            "                \"EmployeePersonAffiliation\": {\n" +
            "                    \"EmployeeID\": \"000210480\",\n" +
            "                    \"EmployeeWhitePages\": {\n" +
            "                        \"Addresses\": [],\n" +
            "                        \"EmailAddresses\": [],\n" +
            "                        \"Faxes\": [],\n" +
            "                        \"Name\": null,\n" +
            "                        \"Phones\": [],\n" +
            "                        \"Positions\": [],\n" +
            "                        \"PublishInDirectory\": null,\n" +
            "                        \"TouchDials\": [],\n" +
            "                        \"VoiceMails\": []\n" +
            "                    },\n" +
            "                    \"HomeDepartment\": \"C&C TEST BUDGET\",\n" +
            "                    \"MailStop\": null\n" +
            "                },\n" +
            "                \"StudentPersonAffiliation\": {\n" +
            "                    \"StudentNumber\": \"3090370\",\n" +
            "                    \"StudentSystemKey\": \"990100370\",\n" +
            "                    \"StudentWhitePages\": {\n" +
            "                        \"Class\": null,\n" +
            "                        \"Departments\": [],\n" +
            "                        \"Email\": null,\n" +
            "                        \"Name\": null,\n" +
            "                        \"Phone\": null,\n" +
            "                        \"PublishInDirectory\": null\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    ],\n" +
            "    \"Previous\": null,\n" +
            "    \"TotalCount\": 1\n" +
            "}";

    private static final String TEST_RESPONSE_GET = "{\n" +
            "    \"DisplayName\": \"BART SIMPSON\",\n" +
            "    \"EduPersonAffiliations\": [\"member\",\"student\",\"alum\",\"staff\",\"employee\"],\n" +
            "    \"IsTestEntity\": true,\n" +
            "    \"PreferredFirstName\": null,\n" +
            "    \"PreferredMiddleName\": null,\n" +
            "    \"PreferredSurname\": null,\n" +
            "    \"PriorUWNetIDs\": [ ],\n" +
            "    \"PriorUWRegIDs\": [\"C18452A204E54F8E8F1DAE1E72B679B2\"],\n" +
            "    \"RegisteredFirstMiddleName\": \"BART\",\n" +
            "    \"RegisteredName\": \"BART SIMPSON\",\n" +
            "    \"RegisteredSurname\": \"SIMPSON\",\n" +
            "    \"RepositoryTimeStamp\": \"1/3/2017 9:29:12 AM\",\n" +
            "    \"UIDNumber\": null,\n" +
            "    \"UWNetID\": \"bsimps\",\n" +
            "    \"UWRegID\": \"ADA12DA10F7649B2A8861B14633F0A0A\",\n" +
            "    \"WhitepagesPublish\": false,\n" +
            "    \"PersonAffiliations\": {\n" +
            "        \"AlumPersonAffiliation\": {\n" +
            "            \"DevelopmentID\": \"9999980050\"\n" +
            "        },\n" +
            "        \"EmployeePersonAffiliation\": {\n" +
            "            \"EmployeeID\": \"000210480\",\n" +
            "            \"EmployeeWhitePages\": {\n" +
            "                \"Addresses\": [ ],\n" +
            "                \"EmailAddresses\": [ ],\n" +
            "                \"Faxes\": [ ],\n" +
            "                \"Name\": null,\n" +
            "                \"Phones\": [ ],\n" +
            "                \"Positions\": [ ],\n" +
            "                \"PublishInDirectory\": null,\n" +
            "                \"TouchDials\": [ ],\n" +
            "                \"VoiceMails\": [ ]\n" +
            "            },\n" +
            "            \"HomeDepartment\": \"C&C TEST BUDGET\",\n" +
            "            \"MailStop\": null\n" +
            "        },\n" +
            "        \"StudentPersonAffiliation\": {\n" +
            "            \"StudentNumber\": \"3090370\",\n" +
            "            \"StudentSystemKey\": \"990100370\",\n" +
            "            \"StudentWhitePages\": {\n" +
            "                \"Class\": null,\n" +
            "                \"Departments\": [ ],\n" +
            "                \"Email\": null,\n" +
            "                \"Name\": null,\n" +
            "                \"Phone\": null,\n" +
            "                \"PublishInDirectory\": null\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";
}