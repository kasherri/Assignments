
public class  Recursion {
    /* Print for those with sore fingers. */
    public static void p(String s) {  
      System.out.println(s); 
    }
    
    public boolean isEven(int n){
      return isOdd(n)==false && n%2==0;
  

    }

    public boolean isOdd(int n){

        return isEven(n)==false;
    }


    public static void main(String[] args) {
   
    }



}