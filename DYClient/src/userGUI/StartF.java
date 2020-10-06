package userGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import ClientStart.ClientStart;

public class StartF extends Frame implements ActionListener {
	private LoginF lf = null;
	ClientStart myClient = null;

	JLabel title =new JLabel("Find a real version of you",JLabel.CENTER);
	ImageIcon img = new ImageIcon("img/logo1.png");
	JLabel imageLabel = new JLabel(img);
	
	JButton memo=new JButton("Click here to start");
	
	
	Panel sub=new Panel();
	Panel sub2=new Panel();
	
	public StartF(ClientStart c) {
		this.myClient = c;
		
		
		title.setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 30));
		
		memo.setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 17));
		 
		sub.setBackground(Color.pink);
		memo.addActionListener(this);
		
		//this.add(title,"North");
		this.setBackground(Color.pink);
		this.add(title,"North");
		this.add(imageLabel,"Center");
		this.setBackground(Color.pink);
		this.add(memo,"South");
		this.setBackground(Color.pink);
		
		this.setBounds(300, 50, 600, 500);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(memo)) {
			LoginScreen();
			this.dispose();
		}
	}

	public void LoginScreen() {
		lf = new LoginF(myClient);
		myClient.setLoginF(lf);
	}
}
