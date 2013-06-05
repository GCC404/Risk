package risk.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

import risk.logic.Board;
import risk.logic.Territory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Map extends JPanel {

	private BufferedImage background;
	private ArrayList<String> terrnames=new ArrayList<String>();
	private ArrayList<Rectangle> squares=new ArrayList<Rectangle>();
	private ArrayList<JLabel> labels=new ArrayList<JLabel>();
	private HashMap<String, Territory> territories=new HashMap<String,Territory>();
	private Board board=Board.getInstance();
	private boolean chosingOrigin=true;
	private String origin="", target="";
	/**
	 * Create the panel.
	 */
	public Map() {
		
		setLayout(null);
		setBounds(0, 0, 1380, 748);
		final JLabel lblOrigin = new JLabel("Origin:");
		lblOrigin.setBounds(386, 677, 241, 14);
		add(lblOrigin);
		
		final JLabel lblTarget = new JLabel("Target:");
		lblTarget.setBounds(386, 702, 221, 14);
		add(lblTarget);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println(arg0.getPoint().getX()+" "+arg0.getPoint().getY());
				for(int i=0; i<squares.size(); i++)
					if(squares.get(i).contains(arg0.getPoint())) {
						System.out.println(terrnames.get(i));
						
						if(chosingOrigin) {
							origin=terrnames.get(i);
							lblOrigin.setText("Origin: "+terrnames.get(i));
						}
						else {
							target=terrnames.get(i);
							lblTarget.setText("Target: "+terrnames.get(i));
						}
				
						chosingOrigin=!chosingOrigin;
						return;
					}
				
				repaint();
			}
		});
		
		try {
			background=ImageIO.read(new File("background.png"));
		} catch (IOException e) {
			System.out.println("Couldn't load background image.");
			e.printStackTrace();
		}
		
		initSquares();
		
		repaint();
	}

	private void initSquares() {
		terrnames.add("Alaska");
		int width = 25, labelwidth = 30, height=20;
		squares.add(new Rectangle(89,78,width,height));
		terrnames.add("Alberta");
		squares.add(new Rectangle(155,126,width,height));
		terrnames.add("Central America");
		squares.add(new Rectangle(147,278,width,height));
		terrnames.add("Eastern United States");
		squares.add(new Rectangle(226,209,width,height));
		terrnames.add("Greenland");
		squares.add(new Rectangle(476,40,width,height));
		terrnames.add("Northwest Territory");
		squares.add(new Rectangle(192,71,width,height));
		terrnames.add("Ontario");
		squares.add(new Rectangle(254,130,width,height));
		terrnames.add("Quebec");
		squares.add(new Rectangle(337,130,width,height));
		terrnames.add("Western United States");
		squares.add(new Rectangle(130,188,width,height));
		
		//TODO meter aqui o resto
			
		for(int i=0; i<squares.size(); i++) {
			JLabel label=new JLabel("0");
			label.setBounds((int)squares.get(i).getX(), (int)squares.get(i).getY(), labelwidth, height);
			labels.add(label);
			add(label);
		}
	}
	
	public void setMap(HashMap<String, Territory> territories) {
		this.territories=territories;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
		drawSquares(g);
	}
	
	private void drawSquares(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setColor(Color.BLUE);
		for(int i=0; i<squares.size(); i++) {
			g2d.setColor(choseColor(i));
			g2d.fill(squares.get(i));
			labels.get(i).setText(board.getTerrArmies(terrnames.get(i))+"");
			g2d.draw(squares.get(i));
		}
	}

	private Color choseColor(int i) {
		switch(board.getTerrColor(terrnames.get(i))) {
		case "Pink":
			return Color.pink;
		case "Yellow":
			return Color.yellow;
		case "Green":
			return Color.green;
		case "Blue":
			return Color.blue;
		case "Black":
			return Color.black;
		case "Red":
			return Color.red;
		default:
			return Color.WHITE;
		}
	}

	public String getOrigin() {
		return origin;
	}
	
	public String getTarget() {
		return target;
	}
	
}
