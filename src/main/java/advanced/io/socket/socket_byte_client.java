package advanced.io.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class socket_byte_client {
    public static void main(String[] args) {
        BufferedReader sr = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        Socket socket = null;
        byte[] buf = new byte[1024];
        try {
            socket = new Socket("127.0.0.1", 9000);
            bos = new BufferedOutputStream(socket.getOutputStream());
            String project_path = System.getProperty("user.dir");
            bis = new BufferedInputStream(new FileInputStream(new File(project_path + "/file/test.txt")));
            int t = bis.available(), r = 0;
            while ((r = bis.read(buf)) > 0) {
                t -= r;
                bos.write(buf, 0 ,r);
                bos.flush();
            }
            //发送完成后，关闭输出流告知服务端
            socket.shutdownOutput();
            //读取服务端相应的内容
            sr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = sr.readLine();
            if("ok".equalsIgnoreCase(s)){
                System.out.println("successed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream_util.close(sr);
            stream_util.close(bis);
            stream_util.close(bos);
            stream_util.close(socket);
        }

    }
}
