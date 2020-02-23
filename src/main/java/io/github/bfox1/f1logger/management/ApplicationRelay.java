package io.github.bfox1.f1logger.management;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ApplicationRelay is to handle Incoming requests to Log Applications.
 *
 * It will determine if any Threads are already listening for this Application and assign a worker to maintain the
 * Logs.
 */
public class ApplicationRelay extends Thread
{

    private final ServerSocket serverClientSocket;

    private boolean closeService;

    public ApplicationRelay(ServerSocket s)
    {
        this.serverClientSocket = s;

        this.closeService = false;
    }

    public void run()
    {
        while(!closeService)
        {
            try
            {
                System.out.println("Listening");
                System.out.println(InetAddress.getLocalHost());
                Socket s = serverClientSocket.accept();

                new EchoThread(s);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            boolean flag = true;

            System.out.println("Logging Services have been Closed");

            while(flag)
            {
                try
                {
                    Thread.sleep(5000);
                    System.out.println("Sleeping while services are closed.");

                    if(this.closeService)
                    {
                        flag = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.run();

            }
        }
    }

    public void closeService()
    {
        System.out.println("Closing Logging Services. Standby");
        //TODO: Need to close out ALL Applications that are currently Listening
        this.closeService = false;
    }

    public void openServices()
    {
        System.out.println("Opening Logging Services. Standby");
        //TODO: Do Prep testing to ensure everything will work.
        this.closeService = true;
    }
}
