package Demo02;

import java.util.Comparator;


class MyComparator implements Comparator{//实现Comparator接口，自定义比较器，实现compare方法定义比较算法

/*
 * compare(o1,o2)：参数o1是待插入的结点，o2是从树根节点逐层遍历下来的结点，用于当前比较。
 * 方法返回三个值，分别对应三种动作：
 * 返回1，则继续递进，把新结点与下一层的结点进行比较；
 * 返回0，则该属性值不足以决定两结点位置区别，再定义其他属性的比较算法来进一步比较两对象；
 * 返回-1，则新结点优先级大于当前层，往上一层比较；
*/
	public int compare(Object o1, Object o2) {
		Person p1 = (Person) o1;
		Person p2 = (Person) o2;

		int result = p1.age < p2.age ? 1 : (p1.age == p2.age ? 0 : -1);//降序排列
		//int result=p1.age<p2.age?1:(p1.age==p2.age?0:-1);//升序排列

		if (result == 0) {
			result = p1.name.compareTo(p2.name);
		}
		return result;
	}

}