package org.yesworkflow.save;


import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.data.LoginDto;
import org.yesworkflow.save.data.RegisterDto;
import org.yesworkflow.save.data.TestData;
import org.yesworkflow.save.response.RegisterResponse;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.serialization.JsonSerializer;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.UUID;

public class Utility
{
    private final String testPassword = "Password!@#";

    private IClient client;
    private String connectionString;
    private HashMap<String, String> usernamePasswordCombo;
    private IYwSerializer serializer;
    private PrintStream outStream;
    private PrintStream errStream;

    public Utility()
    {
        serializer = new JsonSerializer();
        outStream = new PrintStream(System.out);
        errStream = new PrintStream(System.out);
        usernamePasswordCombo = new HashMap<>();
        connectionString = String.format("http://%s:%s/", TestData.testingurl, TestData.testingport);
        client = new YwClient(connectionString, serializer);
    }

    public String GetTestConnectionString()
    {
        return connectionString;
    }

    public IYwSerializer GetSerializer()
    {
        return serializer;
    }

    public PrintStream GetOutStream()
    {
        return outStream;
    }

    public PrintStream getErrStream()
    {
        return errStream;
    }

    public IClient GetNewClient()
    {
        return new YwClient(connectionString, serializer);
    }

    public String GetPassword(String username)
    {
        return usernamePasswordCombo.get(username);
    }

    public String GetTestPassword()
    {
        return testPassword;
    }

    public String CreateUsername()
    {
        return UUID.randomUUID().toString();
    }

    public String LineSeparator()
    {
        return System.getProperty("line.separator");
    }

    // Get Username back as string
    public String CreateNewUser() throws YwSaveException
    {
        String username = CreateUsername();
        RegisterDto registerDto = new RegisterDto.Builder(username, testPassword).build();
        RegisterResponse registerResponse = client.CreateUser(registerDto);
        if(registerResponse.OK)
            usernamePasswordCombo.put(username, testPassword);
        return username;
    }

    public void LoginUser(IClient client, String username) throws YwSaveException
    {
        String password = usernamePasswordCombo.get(username);
        LoginDto loginDto = new LoginDto.Builder(username, password).build();
        client.Login(loginDto);
    }

    public void LoginUser(String username) throws YwSaveException
    {
        LoginUser(GetNewClient(), username);
    }
}
