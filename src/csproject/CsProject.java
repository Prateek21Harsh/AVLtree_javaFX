/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csproject;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import static javax.lang.model.type.TypeKind.DOUBLE;

class Node { 
    int key, height; 
    Node left, right; 
  
    Node(int d) { 
        key = d; 
        height = 1; 
    } 
}

class Console extends OutputStream
{
    private TextArea    output;

    public Console(TextArea ta)
    {
        this.output = ta;
    }

    @Override
    public void write(int i) throws IOException
    {
        output.appendText(String.valueOf((char) i));
    }

}
public class CsProject extends Application{

        Node root; 
  
    // A utility function to get the height of the tree 
    int height(Node N) { 
        if (N == null) 
            return 0; 
  
        return N.height; 
    } 
  
    // A utility function to get maximum of two integers 
    int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 
  
    // A utility function to right rotate subtree rooted with y 
    // See the diagram given above. 
    Node rightRotate(Node y) { 
        Node x = y.left; 
        Node T2 = x.right; 
  
        // Perform rotation 
        x.right = y; 
        y.left = T2; 
  
        // Update heights 
        y.height = max(height(y.left), height(y.right)) + 1; 
        x.height = max(height(x.left), height(x.right)) + 1; 
  
        // Return new root 
        return x; 
    } 
  
    // A utility function to left rotate subtree rooted with x 
    // See the diagram given above. 
    Node leftRotate(Node x) { 
        Node y = x.right; 
        Node T2 = y.left; 
  
        // Perform rotation 
        y.left = x; 
        x.right = T2; 
  
        //  Update heights 
        x.height = max(height(x.left), height(x.right)) + 1; 
        y.height = max(height(y.left), height(y.right)) + 1; 
  
        // Return new root 
        return y; 
    } 
  
    Node minValueNode(Node node)  
    {  
        Node current = node;  
  
        /* loop down to find the leftmost leaf */
        while (current.left != null)  
        current = current.left;  
  
        return current;  
    }  
  
    Node deleteNode(Node root, int key)  
    {  
        // STEP 1: PERFORM STANDARD BST DELETE  
        if (root == null)  
            return root;  
  
        // If the key to be deleted is smaller than  
        // the root's key, then it lies in left subtree  
        if (key < root.key)  
            root.left = deleteNode(root.left, key);  
  
        // If the key to be deleted is greater than the  
        // root's key, then it lies in right subtree  
        else if (key > root.key)  
            root.right = deleteNode(root.right, key);  
  
        // if key is same as root's key, then this is the node  
        // to be deleted  
        else
        {  
  
            // node with only one child or no child  
            if ((root.left == null) || (root.right == null))  
            {  
                Node temp = null;  
                if (temp == root.left)  
                    temp = root.right;  
                else
                    temp = root.left;  
  
                // No child case  
                if (temp == null)  
                {  
                    temp = root;  
                    root = null;  
                }  
                else // One child case  
                    root = temp; // Copy the contents of  
                                // the non-empty child  
            }  
            else
            {  
  
                // node with two children: Get the inorder  
                // successor (smallest in the right subtree)  
                Node temp = minValueNode(root.right);  
  
                // Copy the inorder successor's data to this node  
                root.key = temp.key;  
  
                // Delete the inorder successor  
                root.right = deleteNode(root.right, temp.key);  
            }  
        }  
  
        // If the tree had only one node then return  
        if (root == null)  
            return root;  
  
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE  
        root.height = max(height(root.left), height(root.right)) + 1;  
  
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether  
        // this node became unbalanced)  
        int balance = getBalance(root);  
  
        // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Case  
        if (balance > 1 && getBalance(root.left) >= 0)  
            return rightRotate(root);  
  
        // Left Right Case  
        if (balance > 1 && getBalance(root.left) < 0)  
        {  
            root.left = leftRotate(root.left);  
            return rightRotate(root);  
        }  
  
        // Right Right Case  
        if (balance < -1 && getBalance(root.right) <= 0)  
            return leftRotate(root);  
  
        // Right Left Case  
        if (balance < -1 && getBalance(root.right) > 0)  
        {  
            root.right = rightRotate(root.right);  
            return leftRotate(root);  
        }  
  
        return root;  
    }
    
    // Get Balance factor of node N 
    int getBalance(Node N) { 
        if (N == null) 
            return 0; 
  
        return height(N.left) - height(N.right); 
    } 
  
    Node insert(Node node, int key) { 
  
        /* 1.  Perform the normal BST insertion */
        if (node == null) 
            return (new Node(key)); 
  
        if (key < node.key) 
            node.left = insert(node.left, key); 
        else if (key > node.key) 
            node.right = insert(node.right, key); 
        else // Duplicate keys not allowed 
            return node; 
  
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left), 
                              height(node.right)); 
  
        /* 3. Get the balance factor of this ancestor 
              node to check whether this node became 
              unbalanced */
        int balance = getBalance(node); 
  
        // If this node becomes unbalanced, then there 
        // are 4 cases Left Left Case 
        if (balance > 1 && key < node.left.key) 
            return rightRotate(node); 
  
        // Right Right Case 
        if (balance < -1 && key > node.right.key) 
            return leftRotate(node); 
  
        // Left Right Case 
        if (balance > 1 && key > node.left.key) { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
        } 
  
        // Right Left Case 
        if (balance < -1 && key < node.right.key) { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 
  
        /* return the (unchanged) node pointer */
        return node; 
    } 
    
    void printTree2(Node root,int x,int c,int r,GridPane G,StackPane S){
        
        if(root.left==null && root.right==null)
            G.add(S,c,r);
        
        else{
            
            Node temp;
	    temp = root;
        
            int n = 7;
                
            while(temp.left!=null || temp.right!=null)
            {	

        	if(x<temp.key)
               {
                temp = temp.left;
                c = c-n;
                r = r + 1;
               } 
            
                else if(x>temp.key)
               {
                temp = temp.right;
                c = c+n;
                r = r + 1;            
               }
             n--;
             
            }
        G.add(S,c,r);
   
        }       
    }
    
    void delete2(ArrayList list,int key){
          
          int size = list.size();
          Label lb = new Label();
          
          StackPane s1 = new StackPane();
          for(int i=0;i<size;i++){
              s1 = (StackPane) list.get(i);
              javafx.scene.Node nodeOut = s1.getChildren().get(1);
              lb = (Label) nodeOut;
              int a = Integer.parseInt(lb.getText());
              if(key == a){
                  s1.getChildren().clear();
              }
          }
      }
      
    void search(ArrayList list,int key){
          
          int size = list.size();
          Label lb = new Label();
          Circle circ = new Circle();
          
          StackPane s1 = new StackPane();
          for(int i=0;i<size;i++){
              
              s1 = (StackPane) list.get(i);
              javafx.scene.Node nodeOut = s1.getChildren().get(1);
              javafx.scene.Node node2 = s1.getChildren().get(0);
              lb = (Label) nodeOut;
              circ = (Circle) node2;
              
              int a = Integer.parseInt(lb.getText());
              if(key == a){
                  System.out.println(a + " is found!!!\nIt is highlighted with Yellow fill in the diagram");
                  circ.setFill(Color.YELLOW);
                  
              }
              
          }
      }
      
      
    void preOrder(Node node) { 
        if (node != null) { 
            System.out.print(node.key + " ->");
            preOrder(node.left); 
            preOrder(node.right); 
        } 
       
    } 
    
    void postOrder(Node node){
        if(node != null){
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + " ->");
            
        }
    }
    
    void inOrder(Node node){
        if(node != null){
            inOrder(node.left);
            System.out.print(node.key + " ->");
            inOrder(node.right);
        }
            
    }

    int no_vertices(Node node){
        
        if(node==null)
            return 0;
        
        int ver=0;
        
        ver++;
        ver+=no_vertices(node.left);
        ver+=no_vertices(node.right);
        
        return ver;
    }
    
    
    @Override
    public void start (Stage primaryStage)
    {
        CsProject tree = new CsProject(); 
        HBox rootPane = new HBox();
        rootPane.setMinSize(1000, 750);
        rootPane.setMaxSize(810, Double.MAX_VALUE);
        BorderPane disp = new BorderPane();
        disp.prefWidth(100);
        disp.setMinSize(500, 500);
        
        VBox secondary = new VBox();
        secondary.setAlignment(Pos.TOP_RIGHT);
        secondary.setMinSize(300, 500);
        secondary.setPadding(new Insets(20, 20, 20, 20));
        
        TextField ins = new TextField();
        ins.setMinWidth(250);
        ins.setMaxWidth(250);
        ins.setStyle("-fx-control-inner-background: #5f9ea0; -fx-border-color: AQUA");
        Button i = new Button("INSERT");
        i.setMinWidth(250);
        i.setMaxWidth(250);
        TextField del = new TextField();
        del.setMinWidth(250);
        del.setMaxWidth(250);
        del.setStyle("-fx-control-inner-background: #5f9ea0");
        Button d = new Button("DELETE");
        d.setMinWidth(250);
        d.setMaxWidth(250);
        TextField srch = new TextField();
        srch.setMinWidth(250);
        srch.setMaxWidth(250);
        srch.setStyle("-fx-control-inner-background: #5f9ea0; -fx-border-color: AQUA");
        Button s = new Button("SEARCH");
        s.setMinWidth(250);
        s.setMaxWidth(250);
        
        Button pre = new Button("PRE ORDER");
        Button post = new Button("POST ORDER");
        Button in = new Button("IN ORDER");
        Button rand = new Button("RANDOM");
        
        pre.setMinWidth(250);
        pre.setMaxWidth(250);
        post.setMinWidth(250);
        post.setMaxWidth(250);
        in.setMinWidth(250);
        in.setMaxWidth(250);
        rand.setMinWidth(250);
        rand.setMaxWidth(250);
             
        GridPane grid = new GridPane();
        StringBuilder sb = new StringBuilder();
        ArrayList<StackPane> list = new ArrayList<>(); 
              
        
               
        TextArea ta = TextAreaBuilder.create()
            .prefWidth(600)
            .prefHeight(50)
            .minHeight(100)
            .wrapText(true)
            .build();

        ta.setEditable(false);
        ta.setStyle("-fx-opacity: 1; -fx-control-inner-background: BLACK");
        
        //insert event
        EventHandler<ActionEvent> event1 = (ActionEvent e) -> {
            if(ins.getText().isEmpty())
            {
                ta.clear();
                System.out.println("Please Enter valid input");
            }
            else{
                ta.clear();
                int a1 = Integer.valueOf(ins.getText());
                tree.root = tree.insert(tree.root, a1);
                System.out.print("Pre-Order: ");
                tree.preOrder(tree.root);
                System.out.println("\nHeight: " + tree.height(tree.root));
                System.out.println("No. of Vertices: " + tree.no_vertices(tree.root));
                String str = Integer.toString(a1);
            
                Label label1 = new Label(str);
            
                label1.setStyle("-fx-font-size: 40;");
            
                Circle c1 = new Circle();
                c1.setRadius(30);
                c1.setFill(Color.AQUA);
                c1.setStroke(Color.BLUE);
            
                StackPane s1 = new StackPane();
            
                s1.getChildren().addAll(c1,label1); 
            
                list.add(s1);
                s1.getChildren().get(1);
            
                ins.setText("");
                printTree2(tree.root,a1,50,5,grid,s1);
                
            }
            
        
        
        };
        
        //delete event
        EventHandler<ActionEvent> event2 = (ActionEvent e) -> {
            if(del.getText().isEmpty())
            {
                ta.clear();
                System.out.println("Please Enter valid input");
            }
            else
            {
                ta.clear();
                int a1 = Integer.valueOf(del.getText());
                tree.root = tree.deleteNode(tree.root, a1);
                System.out.print("Pre-Order: ");
                tree.preOrder(tree.root);
                System.out.println("\nHeight: " + tree.height(tree.root));
                System.out.println("No. of Vertices: " + tree.no_vertices(tree.root));
                tree.delete2(list,a1);
                del.setText("");
            }
        };
        
        //pre order print event
        EventHandler<ActionEvent> event3 = (ActionEvent e) -> {
            ta.clear();
            System.out.print("Pre-Order: ");
            tree.preOrder(tree.root);
            System.out.println("\nHeight: " + tree.height(tree.root));
            System.out.println("No. of Vertices: " + tree.no_vertices(tree.root));
        };
        
        //post order print event
        EventHandler<ActionEvent> event4 = (ActionEvent e) -> {
            ta.clear();
            System.out.print("Post-Order: ");
            tree.postOrder(tree.root);
            System.out.println("\nHeight: " + tree.height(tree.root));
            System.out.println("No. of Vertices: " + tree.no_vertices(tree.root));
        };
        
        //in order print event
        EventHandler<ActionEvent> event5 = (ActionEvent e) -> {
            ta.clear();
            System.out.print("In-Order: ");
            tree.inOrder(tree.root);
            System.out.println("\nHeight: " + tree.height(tree.root));
            System.out.println("No. of Vertices: " + tree.no_vertices(tree.root));
        };
        
        
        Random r = new Random();
        //event to enter 5 random integers in the tree
        EventHandler<ActionEvent> event6 = (ActionEvent e) -> {
            ta.clear();
            int n = 0 ;
            while(n<5)
            {
                int m = r.nextInt(100);
                tree.root = tree.insert(tree.root, m);
                System.out.println("Inserted " + m);
                n++;
            }
            tree.preOrder(tree.root);
            System.out.println("\nHeight: " + tree.height(tree.root));
            System.out.println("No. of Vertices: " + tree.no_vertices(tree.root));
        };
        
        //search event
        EventHandler<ActionEvent> event7 = (ActionEvent e) -> {
          
          if(srch.getText().isEmpty())
            {
                ta.clear();
                System.out.println("Please Enter valid input");
            }
          
          else
          {
              ta.clear();
              int a = Integer.valueOf(srch.getText());
                              
              tree.search(list, a);
              
              srch.setText("");
          }
        };
        
        
        GridPane gp = new GridPane();
        
        i.setOnAction(event1);
        ins.setOnAction(event1);
        d.setOnAction(event2);
        del.setOnAction(event2);
        pre.setOnAction(event3);
        post.setOnAction(event4);
        in.setOnAction(event5);
        rand.setOnAction(event6);
        srch.setOnAction(event7);
        s.setOnAction(event7);
        
        i.setStyle("-fx-background-color: BLUE; -fx-text-fill: YELLOW; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: AQUA; -fx-border-width: 3px");
        d.setStyle("-fx-background-color: BLUE; -fx-text-fill: YELLOW; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: AQUA; -fx-border-width: 3px");
        pre.setStyle("-fx-background-color: BLUE; -fx-text-fill: YELLOW; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: AQUA; -fx-border-width: 3px");
        post.setStyle("-fx-background-color: BLUE; -fx-text-fill: YELLOW; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: AQUA; -fx-border-width: 3px");
        in.setStyle("-fx-background-color: BLUE; -fx-text-fill: YELLOW; -fx-font-style: italic; -fx-font-weight: bold; -fx-border-color: AQUA; -fx-border-width: 3px");
        rand.setStyle("-fx-background-color: BLUE; -fx-text-fill: YELLOW; -fx-font-style: italic; -fx-font-weight: bold;  -fx-border-color: AQUA; -fx-border-width: 3px");
        s.setStyle("-fx-background-color: BLUE; -fx-text-fill: YELLOW; -fx-font-style: italic; -fx-font-weight: bold;  -fx-border-color: AQUA; -fx-border-width: 3px");
        
        Tooltip tr = new Tooltip("Inserts 5 random numbers between 0 to 99 into the AVL Tree");
        rand.setTooltip(tr);
        Tooltip t_pre = new Tooltip("Prints pre order traversal in Console");
        Tooltip t_post = new Tooltip("Prints post order traversal in Console");
        Tooltip t_in = new Tooltip("Prints in order traversal in Console");
        pre.setTooltip(t_pre);
        post.setTooltip(t_post);
        in.setTooltip(t_in);
        Tooltip t_ta = new Tooltip("Console Output\n[Non-Editable]");
        ta.setTooltip(t_ta);
        
        secondary.setSpacing(10);
        secondary.setStyle("-fx-background-color: #000000;");
        disp.setStyle("-fx-background-color: #3cb371;");
        
        Console console = new Console(ta);
        PrintStream ps = new PrintStream(console, true);
            System.setOut(ps);
            System.setErr(ps);
        
        
        secondary.getChildren().addAll(ins, i, del, d, srch, s, post, in, pre, rand);
        disp.setBottom(ta);
        disp.setCenter(grid);
        rootPane.getChildren().addAll(disp, secondary);
        rootPane.setFillHeight(true);
        rootPane.setPadding(new Insets(20, 30, 20, 20));
        rootPane.setStyle("-fx-background-color: #2f4f4f");
        
        Scene scene = new Scene(rootPane);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("AVL Tree Visulization Tool");
        
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(950);
        primaryStage.setMaxHeight(1000);
        primaryStage.show();
                
    }
    public static void main(String[] args) { 
        
        launch(args);
  
    }
    
}