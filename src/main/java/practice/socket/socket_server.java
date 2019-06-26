package practice.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class socket_server implements Runnable{
    private ServerSocket serverSocket = null;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void start() throws Exception{
        serverSocket = new ServerSocket(9000);
        executorService.execute(this);
    }
    public void close(){
        if(serverSocket != null && !serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (Exception e) {
                log.error("error on close server socket", e);
            }
        }
    }

    @Override
    public void run() {
        Socket socket = null;
        try{
            while ((socket = serverSocket.accept()) != null){
                log.info("client{} connected", socket.getRemoteSocketAddress());
                executorService.execute(new processor(socket));

            }
        } catch (Exception e) {
            log.error("Error on process connection", e);
        }


    }

    public static void main(String[] args) {
        socket_server socket_server = new socket_server();
        BufferedReader br = null;
        try {
            socket_server.start();
            br = new BufferedReader(new InputStreamReader(System.in));
            String cmd = null;
            while((cmd = br.readLine()) != null){
                if("close".equalsIgnoreCase(cmd)){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            stream_util.close(br);
            socket_server.close();
        }
    }
}
