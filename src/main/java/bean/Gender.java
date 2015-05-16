package bean;

import java.util.Locale;

public enum Gender { MALE("m"), FEMALE("f");

    private String displayGender;
    private Gender(String displayGender) {
        this.displayGender = displayGender;
    }

    public String displayGender() {
        return this.displayGender;
    }

    public String genderAsString() {    //метод для вывода пола в виде строки
        return name().toLowerCase(Locale.ENGLISH);
    }
}
