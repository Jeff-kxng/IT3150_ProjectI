package Project1_ASCII;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class ASCII extends JFrame {

    private JButton openFileButton;
    private JComboBox<String> Jcbdelimiter;
    private JComboBox<String> Jchecksum;
    private JComboBox<String> Jxorsum;
    private JButton Reset;
    private JButton Resetcheck;
    private JTextField JFxorsum;
    private JTextField JFdecimiter;
    private JTextArea txtArea;
    private JTextArea hexArea;
    private JTextArea binArea;
    private JTextArea decArea;
    private String asciiText = ""; // Store the ASCII text

    public ASCII() {
        setTitle("ASCII, Hex, Binary, Decimal, Base64 converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(0xFAFAD2));

        // Reset button
        Reset = new JButton("x Reset");
        Reset.setBounds(130, 15, 80, 25);
        Reset.setBackground(new Color(0x808080));
        add(Reset);

        // Number Delimiter
        Jcbdelimiter = new JComboBox<>(new String[]{"None", "Space", "Comma", "User defined"});
        Jcbdelimiter.setBounds(20, 70, 230, 35);
        JLabel Jdelimiter = new JLabel("Number delimiter");
        Jdelimiter.setBounds(20, 50, 100, 15);
        add(Jcbdelimiter);
        add(Jdelimiter);

        //JFdecimiter Jtexfield
        JFdecimiter = new JTextField();
        JFdecimiter.setEditable(false);
        JFdecimiter.setBounds(260, 70, 225, 35);
        add(JFdecimiter);

        // prefixCheckbox;
        JCheckBox prefixCheckbox = new JCheckBox();
        prefixCheckbox.setBounds(20, 120, 20, 25);
        JLabel prefixbox = new JLabel("0x/0b prefix");
        prefixbox.setBounds(48, 120, 100, 25); // Adjusted width to show the text properly
        add(prefixCheckbox);
        add(prefixbox);

        // ASCII text
        JLabel asciiLabel = new JLabel("ASCII text:");
        asciiLabel.setBounds(20, 155, 100, 15);
        // Create a JTextArea
        txtArea = new JTextArea(3, 30);
        txtArea.setLineWrap(true);  // Enable line wrap

        // Create a JScrollPane for the JTextArea
        JScrollPane asciiScrollPane = new JScrollPane(txtArea);
        asciiScrollPane.setBounds(20, 175, 470, 60);
        asciiScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        asciiScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(asciiLabel);
        add(asciiScrollPane); // Add the JScrollPane instead of the JTextArea

        // Hex (bytes)
        JLabel hexLabel = new JLabel("Hex (bytes):");
        hexLabel.setBounds(20, 250, 100, 15);

        // Create a JTextArea
        hexArea = new JTextArea(3, 30);
        hexArea.setLineWrap(true);  // Enable line wrap

        // Create a JScrollPane for the JTextArea
        JScrollPane hexScrollPane = new JScrollPane(hexArea);
        hexScrollPane.setBounds(20, 270, 470, 60);
        hexScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        hexScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(hexLabel);
        add(hexScrollPane); // Add the JScrollPane instead of the JTextArea

        // Binary (bytes)
        JLabel binLabel = new JLabel("Binary (bytes):");
        binLabel.setBounds(20, 350, 100, 15);

        // Create a JTextArea
        binArea = new JTextArea(3, 30);
        // Enable line wrap
        binArea.setLineWrap(true);

        // Create a JScrollPane for the JTextArea
        JScrollPane binScrollPane = new JScrollPane(binArea);
        binScrollPane.setBounds(20, 370, 470, 60);
        binScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        binScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(binLabel);
        add(binScrollPane); // Add the JScrollPane instead of the JTextArea

        // Decimal (bytes)
        JLabel decLabel = new JLabel("Decimal (bytes):");
        decLabel.setBounds(20, 440, 100, 15);

        // Create a JTextArea
        decArea = new JTextArea(3, 30);
        decArea.setLineWrap(true);  // Enable line wrap

        // Create a JScrollPane for the JTextArea
        JScrollPane decScrollPane = new JScrollPane(decArea);
        decScrollPane.setBounds(20, 460, 470, 60);
        decScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        decScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(decLabel);
        add(decScrollPane); // Add the JScrollPane instead of the JTextArea

        // Checksum
        Jchecksum = new JComboBox<>(new String[]{"8-bit", "16-bit", "32-bit"});
        Jchecksum.setBounds(20, 550, 150, 35);
        JLabel Lchecksum = new JLabel("Checksum");
        Lchecksum.setBounds(20, 520, 470, 35);
        add(Lchecksum);
        add(Jchecksum);

        // Xor, Sum, 2's complement
        Jxorsum = new JComboBox<>(new String[]{"Sum", "2's Complement", "Xor"});
        Jxorsum.setBounds(180, 550, 150, 35);
        add(Jxorsum);

        // JTextField after xor, sum, 2's complement JFxorsum
        JFxorsum = new JTextField();
        JFxorsum.setEditable(false);
        JFxorsum.setBounds(340, 550, 151, 35);
        add(JFxorsum);

        // Reset button under Checksum
        Resetcheck = new JButton("x Reset");
        Resetcheck.setBounds(20, 595, 85, 25);
        Resetcheck.setBackground(new Color(0x808080));
        add(Resetcheck);

        // Inside the ActionListener for Number delimiter combo box
        Jcbdelimiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
                String selectedDelimiter = Jcbdelimiter.getSelectedItem().toString();
                if (selectedDelimiter.equals("User defined")) {
                    JFdecimiter.setEditable(true);
                    JFdecimiter.setText(",0x"); // Clear the JTextField
                } else {
                    JFdecimiter.setEditable(false);
                    JFdecimiter.setText("");
                    if (selectedDelimiter.equals("Comma")) {
                        JFdecimiter.setText(",");
                    }
                }
            }
        });

        // Inside the ASCII constructor, after setting up JFdecimiter
        JFdecimiter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFields();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFields();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFields();
            }
        });

        // ActionListener for prefixCheckbox
        prefixCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPrefixEnabled = prefixCheckbox.isSelected();
                updateFields();
            }
        });

        // ActionListener for Checksum combo box
        Jchecksum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
            }
        });

        // ActionListener for Xor, Sum, 2's complement combo box
        Jxorsum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
            }
        });
        Jxorsum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
            }
        });

        // ActionListener for Reset button
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        Resetcheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        // ActionListener for ASCII text input
        txtArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                updateFields();
            }
            public void removeUpdate(DocumentEvent e){
                updateFields();
            }
            public void changeUpdate(DocumentEvent e){
                updateFields();
            }
        });
        setSize(520, 670);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Open File button
        openFileButton = new JButton("Open File");
        openFileButton.setBounds(20, 15, 100, 25);
        openFileButton.setBackground(new Color(0x00ff00));
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        add(openFileButton);
    }
    private void resetFields() {
        txtArea.setText("");   // Clear ASCII text area
        hexArea.setText("");   // Clear Hex area
        binArea.setText("");   // Clear Binary area
        decArea.setText("");   // Clear Decimal area
        JFdecimiter.setText(""); // Clear Number delimiter text field
        JFxorsum.setText("");    // Clear Xor, Sum, 2's complement result text field
        Jchecksum.setSelectedIndex(0); // Reset the checksum combo box
        Jxorsum.setSelectedIndex(0);
        Jcbdelimiter.setSelectedIndex(0);
    }
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            new FileLoader(selectedFile).execute();
        }
    }
    private class FileLoader extends SwingWorker<StringBuilder, Void> {
        private final File file;

        public FileLoader(File file) {
            this.file = file;
        }
        @Override
        protected StringBuilder doInBackground() throws Exception {
            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ASCII.this, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return fileContent;
        }
        @Override
        protected void done() {
            try {
                StringBuilder fileContent = get();
                txtArea.setText(fileContent.toString());
                updateFields(); // Update fields when the file is loaded
            } catch (Exception ex) {
            }
        }
    }
    private long calculatedChecksum;
    private boolean isPrefixEnabled = false;

    // Inside the getNumberDelimiter() method
    private String getNumberDelimiter() {
        String delimiter = "";
        String selectedDelimiter = Jcbdelimiter.getSelectedItem().toString();
        switch (selectedDelimiter) {
            case "None":
                delimiter = "";
                break;
            case "Space":
                delimiter = " ";
                break;
            case "Comma":
                delimiter = ",";
                break;
            case "User defined":
                delimiter = " " + JFdecimiter.getText();
                break;
        }
        return delimiter;
    }
    private void updateFields() {
        asciiText = txtArea.getText(); // Store the ASCII text
        // Update the fields
        String hexResult = convertTextToHex(asciiText);
        hexArea.setText(hexResult);
        String binaryResult = convertTextToBinary(asciiText);
        binArea.setText(binaryResult);
        String decimalResult = convertTextToDecimal(asciiText);
        decArea.setText(decimalResult);
        calculateChecksum(); // Calculate and display checksum values
    }

    private String convertTextToDecimal(String asciiText) {
        StringBuilder decimalBuilder = new StringBuilder();
        // Loop through each character in the ASCII text
        for (int i = 0; i < asciiText.length(); i++) {
            char c = asciiText.charAt(i);
            // Convert the character to its decimal representation
            decimalBuilder.append(String.valueOf((int) c));
            // Add delimiter if it's not the last character
            if (i < asciiText.length() - 1) {
                decimalBuilder.append(getNumberDelimiter());
            }
        }
        return decimalBuilder.toString();
    }

    private String convertTextToBinary(String asciiText) {
        StringBuilder binaryBuilder = new StringBuilder();
        // Loop through each character in the ASCII text
        for (int i = 0; i < asciiText.length(); i++) {
            char c = asciiText.charAt(i);
            // Add "0b" prefix if needed
            if (isPrefixEnabled) {
                binaryBuilder.append("0b");
            }
            // Convert the character to its binary representation with 8 bits
            binaryBuilder.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
            // Add delimiter if it's not the last character
            if (i < asciiText.length() - 1) {
                binaryBuilder.append(getNumberDelimiter());
            }
        }
        return binaryBuilder.toString();
    }

    private String convertTextToHex(String asciiText) {
        StringBuilder hexBuilder = new StringBuilder();
        // Loop through each character in the ASCII text
        for (int i = 0; i < asciiText.length(); i++) {
            char c = asciiText.charAt(i);
            // Add "0x" prefix if needed
            if (isPrefixEnabled) {
                hexBuilder.append("0x");
            }
            // Convert the character to its hexadecimal representation
            hexBuilder.append(String.format("%02X", (int) c));

            // Add delimiter if it's not the last character
            if (i < asciiText.length() - 1) {
                hexBuilder.append(getNumberDelimiter());
            }
        }
        return hexBuilder.toString();
    }

    // Calculate Checksum
    private void calculateChecksum() {
        String asciiText = txtArea.getText().trim(); // Get the ASCII text from the text area and remove leading/trailing spaces
        int selectedChecksumSize = getSelectedChecksumSize();
        int selectedXorMode = Jxorsum.getSelectedIndex();
        long calculatedChecksumValue = 0;
        byte[] bytes = asciiText.getBytes(); // Convert the ASCII text to bytes

        // Calculate the sum of ASCII values of characters in the text
        for (int i = 0; i < asciiText.length(); i++) {
            char c = asciiText.charAt(i);
            int asciiValue = (int) c;
            calculatedChecksumValue += asciiValue;
        }
        // Take the modulus to ensure the checksum fits within the selected size
        calculatedChecksumValue %= (1L << selectedChecksumSize);

        switch (selectedXorMode) {
            case 1: // 2's Complement
                int twosComplement32 = 0;
                for (byte b : bytes) {
                    int value = b & 0xFF;
                    twosComplement32 += value;
                }
                twosComplement32 = (~twosComplement32 + 1) & 0xFFFFFFFF;
                calculatedChecksumValue = twosComplement32 & ((1L << selectedChecksumSize) - 1);
                break;
            case 2: // Xor
                byte[] xorBytes = new byte[8]; // Assuming a long (64-bit) checksum
                for (byte b : bytes) {
                    int value = b & 0xFF;
                    for (int i = 0; i < 8; i++) {
                        xorBytes[i] ^= (value >> i) & 0x01;
                    }
                }
                // Calculate the final XOR checksum value from the bytes
                calculatedChecksumValue = 0;
                for (int i = 0; i < 8; i++) {
                    calculatedChecksumValue |= (xorBytes[i] << i);
                }
                break;
        }
        String checksumValueHex = String.format("%0" + (selectedChecksumSize / 4) + "X", calculatedChecksumValue);
        JFxorsum.setText(checksumValueHex);
        JFxorsum.updateUI();
    }
    private int getSelectedChecksumSize() {
        String selectedChecksum = Jchecksum.getSelectedItem().toString();
        switch (selectedChecksum) {
            case "8-bit":
                return 8;
            case "16-bit":
                return 16;
            case "32-bit":
                return 32;
            default:
                return 0; // Handle the default case or error condition appropriately
        }
    }
}
