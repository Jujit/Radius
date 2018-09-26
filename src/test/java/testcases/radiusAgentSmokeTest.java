package testcases;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;

import testscripts.DriverScript;
import testscripts.FunctionLibrary;
import testscripts.RadiusLibrary;

public class radiusAgentSmokeTest extends DriverScript {

    // Verify login
    public static String verifyElements()
	    throws SQLException, InterruptedException, IOException, NoSuchMethodException {

	APPLICATION_LOGS.debug("Executing test case : Navigating to application and logging in");

	RadiusLibrary.navigate();

	String Result = RadiusLibrary.loginAndLogout(1);

	FunctionLibrary.closeDriver();

	if (Result.startsWith("Pass")) {
	    return "Pass: All elements are present in the Login Page";
	}

	return "Fail : Username Field or Password Field or SignIn button is not present on the page ";

    }

}