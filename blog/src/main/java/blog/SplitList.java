package blog;

import java.util.ArrayList;
import java.util.List;

public class SplitList {
	public static <T> List<List<T>> splitList(List<T> itemList, int limitToSplit) {
		final List<List<T>> splittedList = new ArrayList<List<T>>();
		int listSize = itemList.size();
		for (int i = 0; i < listSize; i = i + limitToSplit) {
			splittedList.add(itemList.subList(i, Math.min(i + limitToSplit, listSize - 1)));
		}
		return splittedList;
	}
}
