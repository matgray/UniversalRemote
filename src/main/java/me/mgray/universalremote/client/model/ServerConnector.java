package me.mgray.universalremote.client.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerConnector {
    private int port;
    private String hostname;
    private Connection serverConnection = null;
    private Executor socketListener = Executors.newSingleThreadExecutor();

    public static ServerConnector createNew() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Client-Configuration.xml");
        return (ServerConnector) context.getBean("serverConnector");
    }

    public void connect() {
        try {
            serverConnection = new Connection(new Socket(hostname, port));
            socketListener.execute(new CommandListener(serverConnection));
        } catch (UnknownHostException e) {
            System.err.println(String.format("Don't know about host: %s", hostname));
            System.exit(1);
        } catch (IOException e) {
            System.err.println(String.format("Couldn't get I/O for the connection to: %s:%d", hostname, port));
            System.exit(1);
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
