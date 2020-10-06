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
		clientSet("�ȳ�");
	}
	public void clientSet(String msg) {
		init("localhost", Integer.valueOf("6666")); //ip,port �Ѱ��ֱ� 
		streamSet();
		
		receive(); // ������ ���� �޽����� �޴� �޼���
		//streamSet(userid);
		//mainF = new MainFrame(this);
		//mainF.setUser(userid);
		// send(); // �������� �޽����� ���� �޼���
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
						System.out.println("���� ���� ��ٸ��� ��");
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
						
						//�ִ� ���� ������ ��ȿ�� �Ǵ� 
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();

	}
	public void send(String msg) { //�������� �޼��� ������ 
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

			// outputStream -> socket�� ���� ���濡�� ������ �ڵ� ����
			// ������ ���� ȯ���� �޽����� ������ �ڽ��� ����� id�� �Է��Ѵ�
			String wait="*���";
			sendMsg = client.getOutputStream();
			sendMsg.write(wait.getBytes());
			System.out.println(wait+"���ۿϷ�!");
			// outputStream -> socket�� ���� ���濡�� ������ �ڵ� ��

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
