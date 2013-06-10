package risk.rmi;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import risk.logic.Board;

public class BoardServer {
	public static void main(String[] args) throws RemoteException {
		LocateRegistry.createRegistry(1099);
		BoardInter c = new Board();
		try {
			LocateRegistry.getRegistry().rebind("rmi://localhost:1099/Board", c);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
