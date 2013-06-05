package risk.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import risk.logic.Board;
import risk.logic.Card;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Cards extends JPanel {

	private ArrayList<Card> cards=new ArrayList<Card>();
	private ArrayList<JLabel> labels=new ArrayList<JLabel>();
	private BufferedImage cannon, soldier, horse;
	private Board board=Board.getInstance();
	/**
	 * Create the panel.
	 */
	public Cards() {
		try {
			cannon=ImageIO.read(new File("cannon.png"));
			soldier=ImageIO.read(new File("soldier.png"));
			horse=ImageIO.read(new File("horse.png"));
		} catch (IOException e) {
			System.out.println("Couldn't load card's image.");
			e.printStackTrace();
		}
		setBounds(0, 0, 300, 140);
		setLayout(null);
		
		JLabel lblCard = new JLabel("");
		lblCard.setBounds(15, 100, 46, 14);
		add(lblCard);
		labels.add(lblCard);
		
		lblCard = new JLabel("");
		lblCard.setBounds(85, 100, 46, 14);
		add(lblCard);
		labels.add(lblCard);
		
		lblCard = new JLabel("");
		lblCard.setBounds(155, 100, 46, 14);
		add(lblCard);
		labels.add(lblCard);
		
		lblCard = new JLabel("");
		lblCard.setBounds(225, 100, 46, 14);
		add(lblCard);
		labels.add(lblCard);
		
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		cards=board.getCards();
		int x=0;
		final int width=70, height=140;
		
		g.clearRect(0, 0, width*5, height);
		for(JLabel label: labels)
			label.setText("");
		
		for(int i=0; i<cards.size(); i++) {
			switch(cards.get(i).getArmies()) {
			case 1:
				g.drawImage(soldier, x*width, 0, width, height, null);
				break;
			case 5:
				g.drawImage(horse, x*width, 0, width, height, null);
				break;
			case 10:
				g.drawImage(cannon, x*width, 0, width, height, null);
				break;
			}
			labels.get(i).setText(cards.get(i).getTerritory());
			
			x++;
		}
	}
}
