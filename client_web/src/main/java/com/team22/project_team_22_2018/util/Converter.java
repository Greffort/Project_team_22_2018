package com.team22.project_team_22_2018.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public final class Converter {

    public static String toJson(final Object clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(clazz);
    }

    public static String toJson(final ArrayList clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(clazz);
    }

    public static void toJsonAs(final File file, final Object clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, clazz);
    }

    public static <T> T toJavaObject(final File file, final Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, clazz);
    }

    public static <T> T toJavaObject(final URL url, final Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(url, clazz);
    }

    public static <T> T toJavaObject(final String line, final Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(line, clazz);
    }
}
