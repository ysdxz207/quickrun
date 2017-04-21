package com.puyixiaowo.quickrun.test;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Test {
    public static void main(String[] args) {
        ListFrame f = new ListFrame();
        f.setSize(600, 400);
        f.setTitle("Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

class ListFrame extends JFrame {
    /**
     * *
     */
    private static final long serialVersionUID = 1L;
    private int first;
    private int sec;

    public ListFrame() {
        JPanel panel = new JPanel();
        this.getContentPane().add(panel, BorderLayout.CENTER);
        final DefaultListModel m = new DefaultListModel();
        for (int i = 0; i < 15; i++) m.addElement("0000" + i);
        final JList list = new JList(m);
        list.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                first = list.locationToIndex(e.getPoint());
                System.out.println(first);
            }

            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                sec = list.locationToIndex(e.getPoint());
                System.out.println(sec);
                if (sec != -1) {
                    String s1 = m.getElementAt(first).toString();
                    String s2 = m.getElementAt(sec).toString();
                    if (first != sec) {
                        if (first < sec) {
                            m.add(sec, s1);
                            m.remove(first);
                        } else {
                            m.remove(first);
                            m.add(sec, s1);
                        }
                        list.clearSelection();
                    }
                }
            }
        });
        panel.add(list);
    }
}
