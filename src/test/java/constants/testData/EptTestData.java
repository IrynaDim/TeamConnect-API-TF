package constants.testData;

import model.enums.TaskType;

public enum EptTestData {
    EMPLOYEE_11_PAID_VACATION(8, 11, "Internal", TaskType.PAID_VACATION),
    EMPLOYEE_11_DEVELOPMENT(1, 11, "CloudForge", TaskType.DEVELOPMENT),
    EMPLOYEE_9_DEVELOPMENT(10, 9, "E-commerce Platform", TaskType.DEVELOPMENT),
    HR_5_INTERVIEW(8, 11, "Internal", TaskType.PAID_VACATION),
    PM_6_INTERVIEW(2, 11, "CloudForge", TaskType.DOCUMENTATION);

    private final int id;
    private final int employeeId;
    private final String project;
    private final TaskType taskType;

    EptTestData(int id, int employeeId, String project, TaskType taskType) {
        this.id = id;
        this.employeeId = employeeId;
        this.project = project;
        this.taskType = taskType;
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getProject() {
        return project;
    }

    public TaskType getTaskType() {
        return taskType;
    }
}
