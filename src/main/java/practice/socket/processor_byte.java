package practice.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class processor_byte implements Runnable {
    private Socket socket;

    public processor_byte(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        PrintWriter pw = null;
        byte[] buf = new byte[10240];
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream(), true);
            bos = new BufferedOutputStream(new FileOutputStream(new File("/users/lmc/Documents/test_result.txt")));
            int r =0, t = 0;
            while (!socket.isInputShutdown() && (r =bis.read(buf)) > 0){
                t += r;
                bos.write(buf, 0, r);
                bos.flush();
            }
            pw.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream_util.close(pw);
            stream_util.close(bis);
            stream_util.close(bos);
            stream_util.close(socket);
        }

    }
}
