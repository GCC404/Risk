package risk.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import risk.logic.Board;
import risk.logic.Player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StartMenu {

	private JFrame frame;
	private AddPlayer addplayer=new AddPlayer();
	private GameWindow gamewindow;
	private Board board=Board.getInstance();

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
	 */
	public StartMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
				ArrayList<String> colors=addplayer.getColors();
				System.out.println(colors);
				
				for(String color: colors)
					if(!board.addPlayer(new Player(color,true))) {
						System.out.println(color+" ja existe");
						colors.remove(color);
					}
				
				gamewindow=new GameWindow(colors);
				gamewindow.setVisible(true);
			}
		});

		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add player");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addplayer.setVisible(true);
			}
		});
		panel_1.add(btnNewButton_1);
		
		JButton btnLoadGame = new JButton("Load game");
		panel_1.add(btnLoadGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		panel_1.add(btnExit);
	}
}
