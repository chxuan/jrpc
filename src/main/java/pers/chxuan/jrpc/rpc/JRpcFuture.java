package pers.chxuan.jrpc.rpc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JRpcFuture {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private Object object;

    public void await(long timeout, TimeUnit unit) {
        try {
            countDownLatch.await(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notif() {
        countDownLatch.countDown();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
