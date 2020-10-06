package userGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ClientStart.ChatClient;

public class ChatF extends Frame implements ActionListener {
	ChatClient chatClient = null;
	String id=null;
	String opp=null;
	
	JLabel list = new JLabel("현재들어온 회원목록입니다.", JLabel.CENTER);
	List guestList = new List(10);
	JButton startChat = new JButton("채팅하기");

	JTextField south = new JTextField();
	TextArea south_ta = new TextArea(6, 4);
	Panel south_panel = new Panel();
	
	public ChatF(ChatClient chatClient, String id) {
		this.setTitle(id);
		this.chatClient = chatClient;
		this.id=id;
		list.setFont(new Font("맑은고딕", Font.BOLD, 20));

		south_panel.setLayout(new BorderLayout());
		south_panel.add(list, "North");
		south_panel.add(south_ta, "Center");
		south_panel.add(south, "South");

		south.addActionListener(this);
		startChat.addActionListener(this);

		this.setBackground(Color.pink);
		this.setLayout(new BorderLayout());
		this.add(list, "North");
		this.add(guestList, "Center");
		this.add(startChat, "South");
		this.setBounds(300, 150, 300, 500);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(south)) {
			commandToken();
			south.setText("");
		}else if(e.getSource().equals(startChat)) {
			opp=guestList.getSelectedItem();
			chatClient.send("!"+opp);
			chatScreen(opp);
		}
	}

	private void chatScreen(String opp) {
		
		list.setText(opp+"님과의 채팅을 시작합니다.");
		this.remove(guestList);
		this.remove(startChat);
		this.add(south,"South");
		this.add(south_ta,"Center");
		this.setVisible(true);
	}

	private void commandToken() {
		if(!south.getText().equals("")) {
			south_ta.append("ME :" +south.getText()+"\n");
			chatClient.send("*"+opp+"*"+south.getText());
		}
	}

	public void receiveData(String msgFromServer) {
		if(msgFromServer.charAt(0)=='#') {
			setIdList(msgFromServer);
		} else if(msgFromServer.charAt(0)=='!') {
			String recieveId = msgFromServer.substring(1);
			opp = recieveId;
			chatScreen(recieveId);
		} else if(msgFromServer.charAt(0)=='*') {
			StringTokenizer st = new StringTokenizer(msgFromServer, "*");
			String opp = st.nextToken();
			String Msg = st.nextToken();
			south_ta.append(opp+"님 :"+Msg+"\n");
		}else if(msgFromServer.charAt(0)=='&') {
			StringTokenizer st = new StringTokenizer(msgFromServer, "&");
			String opp = st.nextToken();
			guestList.add(opp);
		}
	}
	
	public void setIdList(String idList) {
		guestList.removeAll();
		StringTokenizer st=new StringTokenizer(idList, "#");
		while(st.hasMoreTokens()) {
			guestList.add(st.nextToken());
		}
	}

}
