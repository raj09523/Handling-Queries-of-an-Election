package col106.assignment3.Heap;

public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E> {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}
	public int capacity=300;
	public Node[] Heaparr=new Node[capacity];
	public int curr=1;													//makke dynamic
	public void insert(T key, E value){
        Node newNode = new Node(key, value);
        
		Heaparr[curr++]=newNode;
		
		int child=curr-1;
		int parent=child/2;
		
		Node a =Heaparr[child];
		Node b=Heaparr[parent];
		

		while(b!=null && b.value.compareTo(a.value)<0 && parent>0){
			
			Node tmp=Heaparr[parent];
			Heaparr[parent]=Heaparr[child];


			Heaparr[child]=tmp;
			
			child=parent;
			parent=child/2;
			a =Heaparr[child];
			b=Heaparr[parent];
			

		}
	}

	/*
	 * end code
	 */
	
	// write your code here	
	public void compare(Node Heaparr[], int i) 
    { 
        int max = i;  
        int l = 2 * i;  
        int r = 2 * i + 1;
        int f=i/2;
        if(f>0 && Heaparr[max].value.compareTo(Heaparr[i/2].value)>0 ){
        	Node swap = Heaparr[i/2]; 
            Heaparr[i/2] = Heaparr[max]; 
           	Heaparr[max] = swap;  
           	compare(Heaparr, i/2); 
        }
        else{
        	if (l < curr && Heaparr[l].value.compareTo(Heaparr[max].value)>0 && Heaparr[l].value.compareTo(Heaparr[r].value)>0) 
            	max = l;  
        	if (r < curr && Heaparr[r].value.compareTo(Heaparr[max].value)>0 && Heaparr[r].value.compareTo(Heaparr[l].value)>0) 
            	max = r;  
        	if (max != i) { 
            	Node swap = Heaparr[i]; 
            	Heaparr[i] = Heaparr[max]; 
            	Heaparr[max] = swap;  
            	compare(Heaparr, max);
            } 
        }   
        /*if (l < curr && Heaparr[l].value.compareTo(Heaparr[max].value)>0 ) 
            max = l;  
        if (r < curr && Heaparr[r].value.compareTo(Heaparr[max].value)>0 ) 
            max = r;  
        if (max != i) { 
            Node swap = Heaparr[i]; 
            Heaparr[i] = Heaparr[max]; 
            Heaparr[max] = swap;  
            compare(Heaparr, max); 
        } */
    }

	public E extractMax() {
		Node d=Heaparr[1];     
        Node lastElement = Heaparr[curr - 1]; 
  
         
        Heaparr[1] = lastElement; 
        
		curr--;
		
        compare(Heaparr, 1);
        System.out.println("Extracting Max :");
        return (E)d.value;
		//write your code here
		//return null;
	}

	public void delete(T key) {
		int k=findnode(key); 
		
        Heaparr[k]=Heaparr[curr-1];
		curr--;
		int parent=k/2;
        compare(Heaparr, k);
		//write your code here
		
	}

	public void increaseKey(T key, E value) {
		int k=findnode(key); 
    	
    	if(value.compareTo(Heaparr[k].value)<=0 )
    		System.out.println("Invalid");
    	Heaparr[k].value=value;
    	int child=k;
		int parent=child/2;
    	while(k>1 && Heaparr[k].value.compareTo(Heaparr[k/2].value)>0){
    		Node temp=Heaparr[parent];
    		Heaparr[parent]=Heaparr[child];
    		Heaparr[child]=temp;
    		child=parent;
			parent=child/2;

    		
    	}
		//write your code here
		
	}

	public void printHeap() {
		//System.out.println("Printing heap in level Order");
		for(int i=1;i<=curr-1;i++){
			System.out.println(Heaparr[i].key+", "+Heaparr[i].value);
		}
		//write your code here
	}
	public int findnode(T key){
    	int k=0;
    	for(int i=1;i<curr;i++){
			if(key==Heaparr[i].key){
				k=i;

			}
		}
		return k;
    }	
}
class Node <T extends Comparable, E extends Comparable>{
	T key;
	E value;
	Node(T key, E value) {
		this.key=key;
		this.value=value;
    }
    
}
