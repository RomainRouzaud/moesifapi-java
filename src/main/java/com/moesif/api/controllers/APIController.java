/*
 * MoesifAPILib
 *
 *
 */
package com.moesif.api.controllers;

import java.util.*;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.moesif.api.*;
import com.moesif.api.models.*;
import com.moesif.api.exceptions.*;
import com.moesif.api.http.client.HttpContext;
import com.moesif.api.http.request.HttpRequest;
import com.moesif.api.http.response.HttpResponse;
import com.moesif.api.http.client.APICallBack;
import com.moesif.api.controllers.syncwrapper.APICallBackCatcher;

public class APIController extends BaseController implements IAPIController {
    private static final Logger logger = Logger.getLogger(APIController.class.toString());
    private static final boolean debug = false; // TODO

    //private static variables for the singleton pattern
    private static Object syncObject = new Object();
    private static APIController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the APIController class
     */
    public static APIController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new APIController();
            }
        }
        return instance;
    }

    /**
     * Add Single API Event Call
     * @param    body    The event to create
     * @throws Throwable on error creating event
     */
    public Map<String, String> createEvent(
                final EventModel body
    ) throws Throwable {
    	String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/events");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4703880768413831931L;
            {
                    put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }
        
        // make the API call
        HttpResponse _response = getClientInstance().executeAsString(_request);
        
        // Wrap the request and the response in an HttpContext object
        HttpContext _context = new HttpContext(_request, _response);
        
        //invoke the callback after response if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnAfterResponse(_context);
        }

        //handle errors defined at the API level
        validateResponse(_response, _context);
        
        // Return headers to the client
        return _response.getHeaders();
    }

    /**
     * Add Single API Event Call
     * @param    body    The event to create
     * @param    callBack Called after the HTTP response is received
     * @throws JsonProcessingException on error creating event
     */
    public void createEventAsync(
                final EventModel body,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/events");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4703880768413831931L;
            {
                    put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, createHttpResponseCallback(callBack));
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * Add multiple API Events in a single batch
     * @param    body    The events to create
     * @throws Throwable on error creating event
     */
    public Map<String, String> createEventsBatch(
                final List<EventModel> body
    ) throws Throwable {
    	//the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/events/batch");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5519066674529741692L;
            {
                    put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }
        
        // make the API call
        HttpResponse _response = getClientInstance().executeAsString(_request);
        
        // Wrap the request and the response in an HttpContext object
        HttpContext _context = new HttpContext(_request, _response);
        
        //invoke the callback after response if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnAfterResponse(_context);
        }

        //handle errors defined at the API level
        validateResponse(_response, _context);
        
        // Return headers to the client
        return _response.getHeaders();
        
    }

    /**
     * Add multiple API Events in a single batch
     * @param    body    The events to create
     * @param    callBack Called after the HTTP response is received
     * @throws JsonProcessingException on error creating event
     */
    public void createEventsBatchAsync(
                final List<EventModel> body,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/events/batch");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5519066674529741692L;
            {
                    put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, createHttpResponseCallback(callBack));
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * Update a Single User
     * @param    body    The user to update
     * @throws Throwable on error updating user
     */
    public void updateUser(
            final UserModel body
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateUserAsync(body, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * Update a Single User async
     * @param    body    The user to update
     * @param    callBack Called after the HTTP response is received
     * @throws JsonProcessingException on error updating user
     */
    public void updateUserAsync(
            final UserModel body,
            final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/users");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4703880768413831931L;
            {
                put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, createHttpResponseCallback(callBack));
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * Update multiple Users in a single batch
     * @param    body    The list of users to update
     * @throws Throwable on error updating users
     */
    public void updateUsersBatch(
            final List<UserModel> body
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateUsersBatchAsync(body, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * Update multiple Users in a single batch async
     * @param    body    The list of users to update
     * @param    callBack Called after the HTTP response is received
     * @throws JsonProcessingException on error updating users
     */
    public void updateUsersBatchAsync(
            final List<UserModel> body,
            final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/users/batch");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5519066674529741692L;
            {
                put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, createHttpResponseCallback(callBack));
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * Get the Application config
     * @throws Throwable on error getting app config
     */
    public HttpResponse getAppConfig() throws Throwable {
    	//the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/config");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4703880768413831931L;
            {
                put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().get(_queryUrl, _headers, null);

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }
        
        // make the API call
        HttpResponse _response = getClientInstance().executeAsString(_request);
        
        // Wrap the request and the response in an HttpContext object
        HttpContext _context = new HttpContext(_request, _response);
        
        //invoke the callback after response if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnAfterResponse(_context);
        }

        //handle errors defined at the API level
        validateResponse(_response, _context);
        
        // Return headers to the client
        return _response;
        
        
    }

    /**
     * Get the Application config async
     * @param    callBack Called after the HTTP response is received
     * @throws JsonProcessingException on error getting app config
     */
    public void getAppConfigAsync(
    		final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/config");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4703880768413831931L;
            {
                put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().get(_queryUrl, _headers, null);

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
            	getClientInstance().executeAsStringAsync(_request, createHttpResponseCallback(callBack));
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    public AppConfigModel getCachedAndLoadAppConfig() {
        // TODO: download new one if necessary
        return getDefaultAppConfig();
    }

    public AppConfigModel getDefaultAppConfig() {
        return new AppConfigBuilder()
            .sampleRate(100)
            .etag("default")
            .build();
    }

    private EventModel buildEvent(
            EventRequestModel eventRequestModel,
            EventResponseModel eventResponseModel,
            String userId,
            String sessionToken,
            String tags,
            Object metadata) {
        EventBuilder eb = new EventBuilder();
        eb.request(eventRequestModel);
        eb.response(eventResponseModel);
        if (userId != null) {
            eb.userId(userId);
        }
        if (sessionToken != null) {
            eb.sessionToken(sessionToken);
        }
        if (tags != null) {
            eb.tags(tags);
        }

        if (metadata != null) {
            eb.metadata(metadata);
        }

        EventModel event = eb.build();

        // actually send the event here.
        APICallBack<Object> callBack = new APICallBack<Object>() {
            public void onSuccess(HttpContext context, Object response) {
                if (debug) {
                    logger.info("send to Moesif success");
                }
            }

            public void onFailure(HttpContext context, Throwable error) {
                if (debug) {
                    logger.info("send to Moesif error ");
                    logger.info( error.toString());
                }
            }
        };

        return event;
    }

    public boolean trySendEvent(EventModel event) {
        AppConfigModel appConfig = getCachedAndLoadAppConfig();

        boolean willSend = appConfig.getSampleRate() >= Math.random() * 100;

        if (willSend) {
            Map<String, String> eventApiResponse = null;

            try {
                eventApiResponse = createEvent(event);
            } catch (Throwable e) {
                if (debug) {
                    logger.warning("Send to Moesif failed " + e.toString());
                }
            }

            if (eventApiResponse != null) {

            }
        }

        return willSend;
    }
    
    /**
     * Update a Single Company
     * @param    body    The company to update
     * @throws Throwable on error updating a company
     */
    public void updateCompany(
            final CompanyModel body
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateCompanyAsync(body, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * Update a Single Company async
     * @param    body    The company to update
     * @param    callBack Called after the HTTP response is received
     * @throws JsonProcessingException on error updating a company
     */
    public void updateCompanyAsync(
            final CompanyModel body,
            final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/companies");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4703880768413831931L;
            {
                put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, createHttpResponseCallback(callBack));
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }
    
    /**
     * Update multiple Companies in a single batch
     * @param    body    The list of companies to update
     * @throws Throwable on error updating companies
     */
    public void updateCompaniesBatch(
            final List<CompanyModel> body
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateCompaniesBatchAsync(body, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * Update multiple Companies in a single batch async
     * @param    body    The list of companies to update
     * @param    callBack Called after the HTTP response is received
     * @throws JsonProcessingException on error updating companies
     */
    public void updateCompaniesBatchAsync(
            final List<CompanyModel> body,
            final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.BaseUri;

        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/v1/companies/batch");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5519066674529741692L;
            {
                put( "X-Moesif-Application-Id", Configuration.ApplicationId);
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, createHttpResponseCallback(callBack));
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    private APICallBack<HttpResponse> createHttpResponseCallback(final APICallBack<Object> callBack) {

        return new APICallBack<HttpResponse>() {
            public void onSuccess(HttpContext _context, HttpResponse _response) {
                try {

                    //invoke the callback after response if its not null
                    if (getHttpCallBack() != null)
                    {
                        getHttpCallBack().OnAfterResponse(_context);
                    }

                    //handle errors defined at the API level
                    validateResponse(_response, _context);

                    //let the caller know of the success
                    callBack.onSuccess(_context, _context);
                } catch (APIException error) {
                    //let the caller know of the error
                    callBack.onFailure(_context, error);
                } catch (Exception exception) {
                    //let the caller know of the caught Exception
                    callBack.onFailure(_context, exception);
                }
            }
            public void onFailure(HttpContext _context, Throwable _error) {
                //invoke the callback after response if its not null
                if (getHttpCallBack() != null)
                {
                    getHttpCallBack().OnAfterResponse(_context);
                }

                //let the caller know of the failure
                callBack.onFailure(_context, _error);
            }
        };
    }

}