package io.github.bfox1.f1logger.management;

import io.github.bfox1.f1logger.data.Storage;

import java.util.LinkedList;

/**
 * The Application in which is being listened to
 */
public class Application
{
    private final LinkedList<String> stringArray;

    private final Worker worker;

    private final Storage s;

    private final String name;

    private boolean lastCycle = false;

    public Application(String name, Storage s)
    {
        this.stringArray = new LinkedList<>();
        this.worker = new Worker();
        this.s = s;
        this.name = name;
    }

    public void saveData()
    {
        s.saveApplication(this);
    }

    private void saveDataAndClose()
    {

    }

    public LinkedList<String> getStringArray() {
        return stringArray;
    }

    public Worker getWorker() {
        return worker;
    }

    public String getName() {
        return name;
    }

    public Storage getS() {
        return s;
    }

    public boolean add(String s)
    {
        this.stringArray.add(s);
        this.lastCycle = true;

        return true;
    }

    public boolean isLastCycle() {
        return lastCycle;
    }

    public void setLastCycle(boolean lastCycle)
    {
        this.lastCycle = lastCycle;
    }
}
