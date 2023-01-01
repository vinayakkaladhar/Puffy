# UI Automation

Test framework to test UI of web applications (FrontEnd).


## Tool set

Key tools used in this framework are:

- **[Java 11](https://openjdk.java.net/projects/jdk/11/)** (as the core programming language)
- **[Maven 3.0.0](https://maven.apache.org/download.cgi)** (for dependency management)
- **[Selenium 4.0.0](https://www.selenium.dev/downloads/)** (for automation)
- **[TestNg 7.1.0](https://testng.org/doc/download.html)** (for framework)

## Getting Started

- Install below 2 external dependencies on your machine:
  - **[Java 11](https://openjdk.java.net/projects/jdk/11/)** (as the core programming language)
    - **[Maven 3.8.5](https://maven.apache.org/download.cgi)** (for dependency management)
    - Follow this [document](https://www.oracle.com/in/java/technologies/downloads/) to install Java
    - Follow this [document](https://maven.apache.org/install.html) to install Maven
    - Follow this [document](https://www.jetbrains.com/help/idea/installation-guide.html#standalone) to install IntelliJ based on your OS. Community edition is good enough to work with this framework.

- To test if the setup is correct.
  - Open this project from IntelliJ/Eclipse/VSCode.

## Installation

```sh
cd selenium
mvn install
```

## Running tests

Below are different modes available to run your tests.

### Run tests on localhost

> Open terminal and run the following commands in the root folder of your project.

```sh
cd selenium
mvn clean test
```

> Open terminal and run the following commands in the root folder of your project to run tests in specific browser.

```sh
cd selenium
mvn clean test -Dbrowser=firefox|chrome

```

> Open the project and search for testng.xml file.

```sh
Run the test by right click on the TestNG XML file and select Run As => TestNG Suite.
```

## Reports

> To find each test emailable HTML report

- Navigate to target/surefire-reports/emailable-report.html

> To find entire suite report

- Navigate to target/surefire-reports/All Test Suite/*.html

## Screenshots
```Screenshots are available in the HTML reports.```
```Screenshots are also available in the screenshots folder appended with method name and time of execution.```

## Setting up CI/CD pipeline on CodeMagic

- Add a new application (https://codemagic.io/app)
- Connect with the GitHub repository and select branch
- Navigate to settings and configure build workflow using codemagic.yml (https://docs.codemagic.io/yaml-basic-configuration/yaml-getting-started/)
- Push the code (https://docs.codemagic.io/yaml-running-builds/starting-builds-automatically/)
- Open the CodeMagic (https://codemagic.io/app) CI/CD UI to check the run status

## Browsers Supported
```sh
Chrome, Firefox
```
