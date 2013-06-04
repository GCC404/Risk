package risk.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import risk.logic.Card;

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
			System.out.println("Couldn't load background image.");
			e.printStackTrace();
		}
		
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int x=0;
		final int width=70, height=140;
		
		g.clearRect(0, 0, width*4, height);
		
		for(Card card: cards) {
			switch(card.getArmies()) {
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
			x+=width;
		}
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards=cards;
	}
}
