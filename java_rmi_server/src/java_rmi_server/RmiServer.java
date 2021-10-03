package java_rmi_server;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RmiServer {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
	try {
			List<String> configFile = Files.readAllLines(Paths.get(args[0]));
			
			String bindName = configFile.get(0);
			int capacityOfArray = Integer.parseInt(configFile.get(1));
			List<String> arrayStr = new ArrayList<>();
			
			for(int i = 0;i < capacityOfArray; i++) {
				String str = configFile.get(2).split(" ")[i];
				arrayStr.add(str);
			}
			int serverPort = Integer.parseInt(configFile.get(3).split(" ")[0]);
			
			System.out.println("Server starting...");
			RemoteStringArray remoteStrArr = new RemoteStringArray(capacityOfArray, arrayStr);
			Registry registry = LocateRegistry.createRegistry(serverPort);

			//System.setProperty("java.security.policy","file:./security.policy");
			registry.bind(bindName, remoteStrArr);
			
			System.out.println("The server started with bindName " + bindName + " at port " + serverPort + " with array capacity " + capacityOfArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
