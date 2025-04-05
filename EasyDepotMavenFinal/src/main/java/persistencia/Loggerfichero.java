/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;


/**
 *
 * @author Usuario
 */
public class Loggerfichero {
	
        private static Loggerfichero log ;
        BufferedWriter buffer;
        private Loggerfichero() {
            try {
                String rutafichero = System.getProperty("user.dir")+"\\Datos\\";
                String ficheroLog=rutafichero+"log.txt";
                // true añade al final
                buffer = new BufferedWriter(new FileWriter(ficheroLog,true));
            } catch (IOException ex) {
                System.out.println("ERROR al abrir fichero");
            }
        }


        public static Loggerfichero getInstance() {
        	// Si no existe el log se crea
            if(null == log) {
                log = new Loggerfichero();
            }
            return log;
        }

        public void writeSmg(String msg){
        	//añade la fecha y le mete el mensaje
            String linea = "\n"  +  LocalDateTime.now() + " - ";
            linea +=msg;
            try {
                buffer.write(linea);
            } catch (IOException ex) {
                System.out.println("ERROR");
            }
        }
        
        public void closeLog(){
            try {
                buffer.close();
            } catch (IOException ex) {
                System.out.println("ERROR");
            }
        }
}
