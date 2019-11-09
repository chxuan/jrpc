package pers.chxuan.jrpc.entity;

public class NetworkMessage {

    private int totalLength = 0;

    private int serial = 0;

    private int messageClassNameLength = 0;

    private String messageClassName;

    private int contentLength = 0;

    private byte[] content;

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getMessageClassNameLength() {
        return messageClassNameLength;
    }

    public void setMessageClassNameLength(int messageClassNameLength) {
        this.messageClassNameLength = messageClassNameLength;
    }

    public String getMessageClassName() {
        return messageClassName;
    }

    public void setMessageClassName(String messageClassName) {
        this.messageClassName = messageClassName;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
