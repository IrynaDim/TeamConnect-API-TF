# TeamConnect API Test Framework

## 📋 Overview

**TeamConnect** is a corporate system for time tracking and employee management.  
The platform allows users to:
- track work hours via **Time Logs**,
- view information about **employees, departments, positions, and technology stacks**,
- manage user profiles and access levels.

This repository contains an **API Test Framework** that provides:
- validation of core business logic,
- health and availability checks for API endpoints,
- automatic **Allure reporting** for results visualization.

---

## 🌐 Environments

| Environment | Description | Base URL |
|--------------|--------------|-----------|
| **PROD** | Production environment – read-only mode (Smoke tests) | `http://tracker-prod-tc3.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html` |
| **DEV** | Development environment – full CRUD functionality | `http://tracker-panel-activity-dev.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html` |

---

## 🔐 Authentication

All API requests require a **Bearer Token**.  
The token is obtained from the Auth service:
http://auth-service.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html

**Steps:**
1. Send a POST request with `email` and `password`.
2. Receive `access_token` in the response.
3. Pass the token in every request header:
   Authorization: Bearer <token>


---

## 🚀 How to Run Tests

### 🔸 PROD Environment

Used for **Smoke** test suites.  
These tests do not modify data — they only verify endpoint availability and structural correctness of responses.

**Run Smoke tests:**
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/suites/testng-smoke.xml -Dtc.env=prod
```

### 🔸 DEV Environment

Used for **Functional** and **Authorization** tests.
These tests include CRUD operations, field validations, and role-based access control.

**Run Functional tests:**
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/suites/testng-functional.xml -Dtc.env=dev
```

**Run Authorization tests:**
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/suites/testng-rbac.xml -Dtc.env=dev

```

## 🧾 Reporting
Test results are automatically collected and visualized via Allure Reports.

**Generate Allure report:**
```bash
mvn allure:serve
```

The report includes:
* status of each test (Passed / Failed),
* request and response attachments,
* logs and debug information.


## ⚙ Configuration

Environment-specific settings (URLs, credentials etc.) are stored in
application-<env>.yml under src/test/resources/.

Environment is selected via JVM parameter:

```bash
-Dtc.env=dev-test
```

## ⚙️ CI/CD Integration (GitHub Actions)

This project includes a fully configured **GitHub Actions** pipeline for automated test execution.

### 🔁 Workflows

| Workflow | Environment | Description |
|-----------|--------------|--------------|
| **Functional Tests (dev-test)** | DEV | Runs functional test suite with CRUD and validation scenarios. |
| **RBAC Tests (dev-test)** | DEV | Validates role-based access and permissions. |
| **Smoke Tests (prod-test)** | PROD | Executes lightweight availability checks on production APIs. |
| **pages-build-deployment** | — | Deploys generated reports (e.g., Allure) to GitHub Pages. |

Each workflow:
- Automatically installs dependencies via Maven,
- Runs TestNG test suites based on the specified environment,
- Generates **Allure reports** at the end of execution.

### 📊 Allure Report Publishing

After each successful run, the **Allure report** is automatically published and linked directly in the workflow summary.

