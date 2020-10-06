package ClientStart;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import userGUI.ChatF;

public class ChatClient {
	private OutputStream sendMsg;
	private InputStream reMsg;
	private Socket client = null;
	private ChatF chat = null;
	private String id = null;

	public ChatClient(String myid) {
		chat = new ChatF(this, id);
		id = myid;
		clientSet("�ȳ�" + id);
	}

	private void clientSet(String string) {
		init("localhost", 6667); // ip,port �Ѱ��ֱ�
		streamSet();
		System.out.println("��Ʈ�� ��");
		receive(); // ������ ���� �޽����� �޴� �޼���
	}

	private void receive() {

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
						chat.receiveData(msgFromServer);

						// �ִ� ���� ������ ��ȿ�� �Ǵ�
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();

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
			sendMsg = client.getOutputStream();
			sendMsg.write(id.getBytes());
			System.out.println(id + "���ۿϷ�!");
			// outputStream -> socket�� ���� ���濡�� ������ �ڵ� ��

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init(String serverIP, Integer serverPort) {
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

	public void send(String msg) { // �������� �޼��� ������
		try {
			System.out.println(msg);
			sendMsg.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
