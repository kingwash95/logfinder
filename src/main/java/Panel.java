import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Panel extends JPanel {
    FileFinder fileFinder;
    //создание текстового поля для адреса
    JTextField addresField;
    //создание кнопки для добавления адреса
    JButton directoryButton;
    JFileChooser fileChooser;
    String[] typeOfFiles = {".log", ".txt", ".doc"};
    JComboBox fileType;
    JTextArea textField;
    JScrollPane scrollBar;
    JButton searchButton;

    public Panel() {
        fileFinder = new FileFinder();

        // Создание текстовых полей
        addresField = new JTextField("Введите адрес директории, где будет производиться поиск файлов", 50);
        addresField.setToolTipText("Поле для ввода адреса директории");
        // Настройка шрифта
        addresField.setFont(new Font("Dialog", Font.PLAIN, 14));
        addresField.setHorizontalAlignment(JTextField.LEFT);
        //Добавление кнопки
        directoryButton = new JButton("...");
        fileChooser = new JFileChooser();
        directoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Выбор директории");
                // Определение режима - только каталог
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(getParent());
                // Если директория выбрана, занесем в поле адрес директории
                if (result == JFileChooser.APPROVE_OPTION)
                    addresField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        fileType = new JComboBox(typeOfFiles);
        fileType.setSelectedIndex(0);

        textField = new JTextArea("Введите текст для поиска", 10, 40);
        textField.setFont(new Font("Dialog", Font.PLAIN, 14));
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        scrollBar = new JScrollPane(textField);
        searchButton = new JButton("Начать поиск");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String addresString = addresField.getText();
                String typeFileString = (String) fileType.getSelectedItem();

                String textString = textField.getText();
                StringBuffer addresBuffer = new StringBuffer("");
                for (int i = 0; i < addresString.length(); i++) {
                    char c = addresString.charAt(i);
                    if (c == '\\') {
                        addresBuffer.append('\\');
                    } else {
                        addresBuffer.append(c);
                    }
                }
                String newAddres = addresBuffer.toString();
                File fileDirectory = new File(newAddres);
                ArrayList<String> filesInDirectory = new ArrayList<String>();
                String pattern = ".*\\" + typeFileString;
                ArrayList<String> allFind = new ArrayList<String>();
                fileFinder.search(pattern, fileDirectory, filesInDirectory);
                for (String s : filesInDirectory) {
                    try {
                        fileFinder.searchFile2(s, textString, allFind);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                for (String s : allFind) {
                    System.out.println(s + "\n");
                }
                TreeDemo trees = new TreeDemo();

            }
        });



        //все смещается влево
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(new JLabel("Адрес директории: ", SwingConstants.LEFT));
        this.add(addresField);
        this.add(directoryButton);
        this.add(new JLabel("Тип файла для поиска: ", SwingConstants.LEFT));
        this.add(fileType);
        this.add(new JLabel("Текст для поиска: ", SwingConstants.LEFT));
        this.add(scrollBar);
        this.add(searchButton);


    }

}
