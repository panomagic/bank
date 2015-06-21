package bean;

public enum Role { ADMINISTRATOR("a"), CLIENT("c");
    private String displayRole;
    Role(String displayRole) {
        this.displayRole = displayRole;
    }

    public String roleAsChar() {      //метод для работы с БД
        return this.displayRole;
    }

    public static Role fromString(String displayRole) {
        if (displayRole != null) {
            for (Role r : Role.values()) {
                if (displayRole.equalsIgnoreCase(r.displayRole)) {
                    return r;
                }
            }
        }
        throw new IllegalArgumentException("Ошибка при выводе роли, значение " + displayRole + " не найдено");
    }
}