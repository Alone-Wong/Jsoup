package DecoratorDemo;

public class DecoraterTest {
	public static void main(String[] args) throws ClassNotFoundException {
		Source source = new Source();
		
	/*	Class<?> name = Class.forName("DecoratorDemo.Source");
		System.out.println(name);*/
		Decorator decorator = new Decorator(source);
		decorator.method();
	}

}
