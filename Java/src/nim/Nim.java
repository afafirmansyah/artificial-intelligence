/*
 * Simple version of NIM.
 * Program based on the winning strategy which is available on Wiki.
 */

package nim;
import java.util.Arrays;
import java.util.*;
import java.io.*;

public class Nim {	
	/*
	 * Main function
	 */        
    public static String[] readFileAsString(String fileName)throws Exception{ 
        File txt = new File(fileName);
        Scanner scan = new Scanner(txt);
        ArrayList<String> data = new ArrayList<>() ;
        while(scan.hasNextLine()){
            data.add(scan.nextLine());
        }        
        String[] simpleArray = data.toArray(new String[]{});
        return simpleArray;
    }
    public static void WriteWeight (String filename, double[]x) throws IOException{
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < x.length; i++) {          
          outputWriter.write(String.format("%.6f",x[i]).replace(",", "."));
          outputWriter.newLine();
        }
        outputWriter.flush();  
        outputWriter.close();  
    }    
    
    public static int[] splitToInt(String input, String delimeter){            
        String[] strArray = input.split(delimeter);
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }         

    public static boolean check_win(int[] value){
       boolean status = true;
       for(int i=0; i<value.length; i++){
           int cek = value[i] / 3;
           if(cek>0){
               status = false;
               break;
           }
       }
       return status;
    }

    public static double getRandom(){
        double rand;
        rand = new Random().nextDouble() * 2 - 1;
        rand = new Double(String.format("%.1f", rand).replace(",", "."));
        return rand;

    }
    
    public static int[] reverse(int[] input) {
        int[] new_array = new int[input.length];
        int last = input.length - 1;
        for (int i = 0; i <= last; i++) {
          new_array[i] = input[last-i];          
        }
        return new_array;
    }
    
    public static String implode(int[] inputArray){
        String AsImplodedString;
        if (inputArray.length==0) {
            AsImplodedString = "";
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(inputArray[0]);
            for (int i=1;i<inputArray.length;i++) {
                if(inputArray[i] == 0){
                    break;
                }
                sb.append(",");
                sb.append(inputArray[i]);
            }
            AsImplodedString = sb.toString();
        }
        return AsImplodedString;
    }
    
    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
                return 1;
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
        
    public static int checkMax(double[] a) {
        int max = 0, index=0;
        double val_max = 0;
        int[] index_choose = new int[a.length];
        boolean rnd = false;        
        for (int i = 0; i < a.length; i++) {
            //System.out.println(a[i]);            
            if( val_max < a[i] ){
                max = i;
                val_max = a[i];                
                index_choose = new int[a.length];;
                index_choose[0] = max;
                index = 0;
            } else if(val_max == a[1]){
                rnd = true;          
                index_choose[++index] = i;
            }
        }   
        int res=max;
        if(rnd){
            res = getRandomNumberInRange(1, index_choose.length)-1;
            res = index_choose[res];            
        }        
        return res;
    }


    public static void main(String args[]) throws Exception {
        int Rot =  7,CP_RUN=0,HM_RUN=1,playing_mode;
        double greedPoint = 0.1,miu = 0.1;
        int[] chain_up = new int[24];
        int[][] chain_down = new int[24][24];
        int[][] data = new int[24][24];
        double[] weight = new double[24];
        int training=0;
        String[] dataStr = readFileAsString("data.txt");            
        String[] chain = readFileAsString("chain.txt");            
        String[] weightStr = readFileAsString("weight.txt");                      
        Scanner input = new Scanner(System. in);
        
        for(int i=0; i<24; i++){
            ///Insert Data
            data[i] = splitToInt(dataStr[i], ",");
            chain_up[i] = Integer.parseInt(chain[i].split("#")[0]);
            chain_down[i] = splitToInt(chain[i].split("#")[1],",");
            weight[i] = Double.parseDouble(weightStr[i]);                            
        } 
        /// END INSERT DATA                                   
        System.out.println("1. Mesin vs Mesin \n2. Mesin vs User\n3. Training\n");
        System.out.print("Pilih mode: ");
        playing_mode = input.nextInt();
        
        if(playing_mode == 3){
            System.out.print("Jumlah Iterasi Training: ");
            training = input.nextInt();
        } else {
            training = 1;
        }
        
        
        for(int xx = 0; xx<training; xx++){
            int[] data_game = new int[10];
            int index_game = 0;
            int player = CP_RUN;
            data_game[0] = Rot;           
            int i_game = 1, index_new=0;
            boolean status_game = true;
            if(playing_mode == 3){
                System.out.println("Training ke - "+ (xx+1) );
            }
            while(status_game){
                System.out.println("Permainan ke-"+(i_game++));
                if(player == CP_RUN){                
                    System.out.println("-------COMPUTER TURN-------");
                    double p = getRandom();                     
                    if(p<greedPoint){                    
                        ///Exploration
                        int rnd_index = 0;
                        if(chain_down[index_game].length > 1){
                            rnd_index = getRandomNumberInRange(1,chain_down[index_game].length) - 1;                    
                        }                                                
                        data_game = data[chain_down[index_game][rnd_index]-1];                    
                        index_new = chain_down[index_game][rnd_index]-1;
                    } else {
                        ///Exploitation
                        int max_nodes = 0;
                        double[] temp_nodes = new double[chain_down[index_game].length];
                        if(temp_nodes.length > 1){
                            for(int ex1=0; ex1<chain_down[index_game].length; ex1++){
                                temp_nodes[ex1] = weight[chain_down[index_game][ex1]-1];
                            }
                            max_nodes = checkMax(temp_nodes);                    
                        }                           
                        data_game = data[chain_down[index_game][max_nodes]-1];                    
                        index_new = chain_down[index_game][max_nodes]-1;
                    }
                    if(playing_mode == 2)
                        player = HM_RUN;
                } else {            
                    System.out.println("Human Turn - Pleas Choose  : ");
                    for(int y=0; y<chain_down[index_game].length; y++){
                        System.out.println((y+1)+" - "+dataStr[chain_down[index_game][y]-1]);
                    }
                    System.out.println("");                     
                    System.out.println("Your Choose : ");
                    int choose = input.nextInt();                    
                    data_game = data[chain_down[index_game][choose-1]-1];                    
                    index_new = chain_down[index_game][choose-1]-1;
                    player = CP_RUN;
                }
                ///Update Weight////
                weight[index_game] = weight[index_game] + miu * (  weight[index_new] - weight[index_game] );
                index_game = index_new;
                /////END - Update Weight////                
                System.out.println(Arrays.toString(data_game));
                System.out.println("");
                if(check_win(data_game)){
                    if(player == CP_RUN){                    
                        System.out.println("--HUMAN WIN--");
                    } else {
                        System.out.println("--COMPUTER WIN--");
                    }
                    status_game = false;
                }            
            }
        } 
        if( playing_mode == 3 ){
            System.out.println("");
            for(int cc = 0; cc<weight.length; cc++){
                System.out.println(String.format("%.6f",weight[cc]) );
            }                
        }        
        WriteWeight("weight.txt",weight);
    }
}	