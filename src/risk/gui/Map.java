package risk.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import risk.rmi.BoardInter;

@SuppressWarnings("serial")
public class Map extends JPanel {

	private BufferedImage background;
	private ArrayList<String> terrnames=new ArrayList<String>();
	private ArrayList<Rectangle> squares=new ArrayList<Rectangle>();
	private ArrayList<JLabel> labels=new ArrayList<JLabel>();
	private BoardInter board;
	private boolean chosingOrigin=true;
	private String origin="", target="";
	/**
	 * Create the panel.
	 * @throws RemoteException 
	 */
	public Map(BoardInter board) throws RemoteException {
		this.board=board;
		//board.addObserver(this);
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
				for(int i=0; i<squares.size(); i++)
					if(squares.get(i).contains(arg0.getPoint())) {
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
		int width = 25, labelwidth = 30, height=20;

		//North America
		terrnames.add("Alaska");
		squares.add(new Rectangle(89,78,width,height));
		terrnames.add("Alberta");
		squares.add(new Rectangle(155,126,width,height));
		terrnames.add("Central America");
		squares.add(new Rectangle(152,292,width,height));
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
		squares.add(new Rectangle(139,205,width,height));

		//South America
		terrnames.add("Argentina");
		squares.add(new Rectangle(330,600,width,height));
		terrnames.add("Brazil");
		squares.add(new Rectangle(379,483,width,height));
		terrnames.add("Peru");
		squares.add(new Rectangle(314,510,width,height));
		terrnames.add("Venezuela");
		squares.add(new Rectangle(301,387,width,height));

		//Europe
		terrnames.add("Great Britain");
		squares.add(new Rectangle(581,144,width,height));
		terrnames.add("Iceland");
		squares.add(new Rectangle(555,99,width,height));
		terrnames.add("Northern Europe");
		squares.add(new Rectangle(678,150,width,height));
		terrnames.add("Scandinavia");
		squares.add(new Rectangle(681,94,width,height));
		terrnames.add("Southern Europe");
		squares.add(new Rectangle(713,189,width,height));
		terrnames.add("Ukraine");
		squares.add(new Rectangle(764,134,width,height));
		terrnames.add("Western Europe");
		squares.add(new Rectangle(623,189,width,height));

		//Africa
		terrnames.add("Congo");
		squares.add(new Rectangle(714,427,width,height));
		terrnames.add("East Africa");
		squares.add(new Rectangle(802,382,width,height));
		terrnames.add("Egypt");
		squares.add(new Rectangle(714,275,width,height));
		terrnames.add("Madagascar");
		squares.add(new Rectangle(830,527,width,height));
		terrnames.add("North Africa");
		squares.add(new Rectangle(609,324,width,height));
		terrnames.add("South Africa");
		squares.add(new Rectangle(716,531,width,height));

		//Asia
		terrnames.add("Afghanistan");
		squares.add(new Rectangle(893,185,width,height));
		terrnames.add("China");
		squares.add(new Rectangle(1061,245,width,height));
		terrnames.add("India");
		squares.add(new Rectangle(971,295,width,height));
		terrnames.add("Irkutsk");
		squares.add(new Rectangle(1065,135,width,height));
		terrnames.add("Japan");
		squares.add(new Rectangle(1244,224,width,height));
		terrnames.add("Kamchatka");
		squares.add(new Rectangle(1200,85,width,height));
		terrnames.add("Middle East");
		squares.add(new Rectangle(805,240,width,height));
		terrnames.add("Mongolia");
		squares.add(new Rectangle(1077,178,width,height));
		terrnames.add("Siam");
		squares.add(new Rectangle(1090,335,width,height));
		terrnames.add("Siberia");
		squares.add(new Rectangle(960,86,width,height));
		terrnames.add("Ural");
		squares.add(new Rectangle(877,105,width,height));
		terrnames.add("Yakutsk");
		squares.add(new Rectangle(1066,80,width,height));

		//Australia
		terrnames.add("Eastern Australia");
		squares.add(new Rectangle(1274,567,width,height));
		terrnames.add("Indonesia");
		squares.add(new Rectangle(1146,425,width,height));
		terrnames.add("New Guinea");
		squares.add(new Rectangle(1281,457,width,height));
		terrnames.add("Western Australia");
		squares.add(new Rectangle(1168,572,width,height));

		for(int i=0; i<squares.size(); i++) {
			JLabel label=new JLabel("0");
			label.setBounds((int)squares.get(i).getX(), (int)squares.get(i).getY(), labelwidth, height);
			labels.add(label);
			add(label);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
		try {
			drawSquares(g);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void drawSquares(Graphics g) throws RemoteException {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setColor(Color.BLUE);
		for(int i=0; i<squares.size(); i++) {
			g2d.setColor(choseColor(i));
			g2d.fill(squares.get(i));
			labels.get(i).setText(board.getTerrArmies(terrnames.get(i))+"");
			g2d.draw(squares.get(i));
		}
	}

	private Color choseColor(int i) throws RemoteException {
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

	public String notify(String phase) throws RemoteException {
		System.out.println("Aconteceu algo no map");
		repaint();
		
		return null;
	}

}
