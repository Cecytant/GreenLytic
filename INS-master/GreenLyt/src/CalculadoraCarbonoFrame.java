import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Desktop;
import java.net.URI;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraCarbonoFrame extends JFrame {
    // Componentes
    private JLabel labelTitulo, labelSubtitulo;
    private JTextField textElectricidad, textTransporte, textGas, textAgua, textResiduos;
    private JButton calcularButton, limpiarButton, recomendacionesButton, instagramButton, exportarButton;
    private JTextArea textAreaResultado;
    private JList<String> historialList;
    private DefaultListModel<String> historialModel;
    private JPanel panel;

    // Colores
    private Color colorFondo = new Color(245, 255, 245);  // Verde pastel claro
    private Color colorBotonCalcular = new Color(34, 139, 34);  // Verde oscuro
    private Color colorBotonLimpiar = new Color(128, 0, 32);  // Color vino
    private Color colorBotonRecomendaciones = new Color(70, 130, 180);  // Azul
    private Color colorBotonInstagram = new Color(255, 105, 180);  // Rosa claro

    // Temas
    private String[] temas = {"Verde Pastel", "Azul Claro", "Amarillo Suave"};

    public CalculadoraCarbonoFrame() {
        // Configuración del JFrame
        setTitle("C.H.C - GREEN LYTIC");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana

        // Crear un panel principal y establecer el color de fondo
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(colorFondo);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));  // Espaciado

        // Configuración de GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título principal
        labelTitulo = new JLabel("Calculadora de Huella de Carbono");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        labelTitulo.setForeground(new Color(34, 139, 34));  // Verde oscuro
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(labelTitulo, gbc);

        // Subtítulo con el nombre de la empresa
        labelSubtitulo = new JLabel("GREEN LYTIC");
        labelSubtitulo.setFont(new Font("Arial", Font.PLAIN, 22));
        labelSubtitulo.setForeground(new Color(34, 139, 34));  // Verde oscuro
        gbc.gridy = 1;
        panel.add(labelSubtitulo, gbc);

        // Inicializar componentes para las entradas
        String[] labels = {"Electricidad (kWh):", "Transporte (km):", "Gas (m³):", "Agua (litros):", "Residuos (kg):"};
        JTextField[] textFields = {textElectricidad = new JTextField(), textTransporte = new JTextField(),
                textGas = new JTextField(), textAgua = new JTextField(),
                textResiduos = new JTextField()};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 2;
            gbc.gridwidth = 1;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(label, gbc);

            gbc.gridx = 1;
            textFields[i].setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(textFields[i], gbc);
        }

        // Botón de cálculo
        calcularButton = new JButton("Calcular");
        styleButton(calcularButton, colorBotonCalcular);
        gbc.gridx = 0;
        gbc.gridy = labels.length + 2;
        gbc.gridwidth = 1;
        panel.add(calcularButton, gbc);

        // Botón de limpiar
        limpiarButton = new JButton("Limpiar");
        styleButton(limpiarButton, colorBotonLimpiar);
        gbc.gridx = 1;
        panel.add(limpiarButton, gbc);

        // Botón de recomendaciones
        recomendacionesButton = new JButton("Recomendaciones");
        styleButton(recomendacionesButton, colorBotonRecomendaciones);
        gbc.gridx = 0;
        gbc.gridy = labels.length + 3;
        gbc.gridwidth = 2;
        panel.add(recomendacionesButton, gbc);

        // Área de texto para mostrar el resultado
        textAreaResultado = new JTextArea(8, 20);
        textAreaResultado.setEditable(false);
        textAreaResultado.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaResultado.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        textAreaResultado.setBackground(Color.WHITE);
        textAreaResultado.setForeground(new Color(34, 34, 34));  // Texto oscuro
        textAreaResultado.setLineWrap(true);
        textAreaResultado.setWrapStyleWord(true);
        gbc.gridx = 0;
        gbc.gridy = labels.length + 4;
        gbc.gridwidth = 2;
        panel.add(new JScrollPane(textAreaResultado), gbc);

        // Botón de Instagram
        instagramButton = new JButton("Síguenos en: ");
        instagramButton.setIcon(new ImageIcon("path/to/instagram_logo.png")); // Agrega la ruta de tu logo de Instagram
        instagramButton.setBackground(colorBotonInstagram);
        instagramButton.setForeground(Color.WHITE);
        instagramButton.setFocusPainted(false);
        styleButton(instagramButton, colorBotonInstagram);
        gbc.gridy = labels.length + 5;
        panel.add(instagramButton, gbc);

        // Botón de exportar
        exportarButton = new JButton("Exportar Resultados");
        styleButton(exportarButton, colorBotonRecomendaciones);
        gbc.gridy = labels.length + 6;
        panel.add(exportarButton, gbc);

        // Inicializar historial
        historialModel = new DefaultListModel<>();
        historialList = new JList<>(historialModel);
        JScrollPane historialScrollPane = new JScrollPane(historialList);
        historialScrollPane.setPreferredSize(new Dimension(400, 100));
        gbc.gridy = labels.length + 7;
        gbc.gridwidth = 2;
        panel.add(historialScrollPane, gbc);

        // ComboBox de temas
        JComboBox<String> temaComboBox = new JComboBox<>(temas);
        gbc.gridy = labels.length + 8;
        panel.add(temaComboBox, gbc);

        temaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTheme = (String) temaComboBox.getSelectedItem();
                switch (selectedTheme) {
                    case "Verde Pastel":
                        panel.setBackground(new Color(245, 255, 245));
                        break;
                    case "Azul Claro":
                        panel.setBackground(new Color(173, 216, 230));
                        break;
                    case "Amarillo Suave":
                        panel.setBackground(new Color(255, 255, 224));
                        break;
                }
            }
        });

        // Acción del botón Calcular
        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularHuellaCarbono();
            }
        });

        // Acción del botón Limpiar
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Acción del botón Recomendaciones
        recomendacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRecomendaciones();
            }
        });

        // Acción del botón de Instagram
        instagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirInstagram();
            }
        });

        // Acción del botón de exportar
        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarResultados();
            }
        });

        // Añadir el panel al JFrame
        add(panel);
    }

    // Método para calcular la huella de carbono
    private void calcularHuellaCarbono() {
        try {
            double electricidad = validarEntrada(textElectricidad.getText());
            double transporte = validarEntrada(textTransporte.getText());
            double gas = validarEntrada(textGas.getText());
            double agua = validarEntrada(textAgua.getText());
            double residuos = validarEntrada(textResiduos.getText());

            // Cálculo de la huella de carbono
            double huellaCarbono = (electricidad * 0.233) + (transporte * 0.21) + (gas * 2.2) + (agua * 0.005) + (residuos * 0.1);
            historialModel.addElement(String.format("Huella: %.2f kgCO2e", huellaCarbono));

            // Mostrar resultados en el área de texto
            textAreaResultado.setText(String.format("Tu huella de carbono total es: %.2f kgCO2e\n\n" +
                    "Desglose por categoría:\n" +
                    "Electricidad: %.2f kgCO2e\n" +
                    "Transporte: %.2f kgCO2e\n" +
                    "Gas: %.2f kgCO2e\n" +
                    "Agua: %.2f kgCO2e\n" +
                    "Residuos: %.2f kgCO2e", 
                    huellaCarbono, 
                    electricidad * 0.233, 
                    transporte * 0.21, 
                    gas * 2.2, 
                    agua * 0.005, 
                    residuos * 0.1));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        textElectricidad.setText("");
        textTransporte.setText("");
        textGas.setText("");
        textAgua.setText("");
        textResiduos.setText("");
        textAreaResultado.setText("");
    }

    // Método para mostrar recomendaciones
    private void mostrarRecomendaciones() {
        String recomendaciones = "Recomendaciones para reducir tu huella de carbono:\n" +
                "- Utiliza fuentes de energía renovable.\n" +
                "- Opta por transporte público o compartir coche.\n" +
                "- Ahorrar gas utilizando electrodomésticos eficientes.\n" +
                "- Usar agua de manera responsable, reparando fugas.\n" +
                "- Reducir, reutilizar y reciclar residuos.";
        
        JOptionPane.showMessageDialog(this, recomendaciones, "Recomendaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para abrir Instagram
    private void abrirInstagram() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.instagram.com/green_lytic?igsh=ZWlrN3p2dTg3ZTJ5"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al abrir Instagram.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para exportar resultados a un archivo de texto
    private void exportarResultados() {
        String resultados = textAreaResultado.getText();
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay resultados para exportar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultados_huella.txt"))) {
            writer.write(resultados);
            JOptionPane.showMessageDialog(this, "Resultados exportados a resultados_huella.txt", "Exportar", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar resultados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para validar la entrada
    private double validarEntrada(String texto) {
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Los campos no pueden estar vacíos.");
        }
        double valor = Double.parseDouble(texto);
        if (valor < 0) {
            throw new IllegalArgumentException("Los valores deben ser positivos.");
        }
        return valor;
    }

    // Método para estilizar botones
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    public static void main(String[] args) {
        // Crear y mostrar la interfaz
        SwingUtilities.invokeLater(() -> {
            CalculadoraCarbonoFrame frame = new CalculadoraCarbonoFrame();
            frame.setVisible(true);
        });
    }
}
