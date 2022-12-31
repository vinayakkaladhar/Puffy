package Utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class ListenerTest implements ITestListener
    {
        public WebDriver driver;

        @AfterSuite
        public void onFinish(ITestContext arg0) {
            // TODO Auto-generated method stub

        }

        @BeforeSuite
        public void onStart(ITestContext arg0) {
                System.out.println("******" + arg0.getName() + "*********");
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestFailure(ITestResult arg0) {
            try{

                takeScreenShot(Utilities.getDriver(),arg0.getName());
                // TODO Auto-generated method stub
            }
          catch(Exception e){
              e.printStackTrace();
          }

        }

        public void takeScreenShot(WebDriver driver,String methodName) {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Calendar c = Calendar.getInstance();
            String time = String.valueOf(c.getTimeInMillis());
            try {
                File screenShotName = new File(System.getProperty("user.dir")+ File.separator + "screenshots" + File.separator +methodName+time+".png");
                FileUtils.copyFile(scrFile, screenShotName);
                System.out.println("*** Placed screen shot in "+"./"+" ***");
                Reporter.log(" <a href='/selenium/screenshots/"+methodName+time+".png'> <img src='/selenium/screenshots/"+ methodName+time+".png' height='400' width='800'/> </a>  ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTestSkipped(ITestResult arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestStart(ITestResult arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestSuccess(ITestResult arg0) {
            // TODO Auto-generated method stub

        }
    }

