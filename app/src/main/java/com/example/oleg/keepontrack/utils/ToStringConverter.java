package com.example.oleg.keepontrack.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;

public final class ToStringConverter {

    public static String bodyAsString(RequestBody body) {
        try {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            return buffer.readString(body.contentType().charset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}