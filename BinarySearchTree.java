/**
   Implements a BinarySearchTree (BST).  Given a node r, all nodes in
   the left subtree of r are less than r.  All nodes in the right
   subtree of r are greater than or equal to r.

   Only Objects that implement Comparable can be stored in the tree.

   @author: S. Anderson and Kasherri  */



public class BinarySearchTree<TYPE extends Comparable> {
    protected BTNode<TYPE> root; // root of the tree
    
    public BinarySearchTree() { super(); root = null; }

    /**
       Adds an element to the tree.

       @param o an element that can be compared to others in the tree.
    */
    public void addElement(TYPE o) {
	if (root == null) root = new BTNode<TYPE>(o,null,null);
	else addElement(root,o);
    }

    /* 
       Helper method for adding element
       @param t root node of tree to which o is added.  Must NOT be null.
       @param o object to be inserted
     */
        private void addElement(BTNode<TYPE> t,TYPE o) {
	    int x= o.compareTo(t.element);
	    

	    if (0<=x) { //added to the right if o is greater than or equal to t
	    	if (t.right==null) 
	    		t.right= new BTNode<TYPE>(o,null,null);
	    	
	    	else
	    		addElement(t.right, o);

	    }
	    // add to left subtree
	    if (x<0) { //added to the left if o is less than t
	    	if (t.left==null) 
	    		t.left=new BTNode<TYPE>(o,null,null);
	    		
	    	
	    	else
	    		addElement(t.left,o);

	    }

	   
    }

    /**
       Return element that is equivalent to o.
       Return null if no match is found.
       @param o element to find.
    */

    public TYPE  getElement(TYPE o) {
	if (o == null) return null;
	else return getElement(root,o);
    }

    private TYPE getElement(BTNode<TYPE> t, TYPE o) {
	// TODO: There are three more cases!
    	BTNode<TYPE> parentofp = null;
    	BTNode<TYPE> p = t;

	// Find the node that is to be removed in a while loop.
	// Loop terminates when there is a match or null is encountered.
	// As you loop, you need to keep the parent of the current node, too.
	

	while (p!=null) {
		if (o.compareTo(p.element) < 0) {
			parentofp=p;
			p=p.left;	
		}
		else if (o.compareTo(p.element) > 0) {
			parentofp=p;
			p=p.right;
			
		}
		else
			break;
	}
	//p will either be null or contain o therefore:
	if (p!=null) {
		return p.element;
	}
	else
		return null;
	
    }

    

    /**
       Remove element with matching key and return it.
       @returns First object in tree with matching key.  Returns null
       if no match found.
    */
    public TYPE  removeElement(TYPE o) {
	if (root == null || o == null) return null;
	else return removeElement(root,o);
    }


    /**
       
       1. Locate the node you wish to delete. 

       2. If the node is a leaf, then disconnect it from its parent and set the
       parent's pointer that pointed to it to null.

       3. Otherwise if T has a right child, but no left child, then
       remove T from the tree by making T's parent point to T's right child.

       4. Otherwise if T has a left child but no right child, remove T from
       the tree by making T's parent point to T's left child.

       5. Finally, if T has both children, then find its logical
       follower in the right subtree.  Copy the value of the follower
       into T, then delete the follower.  Use recursion to delete the 
       follower.

    */
    private TYPE removeElement(BTNode<TYPE> t, TYPE o) {
	// nothing to remove from empty tree
	if (t == null) return null;
	
	BTNode<TYPE> parentofp = null;
	BTNode<TYPE> p = t;
	
	
	

	// Find the node that is to be removed in a while loop.
	// Loop terminates when there is a match or null is encountered.
	// As you loop, you need to keep the parent of the current node, too.
	

	while (p!=null) {
		if (o.compareTo(p.element) < 0) {
			parentofp=p;
			p=p.left;	
		}
		else if (o.compareTo(p.element) > 0) {
			parentofp=p;
			p=p.right;
			
		}
		else
			break;
	}
	//p is going to be null or contains the element o
	

//remove if p is leaf
	if (p.left==null && p.right==null) {
		TYPE x=p.element;
		removeLeaf(p, parentofp);
		return x;
		
	}
//remove if p has one subtree
	else if (p.left!=null && p.right==null || p.left==null && p.right!=null) {
		TYPE x=p.element;
		removeOneSubtree(p, parentofp);
		return x;
		
	}
	//remove if p has two subtrees
	else if (p.left!=null && p.right!=null) {
		TYPE x=p.element;
		removeTwoSubtrees(p, parentofp);
		return x;
		
	}
//if o cannot be found
	return null;
	
    }

    /*
      Remove a leaf.  The node p must be a leaf
      and pp must be its parent node.
      If p is root, then root is set to null.
    */
    private void removeLeaf(BTNode p, BTNode pp) {

	if (p== root) { 
	    root = null; 
	}
	else if (pp.left==p) {
		pp.left=null;
		
	}
	else {
		pp.right=null;
	}

    }	

    /**
       Remove p when it has only one subtree.
    */
    private void removeOneSubtree(BTNode p, BTNode pp) {
    	//when p is root and its subtree is on left side
    	if (p==root && p.left !=null) {
    		root=p.left;
    		p=null;
    		
    	}
    	//when p is root and its subtree is on right side
    	else if (p==root && p.right !=null) {
    		root=p.right;
    		p=null;
    		
    	}
    	//when p is left of its parent and its subtree is on left side
    	else if (pp.left==p && p.left!=null) {
    		pp.left=p.left;
    		p=null;
    	}
    	//when p is left of its parent and its subtree is on right side
    	else if (pp.left==p && p.right!=null) {
    		pp.left=p.right;
    		p=null;
    		
    	}
    	//when p is right of its parent and its subtree is on right side
    	else if (pp.right==p && p.right!=null) {
    		pp.right=p.right;
    		p=null;
    		
    	}
    	//when p is right of its parent and its subtree is on left side
    	
    	else 
    		pp.right=p.left;
    		p=null;
    	
	
    } 


    /**
       Remove p when it has two subtrees.  In this
       case we replace material in p with greatest element in 
       p's left subtree (maxnode).
    */
    private void removeTwoSubtrees(BTNode p, BTNode pp) {
	// TODO
    	BTNode maxnode=p.left;//left tree of p
    	BTNode parentoft=p; //parent of temp intially p

    	//transversing through right side of p.left
    	while(maxnode.right!=null){
         parentoft=maxnode;
         maxnode=maxnode.right;     
      }
      //maxnode.right is always null 


//base case
      if (maxnode.left==null) {
      	p.element=maxnode.element;
      	removeLeaf(maxnode, parentoft);
      }

      else if(maxnode.left!=null) {
      	p.element=maxnode.element;
      	removeOneSubtree(maxnode, parentoft);
   
}



 }

    /* 
       Returns height of tree.  Returns -1 if tree has no node. 
       sets heights in all encountered nodes.
    */
   private int height(BTNode t) {
	if (t == null) return -1;
	int leftHeight=height(t.left);
	int rightHeight=height(t.right);
	
	if (leftHeight>rightHeight) {
		return leftHeight+1;	
	}
	else
		return leftHeight+1;

    }


    /**
       Find heights of all nodes in tree.
    */
    public void computeNodeHeights() {
	height(root);
    }



    /******************************************************************/
    /* Graphics and display methods. */
    /******************************************************************/


    /**
       Return map containing keys.  Map is used
       to generate graphical representation of tree.
    */
    public TYPE[][] getMap() {
	int maxsize = 50;
	int maxrow = height(root)+1; // maximum height + 1
	int maxcol = (int) Math.pow(2.0,maxrow); //max cols in binary tree
	

	TYPE[][] map = (TYPE[][]) (new Object[20][20]);
	// fill array with keys from tree nodes.
	maxcol = drawTree(root,map,0,0,maxrow);

	TYPE[][] newmap = (TYPE[][]) (new Object[maxrow][maxcol]);
	for (int i = 0; i < newmap.length; i++) {
	    for (int j = 0; j < newmap[i].length; j++)
		newmap[i][j] = map[i][j];
	}
	return newmap;
    }


    /**
       Draw a binary search tree to stdout. (text)
    */
    public void showTree() {
	// map holds pointers to all objects in the tree
	// cannot handle more than 400 nodes on a page.
	//TYPE[][] map = (TYPE[][]) (new Object[20][20]);
	Object[][] map = new Object[20][20];
	int maxcol = 0;
	int maxrow = height(root)+1;
	// fixed field width for each node and for each blank
	final String padding = "   "; 

	System.out.println("Tree height is " + (maxrow-1));

	// fill array with keys from tree nodes.
	maxcol = drawTree(root,map,0,maxcol,maxrow);

	// use keys in map to place 
	for (int row = 0; row < maxrow; row++) {
	    for (int col = 0; col < maxcol; col++) {
		// if no node, print padding
		if (map[row][col] == null) System.out.print(padding);
		// if there is a node print it in a field of 
		//length padding.length
		else padPrint(map[row][col],padding.length());
	    }
	    System.out.println();
	}
    }

    /* print padding around strings */
    private void padPrint(Object o, int padlen) {
	String s = o.toString();
	System.out.print(s);
	int len = s.length();
	while (len++ < padlen) {
	    System.out.print(' ');
	}

    }


    /*
      Position tree nodes in a 2D array by first position left subtree,
      then node, then right subtree.
      @returns col, the maximum column used.
     */
    private int drawTree(BTNode root, Object[][] map,
				 int row, int col, int maxrow) {
	// stop if leaf node or if map dimensions exceeded
	if (root == null) return col;
	if ((maxrow+1) >= map.length || 
	    (col+1) >= map[0].length) {
	    System.out.println("WARNING. Tree could not be completely printed.");
	    return col; 
	}
	// maximum row (level) containing a node
	if (row > maxrow) maxrow = row; 
	
	col = drawTree(root.left,map,row+1,col,maxrow);
	map[row][col] = ((TYPE) root.element);
	col++;
	col = drawTree(root.right,map,row+1,col,maxrow);	
	return col;
    }

    /**
       Update drawing positions in tree prior to draw.
    */
    public void updatePositions() {
	positionNodes(root,0,0);
    }

    /**
       Update x,y in each node.
       y is the row = node depth.
       x is the col = #preceding nodes in left subtree + 1
    */
    private int positionNodes(BTNode r, int row, int col) {
	// stop if leaf node
	if (r == null) return col;
	
	// set position left subtree nodes
	col = positionNodes(r.left,row+1,col); 

	// set position of this node
	r.y = row;  // 
	r.x = col;
	col++;
	// set pos of right subtree nodes
	col = positionNodes(r.right,row+1,col); 
	return col;
    }

    /**
       Inorder traversal.
    */
    public void inOrder() {
        inOrderRec(root);
    }

    // recursive inorder traversal
    private void inOrderRec(BTNode<TYPE> t) {
        if (t == null) return;
        inOrderRec(t.left);
        System.out.print(t.element + " ");
        inOrderRec(t.right);
    }



}
