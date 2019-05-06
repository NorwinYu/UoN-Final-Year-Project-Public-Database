package org.yesworkflow.save;

import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.data.LoginDto;
import org.yesworkflow.save.data.RegisterDto;
import org.yesworkflow.save.data.RunDto;
import org.yesworkflow.save.response.*;

import java.io.IOException;

public interface IClient {
    PingResponse Ping() throws YwSaveException;
    RegisterResponse CreateUser(RegisterDto registerDto) throws YwSaveException;
    LoginResponse Login(LoginDto loginDto) throws YwSaveException;
    LogoutResponse Logout() throws YwSaveException;
    SaveResponse SaveRun(RunDto runDto) throws YwSaveException;
    UpdateResponse UpdateWorkflow(Integer workflowId, RunDto runDto) throws YwSaveException;
    IClient Close() throws YwSaveException;
}
