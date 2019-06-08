package com.notepad;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame implements ActionListener {
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu file = new JMenu("文件");
    private JMenuItem open = new JMenuItem("打开");
    private JMenuItem save = new JMenuItem("保存");

    private JScrollPane jScrollPane = new JScrollPane();
    private JTextArea jTextArea = new JTextArea();

    private JFileChooser jFileChooser = new JFileChooser();

    public MainFrame() {
        this.setSize(500, 300);
        this.setLocation(500, 100);
        this.setTitle("记事本");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        file.add(open);
        file.add(save);
        jMenuBar.add(file);

        jScrollPane.setViewportView(jTextArea);

        this.add(jMenuBar, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setVisible(true);

        open.addActionListener(this);
        save.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            try {
                openFile();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == save) {
            try {
                saveFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void openFile() throws IOException {
        jFileChooser.setFileFilter(new FileNameExtensionFilter("文本文件(*.txt)","txt"));
        jFileChooser.showOpenDialog(this);
        File file = jFileChooser.getSelectedFile();
        if (file != null) {
            String result = "";
            BufferedReader br = new BufferedReader(new FileReader(file));
            while(true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                result += line + "\r\n";
            }
            br.close();
            jTextArea.setText(result);
        }
    }

    public void saveFile() throws IOException {
        jFileChooser.showSaveDialog(this);
        File file = jFileChooser.getSelectedFile();

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(jTextArea.getText());
        fileWriter.flush();
        fileWriter.close();
    }
}
