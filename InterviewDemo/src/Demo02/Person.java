package Demo02;

public class Person {

	String name;
	int age;

	public Person() {
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String toString() {
		return "姓名：" + name + ",年龄：" + age;
	}

}
