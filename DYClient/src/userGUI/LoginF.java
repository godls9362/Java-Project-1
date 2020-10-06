package userGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ClientStart.ClientStart;

public class LoginF extends JFrame implements ActionListener {
	private MainFrame mainF = null;
	private JoinF jf = null;
	// client
	String serverip = "localhost";
	String serverport = "6666";
	// Label
	Label Title = new Label("          ");
	Label ID = new Label("  ID         ");
	Label PWD = new Label("비밀번호");
	Label personal = new Label(" '  Personal   Training Club  ' ");

	ImageIcon img = new ImageIcon("img/logo.png");
	JLabel imageLabel = new JLabel(img);

	JButton logo = new JButton("img/logo.png");
	// Text
	JTextField Idtext = new JTextField(10);
	JPasswordField pwdtext = new JPasswordField(10);
	JTextField Center = new JTextField();

	// Button
	JButton east1 = new JButton("로그인");
	JButton east2 = new JButton("회원가입");

	// Center Panel

	Panel center_panel = new Panel();
	Panel center_north_panel = new Panel();
	Panel center_north_panel_sub1 = new Panel();
	Panel center_north_panel_sub2 = new Panel();
	Panel center_north_panel_sub3 = new Panel();
	Panel center_center_panel = new Panel();
	Panel center_center_panel_sub = new Panel();
	Panel center_south_panel = new Panel();
	Panel center_south_panel_sub = new Panel();

	// ClientStart와 데이터를 주고 받기 위한 참조변수
	ClientStart myClient = null;

	public LoginF(ClientStart c) {

		this.myClient = c;
		this.setLayout(new BorderLayout());

		// Personal Training
		personal.setFont(new Font("맑은고딕", Font.BOLD, 20));
		center_north_panel_sub1.add(personal);
		// center_north_panel_sub1.add(program);
		personal.setBounds(200, 100, 100, 100);
		// id
		center_north_panel_sub2.add(ID);
		center_north_panel_sub2.add(Idtext);
		// pwd
		center_north_panel_sub3.add(PWD);
		center_north_panel_sub3.add(pwdtext);

		//
		center_north_panel.setLayout(new BoxLayout(center_north_panel, BoxLayout.Y_AXIS));
		center_north_panel.add(center_north_panel_sub1);
		center_north_panel.add(center_north_panel_sub2);
		center_north_panel.add(center_north_panel_sub3);

		east1.setBackground(Color.LIGHT_GRAY);
		east2.setBackground(Color.LIGHT_GRAY);
		east1.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		east2.setFont(new Font("맑은고딕", Font.PLAIN, 12));

		east1.addActionListener(this);
		east2.addActionListener(this);

		center_south_panel_sub.add(east1);
		center_south_panel_sub.add(east2);
		center_south_panel.setLayout(new FlowLayout());
		center_south_panel.add(center_south_panel_sub);

		center_panel.setLayout(new BorderLayout());
		center_panel.add(center_north_panel, "North");
		center_panel.add(center_south_panel, "Center");
		center_panel.setBackground(Color.pink);
		// imageLabel.setSize(4, 4);
		// center_panel.add(imageLabel);
		logo.setSize(20, 20);

		this.add(Title);
		this.add(center_panel, "Center");
		this.setTitle("LOGIN");

		this.setBounds(300, 150, 600, 230);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(east1)) {
			String id = Idtext.getText();
			String pwd = pwdtext.getText();
			loginScreen(id, pwd);
		} else if (e.getSource().equals(east2)) {
			JoinScreen();
		}
	}

	public void JoinScreen() {
		jf = new JoinF(myClient);
		myClient.setJoinF(jf);

	}

	public void close() {
		this.dispose();
		String id=Idtext.getText();
		mainF = new MainFrame(id,myClient);
		myClient.setMainF(mainF);
	}

	public void loginScreen(String id, String pwd) {
		myClient.send("@LoginChk/" + id + "/" + pwd);
	}

	public void receivedata(String msgFromServer) {  
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (msgFromServer.equals("@로그인성공!")) {
					JOptionPane.showMessageDialog(null, "You are on the right track. ");
					close();
				} else if(!msgFromServer.equals("@로그인성공!")) {
					JOptionPane.showMessageDialog(null, "Check your ID or Password.");
					
				}
			}

		}).start();
	}

}