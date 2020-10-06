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
		clientSet("안녕" + id);
	}

	private void clientSet(String string) {
		init("localhost", 6667); // ip,port 넘겨주기
		streamSet();
		System.out.println("스트림 셋");
		receive(); // 서버로 부터 메시지를 받는 메서드
	}

	private void receive() {

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
						chat.receiveData(msgFromServer);

						// 있다 없다 보내고 유효성 판단
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

			// outputStream -> socket을 통해 상대방에게 보내는 코드 시작
			// 서버로 부터 환영의 메시지를 받으면 자신이 사용할 id를 입력한다
			sendMsg = client.getOutputStream();
			sendMsg.write(id.getBytes());
			System.out.println(id + "전송완료!");
			// outputStream -> socket을 통해 상대방에게 보내는 코드 끝

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

	public void send(String msg) { // 서버에게 메세지 보내기
		try {
			System.out.println(msg);
			sendMsg.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
