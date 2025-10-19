package constants.testData;

import model.enums.Role;

public enum UserTestData {
    EMPLOYEE_11(11, "john.doe@example.com", Role.EMPLOYEE, 6),
    EMPLOYEE_9(9, "gabrielle.davis@test.com", Role.EMPLOYEE, 8),
    PM_6(6, "laura.wilson@example.com", Role.PM, null),
    HR_5(5, "sarah.brown@example.com", Role.HR, null),
    ADMIN_1(1, "admin@example.com", Role.ADMIN, null);

    private final int userId;
    private final String email;
    private final Role role;
    private final Integer managerId;

    UserTestData(int userId, String email, Role role, Integer managerId) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.managerId = managerId;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Integer getManagerId() {
        return managerId;
    }
}
