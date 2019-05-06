package org.yesworkflow.save.response;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.serialization.JsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Scanner;

public abstract class YwResponse<Dto>
{
    private String defaultEncoding = "UTF-8";

    public boolean OK;
    public boolean BadRequest;
    public String ResponseBody;
    public Dto ResponseObject;
    protected Hashtable<String, String> headers;
    protected int statusCode;
    protected String statusReason;
    protected IYwSerializer serializer;

    public abstract YwResponse<Dto> Build(HttpResponse response, IYwSerializer serializer) throws YwSaveException;

    protected abstract Dto DeserializeResponseContent();

    public String GetHeaderValue(String headerName)
    {
        return headers.get(headerName);
    }

    public int GetStatusCode()
    {
        return this.statusCode;
    }

    public String GetStatusReason()
    {
        return this.statusReason;
    }

    protected void build(HttpResponse response, IYwSerializer serializer)
            throws YwSaveException
    {
        this.serializer = serializer;
        if(this.serializer == null)
            this.serializer = new JsonSerializer();

        this.statusCode = response.getStatusLine().getStatusCode();
        this.statusReason = response.getStatusLine().getReasonPhrase();
        this.OK = this.statusCode >= 200 && this.statusCode < 300;
        this.BadRequest = this.statusCode >= 500;
        this.headers = AllocateHeaders(response);
        this.ResponseBody = ScanResponse(response);
        this.ResponseObject = DeserializeResponseContent();
    }

    private String ScanResponse(HttpResponse response) throws YwSaveException
    {
        String responseBody;
        try(InputStream content = response.getEntity().getContent()){
            Scanner scanner = new Scanner(content, defaultEncoding).useDelimiter("\\A");
            responseBody = scanner.next();
        } catch(IOException ioe)
        {
            throw new YwSaveException(String.format("Invalid content encoding: %s", ioe.getMessage()));
        }
        return responseBody;
    }

    private Hashtable<String, String> AllocateHeaders(HttpResponse response)
    {
        Hashtable<String, String> hashtable = new Hashtable<>();
        for(Header header : response.getAllHeaders())
        {
            hashtable.put(header.getName(), header.getValue());
        }
        return hashtable;
    }
}
