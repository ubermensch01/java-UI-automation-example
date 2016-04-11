package com.fedii;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Fedii
 * Date: 4/16/15
 * Time: 6:07 PM
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private int maxCount = 2;

    public boolean isRetryAvailable() {
        return (count < maxCount);
    }

    @Override
    public boolean retry( ITestResult result )
    {
        if ( !result.isSuccess() ) {
            if ( count < maxCount ) {
                count++;
                result.setStatus( ITestResult.SUCCESS_PERCENTAGE_FAILURE );
                String message = Thread.currentThread().getName() + "Error in '" + result.getName() + "' with status '" + result.getStatus() +
                        "'. Retrying '" + count + "' times.";
                Reporter.log(message, true);
                return true;
            }
        }
        return false;
    }

}
