package cn.black.Test;

public class TestDemo {

	public static void main(String[] args) {
		Writer writer = new Writer();
		writer.lisenter();
		writer.setCallback(new Person());
	}
}
