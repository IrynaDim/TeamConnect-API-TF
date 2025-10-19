package constants.testData;

public enum TimeLogTestData {
    EMPLOYEE_11_OLD_DATE(17, UserTestData.EMPLOYEE_11, EptTestData.EMPLOYEE_11_DEVELOPMENT, "2025-10-09", 8.0, "test"),
    EMPLOYEE_9(17, UserTestData.EMPLOYEE_9, EptTestData.EMPLOYEE_11_DEVELOPMENT, "2025-10-09", 8.0, "test");

    private final int id;
    private final UserTestData user;
    private final EptTestData ept;
    private final String date;
    private final double hours;
    private final String note;

    TimeLogTestData(int id, UserTestData user, EptTestData ept, String date, double hours, String note) {
        this.id = id;
        this.user = user;
        this.ept = ept;
        this.date = date;
        this.hours = hours;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public UserTestData getUser() {
        return user;
    }

    public EptTestData getEpt() {
        return ept;
    }

    public String getDate() {
        return date;
    }

    public double getHours() {
        return hours;
    }

    public String getNote() {
        return note;
    }
}
