package userGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ClientStart.ClientStart;

public class JoinF extends Frame implements ActionListener {
	LoginF loginF = null;
	ClientStart myclient = null;

	JLabel jlb = new JLabel("");
	Label joinUs = new Label("회원가입"); // 회원가입제목

	Label id = new Label("아이디 : ", JLabel.CENTER);// 아이디
	JTextField Idtext = new JTextField(12);
	Label idSpeak = new Label("*4~10자 영어대소문자만 사용가능합니다. ");

	Label pwd = new Label("비밀번호 :", JLabel.CENTER); // 비밀번호
	Label pwdSpeak = new Label("*4~8자 특수문자(~!@#$%^&*) 중 하나 포함되어야합니다.");
	JPasswordField pwdText = new JPasswordField(10);

	// etc
	Label kg = new Label("현재 몸무게 :", JLabel.CENTER);
	JTextField kgtext = new JTextField(10);
	Label cm = new Label("신장 :");
	JTextField cmtext = new JTextField(10);

	JButton join = new JButton("회원가입");
	JButton exit = new JButton("취소");
	JButton duplicate = new JButton("중복확인");
	JButton PWdChk = new JButton("비밀번호확인");
	// panel
	Panel center_panel = new Panel();
	Panel center_center_panel = new Panel();
	Panel center_center_sub1 = new Panel();
	Panel join_speak_panel = new Panel();
	Panel pwd_speak_panel = new Panel();
	Panel center_center_sub2 = new Panel();
	Panel center_center_sub3 = new Panel();
	Panel center_center_sub4 = new Panel();
	Panel center_center_sub5 = new Panel();
	Panel box = new Panel();

	public JoinF(ClientStart m) {
		this.myclient = m;
		// 객체의 위치를 받아서 저장한다.

		join.addActionListener(this);
		exit.addActionListener(this);
		duplicate.addActionListener(this);
		PWdChk.addActionListener(this);

		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

		// id.setBounds(50, 330, 300, 45);
		// Idtext.setBounds(60, 330, 300, 45);
		// i/dSpeak.setBounds(70, 330, 300, 45);

		// center_center_sub1.setLayout(new FlowLayout());
		center_center_sub1.add(id);
		center_center_sub1.add(Idtext);
		duplicate.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		center_center_sub1.add(duplicate);
		// center_center_sub1.add(idSpeak);

		box.add(center_center_sub1);
		join_speak_panel.add(idSpeak);
		box.add(join_speak_panel);

		// center_center_sub2.setLayout(new FlowLayout());
		center_center_sub2.add(pwd);
		center_center_sub2.add(pwdText);
		PWdChk.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		center_center_sub2.add(PWdChk);
		// center_center_sub2.add(pwdSpeak);
		box.add(center_center_sub2);

		pwd_speak_panel.add(pwdSpeak);
		box.add(pwd_speak_panel);

		// center_center_sub3.setLayout(new FlowLayout());
		center_center_sub3.add(kg);
		center_center_sub3.add(kgtext);
		box.add(center_center_sub3);

		// center_center_sub4.setLayout(new FlowLayout());
		center_center_sub4.add(cm);
		center_center_sub4.add(cmtext);
		box.add(center_center_sub4);

		join.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		exit.setFont(new Font("맑은고딕", Font.PLAIN, 12));
		center_center_sub5.add(join);
		center_center_sub5.add(exit);
		box.add(center_center_sub5);
		box.setBackground(Color.pink);
		center_center_sub5.setSize(200, 200);

		center_center_panel.add(joinUs);
		joinUs.setFont(new Font("맑은고딕", Font.BOLD, 50));
		center_center_panel.setBackground(Color.pink);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		this.add(center_center_panel, "North");
		this.add(box, "Center");
		this.add(center_center_sub5, "South");
		this.setBounds(100, 50, 800, 600);
		this.setVisible(true);
		this.setTitle("JOIN");
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 액션 시
		// TODO Auto-generated method stub
		if (e.getSource().equals(duplicate)) {
			String tempid = Idtext.getText();
			chkDu(tempid);
		} else if (e.getSource().equals(PWdChk)) {
			String tempPW = pwdText.getText();
			chkPW(tempPW);
		} else if (e.getSource().equals(join)) {
			joinScreen();
		} else if (e.getSource().equals(exit)) {
			this.dispose();
			loginF = new LoginF(myclient);

		}
	}

	private void joinScreen() {
		String sendId = Idtext.getText();
		String sendPwd = pwdText.getText();
		String sendcm = cmtext.getText();
		String sendkg = kgtext.getText();
		myclient.send("*Join/" + sendId + "/" + sendPwd + "/" + sendcm + "/" + sendkg);
	}

	private void chkPW(String tempPW) { // 안나옴 뭔데 !!!!!!!!!!!!!
		String wordChk = "~!@#$%^&*";
		int cnt = 0;
		for (int i = 0; i < tempPW.length(); i++) {
			char nowIndex = tempPW.charAt(i);
			for (int j = 0; j < wordChk.length(); j++) {
				char cutIndex = wordChk.charAt(j);
				if (nowIndex == cutIndex) {
					cnt = cnt + 1;
				}
			}
			if (cnt > 0) {
				break;
			}
		}
		if (cnt > 0) {
			JOptionPane.showMessageDialog(null, "사용가능한 비밀번호입니다.");
		} else {
			JOptionPane.showMessageDialog(null, "비밀번호에는 반드시 특수문자가 들어가야합니다.");
		}
	}

	private void chkDu(String tempid) { 
		myclient.send("*DuChk/" + tempid);
	}

	public void receiveData(String msgFromServer) {
		System.out.println("rec: " + msgFromServer);
		if (msgFromServer.equals("*중복")) {
			JOptionPane.showMessageDialog(null, "아이디가 중복됩니다.");
		} else if (msgFromServer.equals("*사용가능")) {
			JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.");
		} else if (msgFromServer.equals("*ok")) {
			JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
			this.dispose();

		}
	}
}
