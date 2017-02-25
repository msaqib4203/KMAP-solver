/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmap;

import java.awt.Point;
import static java.lang.Math.pow;
import java.util.ArrayList;
/**
 *
 * @author MSaqib
 */
public class kmap {

    static String variables = "";
    static int R;
    static int C;
    static int rows;
    static int columns;
    static KMAPBlock kmap[][] = null;
    static String exp[];
    static String dexp[];
    static ArrayList<Group> groups = null;
    
    /**
     * @param args the command line arguments
     */
    public static boolean existsVar(char x){
        for(int i=0;i<variables.length();i++){
            char var = variables.charAt(i);
            if(x < 97){
                if(x == var)
                return true;
            }
            else{
                if( var  == x - 32)
                    return true;
            }
        }
        return false;
    }
    
     public static int findValue(String string) {
        int value = 0;
        for(int i=0;i<string.length();i++){
            for(int j=0;j<variables.length();j++){
                if(variables.charAt(j) == string.charAt(i)){
                    value = (int) (value + pow(2,j));
                    break;
                }
            }
        }
        return value;
   }

    
    
     public static class KMAPBlock{
         String id;
         int value;
         String bin;
         boolean isGrouped;
         int number;

        public KMAPBlock(String bin) {
            this.bin = bin;
            this.value = 0;
            this.number = Integer.parseInt(bin, 2);
            this.isGrouped = true;
        }
        
       
         
      }
     
     public static class Group{
         int size;
         int[] start = new int[2];
         int[] end = new int[2];
         boolean hasEvolved;
         ArrayList<Integer> numbers;
         ArrayList<Point> points;
         
         public Group(int n,int startr,int startc,int endr,int endc){
             
             this.size = n;
             this.start[0] = startr;
             this.start[1] = startc; 
             this.end[0] = endr;
             this.end[1] = endc;
             this.hasEvolved = false;
             this.numbers = new ArrayList<>();   
             this.points = new ArrayList<>();
         }
     }
     
     

     public static void parseInput(String s){
           s =  s.replaceAll("\\s", "");
       exp = s.split("\\+");
        s = s.replaceAll("\\+", "");
       for(int i=0;i<s.length();i++){
           if(!existsVar(s.charAt(i))){
               if(s.charAt(i)<97)
               variables = variables + s.charAt(i);
               else
                variables = variables + Character.toUpperCase(s.charAt(i));
           }
       }
        
        
        
     }
     
      public static void parseDontcare(String s){
           s =  s.replaceAll("\\s", "");
        
       dexp = s.split("\\+");
        s = s.replaceAll("\\+", "");
       /*for(int i=0;i<s.length();i++){
           if(!existsVar(s.charAt(i))){
               if(s.charAt(i)<97)
               variables = variables + s.charAt(i);
               else
                variables = variables + Character.toUpperCase(s.charAt(i));
           }
       }*/
       
       variables = variables.toUpperCase();
        rows = variables.length()/2;
        R = (int) pow(2,rows);
        columns = variables.length() - rows;
        C = (int) pow(2,columns);
        kmap = new KMAPBlock[R][C];
      }
     
     public static void makeHeadings(){
                
         /** Generate Vertical Gray Code*/
         String[] verticalCode = new String[R];
         String[] horizontalCode = new String[C];
         verticalCode[0] = "0";
         verticalCode[1] = "1";
         horizontalCode[0] = "0";
         horizontalCode[1] = "1";
          int c = 1;
    for (int i = 2; i < R; i = i<<1)
    {
        // Enter the prviously generated codes again in arr[] in reverse
        // order. Nor arr[] has double number of codes.
        for (int j = i-1 ; j >= 0 ; j--)
            verticalCode[++c] = verticalCode[j];
 
        // append 0 to the first half
        for (int j = 0 ; j < i ; j++)
            verticalCode[j] = "0" + verticalCode[j];
 
        // append 1 to the second half
        for (int j = i ; j < 2*i ; j++)
           verticalCode[j] = "1" + verticalCode[j];
    }
    
       c = 1;
    for (int i = 2; i < C; i = i<<1)
    {
        // Enter the prviously generated codes again in arr[] in reverse
        // order. Nor arr[] has double number of codes.
        for (int j = i-1 ; j >= 0 ; j--)
            horizontalCode[++c] = horizontalCode[j];
 
        // append 0 to the first half
        for (int j = 0 ; j < i ; j++)
            horizontalCode[j] = "0" + horizontalCode[j];
 
        // append 1 to the second half
        for (int j = i ; j < 2*i ; j++)
           horizontalCode[j] = "1" + horizontalCode[j];
    }
         
         
                
         for(int i=0;i<R;i++){
          
             for(int j=0;j<C;j++){
              
               KMAPBlock  temp =  new KMAPBlock(verticalCode[i]+horizontalCode[j]);
               kmap[i][j] = temp;
             }
         }
         
           for(int i=0;i<R;i++){
             for (int j=0;j<C;j++){
                 String name = kmap[i][j].bin;
                 String newname = "";
                 for(int k=0;k<kmap[i][j].bin.length();k++){
                     
                     if(name.charAt(k) == '1')
                         newname+= variables.charAt(k);
                   
                     else
                          newname+= Character.toLowerCase(variables.charAt(k));   
                 }
                 kmap[i][j].id = newname;
            
             }
         }
           /**set 1 blocks*/
            for(int i=0;i<exp.length;i++){
              for(int k=0;k<R;k++){
                  for(int m=0;m<C;m++){
                      String testid = kmap[k][m].id;
                      boolean has = true;
                      for(int j=0;j<exp[i].length();j++){
                          if(testid.indexOf(exp[i].charAt(j)) == -1){
                              has = false;
                              break;
                          }
                      }
                      if(has){
                          kmap[k][m].value = 1;
                          kmap[k][m].isGrouped = false;
                      }
                      
                          
                  }
              
          }
      }
            
            /**set don't care blocks*/
            for(int i=0;i<dexp.length&&dexp[i].length()!=0;i++){
              for(int k=0;k<R;k++){
                  for(int m=0;m<C;m++){
                      String testid = kmap[k][m].id;
                      boolean has = true;
                      for(int j=0;j<dexp[i].length();j++){
                          if(testid.indexOf(dexp[i].charAt(j)) == -1){
                              has = false;
                              break;
                          }
                      }
                      if(has){
                          kmap[k][m].value = -1;
                          kmap[k][m].isGrouped = true;
                          System.err.println(dexp[0]);
                          System.err.println("DC"+k+" "+m);
                      }
                      
                          
                  }
              
          }
      }
     }
     
     public static void formGroups(){
         groups = new ArrayList<>();
         //groups formation with 1's
     /**check right*/
     for(int i=0;i<R;i++){
         for(int j=0;j<C;j++){
             if(!kmap[i][j].isGrouped){
                 if(kmap[i][(j+1)%C].value != 0){
                     Group temp = new Group(2,i,j,i,(j+1));
                     temp.numbers.add(kmap[i][j].number);
                     temp.points.add(new Point(i,j));
                     temp.points.add(new Point(i,(j+1)%C));
                     temp.numbers.add(kmap[i][(j+1)%C].number);
                     kmap[i][(j+1)%C].isGrouped = true;
                     kmap[i][j].isGrouped = true;
                     groups.add(temp);
                     
                 }
             }
         }
     }
     
     /**check left*/
     for(int i=0;i<R;i++){
         for(int j=0;j<C;j++){
             if(!kmap[i][j].isGrouped){
                 if((j-1)>=0 && (kmap[i][j-1].value != 0)){   
                     Group temp = new Group(2,i,j-1,i,j);
                     kmap[i][j-1].isGrouped = true;
                     kmap[i][j].isGrouped = true;
                     temp.numbers.add(kmap[i][j].number);
                     temp.numbers.add(kmap[i][j-1].number);
                     temp.points.add(new Point(i,j));
                     temp.points.add(new Point(i,(j-1)));
                     groups.add(temp);
                      System.out.println("added "+(i-1)+" "+j+" "+i+" "+" "+j);             
                 }
                 else if((j-1)<0 && (kmap[i][C-1].value != 0)){
                     
                     Group temp = new Group(2,i,C-1,i,j+C);
                     kmap[i][C-1].isGrouped = true;
                     kmap[i][j].isGrouped = true;
                     temp.numbers.add(kmap[i][j].number);
                     temp.numbers.add(kmap[i][C-1].number);
                     temp.points.add(new Point(i,j));
                     temp.points.add(new Point(i,C-1));
                     groups.add(temp);
                      System.out.println("added "+i+" "+(C-1)+" "+i+" "+" "+j+C);
                 
                 }
             }
         }
     }
     
      /**Check up*/
     for(int i=0;i<R;i++){
         for(int j=0;j<C;j++){
             if(!kmap[i][j].isGrouped){
                if((i-1)>=0 &&(kmap[(i-1)][j].value != 0)){     
                     Group temp = new Group(2,i-1,j,i,j);
                     kmap[i][j].isGrouped = true;
                     kmap[(i-1)][j].isGrouped = true;
                     temp.numbers.add(kmap[i][j].number);
                     temp.numbers.add(kmap[i-1][j].number);
                     temp.points.add(new Point(i,j));
                     temp.points.add(new Point(i-1,j));
                     groups.add(temp);
                      System.out.println("added "+(i-1)+" "+j+" "+i+" "+" "+j);
                 }
                 else if((i-1)<0 && (kmap[R-1][j].value != 0)){
                    
                     Group temp = new Group(2,R-1,j,i+R,j);
                     kmap[R-1][j].isGrouped = true;
                     kmap[i][j].isGrouped = true;
                     temp.numbers.add(kmap[i][j].number);
                     temp.numbers.add(kmap[R-1][j].number);
                     temp.points.add(new Point(i,j));
                     temp.points.add(new Point(R-1,j));
                     groups.add(temp);
                      System.out.println("added "+(R-1)+" "+j+" "+(i+R)+" "+" "+j);
                 
                 }
             }
         }
     }
     /**check down*/
     for(int i=0;i<R;i++){
         for(int j=0;j<C;j++){
             if(!kmap[i][j].isGrouped){
                 if(kmap[(i+1)%R][j].value != 0){
                     Group temp = new Group(2,i,j,(i+1),j);
                     kmap[(i+1)%R][j].isGrouped = true;
                     kmap[i][j].isGrouped = true;
                     temp.numbers.add(kmap[(i+1)%R][j].number);
                     temp.numbers.add(kmap[i][j].number);
                     temp.points.add(new Point((i+1)%R,j));
                     temp.points.add(new Point(i,j));
                     groups.add(temp);
                      System.out.println("added "+i+" "+j+" "+i+1+" "+" "+j);
                 }
             }
         }
     }
    
     
     
     /**Check singles*/
     for(int i=0;i<R;i++){
         for(int j=0;j<C;j++){
             if(!kmap[i][j].isGrouped){
                     Group temp = new Group(1,i,j,i,j);
                     temp.numbers.add(kmap[i][j].number);
                     temp.points.add(new Point(i,j));
                     groups.add(temp);
                     kmap[i][j].isGrouped = true;
                 }   
             }
         }
     ArrayList<Group> tempal = new ArrayList<>();
     /**Grow groups vertically*/
     while(true){
         boolean noChange = true;
     for(Group g:groups){
         for(Group h:groups){
             if(groups.indexOf(g) != groups.indexOf(h)){
                 if(g.size == h.size){
                     if((g.start[0]+(g.size/2))%R == h.start[0] && !g.hasEvolved
                             && g.start[1] == h.start[1] && g.end[1] == h.end[1]){
                         g.hasEvolved = true;
                         h.hasEvolved = true;
                         noChange = false;
                         Group temp = new Group(g.size*2,g.start[0],g.start[1],h.end[0],h.end[1]);
                         for(int t:g.numbers)
                             temp.numbers.add(t);
                         for(int t:h.numbers)
                             temp.numbers.add(t);
                         for(Point p:g.points)
                             temp.points.add(p);
                         for(Point p:h.points)
                             temp.points.add(p);            
                         tempal.add(temp);
                         
                         
                     }
                 }
             }
         }     
     }
     for(Group g:tempal){
         groups.add(g);
     }
     tempal.removeAll(tempal);
         //System.out.println(g.start[0]+" "+g.start[1]+" "+g.end[0]+" "+g.end[1]+" ");
         if(noChange)
             break;
     }
     /** Grow groups horizontally*/
      while(true){
         boolean noChange = true;
     for(Group g:groups){
         for(Group h:groups){
             if(groups.indexOf(g) != groups.indexOf(h)){
                 if(g.size == h.size){
                     if((g.end[1]+1)%C == h.start[1] && !g.hasEvolved
                             && g.start[0] == h.start[0] && g.end[0] == h.end[0]){
                         g.hasEvolved = true;
                         h.hasEvolved = true;
                         noChange = false;
                         Group temp = new Group(g.size*2,g.start[0],g.start[1],h.end[0],h.end[1]);
                         for(int t:g.numbers)
                             temp.numbers.add(t);
                         for(int t:h.numbers)
                             temp.numbers.add(t);
                          for(Point p:g.points)
                             temp.points.add(p);
                         for(Point p:h.points)
                             temp.points.add(p); 
                         tempal.add(temp);
                     
                     
                     }
                 }
             }
         }     
     }
     for(Group g:tempal){
         groups.add(g);
     }
     tempal.removeAll(tempal);
         //System.out.println(g.start[0]+" "+g.start[1]+" "+g.end[0]+" "+g.end[1]+" ");
         if(noChange)
             break;
     }
     
     /**index normalization*/
     for(Group g:groups){
         if(!g.hasEvolved){
             if(g.end[0]<g.start[0]){
                 g.end[0]+=R;
             }
             if(g.end[1]<g.start[1]){
                 g.end[1]+=C;
             }
         }
     }
     
     
     /**Remove no longer needed groups*/
     tempal.removeAll(tempal);
     for(Group g:groups){
         if(!g.hasEvolved)
             tempal.add(g);     
     }
     groups.removeAll(groups);
     for(Group g:tempal){
         groups.add(g);
     }
     
     }
    
 
    public static String calculateOutput() {
        // TODO code application logic here
    
     /**Label trimming*/
     ArrayList<String> outexp = new ArrayList<>();
     for(Group g:groups){
         int[] temp = new int[variables.length()];
        
         if(!g.hasEvolved){
             String out="";
             for(int i=0;i<variables.length();i++){
                 temp[i] = kmap[g.start[0]][g.start[1]].bin.charAt(i)-48;
             }
            System.out.println(g.size+" "+g.start[0]+" "+g.start[1]+" "+g.end[0]+" "+g.end[1]+" ");
             for(int i=g.start[0];i<g.end[0];i++){
                 for(int j=0;j<variables.length();j++){
                   if(kmap[i%R][g.start[1]].bin.charAt(j) == kmap[(i+1)%R][g.start[1]].bin.charAt(j)
                           && temp[j]!=-1){
                   }else{
                       temp[j] = -1;
                   }
                  
                 }
             }
            
             for(int i=g.start[1];i<g.end[1] ;i++){
                 for(int j=0;j<variables.length();j++){
                   if(kmap[g.start[0]][i%C].bin.charAt(j) == kmap[g.start[0]][(i+1)%C].bin.charAt(j)
                           && temp[j]!=-1){
                       //temp[j] = Integer.valueOf(kmap[g.start[0]][j%R].bin.charAt(j)) - 48;
                   }else{
                       temp[j] = -1;
                   }
                 }
             }
              
            
             
             int check = 0;
             for(int i=0;i<variables.length();i++){
            
             if(temp[i] == 1){
                 out = out+variables.charAt(i);
             }else if(temp[i] == 0){
                 out  = out +Character.toLowerCase(variables.charAt(i));
             }
             check = temp[i]+check;
         }
             if(check*(-1) == variables.length())
                 out = "1";
         outexp.add(out);
         }
        
     }
      String finalout = "";
         for(String t:outexp){
             finalout +=t;
             if(outexp.indexOf(t)!=outexp.size()-1)
                 finalout+=" + ";
         }
        return finalout;
    }//output method
}
