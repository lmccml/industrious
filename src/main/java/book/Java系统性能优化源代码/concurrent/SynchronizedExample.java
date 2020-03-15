package book.Java系统性能优化源代码.concurrent;
public class SynchronizedExample {

   Object lock = new Object();

  public static synchronized void getStatus(){

  }

  public void updateStatus(){
    synchronized(lock){

    }
  }


  public synchronized void queryStatus(){

  }

}
