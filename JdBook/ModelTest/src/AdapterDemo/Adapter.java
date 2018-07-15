package AdapterDemo;

public class Adapter extends Source implements Target{

	@Override
	public void method2() {
		System.err.println("This is method2");
		
	}

}
