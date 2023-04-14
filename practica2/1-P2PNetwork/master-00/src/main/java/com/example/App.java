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
        MasterNode mn = new MasterNode();
        mn.start(9000);
    }
}
