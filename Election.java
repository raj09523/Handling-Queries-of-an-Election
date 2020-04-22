package col106.assignment3.Election;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import col106.assignment3.Heap.Heap;

public class Election implements ElectionInterface {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}


 ////////////////////////////////////////////////////////////////// Global variables /////////////////////////////////////////////////
	private Node root = null;
	Queue<Node> levelorder = new LinkedList<>();
	Queue<Node> levelqueue = new LinkedList<>();
	HashMap<String, Candidate> elec = new HashMap<String, Candidate>();
	
 //////////////////////////////////////////////////////////////// Candidate Data Type ///////////////////////////////////////////////
	
 	class Candidate{
		String name;
		String candID; 
		String state; 
		String district; 
		String constituency; 
		String party; 
		String votes;

		Candidate(String name, String candID, String state, String district, String constituency, String party, String votes){
			this.name = name;
			this.candID = candID;
			this.state = state;
			this.district = district;
			this.constituency = constituency;
			this.party = party;
			this.votes = votes;
		}

	}

 /////////////////////////////////////////////////////////// Node //////////////////////////////////////////////////////////////////
	class Node{
		String candID;
		String votes;
		Candidate can;
		Node left;
		Node right;

		Node(String candID, String votes, Candidate can){
			this.candID = candID;
			this.votes = votes;
			this.can = can;
			left = null;
			right = null;
		}
	}

 ////////////////////////////////////////////////////// Inserting in a BST //////////////////////////////////////////////////////////////////

 	public void insert(String name, String candID, String state, String district, String constituency, String party, String votes){
		Candidate can = new Candidate(name, candID, state, district, constituency, party, votes);
		elec.put(candID, can);
		Node temp = insert(root,candID,votes,can);
		if(root == null)
		{
			root = temp;
		}
	}
	
	private Node insert(Node node, String candID, String votes, Candidate can) {
		if(node == null){
    		Node x = new Node(candID,votes,can);
    		return x;
		}
		int v = Integer.parseInt(votes);
		int nodev = Integer.parseInt(node.votes);
    	if (v>nodev){
    		node.right = insert(node.right, candID, votes,can);
    	}
    	else{
    		node.left = insert(node.left, candID, votes,can);
    	}
		return node;
	}

 /////////////////////////////////////////////////////////update Vote/////////////////////////////////////////////////////////////////

	public void updateVote(String name, String candID, String votes) {
		Iterator<String> it = elec.keySet().iterator();
		Candidate c = null;
		while(it.hasNext()){
			String candid = it.next();
			if (candid.equals(candID)){
				c = elec.get(candid);
				break;
			}
		}

		//System.out.println(elec.size());
		Candidate d = new Candidate(c.name, c.candID, c.state, c.district, c.constituency, c.party, c.votes);
		d.votes = votes;
		delete(candID);
		elec.remove(candID);
		insert(root, candID, votes, d);
		elec.put(candID,d);
		//System.out.println(elec.size());
		//printElectionLevelOrder();
		
	}

	public void delete(String candID) {
		String votes = null;
		levelorder = levelTraversal();							
		Iterator<Node> itr = levelorder.iterator();			
		
		while (itr.hasNext()){
			Node temp = itr.next();
			int cmp = Integer.parseInt(temp.candID) - Integer.parseInt(candID);
			if (cmp==0){
				votes = temp.votes;
				break;
			}
		}
		//System.out.println(votes);
		levelorder.clear();
		root = deleteElement(root,votes);
	}
	
	private Node deleteElement(Node root,String votes){
		//System.out.println("im here");
		if (root == null) {
			return null;
		}

		int cmp = Integer.parseInt(votes) - Integer.parseInt(root.votes);
		if (cmp < 0){
			root.left = deleteElement(root.left, votes);
		}
		else if (cmp > 0){
			root.right = deleteElement(root.right, votes);
		}
		else{
			if (root.left == null) {
				return root.right;
			}
			else if (root.right == null) {
				return root.left;
			}
			else if (root.left!=null && root.right!= null){
				Node minNodeForRight = minimumElement(root.right);
				root.votes = minNodeForRight.votes;
				root.candID = minNodeForRight.candID;
				root.can = minNodeForRight.can;
				root.right = deleteElement(root.right, minNodeForRight.votes);
			}
			else
				root = null;
		}
		return root;      
	}

	private Node minimumElement(Node root) {
		while(root.left!=null)
		{
			root = root.left;
		}
		return root;
	}

	////////////////////////////////////////////////////////////Top k in Constituency////////////////////////////////////////////////////////////////error
	
	public void topkInConstituency(String constituency, String k){
		Heap<String,Integer> heap_candid_votes = new Heap<String,Integer>();
		HashMap<String, String> map_votes = new HashMap<String, String>();

		Iterator<String> it = elec.keySet().iterator();					

		while(it.hasNext()){
			String candid = it.next();
			String cons = elec.get(candid).constituency;
			if (cons.equals(constituency)){
				//System.out.println(constituency);
				map_votes.put(candid,elec.get(candid).votes);
				//System.out.println(candid);		
			}
		}

		//System.out.println(map_votes.size());

		Iterator<String> it2 = map_votes.keySet().iterator();			

		while(it2.hasNext()){
			String candid = it2.next();
			//System.out.println(candid+" " + map_votes.get(candid));
			Integer x = Integer.parseInt(map_votes.get(candid)) ;
			heap_candid_votes.insert(candid, x);
			//System.out.println("For id: " + candid + " => " + "Votes: " + x);
		}

		//heap_candid_votes.printHeap();

		int n = Integer.parseInt(k);
		int min = n;
		int hsize = heap_candid_votes.size();
		if (hsize<n){
			min = hsize;
		}

		while(min>0){
			String id = heap_candid_votes.extractMaxkey();
			//Integer idval = heap_candid_votes.extractMax();
			//System.out.println(idval);
			//heap_candid_votes.printHeap();
			//System.out.println(id);
			Candidate cd = elec.get(id);
			
			String name1 = cd.name;
			String candID1 = cd.candID;
			String party1 = cd.party;

			System.out.println(name1 + ", " + candID1 + ", " + party1 );
			min--;
		}

	}
	
	/////////////////////////////////////////////////////////////Leading Party in State/////////////////////////////////////////////////////////////
	
	public void leadingPartyInState(String state){
		HashMap<String, idvotes> party_votes = new HashMap<String, idvotes>();
		Map<String, String> party_votes_new = new HashMap<String, String>();
		HashMap<String, String> maximum_hm = new HashMap<String, String>();
		ArrayList<idvotes> arrli = new ArrayList<idvotes>();
		Heap<String,String> heap_of_max = new Heap<String,String>();

		Iterator<String> it = elec.keySet().iterator();	
		while(it.hasNext()){
			String candID = it.next();
			if (elec.get(candID).state.equals(state)){
				idvotes a = new idvotes(elec.get(candID).party, elec.get(candID).votes);
				party_votes.put(candID, a);
			}
		}

		Iterator<String> itr = party_votes.keySet().iterator();

		while(itr.hasNext()){
			String pointer = itr.next();
			idvotes a = new idvotes(party_votes.get(pointer).Party, party_votes.get(pointer).Votes);
			arrli.add(a);
		}

		for(int i = 0; i<arrli.size();i++){
			if (party_votes_new.containsKey(arrli.get(i).Party)){
				String a = arrli.get(i).Party;
				String newvotes = addvotes(arrli.get(i).Votes,party_votes_new.get(a));
				party_votes_new.remove(a);
				party_votes_new.put(a, newvotes);
			}
			else{
				party_votes_new.put(arrli.get(i).Party, arrli.get(i).Votes);
			}
		}

		int max = 0;

		Iterator<String> k = party_votes_new.keySet().iterator();
		while(k.hasNext()){
			String x = k.next();
			int y = Integer.parseInt(party_votes_new.get(x));

			if(y>max){
				max = y;
			}
		}

		//System.out.println(party_votes_new.size());

		Iterator<String> iter = party_votes_new.keySet().iterator();
		while(iter.hasNext()){
			String x = iter.next();
			int y  =Integer.parseInt(party_votes_new.get(x));
			if (y == max){
				maximum_hm.put(x, Integer.toString(max));
			}
		}

		//System.out.println(maximum_hm.size());
		Iterator<String> ITR = maximum_hm.keySet().iterator();
		while(ITR.hasNext()){
			String x = ITR.next();
			heap_of_max.insert(maximum_hm.get(x), x);							
		}

		int n = heap_of_max.size();

		//System.out.println(n);

		String[] arr = new String[n];

		while(n!=0){
			String x = heap_of_max.extractMax();
			arr[n-1] = x;
			n--;
		}
		
		
		for (String y : arr){																
			System.out.println(y);
		}
	}

	private String addvotes(String a, String b){
		int aint = Integer.parseInt(a);
		int bint = Integer.parseInt(b);

		return Integer.toString(aint+bint);

	}

	private class idvotes{
		String Party;
		String Votes;

		idvotes(String Party, String Votes){
			this.Party = Party;
			this.Votes = Votes;
		}
	}

 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void cancelVoteConstituency(String constituency){
		HashMap<String, Candidate> id_candidate = new HashMap<String, Candidate>();
		Heap<String ,String> to_sort_id = new Heap<String,String>();

		Iterator<String> it = elec.keySet().iterator();	
		while(it.hasNext()){
			String candID = it.next();
			if (elec.get(candID).constituency.equals(constituency)){	
				id_candidate.put(candID, elec.get(candID));
			}
		}

		Iterator<String> itr  = id_candidate.keySet().iterator();
		while(itr.hasNext()){
			String a = itr.next();
			to_sort_id.insert(a, id_candidate.get(a).candID);
		}

		int n = to_sort_id.size();

		String[] arr = new String[n];

		while(n!=0){
			String x = to_sort_id.extractMax();
			arr[n-1] = x;
			n--;
		}

		for(String t : arr){
			delete(t);
			elec.remove(t);
		}

	}

 ////////////////////////////////////////////////////////////Global Leader/////////////////////////////////////////////////////////

	public void leadingPartyOverall(){
		ArrayList<idvotes> arrli = new ArrayList<idvotes>();
		HashMap<String, idvotes> id_partyvotes = new HashMap<String, idvotes>();
		Map<String, String> id_partyvotes_new = new HashMap<String, String>();
		HashMap<String, String> maximum_hm = new HashMap<String, String>();
		Heap<String,String> heap_of_max = new Heap<String,String>();


		Iterator<String> it1 = elec.keySet().iterator();	
		while(it1.hasNext()){
			String candID = it1.next();
			idvotes a = new idvotes(elec.get(candID).party, elec.get(candID).votes);
			id_partyvotes.put(candID, a);
		}
		
		Iterator<String> itr = id_partyvotes.keySet().iterator();

		while(itr.hasNext()){
			String pointer = itr.next();
			//idvotes a = new idvotes(id_partyvotes.get(pointer).Party, id_partyvotes.get(pointer).Votes);
			arrli.add(id_partyvotes.get(pointer));
		}

		for(int i = 0; i<arrli.size();i++){
			if (id_partyvotes_new.containsKey(arrli.get(i).Party)){
				String a = arrli.get(i).Party;
				String newvotes = addvotes(arrli.get(i).Votes,id_partyvotes_new.get(a));
				id_partyvotes_new.remove(a);
				id_partyvotes_new.put(a, newvotes);
			}
			else{
				id_partyvotes_new.put(arrli.get(i).Party, arrli.get(i).Votes);
			}
		}
		
		//System.out.println(id_partyvotes_new.size());
		//have a hashmap of party(key) vs votes(value)

		int max = 0;

		Iterator<String> k = id_partyvotes_new.keySet().iterator();
		while(k.hasNext()){
			String x = k.next();
			int y = Integer.parseInt(id_partyvotes_new.get(x));

			if(y>max){
				max = y;
			}
		}

		Iterator<String> iter = id_partyvotes_new.keySet().iterator();
		while(iter.hasNext()){
			String x = iter.next();
			int y  =Integer.parseInt(id_partyvotes_new.get(x));
			if (y == max){
				maximum_hm.put(x, Integer.toString(max));
				//System.out.println(x+max);
			}
		}


		Iterator<String> ITR = maximum_hm.keySet().iterator();
		while(ITR.hasNext()){
			String x = ITR.next();
			heap_of_max.insert(maximum_hm.get(x), x);							
		}

		int n = heap_of_max.size();
		//System.out.println(n);

		String[] arr = new String[n];

		while(n!=0){
			String x = heap_of_max.extractMax();
			arr[n-1] = x;
			n--;
		}

		for (String y : arr){																	
			System.out.println(y);
		}

	}

 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void voteShareInState(String party,String state){
		HashMap<String, idvotes> id_partyvotes = new HashMap<String, idvotes>();
		ArrayList<idvotes> arrli = new ArrayList<idvotes>();
		Map<String, String> party_votes_new = new HashMap<String, String>();
		
		Iterator<String> it = elec.keySet().iterator();	
		while(it.hasNext()){
			String candID = it.next();
			if (elec.get(candID).state.equals(state)){
				idvotes a = new idvotes(elec.get(candID).party, elec.get(candID).votes);
				id_partyvotes.put(candID, a);
			}
		}

		Iterator<String> itr = id_partyvotes.keySet().iterator();
		while(itr.hasNext()){
			String pointer = itr.next();
			idvotes a = new idvotes(id_partyvotes.get(pointer).Party, id_partyvotes.get(pointer).Votes);
			arrli.add(a);
		}


		for(int i = 0; i<arrli.size();i++){
			if (party_votes_new.containsKey(arrli.get(i).Party)){
				String a = arrli.get(i).Party;
				String newvotes = addvotes(arrli.get(i).Votes,party_votes_new.get(a));
				party_votes_new.put(a, newvotes);
			}
			else{
				party_votes_new.put(arrli.get(i).Party, arrli.get(i).Votes);
			}
		}

		int total = 0;

		Iterator<String> x = party_votes_new.keySet().iterator();
		while(x.hasNext()){
			String key = x.next();
			int num = Integer.parseInt(party_votes_new.get(key));
			total = total + num;
		}
		
		int share = Integer.parseInt(party_votes_new.get(party));
		int uttar = (int)(100*(((float)share)/((float)total)));

		System.out.println(uttar);				

	}




/*                                        ALSO, Change the edit done in driver code                                                */



 //////////////////////////////////////////////////Doing LevelOrder Traversal////////////////////////////////////////////////////
	
 	private Queue<Node> levelTraversal(){
		for (int i = 1; i<=height(root); i++){
			getChild(root, i);
		}
		return levelqueue;
	}

	private void getChild(Node root, int level){
		if (root == null){
			return;
		}
		if (level == 1){
			levelqueue.add(root);
		}
		else if (level > 1){
			getChild(root.left, level - 1);
			getChild(root.right, level - 1);
		}
	}

	private int height(Node root){
		if (root == null){
			return 0;
		}
		else{
			int lsub = height(root.left);
			int rsub = height(root.right);
			if(lsub > rsub){
				return (lsub + 1);
			}
			else{
				return (rsub + 1);
			}
		}
	}

 /////////////////////////////////////////////////////////Printing LevelOrder Traversal////////////////////////////////////////////////
	
 	public void printElectionLevelOrder() {
		levelorder = levelTraversal();							
		Iterator<Node> itr = levelorder.iterator();

		while (itr.hasNext()){
			Node temp = itr.next();
			System.out.println(temp.can.name + ", " + temp.can.candID + ", " + temp.can.state + ", " + temp.can.district + ", " + temp.can.constituency + ", " + temp.can.party + ", " + temp.can.votes);
		}
		levelorder.clear();				
	}
}