package col106.assignment3.BST;
import java.util.LinkedList;
import java.util.Queue;


public class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E>  {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
        /*BST theTree = new BST();
        theTree.insert(1, 15);
        theTree.insert(2, 5);
        theTree.insert(3, 1);
        theTree.insert(4, 6);
        theTree.insert(5, 20);
        theTree.insert(6, 16);
        theTree.insert(7, 25);
        theTree.insert(8, 21);
        theTree.insert(9, 26);
        theTree.printBST();*/
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}
	Node root;
	/*
	 * end code
	 * start writing your code from here
	 */
	
	//write your code here 
    public void insert(T key, E value) {
    	Node newNode = new Node(key, value);
		if (root == null) 
            root = newNode;
        else {
        	Node focusNode = root;
        	Node parent;
        	while (true) {
        		parent = focusNode;
        		if (value.compareTo(focusNode.value)<0 ) {
        			focusNode = focusNode.left;
        			if (focusNode == null){
        				parent.left = newNode;
                        return;
        			}
        		}
                else{
                	focusNode = focusNode.right;
                	if (focusNode == null){
                		parent.right = newNode;
                        return;
                	}
                }
        	}
        }
		//write your code here
    }

    public void update(T key, E value) {
        
        Queue<Node> q = new LinkedList<>();   
        q.add(root);
         
  
     
        while (q.size() > 0)  
        {  
         
        Node node = q.peek(); 
         
        if (node.key == key){
            delete(key);
            insert(key, value);
 
        }  
        q.remove();  
        if (node.left != null)  
            q.add(node.left);  
        if (node.right != null)  
            q.add(node.right);
        }  
        
		//write your code here
    }

    public void delete(T key) {
    	deleteNode(this.root, key);
    }
    private Node deleteNode(Node root, T key) {
        E data=findNode(this.root,key);
        
 
        if(root == null) return root;
 
        if(data.compareTo(root.value)<0 ) {
            root.left=deleteNode(root.left, key);
        } else if(data.compareTo(root.value)>0 ) {
            root.right=deleteNode(root.right, key);
        } else {
            
            if( root.right == null && root.left == null) {
                return null;
            } else if(root.left == null) {
                return root.right;
            } else if(root.right == null) {
                return root.left;
            } else {
                
                Node prev = minValue(root.right);
                root.key = prev.key;
                root.value=prev.value;
                root.right=deleteNode(root.right, (T)root.key);
                
            }
        }
 
        return root;
    }
    public Node minValue(Node meh) 
    { 
        while (meh.left != null) {
            meh = meh.left;
        }

        return meh;
    }
    public E findNode(Node root,T key) 
    { 
        if (root == null)  
        return null;  
  
      
        Queue<Node> q = new LinkedList<>();   
        q.add(root);
        E v=(E)root.value;  
  
     
        while (q.size() > 0)  
        {  
         
        Node node = q.peek(); 
         
        if (node.key == key)  
            v=(E)node.value;    
        q.remove();  
        if (node.left != null)  
            q.add(node.left);  
        if (node.right != null)  
            q.add(node.right);  
        }  
   
        return v;
    } 

    public void printBST () {
    	Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node curr;
        //System.out.println("Printing BST in level Order");
        while (!queue.isEmpty()){
            curr = queue.poll();
            System.out.println(curr.key + ", "+curr.value);
            if (curr.left != null){
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }
		//write your code here
    }
    

}
class Node <T extends Comparable, E extends Comparable>{
	T key;
	E value;
	Node left;
	Node right;
	Node(T key, E value) {
		this.key=key;
		this.value=value;
    }
    
}