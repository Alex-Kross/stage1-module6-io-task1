package com.epam.mjc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileReader {
    private static final String NAME_STRING = "name:{1}[\\s]|Name:{1}[\\s]|name{1}[\\s]|Name{1}[\\s]";
    private static final  String AGE_STRING = "age:{1}[\\s]|Age:{1}[\\s]|age{1}[\\s]|Age{1}[\\s]";
    private static final  String EMAIL_STRING = "email:{1}[\\s]|Email:{1}[\\s]|email{1}[\\s]|Email{1}[\\s]";
    private static final  String PHONE_STRING = "phone:{1}[\\s]|Phone:{1}[\\s]|phone{1}[\\s]|Phone{1}[\\s]";

    private static final  Pattern NAME_STRING_PATTERN = Pattern.compile(NAME_STRING);
    private static final  Pattern AGE_STRING_PATTERN = Pattern.compile(AGE_STRING);
    private static final  Pattern EMAIL_STRING_PATTERN = Pattern.compile(EMAIL_STRING);
    private static final  Pattern PHONE_STRING_PATTERN = Pattern.compile(PHONE_STRING);


    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String strData;
            while ((strData = reader.readLine()) != null) {
                profile = parseString(profile, strData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return profile;
    }

    private Profile parseString(Profile profile, String data) {
        Matcher nameStringMather = NAME_STRING_PATTERN.matcher(data);
        Matcher ageStringMather = AGE_STRING_PATTERN.matcher(data);
        Matcher emailStringMather = EMAIL_STRING_PATTERN.matcher(data);
        Matcher phoneStringMather = PHONE_STRING_PATTERN.matcher(data);

        if (nameStringMather.find()) {
            profile.setName(data.substring(nameStringMather.end()));
        } else if (ageStringMather.find()) {
            profile.setAge(Integer.parseInt(data.substring(ageStringMather.end())));
        } else if (emailStringMather.find()) {
            profile.setEmail(data.substring(emailStringMather.end()));
        } else if (phoneStringMather.find()) {
            profile.setPhone((long) Integer.parseInt(data.substring(phoneStringMather.end())));
        }
        return profile;
    }
}
