package risk.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GUICallback extends Remote {
	public void notify(String phase) throws RemoteException;
}
