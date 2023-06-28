package org.GUI;
import javax.swing.*;
import java.awt.*;

public class PokemonDetailsGUI extends JFrame {
    private JTable detailsTable;

    public PokemonDetailsGUI(String pokemonName, String pokemonType, int pokemonHeight, int pokemonWeight) {
        setTitle("Detalles de Pokemon: " + pokemonName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Nombre", "Tipo", "Altura", "Peso"};
        Object[][] data = {
                {pokemonName, pokemonType, pokemonHeight, pokemonWeight}
        };

        detailsTable = new JTable(data, columnNames);

        setLayout(new BorderLayout());
        add(new JScrollPane(detailsTable), BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
}
