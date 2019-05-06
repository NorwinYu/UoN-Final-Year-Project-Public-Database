package org.yesworkflow.save;

import org.junit.*;
import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.data.*;
import org.yesworkflow.save.response.*;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.serialization.JsonSerializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class TestYwClient
{
    private String connection;
    private IClient client;
    private Scanner inStream;
    private Utility utility;

    @BeforeClass
    public static void checkServer()
    {
        boolean hostAvailable;
        try(Socket s = new Socket(TestData.testingurl, TestData.testingport))
        {
            hostAvailable = s.isConnected();
        }
        catch(IOException e)
        {
            hostAvailable = false;
        }
        Assume.assumeTrue(String.format("Ignoring TestYwClient test(s). Host '%s' at port '%d' not available",
                                        TestData.testingurl,
                                        TestData.testingport),
                          hostAvailable);
    }

    @Before
    public void setUp() throws Exception
    {
        utility = new Utility();
        connection = String.format("http://%s:%d/",
                                   TestData.testingurl,
                                   TestData.testingport);
        IYwSerializer serializer = utility.GetSerializer();
        client = new YwClient(connection, serializer);
        inStream = new Scanner(System.in);
    }

    @After
    public void tearDown() throws Exception
    {
        client.Close();
    }

    @Test
    public void testYwClient_Ping() throws YwSaveException
    {
        PingResponse response = client.Ping();
        assertTrue(response.OK);
    }

    @Test
    public void testYwClient_Save() throws YwSaveException
    {
        IClient client = utility.GetNewClient();
        String username = utility.CreateNewUser();
        utility.LoginUser(client, username);

        ScriptDto scriptDto = new ScriptDto("name", "content", "checksum");
        ArrayList<ScriptDto> scripts = new ArrayList<>();
        scripts.add(scriptDto);
        RunDto run = new RunDto.Builder("model", "check", "graph", scripts)
                                .build();
        SaveResponse response = client.SaveRun(run);
        assertTrue(response.ResponseBody, response.OK);
    }

    @Test
    public void testYwClient_Register() throws YwSaveException
    {
        RegisterDto registerDto = new RegisterDto.Builder(UUID.randomUUID().toString(), "Password!@#")
                                                    .build();
        RegisterResponse response = client.CreateUser(registerDto);
        assertTrue(response.ResponseBody, response.OK);
    }

    @Test
    public void testYwClient_Login() throws YwSaveException
    {
        String username = utility.CreateNewUser();
        String password = utility.GetPassword(username);

        LoginDto loginDto = new LoginDto.Builder(username, password)
                                            .build();
        LoginResponse response = client.Login(loginDto);
        assertTrue(response.ResponseBody, response.OK);
    }

    @Test
    public void testYwClient_Logout() throws YwSaveException
    {
        String username = utility.CreateNewUser();
        IClient client = utility.GetNewClient();
        utility.LoginUser(client, username);

        LogoutResponse response = client.Logout();
        assertTrue(response.ResponseBody, response.OK);
    }

    @Test
    public void testYwClient_UpdateWorkflow() throws YwSaveException
    {
        IClient client = utility.GetNewClient();
        String username = utility.CreateNewUser();
        utility.LoginUser(client, username);

        ScriptDto scriptDto = new ScriptDto("name", "content", "checksum");
        ArrayList<ScriptDto> scripts = new ArrayList<>();
        scripts.add(scriptDto);
        RunDto run = new RunDto.Builder("model", "check", "graph", scripts)
                .build();

        SaveResponse saveResponse = client.SaveRun(run);
        UpdateResponse updateResponse = client.UpdateWorkflow(saveResponse.ResponseObject.workflowId, run);
        assertTrue(updateResponse.ResponseBody, updateResponse.OK);
        assertEquals(updateResponse.ResponseObject.workflowId, saveResponse.ResponseObject.workflowId);
        assertEquals(updateResponse.ResponseObject.versionNumber, saveResponse.ResponseObject.versionNumber);
        assertEquals(updateResponse.ResponseObject.runNumber, saveResponse.ResponseObject.runNumber + 1);
        assertFalse(updateResponse.ResponseObject.newVersion);
    }

    @Test(expected = YwSaveException.class)
    public void TestYwClient_BadConnection() throws YwSaveException
    {
        String badConnectionString = "badurl";
        IYwSerializer serializer = new JsonSerializer();
        IClient badClient = new YwClient(badConnectionString, serializer);
        badClient.Ping();
    }

    @Test(expected = YwSaveException.class)
    public void TestYwClient_BadSaveUsername() throws YwSaveException
    {
        String username = UUID.randomUUID().toString();
        ScriptDto scriptDto = new ScriptDto("name", "content", "checksum");
        ArrayList<ScriptDto> scripts = new ArrayList<>();
        scripts.add(scriptDto);
        RunDto run = new RunDto.Builder("model", "check", "graph", scripts)
                .build();

        client.SaveRun(run);
    }

//    TODO:: fix bug where different user can update a workflow server side
//    @Test(expected = YwSaveException.class)
//    public void TestYwClient_BadUpdateUsername() throws YwSaveException
//    {
//        String username = UUID.randomUUID().toString();
//        String badUsername = UUID.randomUUID().toString();
//        String password = "Passoword!@#";
//
//        RegisterDto registerDto = new RegisterDto.Builder(username, password).build();
//        LoginDto loginDto = new LoginDto.Builder(username, password).build();
//
//        client.CreateUser(registerDto);
//        client.Login(loginDto);
//        ScriptDto scriptDto = new ScriptDto("name", "content", "checksum");
//        ArrayList<ScriptDto> scripts = new ArrayList<>();
//        scripts.add(scriptDto);
//        RunDto run = new RunDto.Builder(username, "model", "check", "graph", "recon", scripts)
//                .build();
//
//        SaveResponse saveResponse = client.SaveRun(run);
//        RunDto badRun = new RunDto.Builder(badUsername, "model", "check", "graph", "recon", scripts)
//                .build();
//
//        UpdateResponse updateResponse = client.UpdateWorkflow(saveResponse.ResponseObject.workflowId, badRun);
//        int x = 5;
//    }

    @Test
    public void TestAuthenticator_GetPassword()
    {
        String expectedOutput = "password";

        ByteArrayInputStream in = new ByteArrayInputStream(expectedOutput.getBytes());
        Scanner scanner = new Scanner(in);

        Authenticator authenticator = new Authenticator(null, utility.GetOutStream(), scanner);
        String actualOutput = authenticator.GetPassword();
        assertEquals("Authenticator method did not return the right user input", expectedOutput, actualOutput);
    }

    @Test
    public void TestAuthenticator_GetUsername()
    {
        String expectedOutput = "username";

        ByteArrayInputStream in = new ByteArrayInputStream(expectedOutput.getBytes());
        Scanner scanner = new Scanner(in);

        Authenticator authenticator = new Authenticator(null, utility.GetOutStream(), scanner);
        String actualOutput = authenticator.GetUsername();
        assertEquals("Authenticator method did not return the right user input", expectedOutput, actualOutput);
    }

    @Test
    public void TestAuthenticator_CheckConnectionGood()
    {
        Authenticator authenticator = new Authenticator(client, null, null);
        boolean connection = authenticator.CheckConnection();
        assertTrue("Authenticator returned false on a valid connection", connection);
    }

    @Test
    public void TestAuthenticator_CheckConnectionBad()
    {
        String badConnectionString = "https://www.google.com";
        IYwSerializer serializer = new JsonSerializer();
        IClient badConnectionClient = new YwClient(badConnectionString, serializer);
        Authenticator authenticator = new Authenticator(badConnectionClient, null, null);
        boolean connection = authenticator.CheckConnection();
        assertFalse("Authenticator returned true on an invalid connection to " + badConnectionString, connection);
    }

    @Test
    public void TestAuthenticator_TryLoginNullUsernameGoodCombo() throws YwSaveException
    {
        String username = utility.CreateNewUser();
        String password = utility.GetPassword(username);

        String simulatedUserInput = username + utility.LineSeparator() +
                                    password + utility.LineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);

        Authenticator authenticator = new Authenticator(utility.GetNewClient(), utility.GetOutStream(), scanner);
        boolean result = authenticator.TryLogin(null);
        assertTrue("Failed to authenticate for username: " + username + " and password: " + password, result);
    }

    @Test
    public void TestAuthenticator_TryLoginNullUsernameBadCombo() throws YwSaveException
    {
        String username = utility.CreateNewUser();
        String badpassword = utility.GetTestPassword() + "!@#$%";

        String simulatedUserInput = username + utility.LineSeparator() +
                                    badpassword + utility.LineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);

        Authenticator authenticator = new Authenticator(utility.GetNewClient(), utility.GetOutStream(), scanner);
        boolean result = authenticator.TryLogin(null);
        assertFalse("Returned true for bad user pass combo", result);
    }

    @Test
    public void TestAuthenticator_TryLoginGoodCombo() throws YwSaveException
    {
        String username = utility.CreateNewUser();
        String password = utility.GetPassword(username);
        String simulatedUserInput = password + utility.LineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);

        Authenticator authenticator = new Authenticator(utility.GetNewClient(), utility.GetOutStream(), scanner);
        boolean result = authenticator.TryLogin(username);
        assertTrue("Failed to authenticate for username " + username + " and password " + password, result);
    }

    @Test
    public void TestAuthenticator_TryLoginBadCombo() throws YwSaveException
    {
        String username = utility.CreateNewUser();
        String badpassword = utility.GetTestPassword() + "!@#$";
        String simulatedUserInput = badpassword + utility.LineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);

        Authenticator authenticator = new Authenticator(utility.GetNewClient(), utility.GetOutStream(), scanner);
        boolean result = authenticator.TryLogin(username);
        assertFalse("Returned true for bad user pass combo", result);
    }
}
