import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Panel extends JPanel implements TreeSelectionListener {
    private FileFinder fileFinder;
    private JTextField addresField;
    private JButton directoryButton;
    private JFileChooser fileChooser;
    private String[] typeOfFiles = {".log", ".txt"};
    private JComboBox fileType;
    private JTextArea textField;
    private JScrollPane scrollBar;
    private JButton searchButton;

    private MTreeModel treeModel;
    private JTree tree;
    private JEditorPane htmlPane;
    private JSplitPane splitPane;


    public Panel() {
        fileFinder = new FileFinder();


        addresField = new JTextField("Введите адрес директории, где будет производиться поиск файлов", 50);
        addresField.setToolTipText("Поле для ввода адреса директории");

        addresField.setFont(new Font("Dialog", Font.PLAIN, 14));
        addresField.setHorizontalAlignment(JTextField.LEFT);

        directoryButton = new JButton("...");
        fileChooser = new JFileChooser();
        directoryButton.addActionListener(e -> chooseDirectory());

        fileType = new JComboBox(typeOfFiles);
        fileType.setSelectedIndex(0);

        textField = new JTextArea("Введите текст для поиска", 1, 50);
        textField.setFont(new Font("Dialog", Font.PLAIN, 14));
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        scrollBar = new JScrollPane(textField);
        searchButton = new JButton("Начать поиск");
        searchButton.addActionListener(e -> new JThread("JThread").start());

        treeModel = new MTreeModel();
        tree = new JTree(treeModel);

        tree.addTreeSelectionListener(this);
        JScrollPane treeView = new JScrollPane(tree);
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        JScrollPane htmlView = new JScrollPane(htmlPane);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(1000, 300);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(300);
        splitPane.setPreferredSize(new Dimension(1000, 300));


        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(new JLabel("Адрес директории: ", SwingConstants.LEFT));
        this.add(addresField);
        this.add(directoryButton);

        this.add(new JLabel("         Тип файла для поиска: ", SwingConstants.LEFT));
        this.add(fileType);
        this.add(new JLabel("Текст для поиска:", SwingConstants.LEFT));
        this.add(scrollBar);
        this.add(searchButton);

        this.add(splitPane);
    }


    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) return;
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            File file = new File((String) nodeInfo);
            try {
                displayURL(file.toURI().toURL());
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void chooseDirectory() {
        fileChooser.setDialogTitle("Выбор директории");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(getParent());
        if (result == JFileChooser.APPROVE_OPTION)
            addresField.setText(fileChooser.getSelectedFile().getAbsolutePath());

    }

    private void searchFiles() {
        String addresString = addresField.getText();
        String typeFileString = (String) fileType.getSelectedItem();

        String textString = textField.getText();

        File fileDirectory = new File(addresString);
        if (fileDirectory.exists()) {
            ArrayList<String> filesInDirectory = new ArrayList<String>();
            String pattern = ".*\\" + typeFileString;
            ArrayList<String> allFind = new ArrayList<String>();
            fileFinder.search(pattern, fileDirectory, filesInDirectory);
            for (String s : filesInDirectory) {
                try {
                    fileFinder.searchFile(s, textString, allFind);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (allFind.size() == 0) {
                JOptionPane.showMessageDialog(Panel.this, "Файлы не были найдены",
                        "Окно сообщения", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (String s : allFind) {
                    System.out.println(s + "\n");
                }
                treeModel.load(allFind);
            }
        } else {
            JOptionPane.showMessageDialog(Panel.this,
                    "Вы указали несуществующую директорию, повторите поиск заново",
                    "Окно сообщения", JOptionPane.INFORMATION_MESSAGE);
        }


    }

    private void displayURL(URL url) {
        try {
            if (url != null) {
                htmlPane.setPage(url);
            } else {
                htmlPane.setPage("FILE NOT CHOSEN ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class JThread extends Thread {

        JThread(String name) {
            super(name);
        }

        public void run() {
            System.out.printf("%s started... \n", Thread.currentThread().getName());
            searchFiles();
            System.out.printf("%s finished... \n", Thread.currentThread().getName());
        }
    }

}
