package tests.authz;

import org.testng.annotations.BeforeSuite;
import tests.BaseConfiguration;
import util.EnvConfig;

public abstract class AuthzBaseTest extends BaseConfiguration {

    @BeforeSuite(alwaysRun = true)
    public void enforceSafetyChecks() {
        preventProdExecution();
        forbidParallel();
    }

    private void preventProdExecution() {
        String url = EnvConfig.apiBaseUrl();
        if (url.contains("prod")) {
            throw new IllegalStateException("RBAC tests are forbidden to run on PROD: " + url);
        }
    }

    private void forbidParallel() {
        String parallel = System.getProperty("parallel", "");
        if ("methods".equalsIgnoreCase(parallel)) {
            throw new IllegalStateException("RBAC tests cannot run in parallel!");
        }
    }
}
