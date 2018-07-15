package cn.black.Test;

public class Writer {

	private Callback callback;

	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public void lisenter(){
		System.out.println("Police is coming....");
		callback.call("cop is here,run......");
	}
}
