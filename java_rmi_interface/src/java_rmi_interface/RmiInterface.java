package java_rmi_interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote {
	
	public String[] printAck() throws RemoteException;
	
	/*--Insert array element--*/
	public boolean insertArrayElement (int index, int clientId, String stringVal) throws RemoteException;
	public int fetchArrayCapacity () throws RemoteException;
	
	/*---Acquiring/Releasing the locks---*/
	
	
	public void releaseLock (int index, int clientId, String lockToRelease) throws RemoteException;
	
	/*---Read/Write using locks---*/
	
	public String fetchElementForRead (int index, int clientId) throws RemoteException;
	
	public String fetchElementForWrite (int index, int clientId) throws RemoteException;
	
	public boolean writeBackElement (String stringVal, int index, int clientId) throws RemoteException;
	
	
	
	
	
	
}
