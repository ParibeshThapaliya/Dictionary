package Dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * This is a simple GUI application that provides a dictionary service.
 * It provides ADD, REMOVE, FIND, IMPORT and EXPORT functions.
 * The dictionary is stored in memory and can be imported/exported to/from a file.
 * The dictionary is represented as a HashMap where the key is the word and the value is an object of type Word.
 * The Word class contains the word's definition and frequency.
 * The application also provides a search history feature where the user can see the last 10 words searched.
 * The search history is stored in a JComboBox.
 */
public class Dictionary {
    // Button to find words in the dictionary
    public JButton FINDButton;

    // Button to add a new word to the dictionary
    public JButton ADDButton;

    // Button to import a dictionary from a file
    public JButton IMPORTButton;

    // Button to export the dictionary to a file
    public JButton EXPORTButton;

    // Main panel for the GUI components
    public JPanel JPanel;

    // Label for the file path text field
    public JLabel filePathLabel;

    // Label for the new word text field
    public JLabel newWordLabel;

    // Label for the original word text field
    public JLabel originalWorldLabel;

    // Label for the first frequent word text field
    public JLabel freq3Label;

    // Label for the second frequent word text field
    public JLabel freq2Label;

    // Label for the third frequent word text field
    public JLabel freq1Label;

    // Label for the search history list
    public JLabel searchHistoryLabel;

    // Button to modify an existing word in the dictionary
    public JButton MODIFYButton;

    // Button to clear all text fields in the GUI
    public JButton CLEARButton;

    // Button to remove a word from the dictionary
    public JButton REMOVEButton;

    // Text field for the first frequent word
    public JTextField TextFreqWord1;

    public JTextField TextFreqWord2;

    // Text field for the third frequent word
    public JTextField TextFreqWord3;

    // Text field for the word definition
    public JTextField TextArea;

    // Text field for the new word
    public JTextField TextNewWord;

    // Text field for the original word in the dictionary
    public JTextField TextOriginalWord;

    // Text field for the file path
    public JTextField TextFilePath;

    // Combo box for the search history
    public JComboBox<String> searchHistoryList;

    // HashMap to store the dictionary
    public HashMap<String, Word> dictionary;
// Text field for the second frequent word

    public Dictionary() {
        searchHistoryList.setPrototypeDisplayValue("Add Words for Search History");
        dictionary = new HashMap<>();
        //custom action listener for the buttons 
        for (Component component : JPanel.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addActionListener(new ButtonClicked());
            }
        }
        // default action listener for the search history list
        searchHistoryList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextNewWord.setText((String) searchHistoryList.getSelectedItem());
            }
        });


    }

    /**
     * Start the GUI application.
     * <p>
     * This method constructs a new instance of the Dictionary class, gets the
     * JPanel from it, and puts it in a JFrame. It then sets the default close
     * operation to exit the application, packs the frame, and shows it.
     * </p>
     *
     * @param args Command line arguments, ignored.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dictionary");
        frame.setContentPane(new Dictionary().JPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    class ButtonClicked implements ActionListener {

        /**
         * This method is called when the button is clicked. It gets the button's
         * text, and then calls the {@link #handleButton(String)} method with
         * that text. The {@link #handleButton(String)} method then calls the
         * appropriate method to handle the button click.
         *
         * @param e the event object
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonText = e.getActionCommand();
            handleButton(buttonText);
        }

        /**
         * Handle the button click. This method takes a string argument, which is the button's text.
         * It then calls the appropriate method to handle the button click.
         * <p>
         * If the button's text is "FIND", it calls the {@link #handleFind()} method.
         * If the button's text is "ADD", it calls the {@link #handleAdd()} method.
         * If the button's text is "IMPORT", it calls the {@link #handleImport()} method.
         * If the button's text is "EXPORT", it calls the {@link #handleExport()} method.
         * If the button's text is "CLEAR", it calls the {@link #handleClear()} method.
         * If the button's text is "MODIFY", it calls the {@link #handleModify()} method.
         * If the button's text is "REMOVE", it calls the {@link #handleRemove()} method.
         * </p>
         *
         * @param buttonText the button's text
         * @throws WordNotFoundError   if the word is not found
         * @throws InvalidWordError    if the word is invalid
         * @throws FileNotFoundError   if the file is not found
         * @throws WordDuplicatedError if the word already exists
         */
        void handleButton(String buttonText) throws WordNotFoundError, InvalidWordError, FileNotFoundError, WordDuplicatedError {
            switch (buttonText) {
                case "FIND":
                    handleFind();
                    break;
                case "ADD":
                    handleAdd();
                    break;
                case "IMPORT":
                    handleImport();
                    break;
                case "EXPORT":
                    handleExport();
                    break;
                case "CLEAR":
                    handleClear();
                    break;
                case "MODIFY":
                    handleModify();
                    break;
                case "REMOVE":
                    handleRemove();
                    break;
                default:
                    break;


            }
        }

        /**
         * This method is called when the REMOVE button is clicked.
         * It removes the word from the dictionary and from the search history list.
         * If the word is not found, it throws a WordNotFoundError.
         *
         * @throws WordNotFoundError if the word is not found
         */
        private void handleRemove() throws WordNotFoundError {
            String word = TextNewWord.getText().replaceAll("[ \t]", "");
            if (dictionary.containsKey(word)) {
                dictionary.remove(word);

                for (int i = 0; i < searchHistoryList.getItemCount(); i++) {
                    if (searchHistoryList.getItemAt(i).equals(word)) {
                        searchHistoryList.removeItemAt(i);
                    }
                }
            } else {
                TextArea.setText("This word is not found:" + word);
                throw new WordNotFoundError("This word is not found:" + word);
            }
        }


        /**
         * This method is called when the CLEAR button is clicked.
         * It clears all the text fields in the GUI.
         */
        private void handleClear() {
            TextNewWord.setText("");
            TextOriginalWord.setText("");
            TextFreqWord1.setText("");
            TextFreqWord2.setText("");
            TextFreqWord3.setText("");
            TextArea.setText("");
            TextFilePath.setText("");
        }

        /**
         * Modifies an existing word in the dictionary.
         * <p>
         * This method replaces the original word with a new word in the dictionary.
         * It checks if the new word is valid and if the original word exists in the dictionary.
         * If the new word is invalid, an InvalidWordError is thrown.
         * If the original word is not found, a WordNotFoundError is thrown.
         * </p>
         *
         * @throws InvalidWordError  if the new word is invalid
         * @throws WordNotFoundError if the original word is not found in the dictionary
         */
        private void handleModify() {
            String newWord = TextNewWord.getText().replaceAll("[ \t]", "");
            String originalWord = TextOriginalWord.getText().replaceAll("[ \t]", "");
            if (!newWord.matches("[a-zA-Z]+")) {
                TextArea.setText("This word is Invalid: " + newWord);
                throw new InvalidWordError("This is an invalid Word: " + newWord);
            }
            if (dictionary.containsKey(originalWord)) {
                Word wordDef = dictionary.get(originalWord);
                dictionary.remove(originalWord);
                dictionary.put(newWord, wordDef);
            } else {
                TextArea.setText("This word is not found:" + newWord);
                throw new WordNotFoundError("This word is not found:" + newWord);
            }


        }

        /**
         * Exports the current dictionary to a file.
         * <p>
         * This method retrieves the file path from the TextFilePath text field,
         * and writes the dictionary entries to the specified file. Each entry
         * is written along with its frequency and definition, sorted by frequency
         * in descending order. If the file cannot be found or created, a
         * FileNotFoundError is thrown, and an error message is displayed in the
         * TextArea.
         * </p>
         *
         * @throws FileNotFoundError if the file cannot be found or created
         */
        private void handleExport() {
            String filePath = TextFilePath.getText();
            File file = new File(filePath);
            //priority queue to sort the dictionary in descending order by frequency
            PriorityQueue<Map.Entry<String, Word>> subDictionary = new PriorityQueue<>((e1, e2) -> Integer.compare(e2.getValue().getFreq(), e1.getValue().getFreq()));
            subDictionary.addAll(dictionary.entrySet());
            try (PrintWriter out = new PrintWriter(file)) {
                while (!subDictionary.isEmpty()) {
                    Map.Entry<String, Word> entry = subDictionary.poll();
                    String word = entry.getKey();
                    Word wordDef = entry.getValue();
                    //using out.print with \n because printLine does \r\n
                    out.print(word + "\n");
                    out.print(wordDef.getFreq() + "\n");
                    out.print(wordDef.getDefinition() + "\n");
                    out.print("\n");

                }
            } catch (IOException e) {
                TextArea.setText("File not found:" + filePath);
                throw new FileNotFoundError("File not found:" + filePath);
            }
        }

        /**
         * Imports a dictionary from a file.
         * <p>
         * This method retrieves the file path from the TextFilePath text field,
         * and reads the dictionary entries from the specified file. Each entry
         * is read along with its frequency and definition, with no sorting required.
         * If the file cannot be found, a FileNotFoundError is thrown, and an error
         * message is displayed in the TextArea.
         * </p>
         *
         * @throws FileNotFoundError   if the file cannot be found
         * @throws InvalidWordError    if a word in the file is invalid
         * @throws WordDuplicatedError if a word in the file is duplicated
         */
        private void handleImport() {
            String filePath = TextFilePath.getText();
            File file = new File(filePath);
            try {
                //using a scanner to read from file very easy
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine().trim();
                    String definition = scanner.nextLine().trim();
                    Word wordDef = new Word(0, definition);
                    if (scanner.hasNextLine()) {
                        scanner.nextLine();//skip blank line
                    }
                    if (!word.matches("[a-zA-Z]+")) {
                        TextArea.setText("This word is Invalid: " + word);
                        throw new InvalidWordError("This is an invalid Word: " + word);
                    } else if (!dictionary.containsKey(word)) {
                        dictionary.put(word, wordDef);
                    } else {
                        TextArea.setText("This word already exists:" + word);
                        throw new WordDuplicatedError("This word already exists:" + word);

                    }

                }
                TextArea.setText("Dictionary imported successfully");
            } catch (FileNotFoundException e) {
                TextArea.setText("File not found:" + filePath);
                throw new FileNotFoundError("File not found:" + filePath);
            }
        }

        /**
         * Adds a new word to the dictionary.
         * <p>
         * This method retrieves the word from the TextNewWord text field,
         * and the definition from the TextArea text area. It then checks
         * if the word is valid, and if it already exists in the dictionary.
         * If the word is invalid, an InvalidWordError is thrown. If the word
         * already exists, a WordDuplicatedError is thrown.
         * </p>
         *
         * @throws InvalidWordError    if the word is invalid
         * @throws WordDuplicatedError if the word already exists in the dictionary
         */
        private void handleAdd() throws InvalidWordError, WordDuplicatedError {
            String word = TextNewWord.getText().replaceAll("[ \t]", "");
            int freq = 0;
            String def = TextArea.getText();
            if (!word.matches("[a-zA-Z]+")) {
                TextArea.setText("This word is Invalid: " + word);
                throw new InvalidWordError("This is an invalid Word: " + word);
            }
            if (!dictionary.containsKey(word)) {
                dictionary.put(word, new Word(freq, def));


            } else {
                TextArea.setText("This word Already Exists: " + word);
                throw new WordDuplicatedError("This word Already Exists: " + word);
            }
        }

        /**
         * Finds words in the dictionary that match the given word.
         * <p>
         * This method retrieves the word from the TextNewWord text field,
         * and checks if the word is valid. If the word is invalid, an
         * InvalidWordError is thrown. It then searches the dictionary for
         * words that contain the given word, and displays the top 3 words
         * with the highest frequency in the TextFreqWord1, TextFreqWord2,
         * and TextFreqWord3 text fields. It also displays the definitions
         * of the words in the TextArea text area.
         * </p>
         *
         * @throws InvalidWordError  if the word is invalid
         * @throws WordNotFoundError if no word matches the given word
         */
        private void handleFind() throws WordNotFoundError {
            String key1;
            String key2;
            String key3;
            String key1Def = "";
            String key2Def = "";
            String key3Def = "";
            PriorityQueue<Map.Entry<String, Word>> subDictionary = new PriorityQueue<>((e1, e2) -> Integer.compare(e2.getValue().getFreq(), e1.getValue().getFreq()));
            String word = TextNewWord.getText().replaceAll("[ \t]", "");
            //search history stuff
            boolean duplicate = false;
            int itemCount = searchHistoryList.getItemCount();
            //duplicate check for search history
            for (int i = 0; i < itemCount; i++) {
                if (searchHistoryList.getItemAt(i).equals(word)) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                searchHistoryList.insertItemAt(word, 0);
                searchHistoryList.setSelectedIndex(0);
            }
            //invalid check
            if (!word.matches("[a-zA-Z]+") && !word.isEmpty()) {
                TextArea.setText("This word is Invalid: " + word);
                throw new InvalidWordError("This is an invalid Word: " + word);
            }
            //add values to the priority queue
            for (Map.Entry<String, Word> entry : dictionary.entrySet()) {
                if (entry.getKey().contains(word)) {
                    subDictionary.add(entry);
                }
            }
            //assign values to key check if subDictionary is empty
            key1 = !subDictionary.isEmpty() ? subDictionary.poll().getKey() : "";
            key2 = !subDictionary.isEmpty() ? subDictionary.poll().getKey() : "";
            key3 = !subDictionary.isEmpty() ? subDictionary.poll().getKey() : "";
            //assign values to definition with formatted string
            if (!Objects.equals(key1, "")) {
                dictionary.get(key1).incFreq();
                key1Def = ": " + dictionary.get(key1).getDefinition();

            }
            if (!Objects.equals(key2, "")) {
                dictionary.get(key2).incFreq();
                key2Def = ": " + dictionary.get(key2).getDefinition();
            }
            if (!Objects.equals(key3, "")) {
                dictionary.get(key3).incFreq();
                key3Def = ": " + dictionary.get(key3).getDefinition();
            }

            TextFreqWord1.setText(key1);
            TextFreqWord2.setText(key2);
            TextFreqWord3.setText(key3);
            String txtString = String.format("%s %s \n %s %s\n %s %s", key1, key1Def, key2, key2Def, key3, key3Def);
            //no words matched dont throw error
            if (subDictionary.isEmpty() || word.isEmpty()) {

                TextArea.setText("No Word Matched.");

            } else {
                TextArea.setText(txtString);
            }
            //max search history =10
            if (itemCount >= 10) {
                searchHistoryList.removeItemAt(10);
            }


        }


    }

    /**
     * A class representing a word with its frequency and definition.
     */
    class Word {
        /**
         * The frequency of the word.
         */
        private int freq;
        /**
         * The definition of the word.
         */
        private String definition;

        /**
         * The constructor for the Word class.
         *
         * @param freq       the frequency of the word
         * @param definition the definition of the word
         */
        public Word(int freq, String definition) {
            this.freq = freq;
            this.definition = definition;
        }


        /**
         * Gets the frequency of the word.
         *
         * @return the frequency of the word
         */
        public int getFreq() {
            return freq;
        }

        /**
         * Increments the frequency of the word by one.
         */
        public void incFreq() {
            this.freq++;
        }

        /**
         * Gets the definition of the word.
         *
         * @return the definition of the word
         */
        public String getDefinition() {
            return definition;
        }

        /**
         * Sets the definition of the word.
         *
         * @param definition the new definition to set
         */
        public void setDefinition(String definition) {
            this.definition = definition;
        }
    }


}

/**
 * This class represents an error that occurs when a word is invalid.
 * <p>
 * It extends {@link RuntimeException}, allowing it to be used as an unchecked exception.
 * </p>
 */
class InvalidWordError extends RuntimeException {
    /**
     * Constructs a new InvalidWordError with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidWordError(String message) {
        super(message);
    }
}

/**
 * This class represents an error that occurs when a word is not found.
 * <p>
 * It extends {@link RuntimeException}, allowing it to be used as an unchecked exception.
 * </p>
 */
class WordNotFoundError extends RuntimeException {
    /**
     * Constructs a new WordNotFoundError with the specified detail message.
     *
     * @param message the detail message
     */
    public WordNotFoundError(String message) {
        super(message);
    }
}

/**
 * This class represents an error that occurs when a word is duplicated.
 * <p>
 * It extends {@link RuntimeException}, allowing it to be used as an unchecked exception.
 * </p>
 */
class WordDuplicatedError extends RuntimeException {
    /**
     * Constructs a new WordDuplicatedError with the specified detail message.
     *
     * @param message the detail message
     */
    public WordDuplicatedError(String message) {
        super(message);
    }
}

/**
 * This class represents an error that occurs when a file is not found.
 * <p>
 * It extends {@link RuntimeException}, allowing it to be used as an unchecked exception.
 * </p>
 */
class FileNotFoundError extends RuntimeException {
    /**
     * Constructs a new FileNotFoundError with the specified detail message.
     *
     * @param message the detail message
     */
    public FileNotFoundError(String message) {
        super(message);
    }
}