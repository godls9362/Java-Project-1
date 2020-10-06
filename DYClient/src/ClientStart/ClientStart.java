package ClientStart;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import userGUI.JoinF;
import userGUI.LoginF;
import userGUI.MainFrame;
import userGUI.StartF;

public class ClientStart {
	private OutputStream sendMsg;
	private InputStream reMsg;
	private Socket client=null;
	private LoginF loginF =null;
	private JoinF joinF=null;
	private MainFrame mainF = null;
	private StartF start=null;
	ClientStart(){
		//loginF=new LoginF(this);
		start=new StartF(this);
		clientSet("안녕");
	}
	public void clientSet(String msg) {
		init("localhost", Integer.valueOf("6666")); //ip,port 넘겨주기 
		streamSet();
		
		receive(); // 서버로 부터 메시지를 받는 메서드
		//streamSet(userid);
		//mainF = new MainFrame(this);
		//mainF.setUser(userid);
		// send(); // 서버에게 메시지를 보낼 메서드
	}
	private void init(String serverIP, int serverPort) {
		try {
			client = new Socket(serverIP, serverPort);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void receive() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						reMsg = client.getInputStream();
						System.out.println("서버 응답 기다리는 중");
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msgFromServer = new String(reBuffer);
						msgFromServer = msgFromServer.trim();
						System.out.println("ClientStart: " + msgFromServer);
						if(msgFromServer.charAt(0)=='*') {
							joinF.receiveData(msgFromServer);
						} else if(msgFromServer.charAt(0)=='#') {
							mainF.receiveData(msgFromServer);
						} else if(msgFromServer.charAt(0)=='@') {
							loginF.receivedata(msgFromServer);
						}
						
						//있다 없다 보내고 유효성 판단 
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();

	}
	public void send(String msg) { //서버에게 메세지 보내기 
		// sendMsg = client.getOutputStream();
		try {
			//sendMsg.write((userid+"/").getBytes());
			sendMsg.write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void streamSet() {
		try {
			reMsg = client.getInputStream();
			byte[] reBuffer = new byte[100];
			reMsg.read(reBuffer);

			String msgFromServer = new String(reBuffer);
			msgFromServer = msgFromServer.trim();
			System.out.println(msgFromServer);

			// outputStream -> socket을 통해 상대방에게 보내는 코드 시작
			// 서버로 부터 환영의 메시지를 받으면 자신이 사용할 id를 입력한다
			String wait="*대기";
			sendMsg = client.getOutputStream();
			sendMsg.write(wait.getBytes());
			System.out.println(wait+"전송완료!");
			// outputStream -> socket을 통해 상대방에게 보내는 코드 끝

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setJoinF(JoinF joinF) {
		this.joinF = joinF;
	}
	public void setMainF(MainFrame mainF) {
		this.mainF = mainF;
	}
	public void setLoginF(LoginF lf) {
		this.loginF=lf;
	}
}
