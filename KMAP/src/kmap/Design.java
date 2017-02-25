/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmap;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.*;

/**
 *
 * @author MSaqib
 */
public class Design extends JPanel{
    
    /**
     *
     * @param g
     */
    
    public static int index = -1;
    
    
       public Design(){
           setPreferredSize(new Dimension(1000,1000));
       } 

    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D)g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        drawTable(g);
        highlightGroups(g);
    } 

    public static void drawTable(Graphics2D g) {
        int count = kmap.variables.length();
        System.err.println(count);
        int rows = count/2;
        int columns = count - rows;
        if(rows==0 || columns==0)
            return;
        int cell_width = columns*20+8;
        JLabel[][] mapLabels = new JLabel[rows][columns];
        for(int i=0;i< 1<<rows  ;i++){
            for(int j=0;j< 1<<columns ;j++){
                g.drawRect(80+j*cell_width,100+i*40,cell_width,40);
               
            }
        }
        if(kmap.kmap!=null){
            for(int i=0;i< 1<<rows;i++){
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
                g.drawString(kmap.kmap[i][0].id.substring(0,count/2),60-10*count/2, 130+i*40);
            }
            for(int i=0;i< 1<<columns;i++){
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
                g.drawString(kmap.kmap[0][i].id.substring(count/2,count),90+i*cell_width,90);
            }
            for(int i=0;i< 1<<rows  ;i++){
            for(int j=0;j< 1<<columns ;j++){
                g.drawRect(80+j*cell_width,100+i*40,cell_width,40);
                if(kmap.kmap[i][j].value!=-1)
               g.drawString(Integer.toString(kmap.kmap[i][j].value)
                       , 80+j*cell_width+cell_width/2,100+i*40+30 );
                else
                    g.drawString("X"
                       , 80+j*cell_width+cell_width/2,100+i*40+30 );
            }
        }
            
        }
        
    }

    private void highlightGroups(Graphics2D g) {
         int count = kmap.variables.length();
        System.err.println(count);
        int rows = count/2;
        int columns = count - rows;
        if(rows==0 || columns==0)
            return;
        int cell_width = columns*20+8;
      if(index == -1)
          return;

      kmap.Group gr = kmap.groups.get(index);
      for(Point p:gr.points){
          g.setColor(new Color(108,203,235));
           g.fillRect(80+p.y*cell_width,100+p.x*40,cell_width,40);
           g.setColor(Color.black);
             if(kmap.kmap[p.x][p.y].value!=-1)
            g.drawString(Integer.toString(kmap.kmap[p.x][p.y].value)
                       , 80+p.y*cell_width+cell_width/2,100+p.x*40+30 );
             else
                  g.drawString("X"
                       , 80+p.y*cell_width+cell_width/2,100+p.x*40+30 );
          g.setStroke(new BasicStroke(3));
           g.setColor(new Color(4,141,186));
           g.drawRect(80+p.y*cell_width,100+p.x*40,cell_width,40);
           
      }
            index = -1;
    }
  
    
}
