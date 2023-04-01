package com.example.server;

public class CmdRunner {
    public void runCmd(String [] cmd){
        try{
            Runtime.getRuntime().exec(cmd);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String message) {
		System.out.println("");
		System.out.println(message);
	}
}
