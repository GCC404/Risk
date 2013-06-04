package risk.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AddPlayer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String[] colors={"Pink", "Yellow", "Green", "Blue", "Black", "Red"};
	private ArrayList<String> playerscolors=new ArrayList<String>();
	private InvalidPlayer invalidplayer=new InvalidPlayer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddPlayer dialog = new AddPlayer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddPlayer() {
		setBounds(100, 100, 228, 173);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 202, 84);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel colorLabel = new JLabel("Color:");
			colorLabel.setBounds(10, 11, 46, 14);
			contentPanel.add(colorLabel);
		}

		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(colors));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(49, 8, 124, 20);
		contentPanel.add(comboBox);
		{
			JButton btnRemove = new JButton("Remove");
			btnRemove.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("del "+comboBox.getSelectedItem());
					playerscolors.remove((String)comboBox.getSelectedItem());
				}
			});
			btnRemove.setBounds(10, 50, 84, 23);
			contentPanel.add(btnRemove);
		}
		{
			JButton okButton = new JButton("Add");
			okButton.setBounds(104, 50, 69, 23);
			contentPanel.add(okButton);
			okButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					if(playerscolors.contains(comboBox.getSelectedItem()) ||
							playerscolors.size()>6) {
						invalidplayer.setVisible(true);
						return;
					}
					
					playerscolors.add((String)comboBox.getSelectedItem());
					System.out.println("add "+comboBox.getSelectedItem());
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(10, 95, 192, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton closeButton = new JButton("Close");
				closeButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						setVisible(false);
					}
				});
				closeButton.setActionCommand("Close");
				buttonPane.add(closeButton);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getColors() {
		return (ArrayList<String>) playerscolors.clone();
	}
}
