package util;

public class EnvConfig {
    private EnvConfig() {}

    public static String apiBaseUrl()  { return YamlConfig.getString("teamconnect.baseUrl"); }
    public static String authBaseUrl() { return YamlConfig.getString("teamconnect.auth.baseUrl"); }

    public static String empEmail()  { return YamlConfig.getString("teamconnect.users.employee.email"); }
    public static String empPass()   { return YamlConfig.getString("teamconnect.users.employee.password"); }

    public static String pmEmail()   { return YamlConfig.getString("teamconnect.users.pm.email"); }
    public static String pmPass()    { return YamlConfig.getString("teamconnect.users.pm.password"); }

    public static String hrEmail()   { return YamlConfig.getString("teamconnect.users.hr.email"); }
    public static String hrPass()    { return YamlConfig.getString("teamconnect.users.hr.password"); }

    public static String admEmail()  { return YamlConfig.getString("teamconnect.users.admin.email"); }
    public static String admPass()   { return YamlConfig.getString("teamconnect.users.admin.password"); }
}
