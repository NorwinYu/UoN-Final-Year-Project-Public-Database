
package edu.uw.edm.pws.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;

import edu.uw.edm.pws.PersonWebServiceClient;
import edu.uw.edm.pws.exception.BadPersonRequestException;
import edu.uw.edm.pws.exception.NoSuchPersonException;
import edu.uw.edm.pws.exception.PWSAuthenticationException;
import edu.uw.edm.pws.exception.PWSException;
import edu.uw.edm.pws.exception.UnknownPersonRequestException;
import edu.uw.edm.pws.model.PWSError;
import edu.uw.edm.pws.model.Person;
import edu.uw.edm.pws.model.search.PersonSearchModel;
import edu.uw.edm.pws.model.search.PersonSearchResult;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PersonWebServiceClientImpl implements PersonWebServiceClient {


    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders headers;

    private final String pwsURL;
    private static String PWS_GET_PERSON_FULL_V2_URI = "/identity/v2/person/%s/full.json";
    private static String PWS_SEARCH_PERSON_V2_URI = "/identity/v2/person.json";

    public PersonWebServiceClientImpl(RestTemplate restTemplate, String pwsURL) {
        this.restTemplate = restTemplate;

        this.pwsURL = pwsURL;

        ArrayList<MediaType> headers = new ArrayList<>();
        headers.add(MediaType.APPLICATION_JSON);
        this.headers = new HttpHeaders();
        this.headers.setAccept(headers);
        log.debug("Person Web Service headers: {}", this.headers.toString());
    }


    public PersonSearchResult searchPerson(PersonSearchModel personSearchModel) throws PWSException {

        HttpEntity request = new HttpEntity(headers);


        final MultiValueMap<String, String> searchParams = searchModelToQueryParams(personSearchModel);

        final UriComponents uri = UriComponentsBuilder.fromHttpUrl(pwsURL + PWS_SEARCH_PERSON_V2_URI)
                .queryParams(searchParams).build();

        final ResponseEntity<PersonSearchResult> pwsResponse;
        try {
            pwsResponse = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, request, PersonSearchResult.class, searchParams);
            return pwsResponse.getBody();

        } catch (HttpStatusCodeException e) {
            throwSearchPWSError(e);
            return null;
        } catch (Exception e) {
            throw new PWSException(e);
        }
    }


    public Person getPersonByRegId(String regId) throws PWSException {
        return getPersonById(regId);
    }

    private Person getPersonById(String regId) throws PWSException {

        HttpEntity request = new HttpEntity(headers);
        final String url = getGetUrl(regId);
        final ResponseEntity<Person> pwsResponse;
        try {
            pwsResponse = restTemplate.exchange(url, HttpMethod.GET, request, Person.class);
            return pwsResponse.getBody();

        } catch (HttpStatusCodeException e) {
            throwGetPWSError(regId, e);
            return null;
        } catch (Exception e) {
            throw new PWSException(e);
        }

    }

    private void throwSearchPWSError(HttpStatusCodeException e) throws PWSException {
        PWSError pwsError = parsePWSError(e);
        switch (e.getStatusCode()) {
            case BAD_REQUEST:
                throw new BadPersonRequestException(pwsError);
            case UNAUTHORIZED:
                throw new PWSAuthenticationException(pwsError);
            default:
                throw new PWSException(pwsError);
        }
    }


    private void throwGetPWSError(String regId, HttpStatusCodeException e) throws PWSException {
        PWSError pwsError = parsePWSError(e);
        switch (e.getStatusCode()) {
            case NOT_FOUND:
                throw new NoSuchPersonException(pwsError, regId);
            case BAD_REQUEST:
                throw new BadPersonRequestException(pwsError);
            case UNAUTHORIZED:
                throw new PWSAuthenticationException(pwsError);
            default:
                throw new PWSException(pwsError);
        }
    }


    private PWSError parsePWSError(HttpStatusCodeException e) throws UnknownPersonRequestException {
        final String responseBodyAsString = e.getResponseBodyAsString();
        PWSError pwsError;
        try {
            pwsError = objectMapper.readValue(responseBodyAsString, PWSError.class);
        } catch (IOException e1) {
            log.error("Couldn't parse pwsError ", e);
            throw new UnknownPersonRequestException(e.getRawStatusCode() + " - " + responseBodyAsString, e);
        }
        return pwsError;
    }

    private String getGetUrl(String id) {
        return String.format(pwsURL + PWS_GET_PERSON_FULL_V2_URI, id);
    }

    private MultiValueMap<String, String> searchModelToQueryParams(PersonSearchModel personSearchModel) {
        final MultiValueMap<String, String> searchParams = new LinkedMultiValueMap<>();

        //Always get the full person
        searchParams.add("verbose", "true");

        searchParams.add("page_size", String.valueOf(personSearchModel.getPageSize()));

        searchParams.add("page_start", String.valueOf(personSearchModel.getPageStart()));

        if (!StringUtils.isEmpty(personSearchModel.getAddress())) {
            searchParams.add("address", personSearchModel.getAddress());
        }

        if (!StringUtils.isEmpty(personSearchModel.getChangedSinceDate())) {
            searchParams.add("changed_since_date", personSearchModel.getChangedSinceDate());
        }

        if (!StringUtils.isEmpty(personSearchModel.getDepartment())) {
            searchParams.add("department", personSearchModel.getDepartment());
        }

        if (!StringUtils.isEmpty(personSearchModel.getDevelopmentID())) {
            searchParams.add("development_id", personSearchModel.getDevelopmentID());
        }

        if (personSearchModel.isEduPersonAffiliationAffiliate()) {
            searchParams.add("edupersonaffiliation_affiliate", "true");
        }

        if (personSearchModel.isEduPersonAffiliationAlum()) {
            searchParams.add("edupersonaffiliation_alum", "true");
        }

        if (personSearchModel.isEduPersonAffiliationEmployee()) {
            searchParams.add("edupersonaffiliation_employee", "true");
        }

        if (personSearchModel.isEduPersonAffiliationFaculty()) {
            searchParams.add("edupersonaffiliation_faculty", "true");
        }

        if (personSearchModel.isEduPersonAffiliationMember()) {
            searchParams.add("edupersonaffiliation_member", "true");
        }

        if (personSearchModel.isEduPersonAffiliationStaff()) {
            searchParams.add("edupersonaffiliation_staff", "true");
        }

        if (personSearchModel.isEduPersonAffiliationStudent()) {
            searchParams.add("edupersonaffiliation_student", "true");
        }

        if (!StringUtils.isEmpty(personSearchModel.getEmail())) {
            searchParams.add("email", personSearchModel.getEmail());
        }

        if (!StringUtils.isEmpty(personSearchModel.getEmployeeID())) {
            searchParams.add("employee_id", personSearchModel.getEmployeeID());
        }

        if (!StringUtils.isEmpty(personSearchModel.getFirstName())) {
            searchParams.add("first_name", personSearchModel.getFirstName());
        }

        if (!StringUtils.isEmpty(personSearchModel.getHomeDepartment())) {
            searchParams.add("home_dept", personSearchModel.getHomeDepartment());
        }

        if (!StringUtils.isEmpty(personSearchModel.getLastName())) {
            searchParams.add("last_name", personSearchModel.getLastName());
        }

        if (!StringUtils.isEmpty(personSearchModel.getMailStop())) {
            searchParams.add("mail_stop", personSearchModel.getMailStop());
        }

        if (!StringUtils.isEmpty(personSearchModel.getPhoneNumber())) {
            searchParams.add("phone_number", personSearchModel.getPhoneNumber());
        }

        if (!StringUtils.isEmpty(personSearchModel.getStudentNumber())) {
            searchParams.add("student_number", personSearchModel.getStudentNumber());
        }

        if (!StringUtils.isEmpty(personSearchModel.getStudentSystemKey())) {
            searchParams.add("student_system_key", personSearchModel.getStudentSystemKey());
        }

        if (!StringUtils.isEmpty(personSearchModel.getTitle())) {
            searchParams.add("title", personSearchModel.getTitle());
        }

        if (!StringUtils.isEmpty(personSearchModel.getUWNetID())) {
            searchParams.add("uwnetid", personSearchModel.getUWNetID());
        }

        if (!StringUtils.isEmpty(personSearchModel.getUWRegID())) {
            searchParams.add("uwregid", personSearchModel.getUWRegID());
        }


        return searchParams;
    }

}
