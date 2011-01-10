import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DialogCommand extends JDialog implements ActionListener {

	private static final long serialVersionUID = -2216904258505429122L;

	public DialogCommand(
			JFrame _frame,
			String _head,
			ArrayList<String> _command,
			ArrayList<String> _note){
		super(_frame, true);
		setTitle("Mafiamanager");
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.anchor = GridBagConstraints.CENTER;
		con.insets = new Insets(0,0,5,0);
		
		con.gridwidth = GridBagConstraints.REMAINDER;
		con.gridy = 0;
		
		if (!_head.equals("")){
			JLabel label = new JLabel(_head);
			add(label, con);
			con.gridy++;
		}
		
		if (_command.size() != 0){
			for (String command : _command){
				JLabel label = new JLabel(command);
				label.setForeground(new Color(0,180,0));
				add(label, con);
				con.gridy++;
			}
		}
		
		if (_note.size() != 0){
			for (String note : _note){
				JLabel label = new JLabel(note);
				label.setFont(label.getFont().deriveFont(Font.PLAIN));
				add(label, con);
				con.gridy++;
			}
		}
		
		JButton buttonAcc = new JButton(Messages.getString("gui.ok"));
		buttonAcc.addActionListener(this);
		con.gridwidth = GridBagConstraints.REMAINDER;
		add(buttonAcc, con);
		
		pack();
		setLocationRelativeTo(_frame);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		setVisible(false);
	}

}
