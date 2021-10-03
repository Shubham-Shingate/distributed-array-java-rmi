package java_rmi_client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import java_rmi_interface.RmiInterface;

public class RmiClient {
	private static final String EMPTY_STR = "";

	public static void main(String[] args) throws NotBoundException, IOException {
		
		List<String> configFile = Files.readAllLines(Paths.get(args[0]));
		
		String bindName = configFile.get(0);
		String serverName = configFile.get(1);
		int portNumber = Integer.valueOf(configFile.get(2));
		int clientNumber = (int) Math.random();
		
		System.out.println("Connecting to Server..");
		Registry registry = LocateRegistry.getRegistry(serverName, portNumber);
		RmiInterface rmiInterfaceStub = (RmiInterface) registry.lookup(bindName);
		System.out.println("Server Connected!");
		try  {
			Scanner sc = new Scanner(System.in);
			String fetchedResult= EMPTY_STR;
			while(true) {
				//USER COMMANDS BEGIN FROM HERE----->
				System.out.println("What service would you want to leverage?");
				System.out.println(" 1 = Get Array Capacity \n 2 = Fetch element for read \n 3 = Fetch element for write \n"
						+ " 4 = Print Element \n 5 = Concatenate a String \n 6 = Writeback \n 7 = Release lock ");

				String userCmd = sc.nextLine();
				System.out.println("Please enter the location of element in the array");
				int elementLocation = Integer.valueOf(sc.nextLine());

				switch (userCmd) {
				case "1": //  Get Array Capacity
					System.out.println(rmiInterfaceStub.fetchArrayCapacity());
					break;

				case "2": // Fetch element for read
					fetchedResult = rmiInterfaceStub.fetchElementForRead(elementLocation, clientNumber);
					System.out.println(fetchedResult == EMPTY_STR ? "Failure" : "Success!");
					break;

				case "3": // Fetch element for write
					fetchedResult = rmiInterfaceStub.fetchElementForWrite(elementLocation, clientNumber);
					System.out.println(fetchedResult == EMPTY_STR ? "Failure" : "Success!");
					break;

				case "4": // Print Element
                    System.out.println(rmiInterfaceStub.fetchElementForRead(elementLocation, clientNumber));
					break;

				case "5": // Concatenate a String
					String str = sc.nextLine();
					System.out.println(concatenateString(str,0,clientNumber,rmiInterfaceStub));
					break;

				case "6": // Writeback
					String newStr = sc.nextLine();
					System.out.println(rmiInterfaceStub.writeBackElement(newStr,elementLocation, clientNumber) == false ? "Failure" : "Success!");
					break;

				case "7": //Release lock
					break;

				default:
					System.out.println("Invalid Command");
					break;
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	private static String concatenateString(String newStr,int index, int clientId, RmiInterface rmiInterface) throws Exception {
		String orginalString=rmiInterface.fetchElementForRead(0, 0);
		orginalString=orginalString.concat(newStr);
		rmiInterface.writeBackElement(orginalString,0, 0);
		return orginalString;
	}
}
