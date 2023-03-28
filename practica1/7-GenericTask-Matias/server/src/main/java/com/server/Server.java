package com.server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


public class Server {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            System.out.println("Servidor escuchando en el puerto 8080");
            server.createContext("/ejecutarTareaRemota", new RequestHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Staff staff = new Staff();
        // staff.setName("seba");
        // staff.setAge(24);
        // staff.setPosition(new String[]{"estudiante", "desarrollador", "tester"});

        // String jsonStaff = gson.toJson(staff);
        // System.out.println ("STAFF OBJECT: "+jsonStaff);

        // Staff staff2 = gson.fromJson(jsonStaff, Staff.class);
        // staff.setName("facu1");
        // System.out.println ("STAFF OBJECT 2 -name: "+staff2.getName());
        // String jsonStaff2 = gson.toJson(staff);
        // System.out.println ("STAFF OBJECT 2 : "+jsonStaff2);
        //App application = new App();

    }

    
}