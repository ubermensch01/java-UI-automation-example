package com.fedii;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.testng.AllureTestListener;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Fedii
 * Date: 2/19/15
 * Time: 5:54 PM
 */
public class TestListener extends AllureTestListener
{

  @Step("Test failed")
  @Override
  public void onTestFailure(ITestResult tr)
  {
//    createAttachment(DriverFactory.getDriver());
    if (tr.getMethod().getRetryAnalyzer() != null)
    {
      RetryAnalyzer retryAnalyzer = (RetryAnalyzer) tr.getMethod().getRetryAnalyzer();

      if (retryAnalyzer.isRetryAvailable())
      {
        tr.setStatus(ITestResult.SKIP);
      }
      else
      {
        tr.setStatus(ITestResult.FAILURE);
//        createAttachment(DriverFactory.getDriver());
      }
      Reporter.setCurrentTestResult(tr);
    }

  }

  @Attachment("Screenshot of the test state on fail")
  public byte[] createAttachment(WebDriver driver)
  {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

  @Override
  public void onFinish(ITestContext context)
  {
//        Iterator<ITestResult> failedTestCases = context.getFailedTests().getAllResults().iterator();
//        while (failedTestCases.hasNext()) {
//            System.out.println("failedTestCases");
//            ITestResult failedTestCase = failedTestCases.next();
//            ITestNGMethod method = failedTestCase.getMethod();
//            if (context.getFailedTests().getResults(method).size() > 1) {
//                System.out.println("failed test case remove as dup:" + failedTestCase.getTestClass().toString());
//                failedTestCases.remove();
//            } else {
//                if (context.getPassedTests().getResults(method).size() > 0) {
//                    System.out.println("failed test case remove as pass retry:" + failedTestCase.getTestClass().toString());
//                    failedTestCases.remove();
//                }
//            }
//
//            createAttachment(DriverFactory.getDriver());
//        }
  }
}
