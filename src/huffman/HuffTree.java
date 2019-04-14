package huffman;

//import java.util.ArrayDeque;
import java.util.Stack;
//import java.util.Queue;
import java.util.ArrayList;
//import java.util.Collections;
import java.io.*;
//import java.util.Collections;
/**
 *
 * @author admin
 */
public class HuffTree {
    Node root;
    Node NYT;
    Stack s=new Stack();
    ArrayList<Node> larger = new ArrayList();
    boolean[] charList = new boolean [256];
    
    
    HuffTree()
    {
        root=new Node();
        root.code= 512;
        root.nyt="nyt";
        root.weight=0;
        NYT = root;
        NYT.level = 1;
        }
    
    
    public String encode(char c)
    {
        String code = "";
        if(charList[c])
        {
            code = getCode(c);
            updateTree(c);
        }
        else
        {
            code = getCodeNYT();
            code += getAscii(c);
            updateTree(c);
        }
        return code;
    }
    
    private String getCode(char c)
    {
        String code = "";
        Search(c,root);
        while(!s.empty())
        {
            code += s.pop();
        }
        return code;
    }
    
    private String getCodeNYT()
    {
        String code = "";
        Search("nyt",root);
        while(!s.empty())
        {
            code += s.pop();
        }
        return code;
    }
    
    private String getAscii(char c)
    {
        String str = Integer.toBinaryString(c);
        while(str.length() < 8)
            str = "0" + str;
        return str;
    }
    
    private void updateTree(char c)
    {
        if(charList[c])
        {
            Node temp = getNode(c,root);
            while(true)
            {
                if(temp == this.root)
                {
                    temp.weight++;
                    break;
                }
                Node largest = getLargest(temp);
                if(largest == null)
                {
                    temp.weight++;
                    temp = temp.parent;
                    continue;
                }
                //swap temp and largest
                if(largest == largest.parent.left)
                {
                    if(temp == temp.parent.left)
                    {
                        Node p = temp.parent;
                        temp.parent.left = largest;
                        largest.parent.left = temp;
                        temp.parent = largest.parent;
                        largest.parent = p;
                    }
                    else
                    {
                        Node p = temp.parent;
                        temp.parent.right = largest;
                        largest.parent.left = temp;
                        temp.parent = largest.parent;
                        largest.parent = p;
                    }
                }
                else
                {
                    if(temp == temp.parent.left)
                    {
                        Node p = temp.parent;
                        temp.parent.left = largest;
                        largest.parent.right = temp;
                        temp.parent = largest.parent;
                        largest.parent = p;
                    }
                    else
                    {
                        if(temp == temp.parent.left)
                        {
                            Node p = temp.parent;
                            temp.parent.right = largest;
                            largest.parent.right = temp;
                            temp.parent = largest.parent;
                            largest.parent = p;
                        }
                    }
                }
                reOrder(this.root,1);
                temp.weight++;
                temp = temp.parent;
            }
        }
        else
        {
            charList[c] = true;
            Node n = new Node(c);
            n.weight = 1;
            n.parent = NYT;
            NYT.right = n;
            NYT.left = new Node();
            NYT.left.parent = NYT;
            NYT.nyt = "";
            n.code = NYT.code - 2 * NYT.level + 1;
            NYT.left.code = n.code - 1;
            NYT.left.level = NYT.level + 1;
            NYT = NYT.left;
            NYT.nyt = "nyt";
            Node temp = NYT.parent;
            while(true)
            {
                if(temp == root)
                {
                    temp.weight++;
                    break;
                }
                Node largest = getLargest(temp);
                if(largest == null)
                {
                    temp.weight++;
                    temp = temp.parent;
                    continue;
                }
                //swap temp and largest
                if(largest == largest.parent.left)
                {
                    if(temp == temp.parent.left)
                    {
                        Node p = temp.parent;
                        temp.parent.left = largest;
                        largest.parent.left = temp;
                        temp.parent = largest.parent;
                        largest.parent = p;
                    }
                    else
                    {
                        Node p = temp.parent;
                        temp.parent.right = largest;
                        largest.parent.left = temp;
                        temp.parent = largest.parent;
                        largest.parent = p;
                    }
                }
                else
                {
                    if(temp == temp.parent.left)
                    {
                        Node p = temp.parent;
                        temp.parent.left = largest;
                        largest.parent.right = temp;
                        temp.parent = largest.parent;
                        largest.parent = p;
                    }
                    else
                    {
                        if(temp == temp.parent.left)
                        {
                            Node p = temp.parent;
                            temp.parent.right = largest;
                            largest.parent.right = temp;
                            temp.parent = largest.parent;
                            largest.parent = p;
                        }
                    }
                }
                swapOrders(temp,largest);
                reOrder(this.root,1);
                temp.weight++;
                temp = temp.parent;
            }
        }
    }
    
    private Node getNode(char c, Node root)
    {
        Node temp = null;
        if(root != null)
        {
            if(root.c == c)
            {
                return root;
            }
            temp = getNode(c,root.left);
            if(temp == null)
                temp = getNode(c,root.right);
        }
        return temp;
    }
    
    private void swapOrders(Node a, Node b)
    {
        int temp = a.code;
        a.code = b.code;
        b.code = temp;
    }
    private Node getLargest(Node mynode)
    {
        getHighestOrderNode(mynode,this.root);
        int max = 0;
        Node largest = null;
        for(Node temp: larger)
        {
            if(temp.code > max)
            {
                largest = temp;
            }
        }
        larger = new ArrayList();
        return largest;
    }
    private void getHighestOrderNode(Node mynode, Node root)
    {
        if(root == null)
            return;
        if(root.code < mynode.code)
            return;
        if(root.weight == mynode.weight && root.code > mynode.code && root != mynode.parent && root != this.root)
        {
            larger.add(root);
        }
        getHighestOrderNode(mynode,root.left);
        getHighestOrderNode(mynode, root.right);
    }
    
    boolean Search(char c, Node root){
        if(root!=null){
            if(root.c==c){
                return true;
            }
        
            if(Search(c,root.left)){
                s.push(0);
                return true;
            }
            if(Search(c,root.right)){
                s.push(1);
                return true;
            }
           
        }
        return false;
    }
    
    boolean Search(String nyt, Node root){
        if(root!=null){
            if(root == NYT){
                //System.out.println(NYT.code);
                return true;
            }
        
            if(Search(nyt,root.left)){
                s.push(0);
                return true;            
            }
            if(Search(nyt,root.right)){
                s.push(1);
                return true;
            }
        }
        return false;
    }
    
    
    public String decompress(String code)
    {
        Node current = root;
        String decoded = "";
        for(int i = 0;; )
        {
            if(isLeaf(current))
            {
                if(current == NYT)
                {
                    if(i+8 > code.length())
                        code += " ";
                    char c = asciiToChar(code.substring(i,i+8));
                    updateTree(c);
                    decoded += c;
                    i += 8;
                }
                else
                {
                    char c = current.c;
                    updateTree(c);
                    decoded += c;
                }
                current = this.root;
                
            }
            else
            {
                if(!(i < code.length()))
                    break;
                if(code.charAt(i) == '0')
                    current = current.left;
                else
                    current = current.right;
                i++;
            }
        }
        return decoded;
    }
    
    
    private char asciiToChar(String str)
    {
        int val = 0;
        str = str.trim();
        for(int i = str.length() - 1; i >= 0; i--)
        {
            if(str.charAt(i) == '1')
                val += Math.pow(2,str.length()-i - 1);
        }
        return (char) val;
    }
    private boolean isLeaf(Node n)
    {
        return n.left == null && n.right == null;
    }
    
    public void preorder(Node root)
    {
        if(root == null)
            return;
        System.out.println(root.c + " weight:" + root.weight + " order:" + root.code);
        preorder(root.left);
        
        preorder(root.right);
    } 
    
    
    
    private void reOrder(Node root, int level)
    {
        if(root.left != null && root.right != null)
        {
            root.left.code = root.code - 2*level;
            root.right.code = root.code - 2*level+1;
            reOrder(root.left, level+1);
            reOrder(root.right, level+1);
        }
    }
    
    public void decompressfile(String infile, String outfile) throws Exception
    {
        BinaryIn in = new BinaryIn(infile);
        PrintWriter out = new PrintWriter(new File(outfile));
        Node current = root;
        while(true)
        {
            if(isLeaf(current))
            {
                if(current == NYT)
                {
                    int v = 0;
                    for(int i = 0; i < 8; i++)
                    {
                        v += in.readBoolean()? Math.pow(2, 7-i):0;
                    }
                    char c = asciiToChar(Integer.toBinaryString(v));
                    System.out.println(c);
                    if(c == 0)
                    {
                        System.out.println("EOF");
                        break;
                    }
                    updateTree(c);
                    out.write(c);
                }
                else
                {
                    char c = current.c;
                    updateTree(c);
                    out.write(c);
                    //decoded += c;
                }
                current = this.root;
                
            }
            else
            {
                if(in.isEmpty())
                    break;
                int a = in.readBoolean()?1:0;
                if(a == -1)
                    break;
                current = a == 0? current.left:current.right;
            }
        }
        
        out.close();
    }
}



class Node{
    Node left;
    Node right;
    Node parent;
    String nyt;
    char c;
    int weight;
    int code;
    int level;
        Node(){
        }
        Node(char c){
            this.c=c;
        }

}