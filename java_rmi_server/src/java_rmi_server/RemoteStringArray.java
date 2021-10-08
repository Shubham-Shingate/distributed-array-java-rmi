package java_rmi_server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import java_rmi_interface.RmiInterface;

public class RemoteStringArray extends UnicastRemoteObject implements RmiInterface {
	
	private List<StringObj> strArray;
	
	public RemoteStringArray(int size, List<String> arrayStr) throws RemoteException  {
		this.strArray = new ArrayList<StringObj>(size);
		for (int i = 0; i < size; i++) {
			String s = arrayStr.get(i);
			System.out.println("Initalising location " + i + " with initial string " + s);
			strArray.add(i, new StringObj(s));
		}
	}

	@Override
	public String[] printAck() throws RemoteException {
		System.out.println("Hello this is an ack" + strArray);
		return null;
	}

	@Override
	public void releaseLock(int index, int clientId) throws RemoteException {
		strArray.get(index).releaseLocks(clientId);
	}
	
	@Override
	public boolean insertArrayElement(int index, int clientId, String stringValue) throws RemoteException, InterruptedException {
		if (this.requestWriteLock(index, clientId)) {
			strArray.add(index, new StringObj(stringValue));
			//this.releaseLock(index, clientId, "W");
			return true;
		} return false;
	}
	

	@Override
	public String fetchElementForRead(int index, int clientId) throws RemoteException {
		boolean acquired = requestReadLock(index, clientId);
		if (acquired) {
			 return strArray.get(index).getStringVal();
			 
		} return null;
	}

	@Override
	public boolean fetchElementForWrite(int index, int clientId) throws RemoteException {
		boolean acquired = requestWriteLock(index, clientId);
		if (acquired) {
			 strArray.get(index).getStringVal();
			 return true;
		} 
		return false;
	}

	@Override
	public boolean writeBackElement(String stringVal, int index, int clientId) throws RemoteException, InterruptedException {
		boolean acquired = requestWriteLock(index, clientId);
		if (acquired) {
			
			strArray.get(index).setStringVal(stringVal);
			return true;
			
		}return false;
	}
	
	
	private boolean requestReadLock(int index, int clientId) {
		Lock lock= strArray.get(index).getReadLock(clientId);
		if (lock != null) {
			return true;
		}
		return false;
	}

	private boolean requestWriteLock(int index, int clientId) {
		Lock lock= strArray.get(index).getWriteLock(clientId);
		if (lock != null) {
			return true;
		}
		return false;
	}

	@Override
	public int fetchArrayCapacity() throws RemoteException {
		return strArray.size();
	}
}
