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

    //добавлен метод для определения пола при выводе из БД
    public static Gender fromString(String displayGender) {
        if (displayGender != null) {
            for (Gender g : Gender.values()) {
                if (displayGender.equalsIgnoreCase(g.displayGender)) {
                    return g;
                }
            }
        }
        throw new IllegalArgumentException("Ошибка при выводе пола, значение " + displayGender + " не найдено");
    }
}