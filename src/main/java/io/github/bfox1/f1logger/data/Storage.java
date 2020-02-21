package io.github.bfox1.f1logger.data;

import io.github.bfox1.f1logger.management.Application;

import java.io.*;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Storage
{

    private static String path = "F1Logger/Application";


    public Storage()
    {

    }

    /**
     *
     * @param applicationName
     * @throws IOException
     */
    public void createPath(String applicationName) throws IOException
    {
        File f = new File(path);
        if(!f.exists())
        {
            f.mkdirs();
        }
        f = new File(path, applicationName + ".txt");

        f.createNewFile();
    }

    public boolean saveApplication(Application app)  {

        LinkedList<String> finalList = new LinkedList<>();
        try
        {
            //createPath(app.getName());

            File file = new File(path, app.getName() + ".txt");

            if(file.exists())
            {
                BufferedReader r = new BufferedReader(new FileReader(file));

                finalList.addAll(r.lines().collect(Collectors.toList()));
            }
            finalList.addAll(app.getStringArray());
            BufferedWriter s = new BufferedWriter(new FileWriter(new File(path,app.getName() + ".txt")));



            for(String logs : finalList)
            {
                s.write(logs + "\r");
                System.out.println(logs);
            }
            s.close();
            return true;
        } catch (FileNotFoundException e)
        {

            e.printStackTrace();
            return false;
        } catch (IOException e)
        {

            e.printStackTrace();
            return false;
        }
    }
}
