import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LeafWindow {

    public static void main(String[] args) {
        new LeafWindow();
    }

    public LeafWindow() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JWindow frame = new JWindow();
                frame.setBackground(new Color(0, 0, 0, 0));
                frame.setContentPane(new LeafPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setAlwaysOnTop(false);
            }
        });
    }

    public class LeafPane extends JPanel {

        private BufferedImage leaf;

        public LeafPane() {

            setBorder(new CompoundBorder(
                            new LineBorder(Color.RED),
                            new EmptyBorder(0, 0, 250, 0)));

            try {
                leaf = ImageIO.read(getClass().getResource("/Leaf.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            setOpaque(false);
            setLayout(new GridBagLayout());

            JButton button = new JButton("Close");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            add(button);

        }

        @Override
        public Dimension getPreferredSize() {
            return leaf == null ? new Dimension(200, 200) : new Dimension(leaf.getWidth(), leaf.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (leaf != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(leaf, 0, 0, this);
                g2d.dispose();
            }
        }
    }

}