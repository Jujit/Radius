package testscripts;

import java.io.IOException;
import java.sql.SQLException;

import jxl.read.biff.BiffException;
import testcases.radiusAgentSmokeTest;

public class Keywords extends DriverScript {

    // Verify elements on radius
    public static String verifyElements()
	    throws InterruptedException, IOException, BiffException, NoSuchMethodException, SQLException {

	return radiusAgentSmokeTest.verifyElements();

    }

}
