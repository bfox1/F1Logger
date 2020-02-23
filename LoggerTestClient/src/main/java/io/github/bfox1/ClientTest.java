package io.github.bfox1;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTest
{

    public static void main(String args[])
    {
        ClientTest test = new ClientTest();
    }


    public ClientTest()
    {
        Socket s = new Socket();
        System.out.println("Connecting to the server");

        JsonObject obj = new JsonObject();

        obj.addProperty("Name", "Test Application");

        System.out.println(obj);

        try
        {
            s.connect(new InetSocketAddress(InetAddress.getLocalHost(), 2229), 5000);

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

            oos.writeUTF(obj.getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
