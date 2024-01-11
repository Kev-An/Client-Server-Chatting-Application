package chatting.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
    JTextField text;
    JPanel a1;
   static Box vertical=Box.createVerticalBox();
   static JFrame f=new JFrame();
  static DataOutputStream dout;
    Server(){
        
       f.setLayout(null);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(10,42,75));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i2=i1.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back= new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
       p1.add(back);
       back.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent ae){
               f.setVisible(false);
               System.exit(0);
           }
        
    });
       
       ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/Letter_K.png"));
        Image i5=i4.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile= new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
       p1.add(profile);
       
       JLabel name=new JLabel("Kevin");
       name.setBounds(110,22,100,18);
       name.setForeground(Color.WHITE);
       name.setFont(new Font("SAN SERIF",Font.BOLD,20));
       p1.add(name);
       
       a1=new JPanel();
       a1.setBounds(1,75,440,450);
       f.add(a1);
       
       text=new JTextField();
       text.setBounds(5,530,310,40);
       text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
      f.add(text);
       JButton send= new JButton("Send");
       send.setBounds(320,530,123,40);
       send.addActionListener(this);
       f.add(send);
       
        f.setSize(450,600);
        f.setLocation(200,50);
        f.getContentPane().setBackground(Color.WHITE);
       
        f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        try{
     String out=text.getText();
     JPanel p2= formatLabel(out);
     a1.setLayout(new BorderLayout());
     JPanel right=new JPanel(new BorderLayout());
     right.add(p2,BorderLayout.LINE_END);
     vertical.add(right);
     vertical.add(Box.createVerticalStrut(15));
     a1.add(vertical,BorderLayout.PAGE_START);
     dout.writeUTF(out);
     text.setText("");
     f.repaint();
     f.invalidate();
     f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
     
    }
    public static JPanel formatLabel(String out){
       JPanel panel=new JPanel();
       panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
       JLabel output=new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
       output.setForeground(Color.WHITE);
       output.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
       output.setBackground(new Color(10,42,75));
       output.setOpaque(true);
       output.setBorder(new EmptyBorder(15,15,15,50));
       panel.add(output);
       Calendar cal= Calendar.getInstance();
       SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
       JLabel time= new JLabel();
       time.setText(sdf.format(cal.getTime()));
       panel.add(time);
       return panel;
    }
    
    public static void main(String[] args){
        new Server();
        
        try{
            ServerSocket skt= new ServerSocket(6001);
            while(true){
                Socket s=skt.accept();
                DataInputStream din= new DataInputStream(s.getInputStream());
                 dout=new DataOutputStream(s.getOutputStream());
                while(true){
                    String msg=din.readUTF();
                    JPanel panel= formatLabel(msg);
                    
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
