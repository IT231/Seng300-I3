package com.thelocalmarketplace.software.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for all tests in the project
 * 
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AddBagsTest_Bronze.class,
	AddBagsTest_Gold.class,
	AddBagsTest_Silver.class,
	AddBulkyItemTest.class,
	FundsTest.class,
	ItemAddedRuleTest.class,
	PayByCardTest_Bronze.class,
	PayByCardTest_Silver.class,
	PayByCardTest_Gold.class,
	PayByCashControllerTest.class,
	PrintReceiptTest_Bronze.class,
	PrintReceiptTest_Gold.class,
	PrintReceiptTest_Silver.class,
	RemoveItemTests.class,
	SelfCheckoutStationLogicTest.class,
	SelfCheckoutStationSystemTest.class,
	SessionTest.class,
	WeightTest.class,
})

public class TestSuite {

}
