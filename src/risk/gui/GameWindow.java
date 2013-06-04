package risk.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import risk.logic.Board;
import risk.logic.Card;
import risk.logic.Player;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	private JPanel contentPane;
	private Board board=Board.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow frame = new GameWindow(new ArrayList<String>());
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
	 */
	public GameWindow(ArrayList<String> colors) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 950);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Map map = new Map();
		map.setBounds(10, 11, 1380, 748);
		contentPane.add(map);
		
		final Cards cards = new Cards();
		cards.setBounds(10, 770, 320, 140);
		contentPane.add(cards);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(639, 790, 751, 110);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 6, 0, 0));
		
		final JLabel lblCurrentlyPlaying = new JLabel("Currently playing:");
		panel_2.add(lblCurrentlyPlaying);
		
		//TODO tirar isto
		Player t=new Player("Pink",true);	
		board.addPlayer(t);
		board.addPlayer(new Player("Red",true));
		for(int i=0; i<colors.size(); i++)
			board.addPlayer(new Player(colors.get(i),true));
		t.addCard(new Card("Alaska",1));
		board.randomOccupy();
		
		cards.setCards(board.getCards());
		board.startGame();
		//map.setMap((HashMap<String, Territory>) board.aa());
		
		final JLabel lblNewLabel = new JLabel(board.getPlayer().getColor());
		panel_2.add(lblNewLabel);
		
		JButton btnNextTurn = new JButton("Next Turn");
		btnNextTurn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				board.nextPlaying();
				cards.setCards(board.getCards());
				lblNewLabel.setText(board.getPlayer().getColor());
				
				repaint();
			}
		});
		panel_2.add(btnNextTurn);
		
		JButton btnFortify = new JButton("Fortify");
		panel_2.add(btnFortify);
		
		JButton btnLoadGame = new JButton("Save Game");
		panel_2.add(btnLoadGame);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		panel_2.add(btnNewButton);
	}
}
