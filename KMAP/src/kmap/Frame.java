/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MSaqib
 */
public class Frame extends JFrame implements MouseListener{
    
    public static JTextField inputField;
    public static JTextField dcField;
    public static JLabel inputLabel;
    public static JLabel dcLabel;
    public static JTextField outputField;
    public static JLabel outputLabel;
    public static JPanel mapPanel;
    public static JButton submit;
    public static JTable groupTable;
    
    Frame(){
        /**Initialize Frame*/
        JFrame frame = new JFrame("K-Map Simplifier");
        frame.setSize(2000, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        inputLabel = new JLabel("Input Expression");
        inputLabel.setBounds(100,50,150,30);
        frame.add(inputLabel);
        
        inputField = new JTextField();
        inputField.setBounds(250,50,700,30);
        inputField.setText("abcd + abCD + ABCd + ABcd + AbCD + aBCdefgh");
        frame.add(inputField);
        
        dcLabel = new JLabel("Don't Care");
        dcLabel.setBounds(100,100,150,30);
        frame.add(dcLabel);
        
        dcField = new JTextField();
        dcField.setBounds(250,100,700,30);
        dcField.setText("abcd + abCD + ABCd + ABcd + AbCD + aBCdefgh");
        frame.add(dcField);
        
        outputLabel = new JLabel("Output Expression");
        outputLabel.setBounds(100,920,150,30);
        frame.add(outputLabel);
        
        outputField = new JTextField();
        outputField.setBounds(250,920,700,30);
        frame.add(outputField);
        
        mapPanel = new Design();
        //mapPanel.setBackground(Color.white);
        //mapPanel.setBounds(10,150,1500,750);
        
        //frame.add(mapPanel);
       JScrollPane scrollPane1 = new JScrollPane(mapPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   // scrollPane1.setPreferredSize(new Dimension(600, 600));
     scrollPane1.setLocation(10, 150);
        scrollPane1.setSize(new Dimension(1500, 750));
    frame.getContentPane().add(scrollPane1);
        
        groupTable = new JTable(new DefaultTableModel(new Object[][]{},new String[]{"GROUPS"}));
        groupTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                int r = groupTable.getSelectedRow();
                System.err.println(r);
                Design.index = r;
                mapPanel.repaint();
                
                
              
            }
                
        });
        JScrollPane scrollPane = new JScrollPane(groupTable);
        scrollPane.getViewport().add(groupTable);
        scrollPane.setLocation(1560, 150);
        scrollPane.setSize(new Dimension(350, 800));
        frame.getContentPane().add(scrollPane);
        
       
        submit = new JButton("SUBMIT");
        submit.setBounds(1000, 50, 100, 30);
        submit.addActionListener(onSubmit);
        frame.add(submit);
        frame.setVisible(true);
           
    } 
    
    
      public void addEntries() {
            for(kmap.Group g:kmap.groups){
              String entry = "";
                if(!g.hasEvolved){
                    for(int t:g.numbers){
                        entry+=String.valueOf(t);
                        if(g.numbers.indexOf(t)!=g.numbers.size()-1)
                            entry+=",";
                    }
                    DefaultTableModel model = (DefaultTableModel) groupTable.getModel();
                    model.addRow(new Object[]{entry});
                }
                    
                }
            }
        
    
    ActionListener onSubmit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            kmap.kmap = null;
            kmap.groups = null;
          //  kmap.exp = null;
          //  kmap.dexp = null;
            outputField.setText("");
            kmap.variables = "";
            DefaultTableModel model = (DefaultTableModel) groupTable.getModel();
            while(groupTable.getRowCount()!=0)
                model.removeRow(0);
            kmap.parseInput(inputField.getText());
            kmap.parseDontcare(dcField.getText());
            kmap.makeHeadings();
            mapPanel.repaint();
            kmap.formGroups();
            addEntries();
            outputField.setText(kmap.calculateOutput());
                    
        }

      
    };

    @Override
    public void mouseClicked(MouseEvent me) {
     //   System.err.println("Clicked");
       //    System.out.println(groupTable.rowAtPoint(me.getPoint()));
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
