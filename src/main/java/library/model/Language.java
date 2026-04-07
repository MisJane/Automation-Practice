package library.model;

public enum Language {
    RU("Russian"),
    EN("English"),
    DE("German"),
    FR("French"),
    ES("Spanish"),
    OTHER("Other");

    private final String description;

    Language(String description) {
        this.description = description;
    }
}
