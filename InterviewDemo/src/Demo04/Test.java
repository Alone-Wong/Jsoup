package Demo04;

public class Test implements Cloneable{
	
	private String name;
	private int age;
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Test(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public void f(){
		System.out.println("123");
	}

}
