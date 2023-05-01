package com.example;

import org.springframework.boot.SpringApplication;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        EndNode en = new EndNode();
        SpringApplication.run(en.getClass(), args);
        en.start();
        en.search();
    }
}
