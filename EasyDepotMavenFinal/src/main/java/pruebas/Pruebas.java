/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import persistencia.GestorCloudinary;



/**
 *
 * @author mario
 */
public class Pruebas {
    public static void main(String[] args) {
        GestorCloudinary cloud = new GestorCloudinary();
       List <String> nombres =  cloud.listAssets("Pepito");
         for(String palabra: nombres){
             System.out.println(palabra);
         }                            
    }
}
