package edu.ita.honorio.facerecognitionclient;

@SuppressWarnings("WeakerAccess")
public class ServerAddress {
    public String ip;
    public String port;

    public ServerAddress(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }
}
