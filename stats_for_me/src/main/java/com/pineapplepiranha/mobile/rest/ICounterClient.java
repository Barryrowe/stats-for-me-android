package com.pineapplepiranha.mobile.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.pineapplepiranha.mobile.rest.model.StatResult;

import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by barry on 3/13/14.
 */
@Rest(rootUrl = "http://pineapplepiranha.com:3000/stat",
      converters = { GsonHttpMessageConverter.class })
public interface ICounterClient {
    @Get("/{name}")
    @Accept(MediaType.APPLICATION_JSON)
    StatResult getResult(String name);

    @Post("/{action}/{name}/{count}")
    @Accept(MediaType.APPLICATION_JSON)
    StatResult sendCount(String name, String action, int count);
}