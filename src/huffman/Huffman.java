/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.*;
import java.util.*;

/**
 *
 * @author Hp
 */
class Huffman {

    /**
     * @param args the command line arguments
     */
    public static void compress(String infile, String outfile) throws Exception, IOException {
        
        Scanner in = new Scanner(new File(infile));
        
        BinaryOut bout = new BinaryOut(outfile);
        HuffTree tree = new HuffTree();
        while(in.hasNext())
        {
            
            String str = in.nextLine();
            String encoded = "";
            char[] arr = str.toCharArray();
            for(int i = 0; i < arr.length; i++)
            {
                encoded += tree.encode(arr[i]);
            }
            
            for(int i = 0; i < encoded.length(); i++)
            {
                int a = encoded.charAt(i) - '0';
                //System.out.println(a);
                bout.write(a == 1);
            }
            
        }
        
        String eof = tree.encode((char)0);
        for(int i = 0; i < eof.length();i++)
        {
            System.out.println(eof.charAt(i));
            bout.write(eof.charAt(i) == '1');
        }
        in.close();
        bout.close();
        
        /*HuffTree de = new HuffTree();
        de.decompressfile("output.dat", "decompressed.txt");
        */
    }
    
}
