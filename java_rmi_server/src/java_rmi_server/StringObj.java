package java_rmi_server;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StringObj {
	
	private String stringVal = "";
	
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();

	public String getStringVal() {
		return stringVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	public ReadWriteLock getRwLock() {
		return rwLock;
	}

	public void setRwLock(ReadWriteLock rwLock) {
		this.rwLock = rwLock;
	}

	public StringObj(String stringVal) {
		super();
		this.stringVal = stringVal;
	}

	public StringObj() {
		
	}

	@Override
	public String toString() {
		return "StringWrapperObj [stringVal=" + stringVal + ", rwLock=" + rwLock + "]";
	}
	
	
	
}
