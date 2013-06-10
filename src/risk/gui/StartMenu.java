package risk.gui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import risk.logic.Player;
import risk.rmi.BoardInter;
import risk.rmi.GUICallback;

@SuppressWarnings("serial")
public class StartMenu extends UnicastRemoteObject implements GUICallback {

	private JFrame frame;
	private AddPlayer addplayer=new AddPlayer();
	private GameWindow gamewindow;
	private BoardInter board;
	private InvalidMove invalid=new InvalidMove();
	private ArrayList<String> colors;
	private boolean firstPlaying=false;
	private JButton btnPushLocals;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartMenu window = new StartMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws AccessException 
	 */
	public StartMenu() throws AccessException, RemoteException, NotBoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws AccessException 
	 */
	private void initialize() throws AccessException, RemoteException, NotBoundException {
		board = (BoardInter)LocateRegistry.getRegistry().lookup("rmi://localhost:1099/Board");
		board.addObserver((GUICallback)this);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 246, 315);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 210, 150);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Gabriel\\workspace\\LPOO2\\logo.png"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 172, 210, 93);
		frame.getContentPane().add(panel_1);
		
		JButton btnNewButton = new JButton("Start game");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {		
				System.out.println(colors);
				
				try {
					if(board.getNumPlayers()<2) {
						invalid.setVisible(true);
						return;
					}
					
					try {
						gamewindow=new GameWindow();
					} catch (RemoteException | NotBoundException e1) {
						e1.printStackTrace();
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
				if(!firstPlaying)
					gamewindow.disableButtons();
				gamewindow.setVisible(true);
			}
		});

		panel_1.add(btnNewButton);
		
		JButton btnAddLocals = new JButton("Add local players");
		btnAddLocals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addplayer.setVisible(true);
			}
		});
		panel_1.add(btnAddLocals);
		
		btnPushLocals = new JButton("Add local players to Board");
		btnPushLocals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				colors=addplayer.getColors();
				System.out.print("Add "+colors);
				for(String color: colors)
					try {
						if(board.getNumPlayers()==0)
							firstPlaying=true;
						
						if(!board.addPlayer(new Player(color,true))) {
							invalid.setVisible(true);
							colors.remove(color);
						}
					} catch (RemoteException e2) {
						e2.printStackTrace();
					}
				
				btnPushLocals.setVisible(false);
			}
		});
		panel_1.add(btnPushLocals);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		panel_1.add(btnExit);
	}

	@Override
	public void notify(String phase) throws RemoteException {
		try {
			if(!colors.contains(board.getPlayerColor())) {
				gamewindow.disableButtons();
			} else if(board.isGameStarted())
				gamewindow.startTurn();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		switch(phase) {
		case "Attack":
			gamewindow.repaintMap();
			break;
		case "Gamestart":
			gamewindow.repaintMap();
			gamewindow.updateLabels();
			break;
		case "Occupation":
			gamewindow.repaintMap();
			break;
		case "Redeem":
			gamewindow.updateLabels();
			gamewindow.repaintCards();
			break;
		case "Move":
			gamewindow.repaintMap();
			break;
		case "Reinforce":
			gamewindow.updateLabels();
			gamewindow.repaintMap();
			break;
		case "Turn":
			gamewindow.updateLabels();
			break;
		}
	}
}
