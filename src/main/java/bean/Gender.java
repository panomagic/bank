package bean;

import java.util.Locale;

public enum Gender { MALE("m"), FEMALE("f");

    private String displayGender;
    Gender(String displayGender) {
        this.displayGender = displayGender;
    }

    public String genderAsChar() {      //метод для работы с БД
        return this.displayGender;
    }

    public String genderAsString() {    //метод для вывода пола в виде строки
        return name().toLowerCase(Locale.ENGLISH);
    }
}
