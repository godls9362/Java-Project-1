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
	Label joinUs = new Label("ȸ������"); // ȸ����������

	Label id = new Label("���̵� : ", JLabel.CENTER);// ���̵�
	JTextField Idtext = new JTextField(12);
	Label idSpeak = new Label("*4~10�� �����ҹ��ڸ� ��밡���մϴ�. ");

	Label pwd = new Label("��й�ȣ :", JLabel.CENTER); // ��й�ȣ
	Label pwdSpeak = new Label("*4~8�� Ư������(~!@#$%^&*) �� �ϳ� ���ԵǾ���մϴ�.");
	JPasswordField pwdText = new JPasswordField(10);

	// etc
	Label kg = new Label("���� ������ :", JLabel.CENTER);
	JTextField kgtext = new JTextField(10);
	Label cm = new Label("���� :");
	JTextField cmtext = new JTextField(10);

	JButton join = new JButton("ȸ������");
	JButton exit = new JButton("���");
	JButton duplicate = new JButton("�ߺ�Ȯ��");
	JButton PWdChk = new JButton("��й�ȣȮ��");
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
		// ��ü�� ��ġ�� �޾Ƽ� �����Ѵ�.

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
		duplicate.setFont(new Font("�������", Font.PLAIN, 12));
		center_center_sub1.add(duplicate);
		// center_center_sub1.add(idSpeak);

		box.add(center_center_sub1);
		join_speak_panel.add(idSpeak);
		box.add(join_speak_panel);

		// center_center_sub2.setLayout(new FlowLayout());
		center_center_sub2.add(pwd);
		center_center_sub2.add(pwdText);
		PWdChk.setFont(new Font("�������", Font.PLAIN, 12));
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

		join.setFont(new Font("�������", Font.PLAIN, 12));
		exit.setFont(new Font("�������", Font.PLAIN, 12));
		center_center_sub5.add(join);
		center_center_sub5.add(exit);
		box.add(center_center_sub5);
		box.setBackground(Color.pink);
		center_center_sub5.setSize(200, 200);

		center_center_panel.add(joinUs);
		joinUs.setFont(new Font("�������", Font.BOLD, 50));
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
	public void actionPerformed(ActionEvent e) { // �׼� ��
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

	private void chkPW(String tempPW) { // �ȳ��� ���� !!!!!!!!!!!!!
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
			JOptionPane.showMessageDialog(null, "��밡���� ��й�ȣ�Դϴ�.");
		} else {
			JOptionPane.showMessageDialog(null, "��й�ȣ���� �ݵ�� Ư�����ڰ� �����մϴ�.");
		}
	}

	private void chkDu(String tempid) { 
		myclient.send("*DuChk/" + tempid);
	}

	public void receiveData(String msgFromServer) {
		System.out.println("rec: " + msgFromServer);
		if (msgFromServer.equals("*�ߺ�")) {
			JOptionPane.showMessageDialog(null, "���̵� �ߺ��˴ϴ�.");
		} else if (msgFromServer.equals("*��밡��")) {
			JOptionPane.showMessageDialog(null, "��밡���� ���̵��Դϴ�.");
		} else if (msgFromServer.equals("*ok")) {
			JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
			this.dispose();

		}
	}
}
