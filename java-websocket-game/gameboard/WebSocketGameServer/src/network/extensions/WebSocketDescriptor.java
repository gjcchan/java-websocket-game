package network.extensions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

public class WebSocketDescriptor extends SocketImpl {
	private Socket connection;
	
	
	public InetAddress getInetAddress() {
		return connection.getInetAddress();
	}


	public Object getOption(int arg0) throws SocketException {
		// TODO Auto-generated method stub
		return null;
	}


	public void setOption(int arg0, Object arg1) throws SocketException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void accept(SocketImpl arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected int available() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	protected void bind(InetAddress arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void close() throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void connect(String arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void connect(InetAddress arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void connect(SocketAddress arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void create(boolean arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void listen(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void sendUrgentData(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
