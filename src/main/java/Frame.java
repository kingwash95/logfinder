
import java.awt.*;

public class Frame extends javax.swing.JFrame {

    public Frame(){
        setTitle("File Finder");
        Panel panel = new Panel();
        Container container = getContentPane();
        container.add(panel);
        setDefaultCloseOperation(3);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height );
        setResizable(true);
        setVisible(true);

    }
}
