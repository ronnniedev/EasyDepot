package logica;

import java.util.Scanner;

public class Pruebas {
	static Scanner keyboard = new Scanner(System.in);
	public static void main(String[] args) {
		
		GestorCorreo g = new GestorCorreo();
		System.out.println("Introduce tu correo: ");
		String receptor = keyboard.nextLine();
		
		
		int token = g.createEmail(receptor);
		
		System.out.println("Escribeme el token");
		int numero = keyboard.nextInt();
		
		for(int i = 0; i < 5;i++) {
			try {
				System.out.print(".");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.getMessage();
			}
			
		}
		
		if(token == numero) {
			System.out.println("Acertaste");
		}else {
			System.out.println("Fallaste");
		}
		
		

	}

}
