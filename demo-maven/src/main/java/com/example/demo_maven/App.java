package com.example.demo_maven;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String branch = "*";
        for(int i =0; i < 6; i++) {
            System.out.println(branch);
        	branch += "*";
        }
     
    }
}
