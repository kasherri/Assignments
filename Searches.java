/*
  A couple of basic searching algorithms.
*/

public class Searches extends TestRuntime {
    /**
      @returns true iff x is found in a
      
      Uses simple sequential search.
    */
    public  static boolean search(int x, int a[]) {

	for (int i = 0; i < a.length; i++) {
	    if (x == a[i]) {
	    	
	    	counterSearch++;
	    	return true;
	    }
	    counterSearch++;
	}
	return false;
    }

    /**
       @returns true iff x is found in a
       @param x the integer to find
       @param a[] the array to search
       
       Uses iterative binary search.
    */
    public  static boolean binarySearch(int x, int a[]) {

	if (a == null) return false;
	int first = 0;
	int last = a.length-1;
	int mid;

	while (first < last) {
	
	  mid = (first+last)/2;
	  if (a[mid] < x) {
	  counterBinary++;
		first = mid+1;
	    }

	    else 
	    	counterBinary++;
	    	last = mid;
	   
	}

	if (a[first] == x){
		counterBinary++;
		return true;
	}
	// not found and array exhausted
	
	return false;
    }    
    

}
