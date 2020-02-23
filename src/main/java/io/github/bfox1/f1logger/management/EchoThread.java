package io.github.bfox1.f1logger.management;

import com.google.gson.JsonObject;
import io.github.bfox1.f1logger.F1Logger;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

/**
 * Echo Threads Design in to determine if incoming information is already being listened for in Other Threads. .
 */
public class EchoThread extends Thread
{

    private final Socket clientSocket;

    private  ObjectOutputStream oos;

    private  ObjectInputStream ois;


    public EchoThread(Socket s)
    {
        this.clientSocket = s;
        JsonInterpret();
    }

    private void JsonInterpret()
    {
        try
        {
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
            this.ois = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println(ois.readUTF());
            if(ois.readObject() instanceof JsonObject)
            {
                JsonObject jsonObject = (JsonObject)ois.readObject();
                System.out.println(jsonObject.size());
                System.out.println(jsonObject.get("Name").getAsString());

                boolean flag = F1Logger.getInstance().hasApplication(jsonObject.get("Name").getAsString());

                if(flag)
                {
                    System.out.println("Application already Listening");
                    //TODO: Send response back to Client letting them know this sender is redundant.
                }
                else
                {
                    F1Logger.getInstance().addApplication(jsonObject.get("Name").getAsString());
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
