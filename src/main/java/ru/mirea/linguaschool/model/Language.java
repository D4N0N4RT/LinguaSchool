package ru.mirea.linguaschool.model;

public enum Language {
    ENGLISH,
    SPANISH,
    GERMAN,
    FRENCH;

    public String toString() {
        switch(this.name()) {
            case "ENGLISH":
                return "Английский";
            case "FRENCH":
                return "Французский";
            case "SPANISH":
                return "Испанский";
            case "GERMAN":
                return "Немецкий";
        }
        return null;
    }
}
