package io.github.bfox1.f1logger;

import io.github.bfox1.f1logger.data.Storage;
import io.github.bfox1.f1logger.management.Application;
import io.github.bfox1.f1logger.management.ApplicationRelay;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;

public class F1Logger
{
    private static F1Logger instance;
    private boolean state;

    private final ApplicationRelay relay;

    private final HashMap<String, Application> applicationMap;

    private int counter = 10;

    public static void main(String args[]) throws Exception
    {
        F1Logger logger = new F1Logger();

        logger.start();
    }

    public F1Logger() throws IOException {
        this.state = true;
        this.applicationMap = new HashMap<>();
        /*new Storage().createPath("TestPath");
        Application testApp = new Application("testapp", new Storage());

        testApp.add("test");
        testApp.add("More Testing on this end.");
        testApp.add("Even More Testing.");
        this.applicationMap.put(testApp.getName(), testApp);*/

        this.relay = new ApplicationRelay(new ServerSocket(2229));
        this.relay.start();
        instance = this;
    }

    public static F1Logger getInstance()
    {
        return instance;
    }

    /**
     * Checks if application is currently being listened for.
     * @param s The name of the application
     * @return
     */
    public boolean hasApplication(String s)
    {
        return applicationMap.containsKey(s);
    }

    public Application getApplication(String s)
    {
        return applicationMap.get(s);
    }

    public void addApplication(String s)
    {
        this.applicationMap.put(s, new Application(s, new Storage()));
    }

    private void start()
    {
        while(state)
        {
            counter--;

            if(counter == 0)
            {
                counter = 10;
                for (Application a : applicationMap.values())
                {
                    if(a.isLastCycle())
                    {
                        a.setLastCycle(false);
                        a.saveData();
                        //this.stop();
                    }
                }
            }
        }
    }

    private void stop()
    {
        this.state = false;
    }

}
