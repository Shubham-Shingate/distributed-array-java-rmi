package java_rmi_server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		try {
			RemoteStringArray remoteStrArr = new RemoteStringArray(10);
			Registry registry = LocateRegistry.createRegistry(1099);
			
			System.setProperty("java.security.policy","file:./security.policy");
			registry.bind("remoteStringArray", remoteStrArr);
			System.out.println("The server is ready!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
