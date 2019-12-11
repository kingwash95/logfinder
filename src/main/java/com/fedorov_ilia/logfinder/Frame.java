package com.fedorov_ilia.logfinder;

import java.awt.*;

public class Frame extends javax.swing.JFrame {

    public Frame(){
        setTitle("File Finder");
        Panel panel = new Panel();
        Container container = getContentPane();
        container.add(panel);
        setDefaultCloseOperation(3);
        setBounds(0, 0, 1050, 500 );
        setResizable(false);
        setVisible(true);


    }
}
