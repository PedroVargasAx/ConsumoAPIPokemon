package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Elige una opcion:");
        System.out.println("1. Busqueda Aleatoria: ");
        System.out.println("1. Busqueda por nombre: ");
        System.out.print("Seleccion: ");

        int seleccion = scanner.nextInt();

        if(seleccion == 1){
            int rndid = getRandomId();
            String url = API_URL + rndid;
            System.out.println(url);
            String pokeData = obtenerDatosAPI(url);
            System.out.println("Id Aleatorio seleccionado: " + rndid);
            System.out.println("Datos obtenidos por id aleatorio");
            System.out.println(pokeData);

        } else if (seleccion == 2) {
            scanner.nextLine();
            System.out.println("Ingrese el nombre del Pokemon");
            String nombre = scanner.nextLine();
            String url = API_URL + nombre;
            String pokemonData = obtenerDatosAPI(url);
            System.out.println("Datos obtenidos por nombre");
        }else {
            System.out.println("opcion invalida");
        }

        scanner.close();

    }

    private static int getRandomId() {
        Random random = new Random();
        return random.nextInt(150)+1;
    }

    private static String obtenerDatosAPI(String urlString){
        try{
            URL url = new URL(urlString);
            //URLConnection openConnection = new URL(url).openConnection();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();
            conn.disconnect();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}