package eduburner.test.json;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.testng.Assert;

import com.google.gson.Gson;

public class GsonTest {
	//@Test
	public void testSingleThread() {
		Gson gson = new Gson();
		MyObj myObj = new MyObj("hello", "world", 42);
		for (int i = 0; i < 10; i++) {
			gson.toJson(myObj);
		}
	}

	//@Test
	public void testMultiThread() throws InterruptedException {
		final Gson gson = new Gson();

		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch finishedLatch = new CountDownLatch(10);
		final AtomicBoolean failed = new AtomicBoolean(false);

		ExecutorService executor = Executors.newFixedThreadPool(10);

		for (int taskCount = 0; taskCount < 10; taskCount++) {
			executor.execute(new Runnable() {
				public void run() {
					MyObj myObj = new MyObj("hello", "world", 42);
					try {
						startLatch.await();
						for (int i = 0; i < 10; i++) {
							gson.toJson(myObj);
						}
					} catch (Throwable t) {
						failed.set(true);
					} finally {
						finishedLatch.countDown();
					}
				}
			});
		}

		startLatch.countDown();
		finishedLatch.await();
		Assert.assertFalse(failed.get());
	}

	private class BooleanWrapper {
		public boolean failed;
	}

	private class MyObj {
		private String a;
		private String b;
		private int i;

		public MyObj() {
		}

		public MyObj(String a, String b, int i) {
			this.a = a;
			this.b = b;
			this.i = i;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}
}
