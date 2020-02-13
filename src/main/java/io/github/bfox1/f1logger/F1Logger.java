package io.github.bfox1.f1logger;

import io.github.bfox1.f1logger.data.Storage;
import io.github.bfox1.f1logger.management.Application;

import java.io.IOException;
import java.util.HashMap;

public class F1Logger
{
    private static F1Logger instance;
    private boolean state;

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
        new Storage().createPath("TestPath");
        Application testApp = new Application("testapp", new Storage());

        testApp.add("test");
        testApp.add("More Testing on this end.");
        testApp.add("Even More Testing.");
        this.applicationMap.put(testApp.getName(), testApp);
        instance = this;
    }

    private void start()  {
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
                        this.stop();
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
