package org.GUI;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PokemonSearchGUI extends JFrame {
    private JButton randomButton;
    private JButton searchButton;
    private JTextField inputField;

    private OkHttpClient httpClient;

    public PokemonSearchGUI() {
        setTitle("Pokemon Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        randomButton = new JButton("Random Pokemon");
        searchButton = new JButton("Search");
        inputField = new JTextField(20);

        httpClient = new OkHttpClient();

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int randomId = (int) (Math.random() * 150) + 1;
                String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + randomId;
                makeApiRequest(apiUrl);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                boolean isNumeric = userInput.matches("\\d+");
                String apiUrl;

                if (isNumeric) {
                    apiUrl = "https://pokeapi.co/api/v2/pokemon/" + userInput;
                } else {
                    apiUrl = "https://pokeapi.co/api/v2/pokemon/" + userInput.toLowerCase();
                }

                makeApiRequest(apiUrl);
            }
        });

        setLayout(new FlowLayout());
        add(randomButton);
        add(inputField);
        add(searchButton);
        pack();
        setVisible(true);
    }

    private void makeApiRequest(String apiUrl) {
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    displayPokemonDetails(jsonResponse);
                } else {
                    System.out.println("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("Request failed: " + e.getMessage());
            }
        });
    }

    private void displayPokemonDetails(String jsonResponse) {
        try {
            JSONObject pokemonData = new JSONObject(jsonResponse);
            String pokemonName = pokemonData.getString("name");

            JSONArray typesArray = pokemonData.getJSONArray("types");
            StringBuilder pokemonTypeBuilder = new StringBuilder();
            for (int i = 0; i < typesArray.length(); i++) {
                JSONObject typeObject = typesArray.getJSONObject(i);
                JSONObject typeData = typeObject.getJSONObject("type");
                String typeName = typeData.getString("name");
                pokemonTypeBuilder.append(typeName).append(" ");
            }
            final String pokemonType = pokemonTypeBuilder.toString(); // Hacer final o efectivamente final

            int pokemonHeight = pokemonData.getInt("height");
            int pokemonWeight = pokemonData.getInt("weight");

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new PokemonDetailsGUI(pokemonName, pokemonType, pokemonHeight, pokemonWeight);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PokemonSearchGUI();
            }
        });
    }
}
