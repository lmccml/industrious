package concurrent;

import concurrent.assitpackage.AttachmentProcessor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProducerConsumerForAttachment {
	//more https://github.com/LMAX-Exchange/disruptor
	public static void main(String[] args) {
		AttachmentProcessor ap = new AttachmentProcessor();
		ap.init();
		InputStream in = new ByteArrayInputStream("Hello".getBytes());
		try {
			ap.saveAttachment(in, "000887282", "测试文档");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
