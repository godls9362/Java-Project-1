package userGUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
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
import java.util.Timer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ClientStart.ChatClient;
import ClientStart.ClientStart;

public class MainFrame extends Frame implements ActionListener {
	ClientStart myclient = null;
	ChatClient chatClient =null;

	ImageIcon img = new ImageIcon("img/main.png");
	JLabel imageLabel = new JLabel(img);
	Panel center_img = new Panel();

	JLabel title = new JLabel("WE L♥VE YOU!", JLabel.CENTER);
	JLabel title2 = new JLabel("");
	Panel north_title = new Panel();

	ImageIcon img1 = new ImageIcon("img/workout.gif");
	JLabel imageLabel1 = new JLabel(img1);
	Panel center_img1 = new Panel();
	Timer timer = new Timer();

	ImageIcon img2 = new ImageIcon("img/workout2.gif");
	JLabel imageLabel2 = new JLabel(img2);
	Panel center_img2 = new Panel();

	Button west1 = new Button("운동하기 ");
	Button west2 = new Button("내 운동보기 ");
	Button west3 = new Button("채팅하기");

	JButton west4 = new JButton("시작");

	JTextField south = new JTextField();// 컴포넌트
	TextArea south_ta = new TextArea(6, 4);
	Panel south_panel = new Panel();

	Panel center_panel = new Panel(); // 기본파넬
	Panel west_panel = new Panel(); // 버튼들이 들어갈 파넬
	Panel center_center_panel = new Panel(); // 리스트 들어갈파넬
	Panel south_Panel = new Panel(); // 명령문
	Panel plain = new Panel();

	JLabel myList1 = new JLabel("You are doing a great job!", JLabel.CENTER);
	JLabel myList2 = new JLabel("KEEP GOING!", JLabel.CENTER);
	List mylist = new List(13);

	Panel south_sub_panel = new Panel();

	// 운동보기 //
	List dbWKlist = new List(13);
	JLabel listAll = new JLabel("see what we got and try it!");
	JLabel search = new JLabel("검색하기");
	JTextField searchtxt = new JTextField(10);
	JButton goSearch = new JButton("검색");
	JButton goStart = new JButton("시작");

	Panel North_Label_panel = new Panel();
	Panel South_search_panel = new Panel();
	Panel search_Center_Panel = new Panel();

	String myid = null;

	public MainFrame(String id, ClientStart myClient) {
		this.myclient = myClient;
		this.myid = id;
		title.setFont(new Font("맑은고딕", Font.BOLD, 40));
		title2.setText(myid + "님 안녕하세요!");
		title2.setFont(new Font("맑은고딕", Font.BOLD, 17));
		North_Label_panel.add(listAll);
		listAll.setFont(new Font("맑은고딕", Font.BOLD, 19));

		north_title.setLayout(new BorderLayout());
		north_title.add(title, "Center");
		north_title.add(title2, "South");

		South_search_panel.setLayout(new FlowLayout());
		South_search_panel.add(search);
		South_search_panel.add(searchtxt);
		South_search_panel.add(goSearch);
		South_search_panel.add(goStart);

		myList1.setFont(new Font("맑은고딕", Font.BOLD, 19));
		myList2.setFont(new Font("맑은고딕", Font.BOLD, 19));
		plain.setLayout(new BorderLayout());
		plain.add(myList1, "North");
		plain.add(mylist, "Center");
		plain.add(myList2, "South");

		search_Center_Panel.setLayout(new BorderLayout());
		search_Center_Panel.add(North_Label_panel, "North");
		search_Center_Panel.add(dbWKlist, "Center");
		search_Center_Panel.add(South_search_panel, "South");

		center_img.setLayout(new BorderLayout());
		center_img.add(imageLabel, "Center");

		center_img1.setLayout(new BorderLayout());
		center_img1.add(imageLabel1, "Center");

		center_img2.setLayout(new BorderLayout());
		center_img2.add(imageLabel2, "Center");

		west_panel.setLayout(new BoxLayout(west_panel, BoxLayout.Y_AXIS));
		west_panel.add(west1);
		west_panel.add(west2);
		west_panel.add(west3);

		west1.addActionListener(this);
		west2.addActionListener(this);
		west3.addActionListener(this);
		west4.addActionListener(this);
		goSearch.addActionListener(this);
		south.addActionListener(this);
		goStart.addActionListener(this);

		center_panel.setLayout(new BorderLayout());
		center_panel.add(center_center_panel, "Center");

		this.setBackground(Color.pink);

		this.setLayout(new BorderLayout());
		this.add(west_panel, "West");
		this.add(center_img, "Center");
		this.add(north_title, "North");
		this.setTitle("Personal Training");

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
		if (e.getSource().equals(west1)) {
			bringAll();
		} else if (e.getSource().equals(west2)) {
			lookDetails();
		} else if (e.getSource().equals(goSearch)) {
			String word = searchtxt.getText();
			searchScreen(word);
		} else if (e.getSource().equals(goStart)) {
			String msg = dbWKlist.getSelectedItem();
			valueAnalysis(msg);
		}else if(e.getSource().equals(west3)) {
			chatting();
		}
	}

	private void chatting() {
		chatClient=new ChatClient(myid);
		System.out.println("go");
	}

	private void lookDetails() {
		remove();
		this.add(plain, "Center");
		this.setVisible(true);
		myclient.send("#myWork/" + myid);
	}

	private void valueAnalysis(String msg) {
		StringTokenizer st = new StringTokenizer(msg, "/");
		String num = st.nextToken();
		if (num.equals("1")) {
			remove();
			this.add(center_img1, "Center");
			this.setVisible(true);
			myclient.send(("#doitWK/" + myid + "/" + msg));
		} else if (num.equals("41")) {
			remove();
			this.add(center_img2, "Center");
			this.setVisible(true);
			myclient.send(("#doitWK/" + myid + "/" + msg));
		}
	}

	private void searchScreen(String word) {
		myclient.send("#search/" + word);
	}

	private void bringAll() {
		// TODO Auto-generated method stub
		remove();
		this.add(search_Center_Panel, "Center");
		this.setVisible(true);
		myclient.send("#listAll");
	}

	private void remove() {
		this.remove(center_img);
		this.remove(search_Center_Panel);
		this.remove(plain);
		this.remove(center_img1);
		this.remove(center_img2);
	}

	public void receiveData(String msgFromServer) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (msgFromServer.indexOf("#") == 0) {
					StringTokenizer st = new StringTokenizer(msgFromServer, "/");
					String compare = st.nextToken(); // 첫번째 분석할것
					if (compare.equals("#listAll")) {
						String no = st.nextToken();
						String name = st.nextToken();
						String grade = st.nextToken();
						String kcal = st.nextToken();
						String part = st.nextToken();
						dbWKlist.removeAll();
						dbWKlist.add(no + "/" + name + "/ 난이도 :" + grade + "/ 예상소모칼로리 :" + kcal + "/ 운동효과부위 : " + part);
					} else if (compare.equals("#search")) {
						String no = st.nextToken();
						String name = st.nextToken();
						String grade = st.nextToken();
						String kcal = st.nextToken();
						String part = st.nextToken();
						dbWKlist.removeAll();
						dbWKlist.add(no + "/" + name + "/ 난이도 :" + grade + "/ 예상소모칼로리 :" + kcal + "/ 운동효과부위 : " + part);
					} else if (compare.equals("#myWork")) {
						String name=st.nextToken();
						String no=st.nextToken();
						String kcal=st.nextToken();
						String time=st.nextToken();
						mylist.removeAll();
						mylist.add("운동번호 : "+no+"/ 운동이름 :"+name+"/ 소모칼로리 : "+kcal+"/ 운동시간 : "+time);
					} 
				}
			}
		}).start();
	}

}
