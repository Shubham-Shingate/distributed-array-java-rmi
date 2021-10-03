package java_rmi_server;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StringObj {
	private static final String EMPTY_STR = "";
	
	private String stringVal;
	
	private ReadWriteLock rwLock; 

	public StringObj(String stringVal) {
		super();
		rwLock = new ReentrantReadWriteLock();
		this.stringVal = stringVal;
	}

	public StringObj() {
		rwLock = new ReentrantReadWriteLock();
		this.stringVal = EMPTY_STR;
	}

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


	@Override
	public String toString() {
		return "StringObj [stringVal=" + stringVal + ", rwLock=" + rwLock + "]";
	}
	
}
