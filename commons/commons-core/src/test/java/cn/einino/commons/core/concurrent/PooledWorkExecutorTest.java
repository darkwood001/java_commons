package cn.einino.commons.core.concurrent;

import java.util.concurrent.Executors;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.einino.commons.core.concurrent.future.Future;
import cn.einino.commons.core.concurrent.future.FutureListener;

public class PooledWorkExecutorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		final WorkExecutor executor = new PooledWorkExecutor(Executors.newCachedThreadPool());
		final Object obj = new Object();
		Worker<Integer> worker = new Worker<Integer>() {

			@Override
			public Integer work() throws Exception {
				Worker<Integer> w1 = new Worker<Integer>() {

					@Override
					public Integer work() throws Exception {
						return new Integer(13);
					}
				};
				Worker<Integer> w2 = new Worker<Integer>() {

					@Override
					public Integer work() throws Exception {
						return new Integer(24);
					}
				};
				Future<Integer> f1 = executor.execute(w1);
				Future<Integer> f2 = executor.execute(w2);
				Integer v1 = f1.get();
				Integer v2 = f2.get();
				return new Integer(v1 + v2);
			}
		};
		executor.execute(worker, new FutureListener<Integer>() {

			@Override
			public void onValue(Integer value) {
				System.out.println("Listener value: " + value);
				synchronized (obj) {
					obj.notify();
				}
			}

			@Override
			public void onException(Exception cause) {
				cause.printStackTrace();
			}
		});
		try {
			synchronized (obj) {
				obj.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
