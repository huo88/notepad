package com.example.t1;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SimpleNotepad extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JColorChooser colorChooser;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, formatMenu;
    private JMenuItem newItem, openItem, saveItem, exitItem, copyItem, pasteItem, colorItem, cutItem, fontItem;
    private JToolBar toolBar;
    private JLabel statusLabel;

    public SimpleNotepad() {
        setTitle("JAVA记事本");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置背景颜色
        getContentPane().setBackground(new Color(240, 240, 240));

        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.PINK, 5),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        fileChooser = new JFileChooser();
        colorChooser = new JColorChooser();

        menuBar = new JMenuBar();
        fileMenu = new JMenu("文件");
        editMenu = new JMenu("编辑");
        formatMenu = new JMenu("格式");

        newItem = new JMenuItem("新建", new ImageIcon("icons/new.png"));
        openItem = new JMenuItem("打开", new ImageIcon("icons/open.png"));
        saveItem = new JMenuItem("保存", new ImageIcon("icons/save.png"));
        exitItem = new JMenuItem("退出", new ImageIcon("icons/exit.png"));
        copyItem = new JMenuItem("复制", new ImageIcon("icons/copy.png"));
        pasteItem = new JMenuItem("粘贴", new ImageIcon("icons/paste.png"));
        colorItem = new JMenuItem("颜色", new ImageIcon("icons/color.png"));
        cutItem = new JMenuItem("剪切", new ImageIcon("icons/cut.png"));
        fontItem = new JMenuItem("字体", new ImageIcon("icons/font.png"));

        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        colorItem.addActionListener(this);
        cutItem.addActionListener(this);
        fontItem.addActionListener(this);

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(colorItem);

        formatMenu.add(fontItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        setJMenuBar(menuBar);

        toolBar = new JToolBar();
        toolBar.add(newItem);
        toolBar.add(openItem);
        toolBar.add(saveItem);
        toolBar.addSeparator();
        toolBar.add(cutItem);
        toolBar.add(copyItem);
        toolBar.add(pasteItem);
        toolBar.addSeparator();
        toolBar.add(colorItem);
        toolBar.addSeparator();
        toolBar.add(fontItem);

        add(toolBar, BorderLayout.NORTH);

        statusLabel = new JLabel("JAVA记事本");
        add(statusLabel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newItem) {
            textArea.setText("");
            statusLabel.setText("新建文件");
        } else if (e.getSource() == openItem) {
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textArea.read(reader, null);
                    statusLabel.setText("打开文件: " + file.getPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == saveItem) {
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    textArea.write(writer);
                    statusLabel.setText("保存文件: " + file.getPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == exitItem) {
            System.exit(0);
        } else if (e.getSource() == copyItem) {
            textArea.copy();
            statusLabel.setText("复制");
        } else if (e.getSource() == pasteItem) {
            textArea.paste();
            statusLabel.setText("粘贴");
        } else if (e.getSource() == colorItem) {
            Color color = colorChooser.showDialog(this, "选择颜色", textArea.getForeground());
            if (color != null) {
                textArea.setForeground(color);
                statusLabel.setText("设置颜色");
            }
        } else if (e.getSource() == cutItem) {
            textArea.cut();
            statusLabel.setText("剪切");
        } else if (e.getSource() == fontItem) {
            Font currentFont = textArea.getFont();
            Font newFont = JFontChooser.showDialog(this, "选择字体", currentFont);
            if (newFont != null) {
                textArea.setFont(newFont);
                statusLabel.setText("设置字体");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleNotepad notepad = new SimpleNotepad();
            notepad.setVisible(true);
        });
    }
}
