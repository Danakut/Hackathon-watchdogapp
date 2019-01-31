package cz.danakut.coursewatchdog.watchdogapp;

public enum RegistrationStatus {
    OTEVRENA,
    UZAVRENA,
    POZDEJI;

    public static RegistrationStatus fromString(String statusString) {

        switch (statusString) {

            case "konecRegistrace": return UZAVRENA;
            case "registraceOtevrena": return OTEVRENA;
            case "dejteMiVedet": return POZDEJI;
            default: throw new IllegalArgumentException("Unknown status: " + statusString);
        }
    }

    public String toDatabaseString() {

        switch (this) {
            case OTEVRENA: return "otevrena";
            case UZAVRENA: return "uzavrena";
            case POZDEJI: return "pozdeji";
            default: return null;
        }
    }

}
