package risk.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import risk.logic.Card;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Cards extends JPanel {

	private ArrayList<Card> cards=new ArrayList<Card>();
	private BufferedImage cannon, soldier, horse;
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
		lblCard.setBounds(20, 100, 46, 14);
		add(lblCard);
		
		JLabel lblCard_1 = new JLabel("Card2");
		lblCard_1.setBounds(90, 100, 46, 14);
		add(lblCard_1);
		
		JLabel lblCard_2 = new JLabel("Card2");
		lblCard_2.setBounds(160, 100, 46, 14);
		add(lblCard_2);
		
		JLabel lblCard_3 = new JLabel("Card3");
		lblCard_3.setBounds(230, 100, 46, 14);
		add(lblCard_3);
		
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int x=0;
		final int width=70, height=140;
		
		g.clearRect(0, 0, width*5, height);
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
			
			x++;
		}
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards=cards;
	}
}
