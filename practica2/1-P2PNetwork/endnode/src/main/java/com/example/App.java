package com.example;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
        EndNode en = new EndNode("server.properties", 8000);
        en.start();
    }
}
