package org.yesworkflow.save;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.data.LoginDto;
import org.yesworkflow.save.data.RegisterDto;
import org.yesworkflow.save.data.RunDto;
import org.yesworkflow.save.response.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class YwClient implements IClient {
    private CloseableHttpClient client;
    private String baseUrl;
    private String token;
    private IYwSerializer serializer;

    public YwClient(String connection, IYwSerializer serializer)
    {
        client = HttpClients.custom()
                            .build();
        baseUrl = connection;
        this.serializer = serializer;
    }

    public PingResponse Ping()
            throws YwSaveException
    {
        return executeGetRequest("save/ping/", PingResponse.class);
    }

    public RegisterResponse CreateUser(RegisterDto registerDto)
            throws YwSaveException
    {
        return executePostRequest("rest-auth/registration/", registerDto, RegisterResponse.class);
    }

    public LoginResponse Login(LoginDto loginDto)
            throws YwSaveException
    {
        LoginResponse response = executePostRequest("rest-auth/login/", loginDto, LoginResponse.class);
        if(response.OK)
            this.token = response.ResponseObject.key;

        return response;
    }

    public LogoutResponse Logout()
            throws YwSaveException
    {
        return executePostRequest("rest-auth/logout/", null, LogoutResponse.class);
    }

    public SaveResponse SaveRun(RunDto runDto)
            throws YwSaveException
    {
        return executePostRequest("save/", runDto, SaveResponse.class);
    }

    public UpdateResponse UpdateWorkflow(Integer workflowId, RunDto runDto)
            throws YwSaveException
    {
        return executePostRequest(String.format("save/%d/", workflowId), runDto, UpdateResponse.class);
    }

    private <Response extends YwResponse<?>> Response executeGetRequest(String route, Class<Response> rClass)
            throws YwSaveException
    {
        HttpGet getRequest = new HttpGet(String.join("", baseUrl, route));
        getRequest.addHeader("accept", "application/json");
        return executeRequest(getRequest, rClass);
    }

    private <Response extends YwResponse<?>> Response executePostRequest(String route,
                                                                         Object Dto,
                                                                         Class<Response> rClass)
            throws YwSaveException
    {
        HttpPost postRequest = new HttpPost(String.join("", baseUrl, route));
        Response response;
        try
        {
            StringEntity json = new StringEntity(serializer.Serialize(Dto));
            json.setContentType("application/json");
            postRequest.setEntity(json);
            if(token != null)
                postRequest.addHeader("Authentication", token);
            response = executeRequest(postRequest, rClass);
        } catch (UnsupportedEncodingException e)
        {
            throw new YwSaveException("Unexpected error: " + e.getMessage());
        }
        return response;
    }

    private <Response extends YwResponse<?>> Response executeRequest(HttpRequestBase request,
                                                                     Class<Response> rClass)
            throws YwSaveException
    {
        HttpResponse httpResponse;
        try {
            httpResponse = client.execute(request);
        } catch (IOException e) {
            throw new YwSaveException("There was a problem connecting to " + baseUrl);
        }

        Response ywResponse = null;
        try
        {
            ywResponse = rClass.getConstructor().newInstance();
            ywResponse.Build(httpResponse, serializer);
        }
        catch (ReflectiveOperationException e)
        {
            throw new YwSaveException("Unexpected error: " + ywResponse.ResponseBody);
        }

        if(ywResponse.BadRequest){
            throw new YwSaveException(String.format("%s was unable to process the request: %s", baseUrl, ywResponse.GetStatusReason()));
        } else if(ywResponse.GetStatusCode() >= 400) {
            throw new YwSaveException(String.format("There was an error trying to access %s: %s", baseUrl, ywResponse.GetStatusReason()));
        }

        return ywResponse;
    }

    public IClient Close() throws YwSaveException
    {
        try{
            client.close();
        } catch (IOException e)
        {
            throw new YwSaveException("Error terminating program.");
        }

        return this;
    }
}
