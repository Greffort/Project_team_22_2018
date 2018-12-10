package com.team22.Project_team_22_2018.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Greffort
 */
public final class Converter {

    public static String toJSON(Object clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(clazz);
    }

    public static <T> T toJavaObject(String s, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, clazz);
    }
}
