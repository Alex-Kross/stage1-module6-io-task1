package com.epam.mjc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileReader {
    private final String NAME_TEMPLATE = "name:{1}[\\s]|Name:{1}[\\s]|name{1}[\\s]|Name{1}[\\s]";
    private final String AGE_TEMPLATE = "age:{1}[\\s]|Age:{1}[\\s]|age{1}[\\s]|Age{1}[\\s]";
    private final String EMAIL_TEMPLATE = "email:{1}[\\s]|Email:{1}[\\s]|email{1}[\\s]|Email{1}[\\s]";
    private final String PHONE_TEMPLATE = "phone:{1}[\\s]|Phone:{1}[\\s]|phone{1}[\\s]|Phone{1}[\\s]";

    private final Pattern NAME_PATTERN = Pattern.compile(NAME_TEMPLATE);
    private final Pattern AGE_PATTERN = Pattern.compile(AGE_TEMPLATE);
    private final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_TEMPLATE);
    private final Pattern PHONE_PATTERN = Pattern.compile(PHONE_TEMPLATE);

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String strData;
            while ((strData = reader.readLine()) != null){
                profile = parseString(profile, strData);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profile;
    }

    private Profile parseString(Profile profile, String data) {
        Matcher nameMather = NAME_PATTERN.matcher(data);
        Matcher ageMather = AGE_PATTERN.matcher(data);
        Matcher emailMather = EMAIL_PATTERN.matcher(data);
        Matcher phoneMather = PHONE_PATTERN.matcher(data);

        if (nameMather.find()) {
            profile.setName(data.substring(nameMather.end()));
        } else if (ageMather.find()) {
            profile.setAge(Integer.parseInt(data.substring(ageMather.end())));
        } else if (emailMather.find()) {
            profile.setEmail(data.substring(emailMather.end()));
        } else if (phoneMather.find()) {
            profile.setPhone((long)Integer.parseInt(data.substring(phoneMather.end())));
        }
        return profile;
    }
}
