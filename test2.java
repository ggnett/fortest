import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class test2  {
    JButton but;
    JFrame frame;
    int x;;
    int y;

    public static void main(String[] args){
        test2 gui = new test2();
        gui.go(50,20);
    }

        public void go(int xi, int yi) {
            x = xi;
            y = yi;
        MeDrawPanel gt = new MeDrawPanel();
        frame = new JFrame();
        but = new JButton("clisdfsdfsdfsfsfsdck");
        JButton but2 = new JButton("back");
        but.setBackground(Color.orange);
        but.addActionListener(new butact());
        but2.addActionListener(new but2act());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.SOUTH, but);
        frame.getContentPane().add(BorderLayout.CENTER, gt);
        frame.getContentPane().add(BorderLayout.NORTH,but2);
        frame.setSize(300,300);
        frame.setVisible(true);

        }
        class butact implements ActionListener {
        public void actionPerformed(ActionEvent event) {
           for (int i = 0;i<100;i++) {
            x++;
            y++;
            frame.repaint();
           }
        }
        }

        class but2act implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                frame.repaint();
                x -=5;
                y-= 5;
            }
            }

        class MeDrawPanel extends JPanel{
            public void paintComponent(Graphics g) {
                g.setColor(Color.black);
                g.fillRect(0,0,this.getWidth(),this.getHeight());
                int red = (int) (Math.random()*255);
                int green = (int) (Math.random()*255);
                int blue = (int) (Math.random()*255);
                Color cl = new Color(red,green,blue);
                g.setColor(cl);
                g.fillOval(x,y,100,100);
            }
        }
    }

