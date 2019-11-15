package pers.chxuan.jrpc.rpc;

public class JRpcFuture {

    private Object object;

    public synchronized void await(long timeout) {
        try {
            wait(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void wakeup() {
        notify();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
