package com.team22.Project_team_22_2018.util;
//import org.json.simple.*;

//import com.github.cliftonlabs.json_simple.JsonArray;
//import com.github.cliftonlabs.json_simple.JsonObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team22.Project_team_22_2018.models.task.BaseTask;
import com.team22.Project_team_22_2018.models.task.decorator.SubTaskTask;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.Iterator;

//import org.json.simple.JSONObject;
//import

/**
 * @author Json
 */
public class Json {

    public static void main(String[] args) {
        User user = new User();
        String s = "";
        try {
            s = ConverterTest.toJSON1(user);
            System.out.println(user);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            System.out.println(ConverterTest.toJavaObject1(s));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main1(String[] args) {
        SubTaskTask task3 = new SubTaskTask(new BaseTask("ЗАДАНИЕ  11111", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12)));
        JSONObject obj = new JSONObject();
        obj.put("name", task3.toString());
        obj.put("age", new Integer(100));

        JSONArray list = new JSONArray();
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");

        obj.put("messages", list);

        try (FileWriter file = new FileWriter("d:\\test.json")) {

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(obj);

        main2();
    }

    public static void main2() {

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("d:\\test.json"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            long age = (Long) jsonObject.get("age");
            System.out.println(age);

            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("messages");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

//            (SubTaskTask)name = (String) jsonObject.get("name");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}

class User {

    @JsonProperty("Number")
    private long id;

    @JsonProperty("First Name")
    private String name;

    @JsonProperty("Phone Number")
    private String phone;

    @JsonIgnore
    private String level;

    public User() {
    }

    public User(long id, String name, String phone, String level) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "User[ID:" + id + ", Name: " + name + ", Phone: " + phone + "]";
    }
}


class ConverterTest {

    private final static String baseFile = Resources.BASE_FILE.getPath();

    public static void toJSON(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValueAsString(user);
        mapper.writeValue(new File(baseFile), user);
        System.out.println("json created!");
    }

    public static User toJavaObject() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(baseFile), User.class);
    }

    public static String toJSON1(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

//        mapper.writeValueAsString(user);
//        mapper.writeValue(new File(baseFile), user);
        System.out.println("json created!");
        return mapper.writeValueAsString(user);
    }

    public static User toJavaObject1(String s) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s,User.class);
    }

}

