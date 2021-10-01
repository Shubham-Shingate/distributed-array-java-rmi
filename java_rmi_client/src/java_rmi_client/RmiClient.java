package java_rmi_client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import java_rmi_interface.RmiInterface;

public class RmiClient {

	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		RmiInterface rmiInterfaceStub = (RmiInterface) registry.lookup("remoteStringArray");
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			//USER COMMANDS BEGIN FROM HERE----->
			System.out.println("What service would you want to leverage?");
			System.out.println("1 = Get Array Capacity");
			System.out.println("2 = Fetch element for read");
			System.out.println("3 = Fetch element for write");
			System.out.println("4 = Print Element");
			System.out.println("5 = Concatenate a String");
			System.out.println("6 = Writeback");
			System.out.println("7 = Request Read Lock");
			System.out.println("8 = Request Write Lock");
		
			String userCmd = sc.nextLine();
			
			switch (userCmd) {
			case "1":
				
				break;
				
			case "2":
				
				break;
				
			case "3":
				
				break;
				
			case "4":
				
				break;
			
			case "5":
				
				break;
				
			case "6":
				
				break;
			
			case "7":
				
				break;
			
			case "8":
				
				break;
				
			default:
				System.out.println("Invalid Command");
				break;
			}
			
			
		}
		

	}
}
