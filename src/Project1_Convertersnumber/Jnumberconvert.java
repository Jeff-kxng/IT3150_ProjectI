package Project1_Convertersnumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
public class Jnumberconvert extends JFrame {
    private JComboBox<String> fromcbb;
    private JComboBox<String> tocbb;
    private JButton Convert;
    private JButton Reset;
    private JButton Swap;
    private JTextField Enternumber;
    private JTextField resultnumberalsystem;
    private JTextField resultcomplement;
    private static final String[] BASES = {"Decimal", "Binary", "Octal", "Hexadecimal"};
    private static final int[] RADIX_VALUES = {10, 2, 8, 16};

    public Jnumberconvert() {

        setTitle("Number System Converter");
        setSize(580, 400); // Slightly larger dimensions
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0xFAFAD2));
        panel.setLayout(null);

        // Form
        fromcbb = new JComboBox<>(BASES);
        fromcbb.setBounds(30, 70, 220, 30); // Larger dimensions
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(30, 40, 100, 20); // Adjusted position and size
        panel.add(fromLabel);
        panel.add(fromcbb);

        // To
        tocbb = new JComboBox<>(BASES);
        tocbb.setBounds(320, 70, 220, 30); // Larger dimensions
        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(320, 40, 100, 20); // Adjusted position and size
        panel.add(toLabel);
        panel.add(tocbb);

        // Initialize fromcbb and tocbb indices to reflect "Decimal to Hexadecimal" conversion
        fromcbb.setSelectedIndex(0); // Decimal
        tocbb.setSelectedIndex(3);   // Hexadecimal

        // Header label
        JLabel headerLabel = new JLabel();
        headerLabel.setBounds(30, 10, 300, 30); // Adjusted size
        updateHeaderLabel(headerLabel); // Initialize header Label
        panel.add(headerLabel);

        // Convert button
        Convert = new JButton("= Convert");
        Convert.setBounds(30, 190, 120, 30); // Adjusted size
        Convert.setBackground(new Color(0x00ff00));
        panel.add(Convert);

        // Reset button
        Reset = new JButton("x Reset");
        Reset.setBounds(170, 190, 120, 30); // Adjusted size
        Reset.setBackground(new Color(0x808080));
        panel.add(Reset);

        // Swap button
        Swap = new JButton("Swap");
        Swap.setBounds(310, 190, 120, 30); // Adjusted size
        Swap.setBackground(new Color(0x808080));
        panel.add(Swap);

        // Enter number JTextField
        Enternumber = new JTextField();
        Enternumber.setBounds(30, 130, 510, 30); // Larger dimensions
        Enternumber.setEditable(true);
        panel.add(Enternumber);

        // Enter decimal to hex,...
        JLabel LEnternumber = new JLabel();
        LEnternumber.setBounds(30, 100, 510, 30); // Larger dimensions
        updateEnternumber(LEnternumber);
        panel.add(LEnternumber);

        // Result number in the selected system JTextField
        resultnumberalsystem = new JTextField();
        resultnumberalsystem.setEditable(false);
        resultnumberalsystem.setBounds(30, 250, 510, 30); // Larger dimensions
        panel.add(resultnumberalsystem);

        // Result number in the selected system JLabel
        JLabel Lresultnumberalsystem = new JLabel();
        Lresultnumberalsystem.setBounds(30, 220, 510, 30); // Larger dimensions
        updateresultnumberalsystem(Lresultnumberalsystem);
        panel.add(Lresultnumberalsystem);

        // Result complement JTextfield
        resultcomplement = new JTextField();
        resultcomplement.setEditable(false);
        resultcomplement.setBounds(30, 310, 510, 30); // Larger dimensions
        panel.add(resultcomplement);

        // Result complement JLabel
        JLabel Lresultcomplement = new JLabel();
        Lresultcomplement.setBounds(30, 280, 510, 30); // Larger dimensions
        updateresultcomplement(Lresultcomplement);
        panel.add(Lresultcomplement);

        setContentPane(panel);

        Convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Convert number system

                try {

                    String input = Enternumber.getText().trim();
                    int selectedFromIndex = fromcbb.getSelectedIndex();
                    int selectedToIndex = tocbb.getSelectedIndex();

                    // Decimal to Hexadecimal
                    if (selectedFromIndex == 0 && selectedToIndex == 3) {
                        try {
                            BigDecimal decimalValue = new BigDecimal(input);
                            boolean isNegative = false;
                            if (decimalValue.compareTo(BigDecimal.ZERO) < 0) {
                                isNegative = true;
                                decimalValue = decimalValue.abs();
                            }
                            int intPart = decimalValue.intValue();
                            String hexIntPart = Integer.toHexString(intPart).toUpperCase();
                            BigDecimal fractionalPart = decimalValue.subtract(new BigDecimal(intPart));
                            StringBuilder hexFractionalPart = new StringBuilder();
                            for (int i = 0; i < 16; i++) {
                                fractionalPart = fractionalPart.multiply(BigDecimal.valueOf(16));
                                int hexDigit = fractionalPart.intValue();
                                hexFractionalPart.append(Integer.toHexString(hexDigit).toUpperCase());
                                fractionalPart = fractionalPart.subtract(BigDecimal.valueOf(hexDigit));
                                if (fractionalPart.compareTo(BigDecimal.ZERO) == 0) {
                                    break;
                                }
                            }
                            String hexValue = hexIntPart;
                            if (hexFractionalPart.length() > 0) {
                                hexValue += "." + hexFractionalPart.toString();
                            }
                            if (isNegative) {
                                hexValue = "-" + hexValue;
                            }
                            resultnumberalsystem.setText(hexValue);
                            resultcomplement.setText("N/A");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    // Hexadecimal to Decimal
                    else if (selectedFromIndex == 3 && selectedToIndex == 0) {
                        try {
                            String hexValue = input.toUpperCase();
                            boolean isNegative = false;
                            if (hexValue.startsWith("-")) {
                                hexValue = hexValue.substring(1);
                                isNegative = true;
                            }
                            int dotIndex = hexValue.indexOf('.');
                            String hexIntegerPart;
                            String hexFractionalPart;
                            if (dotIndex != -1) {
                                hexIntegerPart = hexValue.substring(0, dotIndex);
                                hexFractionalPart = hexValue.substring(dotIndex + 1);
                            } else {
                                hexIntegerPart = hexValue;
                                hexFractionalPart = "";
                            }
                            int decimalIntegerPart = Integer.parseInt(hexIntegerPart, 16);
                            double decimalFractionalPart = 0.0;
                            if (!hexFractionalPart.isEmpty()) {
                                int fractionalHex = Integer.parseInt(hexFractionalPart, 16);
                                decimalFractionalPart = fractionalHex / Math.pow(16, hexFractionalPart.length());
                            }
                            double decimalResult = decimalIntegerPart + decimalFractionalPart;
                            if (isNegative) {
                                resultnumberalsystem.setText(String.format("%.10f", -decimalResult));
                            } else {
                                resultnumberalsystem.setText(String.format("%.10f", decimalResult));
                            }
                            resultcomplement.setText("N/A");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    // Binary to Decimal
                    else if (selectedFromIndex == 1 && selectedToIndex == 0) {
                        try {
                            String binaryValue = input;
                            // Check for negative binary number
                            boolean isNegative = false;
                            if (binaryValue.startsWith("-")) {
                                isNegative = true;
                                // Remove the negative sign for processing
                                binaryValue = binaryValue.substring(1);
                            }
                            String[] parts = binaryValue.split("\\.");
                            // Convert the integer part to decimal
                            int decimalIntegerPart = Integer.parseInt(parts[0], 2);
                            double decimalFractionalPart = 0.0;
                            // Convert the fractional part to decimal
                            if (parts.length > 1 && !parts[1].isEmpty()) {
                                String fractionalPart = parts[1];
                                for (int i = 0; i < fractionalPart.length(); i++) {
                                    int digit = Character.getNumericValue(fractionalPart.charAt(i));
                                    decimalFractionalPart += digit / Math.pow(2, i + 1);
                                }
                            }
                            double decimalResult = decimalIntegerPart + decimalFractionalPart;
                            if (isNegative) {
                                decimalResult = -decimalResult;
                            }
                            resultnumberalsystem.setText(Double.toString(decimalResult));
                            resultcomplement.setText("N/A");
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("Invalid input");
                        }
                    }

                    // Decimal to Binary
                    else if (selectedFromIndex == 0 && selectedToIndex == 1) {
                        try {
                            String decimalValueStr = input;
                            // Parse the decimal value
                            double decimalValue = Double.parseDouble(decimalValueStr);
                            // Check for negative decimal value
                            boolean isNegative = false;
                            if (decimalValue < 0) {
                                isNegative = true;
                                decimalValue = -decimalValue; // Make it positive for processing
                            }
                            // Initialize variables for binary parts
                            StringBuilder binaryIntegerPart = new StringBuilder();
                            StringBuilder binaryFractionalPart = new StringBuilder();
                            // Convert the integer part to binary
                            int integerPart = (int) decimalValue;
                            do {
                                binaryIntegerPart.insert(0, integerPart % 2);
                                integerPart /= 2;
                            } while (integerPart > 0);
                            // Convert the fractional part to binary
                            double fractionalPart = decimalValue - (int) decimalValue;
                            for (int i = 0; i < 16; i++) { // Convert up to 16 bits of fractional part
                                fractionalPart *= 2;
                                binaryFractionalPart.append((int) fractionalPart);
                                fractionalPart -= (int) fractionalPart;
                            }
                            // Combine integer and fractional binary parts
                            StringBuilder binaryValue = new StringBuilder(binaryIntegerPart);
                            if (binaryFractionalPart.length() > 0) {
                                binaryValue.append('.').append(binaryFractionalPart);
                            }
                            // If it was a negative decimal number, add a '-' sign to the binary result
                            if (isNegative) {
                                binaryValue.insert(0, '-');
                            }
                            resultnumberalsystem.setText(binaryValue.toString());
                            // Display "N/A" for Binary signed 2's complement
                            resultcomplement.setText("N/A");
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("Invalid input");
                        }
                    }

                    // Binary to Hexadecimal conversion
                    else if (selectedFromIndex == 1 && selectedToIndex == 3) {
                        try {
                            String binaryValue = input;
                            // Check if the binary value is negative
                            boolean isNegative = false;
                            if (binaryValue.startsWith("-")) {
                                isNegative = true;
                                // Remove the negative sign for processing
                                binaryValue = binaryValue.substring(1);
                            }
                            // Split the binary number into integer and fractional parts
                            String[] parts = binaryValue.split("\\.");
                            String binaryIntegerPart = parts[0];
                            String binaryFractionalPart = (parts.length > 1) ? parts[1] : "";
                            // Pad the fractional part with zeros to have a length multiple of 4
                            while (binaryFractionalPart.length() % 4 != 0) {
                                binaryFractionalPart += "0";
                            }
                            // Initialize variables for the hexadecimal parts
                            String hexIntegerPart = "";
                            String hexFractionalPart = "";
                            // Convert the integer part to hexadecimal
                            if (!binaryIntegerPart.isEmpty()) {
                                hexIntegerPart = Integer.toHexString(Integer.parseInt(binaryIntegerPart, 2)).toUpperCase();
                            }
                            // Convert the fractional part to hexadecimal
                            for (int i = 0; i < binaryFractionalPart.length(); i += 4) {
                                String nibble = binaryFractionalPart.substring(i, i + 4);
                                hexFractionalPart += Integer.toHexString(Integer.parseInt(nibble, 2)).toUpperCase();
                            }
                            // Add a '-' sign to both the hexadecimal and decimal representations if it was a negative binary number
                            if (isNegative) {
                                hexIntegerPart = "-" + hexIntegerPart;
                            }
                            // Combine integer and fractional hexadecimal parts
                            String hexValue = hexIntegerPart;
                            if (!hexFractionalPart.isEmpty()) {
                                hexValue += "." + hexFractionalPart;
                            }
                            resultnumberalsystem.setText(hexValue);
                            // Calculate the Decimal number equivalent (integer + fractional)
                            double decimalEquivalent = 0.0;
                            if (!hexIntegerPart.isEmpty()) {
                                decimalEquivalent += Integer.parseInt(binaryIntegerPart, 2);
                            }
                            if (!hexFractionalPart.isEmpty()) {
                                for (int i = 0; i < hexFractionalPart.length(); i++) {
                                    char hexDigit = hexFractionalPart.charAt(i);
                                    decimalEquivalent += Integer.parseInt(String.valueOf(hexDigit), 16) / Math.pow(16, i + 1);
                                }
                            }
                            // Check if the binary value was negative and adjust the decimal equivalent accordingly
                            if (isNegative) {
                                decimalEquivalent = -decimalEquivalent;
                            }
                            // Display the Decimal number equivalent (3 digits)
                            resultcomplement.setText(String.format("%.3f", decimalEquivalent));
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }

                    // Hexadecimal to Binary conversion
                    else if (selectedFromIndex == 3 && selectedToIndex == 1) {
                        try {
                            String hexValue = input;
                            // Check if the hexadecimal value is negative (if it starts with '-')
                            boolean isNegative = hexValue.startsWith("-");
                            if (isNegative) {
                                // Remove the negative sign for processing
                                hexValue = hexValue.substring(1);
                            }
                            // Split the hexadecimal number into integer and fractional parts
                            String[] parts = hexValue.split("\\.");
                            String hexIntegerPart = parts[0];
                            String hexFractionalPart = (parts.length > 1) ? parts[1] : "";
                            // Initialize variables for the binary parts
                            StringBuilder binaryIntegerPart = new StringBuilder();
                            StringBuilder binaryFractionalPart = new StringBuilder();
                            // Convert the integer part to binary
                            if (!hexIntegerPart.isEmpty()) {
                                binaryIntegerPart.append(Integer.toBinaryString(Integer.parseInt(hexIntegerPart, 16)));
                            }
                            // Convert the fractional part to binary
                            if (!hexFractionalPart.isEmpty()) {
                                for (int i = 0; i < hexFractionalPart.length(); i++) {
                                    char hexDigit = hexFractionalPart.charAt(i);
                                    // Convert each hex digit to binary and pad with leading zeros as needed
                                    String binaryNibble = Integer.toBinaryString(Integer.parseInt(String.valueOf(hexDigit), 16));
                                    while (binaryNibble.length() < 4) {
                                        binaryNibble = "0" + binaryNibble;
                                    }
                                    binaryFractionalPart.append(binaryNibble);
                                }
                            }
                            // Combine integer and fractional binary parts
                            String binaryValue = binaryIntegerPart.toString();
                            if (binaryFractionalPart.length() > 0) {
                                binaryValue += "." + binaryFractionalPart.toString();
                            }
                            // Add a '-' sign to the binary representation if it was a negative hexadecimal number
                            if (isNegative) {
                                binaryValue = "-" + binaryValue;
                            }
                            resultnumberalsystem.setText(binaryValue);
                            // Calculate the Decimal number equivalent
                            double decimalEquivalent = 0.0;
                            // Convert the integer part to decimal
                            if (!hexIntegerPart.isEmpty()) {
                                decimalEquivalent += Integer.parseInt(hexIntegerPart, 16);
                            }
                            // Convert the fractional part to decimal
                            if (!hexFractionalPart.isEmpty()) {
                                for (int i = 0; i < hexFractionalPart.length(); i++) {
                                    char hexDigit = hexFractionalPart.charAt(i);
                                    int digitValue = Integer.parseInt(String.valueOf(hexDigit), 16);
                                    decimalEquivalent += digitValue / Math.pow(16, i + 1);
                                }
                            }
                            // Check if the hexadecimal value was negative and adjust the decimal equivalent accordingly
                            if (isNegative) {
                                decimalEquivalent = -decimalEquivalent;
                            }
                            // Display the Decimal number equivalent (10 digits)
                            resultcomplement.setText(String.format("%.10f", decimalEquivalent));
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }

                    // Binary to Octal conversion
                    else if (selectedFromIndex == 1 && selectedToIndex == 2) {
                        try {
                            String binaryValue = input;
                            // Check if the binary value is negative
                            boolean isNegative = false;
                            if (binaryValue.startsWith("-")) {
                                isNegative = true;
                                // Remove the negative sign for processing
                                binaryValue = binaryValue.substring(1);
                            }
                            // Split the binary number into integer and fractional parts
                            String[] parts = binaryValue.split("\\.");
                            String binaryIntegerPart = parts[0];
                            String binaryFractionalPart = (parts.length > 1) ? parts[1] : "";
                            // Pad the fractional part with zeros to have a length multiple of 3
                            while (binaryFractionalPart.length() % 3 != 0) {
                                binaryFractionalPart += "0";
                            }
                            // Initialize variables for the octal parts
                            String octalIntegerPart = "";
                            String octalFractionalPart = "";
                            // Convert the integer part to octal
                            if (!binaryIntegerPart.isEmpty()) {
                                octalIntegerPart = Integer.toOctalString(Integer.parseInt(binaryIntegerPart, 2));
                            }
                            // Convert the fractional part to octal
                            for (int i = 0; i < binaryFractionalPart.length(); i += 3) {
                                String nibble = binaryFractionalPart.substring(i, i + 3);
                                octalFractionalPart += Integer.toOctalString(Integer.parseInt(nibble, 2));
                            }
                            // Add a '-' sign to the octal representation if it was a negative binary number
                            if (isNegative) {
                                octalIntegerPart = "-" + octalIntegerPart;
                            }
                            // Combine integer and fractional octal parts
                            String octalValue = octalIntegerPart;
                            if (!octalFractionalPart.isEmpty()) {
                                octalValue += "." + octalFractionalPart;
                            }
                            resultnumberalsystem.setText(octalValue);
                            // Calculate the Decimal number equivalent (integer + fractional)
                            double decimalEquivalent = 0.0;
                            if (!octalIntegerPart.isEmpty()) {
                                decimalEquivalent += Integer.parseInt(binaryIntegerPart, 2);
                            }
                            if (!octalFractionalPart.isEmpty()) {
                                for (int i = 0; i < octalFractionalPart.length(); i++) {
                                    char octalDigit = octalFractionalPart.charAt(i);
                                    decimalEquivalent += Integer.parseInt(String.valueOf(octalDigit), 8) / Math.pow(8, i + 1);
                                }
                            }
                            // Check if the binary value was negative and adjust the decimal equivalent accordingly
                            if (isNegative) {
                                decimalEquivalent = -decimalEquivalent;
                            }
                            // Display the Decimal number equivalent (3 digits)
                            resultcomplement.setText(String.format("%.3f", decimalEquivalent));
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }

                    // Octal to Binary conversion
                    else if (selectedFromIndex == 2 && selectedToIndex == 1) {
                        try {
                            String octalValue = input;
                            // Check if the octal value is negative
                            boolean isNegative = false;
                            if (octalValue.startsWith("-")) {
                                isNegative = true;
                                // Remove the negative sign for processing
                                octalValue = octalValue.substring(1);
                            }
                            // Split the octal number into integer and fractional parts
                            String[] parts = octalValue.split("\\.");
                            String octalIntegerPart = parts[0];
                            String octalFractionalPart = (parts.length > 1) ? parts[1] : "";
                            // Initialize variables for the binary parts
                            String binaryIntegerPart = "";
                            String binaryFractionalPart = "";
                            // Convert the integer part to binary
                            if (!octalIntegerPart.isEmpty()) {
                                binaryIntegerPart = Integer.toBinaryString(Integer.parseInt(octalIntegerPart, 8));
                            }
                            // Convert the fractional part to binary
                            for (int i = 0; i < octalFractionalPart.length(); i++) {
                                char octalDigit = octalFractionalPart.charAt(i);
                                String binaryNibble = Integer.toBinaryString(Integer.parseInt(String.valueOf(octalDigit), 8));
                                // Ensure that each binary nibble has 3 digits
                                while (binaryNibble.length() < 3) {
                                    binaryNibble = "0" + binaryNibble;
                                }
                                binaryFractionalPart += binaryNibble;
                            }
                            // Combine integer and fractional binary parts
                            String binaryValue = binaryIntegerPart;
                            if (!binaryFractionalPart.isEmpty()) {
                                binaryValue += "." + binaryFractionalPart;
                            }
                            // Display the Binary number with a negative sign if applicable
                            resultnumberalsystem.setText(isNegative ? "-" + binaryValue : binaryValue);
                            // Calculate the Decimal number equivalent (integer + fractional)
                            double decimalEquivalent = 0.0;
                            if (!binaryIntegerPart.isEmpty()) {
                                decimalEquivalent += Integer.parseInt(binaryIntegerPart, 2);
                            }
                            if (!binaryFractionalPart.isEmpty()) {
                                for (int i = 0; i < binaryFractionalPart.length(); i++) {
                                    char binaryDigit = binaryFractionalPart.charAt(i);
                                    decimalEquivalent += Integer.parseInt(String.valueOf(binaryDigit), 2) / Math.pow(2, i + 1);
                                }
                            }
                            // Display the Decimal number with a negative sign if applicable
                            resultcomplement.setText(isNegative ? "-" + decimalEquivalent : String.valueOf(decimalEquivalent));
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }

                    // Octal to Hexadecimal conversion
                    else if (selectedFromIndex == 2 && selectedToIndex == 3) {
                        try {
                            String octalValue = input;
                            // Check if the octal value is negative
                            boolean isNegative = false;
                            if (octalValue.startsWith("-")) {
                                isNegative = true;
                                // Remove the negative sign for processing
                                octalValue = octalValue.substring(1);
                            }
                            // Split the octal number into integer and fractional parts
                            String[] parts = octalValue.split("\\.");
                            String octalIntegerPart = parts[0];
                            String octalFractionalPart = (parts.length > 1) ? parts[1] : "0"; // Default to "0" if no fractional part
                            // Convert the octal value to decimal
                            int decimalIntegerPart = Integer.parseInt(octalIntegerPart, 8);
                            // Convert the fractional part to decimal
                            double decimalFractionalPart = 0.0;
                            for (int i = 0; i < octalFractionalPart.length(); i++) {
                                char octalDigit = octalFractionalPart.charAt(i);
                                int digitValue = Character.getNumericValue(octalDigit);
                                decimalFractionalPart += digitValue / Math.pow(8, i + 1);
                            }
                            // Combine the integer and fractional parts
                            double decimalValue = decimalIntegerPart + decimalFractionalPart;
                            // Convert the decimal value to hexadecimal using a custom function
                            String hexValue = convertDecimalToHex(decimalValue);
                            // Add a '-' sign to the hexadecimal representation if it was a negative octal number
                            if (isNegative) {
                                hexValue = "-" + hexValue;
                            }
                            // Display the Hexadecimal number with a negative sign if applicable
                            resultnumberalsystem.setText(hexValue);
                            // Format the Decimal answer to match the desired format
                            String decimalString = String.format("%.6f", decimalValue);
                            if (decimalString.endsWith(".000000")) {
                                decimalString = decimalString.substring(0, decimalString.length() - 7); // Remove trailing ".000000"
                            }
                            // Display the Decimal number equivalent with a negative sign if applicable
                            resultcomplement.setText(isNegative ? "-" + decimalString : decimalString);
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }

                    // Hexadecimal to Octal conversion
                    else if (selectedFromIndex == 3 && selectedToIndex == 2) {
                        try {
                            String hexValue = input;
                            // Check if the hex value is negative
                            boolean isNegative = false;
                            if (hexValue.startsWith("-")) {
                                isNegative = true;
                                // Remove the negative sign for processing
                                hexValue = hexValue.substring(1);
                            }
                            // Remove the "0x" prefix if present
                            if (hexValue.startsWith("0x") || hexValue.startsWith("0X")) {
                                hexValue = hexValue.substring(2);
                            }
                            // Split the hex number into integer and fractional parts
                            String[] parts = hexValue.split("\\.");
                            String hexIntegerPart = parts[0];
                            String hexFractionalPart = (parts.length > 1) ? parts[1] : "0"; // Default to "0" if no fractional part
                            // Convert the hex value to decimal
                            double decimalIntegerPart = Integer.parseInt(hexIntegerPart, 16);
                            // Convert the fractional part to decimal
                            double decimalFractionalPart = 0.0;
                            for (int i = 0; i < hexFractionalPart.length(); i++) {
                                char hexDigit = hexFractionalPart.charAt(i);
                                int digitValue = Character.digit(hexDigit, 16);
                                decimalFractionalPart += digitValue / Math.pow(16, i + 1);
                            }
                            // Combine the integer and fractional parts
                            double decimalValue = decimalIntegerPart + decimalFractionalPart;
                            // Convert the decimal value to octal
                            String octalValue = convertDecimalToOctal(decimalValue);
                            // Add a '-' sign to the octal representation if it was a negative hex number
                            if (isNegative) {
                                octalValue = "-" + octalValue;
                            }
                            // Display the Octal number with a negative sign if applicable
                            resultnumberalsystem.setText(octalValue);
                            // Format the Decimal answer to match the desired format
                            String decimalString = String.format("%.9f", decimalValue);
                            if (decimalString.endsWith(".000000000")) {
                                decimalString = decimalString.substring(0, decimalString.length() - 10); // Remove trailing ".000000000"
                            }
                            // Display the Decimal number equivalent with a negative sign if applicable
                            resultcomplement.setText(isNegative ? "-" + decimalString : decimalString);
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }

                    // Octal to Decimal conversion
                    else if (selectedFromIndex == 2 && selectedToIndex == 0) {
                        try {
                            String octalValue = input;
                            // Check if the octal value is negative
                            boolean isNegative = false;
                            if (octalValue.startsWith("-")) {
                                isNegative = true;
                                // Remove the negative sign for processing
                                octalValue = octalValue.substring(1);
                            }
                            // Split the octal number into integer and fractional parts
                            String[] parts = octalValue.split("\\.");
                            String octalIntegerPart = parts[0];
                            String octalFractionalPart = (parts.length > 1) ? parts[1] : "0"; // Default to "0" if no fractional part
                            // Convert the octal integer part to decimal
                            double decimalIntegerPart = 0.0;
                            for (int i = octalIntegerPart.length() - 1; i >= 0; i--) {
                                char octalDigit = octalIntegerPart.charAt(i);
                                int digitValue = Character.getNumericValue(octalDigit);
                                decimalIntegerPart += digitValue * Math.pow(8, octalIntegerPart.length() - i - 1);
                            }
                            // Convert the octal fractional part to decimal
                            double decimalFractionalPart = 0.0;
                            for (int i = 0; i < octalFractionalPart.length(); i++) {
                                char octalDigit = octalFractionalPart.charAt(i);
                                int digitValue = Character.getNumericValue(octalDigit);
                                decimalFractionalPart += digitValue / Math.pow(8, i + 1);
                            }
                            // Combine the integer and fractional parts
                            double decimalValue = decimalIntegerPart + decimalFractionalPart;
                            // Display the Decimal number with a negative sign if applicable
                            String decimalString = String.format("%.6f", decimalValue);
                            if (decimalString.endsWith(".000000")) {
                                decimalString = decimalString.substring(0, decimalString.length() - 7); // Remove trailing ".000000"
                            }
                            // Add a '-' sign to the decimal representation if it was a negative octal number
                            if (isNegative) {
                                decimalString = "-" + decimalString;
                            }
                            resultnumberalsystem.setText(decimalString);
                            // Calculate and display the Hexadecimal number
                            String hexValue = convertDecimalToHex(decimalValue);
                            // Add a '-' sign to the hexadecimal representation if it was a negative octal number
                            if (isNegative) {
                                hexValue = "-" + hexValue;
                            }
                            resultcomplement.setText(hexValue);
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }

                    // Decimal to Octal conversion
                    else if (selectedFromIndex == 0 && selectedToIndex == 2) {
                        try {
                            double decimalValue = Double.parseDouble(input);
                            // Check if the decimal value is negative
                            boolean isNegative = false;
                            if (decimalValue < 0) {
                                isNegative = true;
                                // Make it positive for processing
                                decimalValue = Math.abs(decimalValue);
                            }
                            // Separate the integer and fractional parts
                            int integerPart = (int) decimalValue;
                            double fractionalPart = decimalValue - integerPart;
                            // Convert the integer part to octal
                            StringBuilder octalIntegerPart = new StringBuilder();
                            while (integerPart > 0) {
                                int remainder = integerPart % 8;
                                octalIntegerPart.insert(0, remainder);
                                integerPart /= 8;
                            }
                            // If the integer part is zero, the octal representation is "0"
                            if (octalIntegerPart.length() == 0) {
                                octalIntegerPart.append("0");
                            }
                            // Convert the fractional part to octal
                            StringBuilder octalFractionalPart = new StringBuilder();
                            for (int i = 0; i < 6; i++) {
                                fractionalPart *= 8;
                                int digit = (int) fractionalPart;
                                octalFractionalPart.append(digit);
                                fractionalPart -= digit;
                            }
                            // Combine the integer and fractional parts
                            StringBuilder octalValue = new StringBuilder(octalIntegerPart.toString());
                            if (octalFractionalPart.length() > 0) {
                                octalValue.append('.').append(octalFractionalPart.toString());
                            }
                            // Add a '-' sign to the octal representation if it was a negative decimal number
                            if (isNegative) {
                                octalValue.insert(0, '-');
                            }
                            resultnumberalsystem.setText(octalValue.toString());
                            // Calculate and display the Hexadecimal number
                            String hexValue = convertDecimalToHex(decimalValue);
                            // Add a '-' sign to the hexadecimal representation if it was a negative decimal number
                            if (isNegative) {
                                hexValue = "-" + hexValue;
                            }
                            resultcomplement.setText(hexValue);
                        } catch (NumberFormatException ex) {
                            resultnumberalsystem.setText("N/A");
                            resultcomplement.setText("N/A");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Enternumber.setText("");
                resultnumberalsystem.setText("");
                resultcomplement.setText("");
            }
        });

        Swap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedFromIndex = fromcbb.getSelectedIndex();
                int selectedToIndex = tocbb.getSelectedIndex();
                fromcbb.setSelectedIndex(selectedToIndex);
                tocbb.setSelectedIndex(selectedFromIndex);
                // Update the UI components based on the swapped values
                updateHeaderLabel(headerLabel);
                updateEnternumber(LEnternumber);
                updateresultnumberalsystem(Lresultnumberalsystem);
                updateresultcomplement(Lresultcomplement);

            }
        });
    }

    // Custom function to convert decimal to octal
    private static String convertDecimalToOctal(double decimalValue) {
        StringBuilder result = new StringBuilder();
        int intValue = (int) decimalValue;
        double fractionalPart = decimalValue - intValue;
        result.append(Integer.toOctalString(intValue));
        if (fractionalPart > 0) {
            result.append('.');
            for (int i = 0; i < 12; i++) {
                fractionalPart *= 8;
                int digit = (int) fractionalPart;
                result.append(digit);
                fractionalPart -= digit;
            }
        }
        return result.toString();
    }

    // Custom function to convert decimal to hexadecimal
    private static String convertDecimalToHex(double decimalValue) {
        String hex = "0123456789ABCDEF";
        StringBuilder result = new StringBuilder(" ");
        int intValue = (int) decimalValue;
        double fractionalPart = decimalValue - intValue;
        result.append(Integer.toHexString(intValue));
        if (fractionalPart > 0) {
            result.append('.');
            for (int i = 0; i < 12; i++) {
                fractionalPart *= 16;
                int digit = (int) fractionalPart;
                result.append(hex.charAt(digit));
                fractionalPart -= digit;
            }
        }
        return result.toString().toUpperCase();
    }

    private void updateHeaderLabel(JLabel headerLabel) {

        String selectedFrom = fromcbb.getSelectedItem().toString();
        String selectedTo = tocbb.getSelectedItem().toString();
        String conversionMode = selectedFrom + " to " + selectedTo + " converter";
        headerLabel.setText(conversionMode);
    }

    private void updateresultnumberalsystem(JLabel resultnumberalsystem) {
        String selectedTo = tocbb.getSelectedItem().toString();
        String conversionMode = selectedTo + " number ";
        resultnumberalsystem.setText(conversionMode);
    }

    private void updateresultcomplement(JLabel lresultcomplement) {
        String selectedFrom = fromcbb.getSelectedItem().toString();
        String selectedTo = tocbb.getSelectedItem().toString();
        // Swap selectedFrom and selectedTo based on the specific conversions
        if (selectedFrom.equals("Binary") && selectedTo.equals("Decimal")) {
            lresultcomplement.setText("Decimal from signed 2's complement");
        } else if (selectedFrom.equals("Decimal") && selectedTo.equals("Binary")) {
            lresultcomplement.setText("Binary signed 2's complement");
        } else if (selectedFrom.equals("Binary") && selectedTo.equals("Octal")) {
            lresultcomplement.setText("Decimal number");
        } else if (selectedFrom.equals("Octal") && selectedTo.equals("Binary")) {
            lresultcomplement.setText("Decimal number");
        } else if (selectedFrom.equals("Binary") && selectedTo.equals("Hexadecimal")) {
            lresultcomplement.setText("Decimal number");
        } else if (selectedFrom.equals("Hexadecimal") && selectedTo.equals("Binary")) {
            lresultcomplement.setText("Decimal number");
        } else if (selectedFrom.equals("Decimal") && selectedTo.equals("Hexadecimal")) {
            lresultcomplement.setText("Hex signed 2's complement");
        } else if (selectedFrom.equals("Hexadecimal") && selectedTo.equals("Decimal")) {
            lresultcomplement.setText("Decimal from signed 2's complement");
        } else if (selectedFrom.equals("Decimal") && selectedTo.equals("Octal")) {
            lresultcomplement.setText("Hex number");
        } else if (selectedFrom.equals("Octal") && selectedTo.equals("Decimal")) {
            lresultcomplement.setText("Hex number");
        } else if (selectedFrom.equals("Octal") && selectedTo.equals("Hexadecimal")) {
            lresultcomplement.setText("Decimal number");
        } else if (selectedFrom.equals("Hexadecimal") && selectedTo.equals("Octal")) {
            lresultcomplement.setText("Decimal number");
        } else {
            lresultcomplement.setText("Result Complement Label");
        }
    }

    private void updateEnternumber(JLabel Enternumber) {
        String selectedFrom = fromcbb.getSelectedItem().toString();
        String selectedTo = tocbb.getSelectedItem().toString();
        String conversionMode = "Enter " + selectedFrom + " number ";
        Enternumber.setText(conversionMode);
    }
}
