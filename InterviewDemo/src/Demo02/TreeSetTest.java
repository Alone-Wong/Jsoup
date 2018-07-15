package Demo02;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetTest {

	/**
	* @param args the command line arguments
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		TreeSet people = new TreeSet(new MyComparator());// 把比较器对象作为TreeSet的构造函数参数
		people.add(new Person("小明", 20));
		people.add(new Person("小张", 30));
		people.add(new Person("小刘", 18));
		people.add(new Person("小林", 17));
		people.add(new Person("小刘", 35));

		Iterator it = people.iterator();// 用迭代器遍历treeset
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

}