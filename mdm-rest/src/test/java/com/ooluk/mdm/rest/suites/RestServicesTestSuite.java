package com.ooluk.mdm.rest.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ooluk.mdm.rest.app.LabelRestServiceTest;
import com.ooluk.mdm.rest.app.LabelTypeRestServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LabelTypeRestServiceTest.class,
	LabelRestServiceTest.class,
 })
public class RestServicesTestSuite {
}