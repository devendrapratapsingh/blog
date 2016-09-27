package blog;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class UtilityTest {

	@Test
	public void test_SplitList_StringList() {
		int itemLimitInOneList = 7;
		List<List<String>> splittedList = Utility.splitList(getStringList(), itemLimitInOneList);
		Assert.assertEquals(15, splittedList.size());
	}

	@Test
	public void test_SplitList_IntegerList() {
		int itemLimitInOneList = 15;
		List<List<Integer>> splittedList = Utility.splitList(getIntegerList(), itemLimitInOneList);
		Assert.assertEquals(7, splittedList.size());
	}

	private List<String> getStringList() {
		List<String> strList = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			strList.add("" + i);
		}
		return strList;
	}

	private List<Integer> getIntegerList() {
		List<Integer> numberList = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			numberList.add(i);
		}
		return numberList;
	}
}
