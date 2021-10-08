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
import java.util.Random;
import java.util.Scanner;

import java_rmi_interface.RmiInterface;

public class RmiClient {
	private static final String EMPTY_STR = "";

	public static void main(String[] args) throws NotBoundException, IOException {
		
		List<String> configFile = Files.readAllLines(Paths.get(args[0]));
		
		String bindName = configFile.get(0);
		String serverName = configFile.get(1);
		int portNumber = Integer.valueOf(configFile.get(2));
		Random rand = new Random();
		int clientId = rand.nextInt((100 - 1) + 1) + 1;
		
		System.out.println("Connecting to Server..");
		Registry registry = LocateRegistry.getRegistry(serverName, portNumber);
		RmiInterface rmiInterfaceStub = (RmiInterface) registry.lookup(bindName);
		System.out.println("Server Connected!");
		try  {
			Scanner sc = new Scanner(System.in);
			while(true) {
				//USER COMMANDS BEGIN FROM HERE----->
				System.out.println("What service would you want to leverage?");
				System.out.println(" 1 = Get Array Capacity \n 2 = Fetch element for read \n 3 = Fetch element for write \n"
						+ " 4 = Print Element \n 5 = Concatenate a String \n 6 = Writeback \n 7 = Release lock ");

				int elementLocation = 0;
				String userCmd = sc.nextLine();
				if (!userCmd.equals("1")) {
					System.out.println("Please enter the location of element in the array");
					elementLocation = Integer.valueOf(sc.nextLine());					
				} 


				switch (userCmd) {
				case "1": //  Get Array Capacity
					System.out.println(rmiInterfaceStub.fetchArrayCapacity());
					break;

				case "2": // Fetch element for read
					try{
						System.out.println(rmiInterfaceStub.fetchElementForRead(elementLocation, clientId) == null ? "Failure!! Please try again after some time.." : "Success!!!") ;
					}catch(RemoteException e) {
					}
					break;

				case "3": // Fetch element for write
					try{
						System.out.println(rmiInterfaceStub.fetchElementForWrite(elementLocation, clientId) == false ? "Failure!! Please try again after some time.." : "Success!!!");
					}catch(RemoteException e) {
					}
					break;

				case "4": // Print Element
                    try{
                    	String result = rmiInterfaceStub.fetchElementForRead(elementLocation, clientId);
                    	if (result != null) {
                    		System.out.println("Element at " + elementLocation + " is " + result);
                    	} else {
                    		System.out.println("Failure!! Please try again");
                    	}
                    } catch (Exception e) {
                    }
                   
					break;

				case "5": // Concatenate a String
					try{
						String str = sc.nextLine();
					    System.out.println(concatenateString(str,elementLocation,clientId,rmiInterfaceStub) == false ? "Failure!! Please try again after some time.." : "Success!!!");
					}catch(RemoteException e) {
					}
					break;

				case "6": // Writeback
					try{
						String newStr = sc.nextLine();
					    System.out.println(rmiInterfaceStub.writeBackElement(newStr,elementLocation, clientId) == false ? "Failure!! Please try again after some time.." : "Success!!!");
					}catch(RemoteException e) {
					}
					    break;

				case "7": rmiInterfaceStub.releaseLock(elementLocation, clientId);
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
	
	private static boolean concatenateString(String newStr,int index, int clientId, RmiInterface rmiInterface) throws Exception {
		String orginalString=EMPTY_STR;
		boolean fetch=false;
		try{
			orginalString=rmiInterface.fetchElementForRead(index, clientId);
			if(null!=orginalString) {
				orginalString=orginalString.concat(newStr);
				rmiInterface.releaseLock(index, clientId);
				fetch=rmiInterface.writeBackElement(orginalString,index, clientId);
			}
			

		}catch(Exception e)
		{
		
		}
		return fetch;
	}
}
