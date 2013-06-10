package risk.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import risk.rmi.BoardInter;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	private JPanel contentPane;
	private InvalidMove invalidMove=new InvalidMove();
	private BoardInter board;
	final JButton btnStartGame, btnRandomOccupy, btnPickTerritory, btnPhase, btnRedeem, btnAction;
	final JLabel lblArmies2, lblSlider, lblCurrentlyPlaying2, lblCurrentlyPlaying;
	final JSlider slider;
	final Cards cards;
	final Map map;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow frame = new GameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param colors 
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	public GameWindow() throws RemoteException, NotBoundException {
		board = (BoardInter)LocateRegistry.getRegistry().lookup("rmi://localhost:1099/Board");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 950);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		map = new Map(board);

		map.setBounds(10, 11, 1380, 748);
		contentPane.add(map);

		cards = new Cards(board);
		cards.setBounds(10, 770, 300, 140);
		contentPane.add(cards);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(639, 790, 751, 110);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 6, 0, 0));

		btnStartGame = new JButton("Start Game");
		panel_2.add(btnStartGame);

		btnRandomOccupy = new JButton("Random Occupy");

		panel_2.add(btnRandomOccupy);

		btnPickTerritory = new JButton("Pick Territory");

		panel_2.add(btnPickTerritory);

		btnRedeem = new JButton("Redeem cards");

		panel_2.add(btnRedeem);

		btnPhase = new JButton("Next phase");

		panel_2.add(btnPhase);

		btnRedeem.setVisible(false);
		btnPhase.setVisible(false);

		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		
		btnAction = new JButton("Reinforce");

		panel_2.add(btnAction);
		btnAction.setVisible(false);

		panel_2.add(btnNewButton);

		JPanel panel = new JPanel();
		panel_2.add(panel);
		panel.setLayout(null);

		lblCurrentlyPlaying = new JLabel("Currently playing:");
		lblCurrentlyPlaying.setBounds(10, 11, 105, 14);
		panel.add(lblCurrentlyPlaying);

		lblCurrentlyPlaying2 = new JLabel(board.getPlayerColor());
		lblCurrentlyPlaying2.setBounds(10, 28, 55, 14);
		panel.add(lblCurrentlyPlaying2);

		JPanel panel_1 = new JPanel();
		panel_2.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblArmies1 = new JLabel("Armies:");
		lblArmies1.setBounds(10, 11, 59, 14);
		panel_1.add(lblArmies1);

		lblArmies2 = new JLabel(board.getPlayerArmies()+"");
		lblArmies2.setBounds(10, 30, 105, 14);
		panel_1.add(lblArmies2);

		slider = new JSlider();
		slider.setMaximum(30);
		lblSlider = new JLabel(board.getPlayerArmies()+"");

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblSlider.setText(slider.getValue()+"");
			}
		});
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setValue(board.getPlayerArmies());
		slider.setBounds(320, 790, 200, 26);
		contentPane.add(slider);

		lblSlider.setBounds(530, 790, 46, 14);
		contentPane.add(lblSlider);

		btnStartGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					if(!board.startGame()) {
						invalidMove.setVisible(true);
						return;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				btnStartGame.setVisible(false);
			}
		});

		btnPhase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				switch(btnAction.getText()) {
				case "Attack":
					btnAction.setText("Fortify");
					break;
				case "Reinforce":
					try {
						if(board.getPlayerArmies()>0) {
							invalidMove.setVisible(true);
							return;
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}				
					btnAction.setText("Attack");
					break;
				case "Fortify":
					try {
						if(!board.nextPlaying()) {
							invalidMove.setVisible(true);
							return;
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					
					btnAction.setText("Reinforce");
					break;
				}
			}
		});

		btnRandomOccupy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					board.randomOccupy();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				btnPickTerritory.setVisible(false);
				btnRandomOccupy.setVisible(false);
			}
		});
		
		btnRedeem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if(!board.redeem()) {
						invalidMove.setVisible(true);
						return;
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnPickTerritory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(map.getOrigin().equals("")) {
					invalidMove.setVisible(true);
					return;
				}
					
				try {
					if(!board.pickOccupy(map.getOrigin(), 1))
						invalidMove.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

				btnRandomOccupy.setVisible(false);
			}
		});
		
		btnAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				switch(btnAction.getText()) {
				case "Attack":
					try {
						if(!board.attack(map.getOrigin(), map.getTarget()))
							invalidMove.setVisible(true);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}		

					break;
				case "Reinforce":					
					try {
						if(!board.reinforce(map.getOrigin(),slider.getValue()))
							invalidMove.setVisible(true);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

					break;
				case "Fortify":					
					try {
						if(!board.move(map.getOrigin(), map.getTarget(),2))
							invalidMove.setVisible(true);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

					break;
				}

			}
		});
	}
	
	void disableButtons() {
		btnRedeem.setVisible(false);
		btnPhase.setVisible(false);
		btnAction.setVisible(false);
		btnPickTerritory.setVisible(false);
		btnStartGame.setVisible(false);
		btnRandomOccupy.setVisible(false);
	}
	
	public void startTurn() {
		btnRedeem.setVisible(true);
		btnPhase.setVisible(true);
		btnAction.setVisible(true);
	}

	public void updateLabels() {
		try {
			lblCurrentlyPlaying2.setText(board.getPlayerColor());
			lblArmies2.setText(board.getPlayerArmies()+"");
			slider.setValue(board.getPlayerArmies());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void repaintMap() {
		map.repaint();
	}
	
	public void repaintCards() {
		cards.repaint();
	}
}
