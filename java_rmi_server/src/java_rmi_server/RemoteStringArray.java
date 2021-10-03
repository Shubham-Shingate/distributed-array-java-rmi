package java_rmi_server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import java_rmi_interface.RmiInterface;

public class RemoteStringArray extends UnicastRemoteObject implements RmiInterface {

	private static final long serialVersionUID = 1L;
	
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
	public void releaseLock(int index, int clientId, String lockToRelease) throws RemoteException {
		
		if (lockToRelease.equals("R")) {
			strArray.get(index).getRwLock().readLock().unlock();
		} else {
			strArray.get(index).getRwLock().writeLock().unlock();
		}
	}
	
	@Override
	public boolean insertArrayElement(int index, int clientId, String stringValue) throws RemoteException {
		if (this.requestWriteLock(index, clientId)) {
			strArray.add(index, new StringObj(stringValue));
			this.releaseLock(index, clientId, "W");
			return true;
		} else {
			throw new RemoteException("Failed to acquire the write lock, the requested object is currently busy.");
		}
	}
	

	@Override
	public String fetchElementForRead(int index, int clientId) throws RemoteException {
		System.out.println(strArray.get(index).getRwLock());
		ReentrantReadWriteLock rc=(ReentrantReadWriteLock) strArray.get(index).getRwLock();
		if (this.requestReadLock(index, clientId)) {
			String resultStr = strArray.get(index).getStringVal();
			this.releaseLock(index, clientId, "R");
			return resultStr;
		} else {
			throw new RemoteException("Failed to acquire the read lock, the requested object is currently busy.");
		}
	}

	@Override
	public String fetchElementForWrite(int index, int clientId) throws RemoteException {
		if (this.requestReadLock(index, clientId)) {
			String resultStr = strArray.get(index).getStringVal();
			this.releaseLock(index, clientId, "R");
			return resultStr;
		} else {
			throw new RemoteException("Failed to acquire the read lock, the requested object is currently busy.");
		}
	}

	@Override
	public boolean writeBackElement(String stringVal, int index, int clientId) throws RemoteException {
		if (this.requestWriteLock(index, clientId)) {
			strArray.get(index).setStringVal(stringVal);
			this.releaseLock(index, clientId, "W");
			return true;
		} else {
			throw new RemoteException("Failed to acquire the write lock, the requested object is currently busy.");
		}
	}
	
	private boolean requestReadLock(int index, int clientId) {
		return strArray.get(index).getRwLock().readLock().tryLock();
	}

	private boolean requestWriteLock(int index, int clientId) {
		return strArray.get(index).getRwLock().writeLock().tryLock();
	}

	@Override
	public int fetchArrayCapacity() throws RemoteException {
		return strArray.size();
	}
}
