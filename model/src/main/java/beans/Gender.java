package beans;

import java.util.Locale;

public enum Gender { MALE("m"), FEMALE("f");

    private String displayGender;
    Gender(String displayGender) {
        this.displayGender = displayGender;
    }

    public String genderAsChar() {
        return this.displayGender;
    }

    public String genderAsString() {    //gender output as string
        return name().toLowerCase(Locale.ENGLISH);
    }

    public static Gender fromString(String displayGender) {
        if (displayGender != null) {
            for (Gender g : Gender.values()) {
                if (displayGender.equalsIgnoreCase(g.displayGender)) {
                    return g;
                }
            }
        }
        throw new IllegalArgumentException("Error while gender output, value " + displayGender + " not found");
    }
}