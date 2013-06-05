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
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	private JPanel contentPane;
	private InvalidMove invalidMove=new InvalidMove();
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

		//TODO tirar isto
		Player t=new Player("Pink",true);	
		board.addPlayer(t);
		board.addPlayer(new Player("Red",true));
		for(int i=0; i<colors.size(); i++)
			board.addPlayer(new Player(colors.get(i),true));
		t.addCard(new Card("Alaska",1));
		t.addCard(new Card("Australia",5));
		t.addCard(new Card("Brazil",10));
		t.addCard(new Card("Peru",10));
		
		board.randomOccupy();
		board.startGame();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 950);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final Map map = new Map();
		
		map.setBounds(10, 11, 1380, 748);
		contentPane.add(map);

		final Cards cards = new Cards();
		cards.setBounds(10, 770, 300, 140);
		contentPane.add(cards);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(639, 790, 751, 110);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 6, 0, 0));

		JPanel panel = new JPanel();
		panel_2.add(panel);
		panel.setLayout(null);

		final JLabel lblCurrentlyPlaying = new JLabel("Currently playing:");
		lblCurrentlyPlaying.setBounds(10, 11, 105, 14);
		panel.add(lblCurrentlyPlaying);

		final JLabel lblCurrentlyPlaying2 = new JLabel(board.getPlayer().getColor());
		lblCurrentlyPlaying2.setBounds(10, 28, 55, 14);
		panel.add(lblCurrentlyPlaying2);

		JLabel lblArmies1 = new JLabel("Armies:");
		lblArmies1.setBounds(10, 73, 55, 14);
		panel.add(lblArmies1);

		final JLabel lblArmies2 = new JLabel(board.getPlayer().getArmies()+"");
		lblArmies2.setBounds(87, 73, 28, 14);
		panel.add(lblArmies2);

		JButton btnRedeem = new JButton("Redeem cards");

		panel_2.add(btnRedeem);

		final JButton btnPhase = new JButton("Next phase");

		panel_2.add(btnPhase);

		final JButton btnAction = new JButton("Reinforce");
		
		btnPhase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch(btnAction.getText()) {
				case "Attack":
					btnAction.setText("Fortify");
					break;
				case "Reinforce":
					if(board.getPlayer().getArmies()>0) {
						invalidMove.setVisible(true);
						return;
					}				
					btnAction.setText("Attack");
					break;
				case "Fortify":
					if(!board.nextPlaying()) {
						invalidMove.setVisible(true);
						return;
					}
					
					lblCurrentlyPlaying2.setText(board.getPlayer().getColor());
					lblArmies2.setText(board.getPlayer().getArmies()+"");
					btnAction.setText("Reinforce");

					repaint();
					break;
				}
			}
		});
		panel_2.add(btnAction);

		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});

		btnRedeem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(btnPhase.getText()!="Reinforce") {
					invalidMove.setVisible(true);
					return;
				}
				
				if(!board.redeem()) {
					invalidMove.setVisible(true);
					return;
				}
				
				lblArmies2.setText(board.getPlayer().getArmies()+"");
				repaint();
			}
		});

		panel_2.add(btnNewButton);
		
		final JSlider slider = new JSlider();
		slider.setMaximum(30);
		final JLabel lblSlider = new JLabel(slider.getValue()+"");
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblSlider.setText(slider.getValue()+"");
			}
		});
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setValue(0);
		slider.setBounds(320, 790, 200, 26);
		contentPane.add(slider);
		
		lblSlider.setBounds(530, 790, 46, 14);
		contentPane.add(lblSlider);
		
		btnAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch(btnAction.getText()) {
				case "Attack":
					if(board.attack(map.getOrigin(), map.getTarget()))
						repaint();
					else invalidMove.setVisible(true);		
					
					break;
				case "Reinforce":					
					if(board.reinforce(map.getOrigin(),slider.getValue())) {
						lblArmies2.setText(board.getPlayer().getArmies()+"");
						repaint();
					}
					else invalidMove.setVisible(true);
					
					break;
				case "Fortify":					
					if(board.move(map.getOrigin(), map.getTarget(),2))
						repaint();
					else invalidMove.setVisible(true);
					
					break;
				}

			}
		});
	}
}
