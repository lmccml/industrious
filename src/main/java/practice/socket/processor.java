package practice.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class processor implements Runnable {
    private Socket socket;

    public processor(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            while (!Thread.interrupted()){
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s = br.readLine();
                System.out.println(s + "{}" + socket.getRemoteSocketAddress());
                pw = new PrintWriter(socket.getOutputStream(), true);
                pw.write(s);
                if ("bye".equalsIgnoreCase(s)) {
                    break;
                }
            }


        } catch (Exception e) {
            log.error("Error on process command", e);
        }finally {
            stream_util.close(br);
            stream_util.close(pw);
            stream_util.close(socket);
        }

    }
}
