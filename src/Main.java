import com.Logger.*;

public class Main {

	public static void main(String[] args) throws Exception{
		Logger logger = Logger.CreateLogger(new ConsoleAppender());
		// logger.Log(LogLevel.info, "Hello world");

		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				int n = 10;
				while (n-- != 0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.Log(LogLevel.info, "Hello World 1");
					//System.out.println("Done Logging 1");
				}
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				int n = 10;
				while (n-- != 0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.Log(LogLevel.info, "Hello World 2");
					//System.out.println("Done logging 2");
				}
			}
		});

		thread1.start();
		thread2.start();
		
		//Thread.currentThread().join();
	}

}
