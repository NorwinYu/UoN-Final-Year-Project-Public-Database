package org.yesworkflow.save;

import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.data.LoginDto;

import java.io.Console;
import java.io.PrintStream;
import java.util.Scanner;

public class Authenticator
{
    private final String AUTH_MESSAGE = "Enter your credentials for %s";
    private final String RETRY_MESSAGE = "Invalid username password combination, try again.";
    private final String PASSWORD_FOR_USERNAME_MESSAGE = "Entering password for user %s.";

    private IClient client;
    private PrintStream outStream;
    private Scanner inStream;
    private Console console;
    private boolean loggedIn;

    public Authenticator(IClient client, PrintStream outStream, Scanner inStream)
    {
        this.client = client;
        this.outStream = outStream;
        this.inStream = inStream;
        this.console = System.console();
        this.loggedIn = false;
    }

    public void Reset()
    {
        loggedIn = false;
    }

    public boolean IsLoggedIn()
    {
        return loggedIn;
    }

    public boolean TryLogin(String username)
    {
        if(username == null)
            username = GetUsername();

        String password = GetPassword();
        LoginDto loginDto = new LoginDto.Builder(username, password)
                                        .build();
        try
        {
            client.Login(loginDto);
        } catch(YwSaveException e)
        { // we want to suppress this error
            loggedIn = false;
            return loggedIn;
        }
        loggedIn = true;
        return loggedIn;
    }

    public void PrintRetryMessage()
    {
        outStream.println(RETRY_MESSAGE);
    }

    public void PrintAuthMessage(String webAddress)
    {
        outStream.println(String.format(AUTH_MESSAGE, webAddress));
    }

    public void PrintPasswordForUsername(String username)
    {
        outStream.println(String.format(PASSWORD_FOR_USERNAME_MESSAGE, username));
    }

    public String GetUsername()
    {
        outStream.print("Username: ");
        return inStream.nextLine();
    }

    public String GetPassword()
    {
        outStream.print("Password: ");

        // console class masks user input, but does not typically work on IDE's
        if(console != null)
            return String.valueOf(console.readPassword());
        return inStream.nextLine();
    }

    public boolean CheckConnection()
    {
        try
        {
            client.Ping();
        } catch (YwSaveException e)
        {
            return false;
        }
        return true;
    }
}
