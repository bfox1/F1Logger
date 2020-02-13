package io.github.bfox1.f1logger;

public class F1Logger
{
    private static F1Logger instance;
    private boolean state;
    public static void main(String args[])
    {
        F1Logger logger = new F1Logger();

        logger.start();
    }

    public F1Logger()
    {
        instance = this;
    }

    private void start()
    {
        while(state)
        {

        }
    }

}
