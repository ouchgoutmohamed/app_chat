package chat.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client_chat implements ActionListener {
    
    JTextField textField;
    static JPanel panel1;
    static Box vertical = Box.createVerticalBox();
    static JFrame fra = new JFrame();
    static DataOutputStream dataOut;
    
    Client_chat() {
        
        fra.setLayout(null);
        
        JPanel pan1 = new JPanel();
        pan1.setBackground(new Color(7, 94, 84));
        pan1.setBounds(0, 0, 450, 70);
        pan1.setLayout(null);
        fra.add(pan1);
        
        ImageIcon image1 = new ImageIcon(ClassLoader.getSystemResource("icons/out.png"));
        Image image2 = image1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel back = new JLabel(image3);
        back.setBounds(5, 20, 25, 25);
        pan1.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        
        ImageIcon image4 = new ImageIcon(ClassLoader.getSystemResource("icons/user.png"));
        Image image5 = image4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon image6 = new ImageIcon(image5);
        JLabel profile = new JLabel(image6);
        profile.setBounds(40, 10, 50, 50);
        pan1.add(profile);
        
        ImageIcon image7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image image8 = image7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon image9 = new ImageIcon(image8);
        JLabel video = new JLabel(image9);
        video.setBounds(300, 20, 30, 30);
        pan1.add(video);
        

        ImageIcon image10 = new ImageIcon(ClassLoader.getSystemResource("icons/tele.png"));
        Image image11 = image10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon image12 = new ImageIcon(image11);
        JLabel phone = new JLabel(image12);
        phone.setBounds(360, 20, 35, 30);
        pan1.add(phone);
        
        ImageIcon image13 = new ImageIcon(ClassLoader.getSystemResource("icons/par.png"));
        Image image14 = image13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon image15 = new ImageIcon(image14);
        JLabel morevert = new JLabel(image15);
        morevert.setBounds(420, 20, 10, 25);
        pan1.add(morevert);
        
        JLabel nom_user = new JLabel("Ouchgout");
        nom_user.setBounds(110, 15, 100, 18);
        nom_user.setForeground(Color.WHITE);
        nom_user.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        pan1.add(nom_user);
        
        JLabel status_user = new JLabel("Online");
        status_user.setBounds(110, 35, 100, 18);
        status_user.setForeground(Color.WHITE);
        status_user.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        pan1.add(status_user);
        
        panel1 = new JPanel();
        panel1.setBounds(5, 75, 440, 570);
        fra.add(panel1);
        
        textField = new JTextField();
        textField.setBounds(5, 655, 310, 40);
        textField.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        fra.add(textField);
        
        JButton envoyer = new JButton("Envoyer");
        envoyer.setBounds(320, 655, 123, 40);
        envoyer.setBackground(new Color(7, 94, 84));
        envoyer.setForeground(Color.WHITE);
        envoyer.addActionListener(this);
        envoyer.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        fra.add(envoyer);
        
        fra.setSize(450, 700);
        fra.setLocation(800, 50);
        fra.setUndecorated(true);
        fra.getContentPane().setBackground(Color.WHITE);
        
        fra.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = textField.getText();

            JPanel p2 = formatLabel(out);

            panel1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            panel1.add(vertical, BorderLayout.PAGE_START);

            dataOut.writeUTF(out);

            textField.setText("");

            fra.repaint();
            fra.invalidate();
            fra.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String[] args) {
        new Client_chat();
        
        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dataOut = new DataOutputStream(s.getOutputStream());
            
            while(true) {
                panel1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                panel1.add(vertical, BorderLayout.PAGE_START);
                
                fra.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
