package com.team22.Project_team_22_2018.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class Converter {

    public static String toJson(Object clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(clazz);
    }

    public static void toJsonAs(File file, Object clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, clazz);
    }

    public static <T> T toJavaObject(File file, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, clazz);
    }

    public static <T> T toJavaObject(URL url, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(url, clazz);
    }
}
