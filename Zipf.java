/*
  Gather frequencies of most common words in a document.

  Compilation: javac -cp .:WHEREVER/algs4.jar Zipf.java
  Running: java -cp .:WHEREVER/algs4.jar Zipf DESIRED_NUM < foo.dat

*/

import edu.princeton.cs.algs4.*;
import java.util.Scanner;
import java.awt.Font;

public class Zipf {


    // Draw plot of word frequencies.
    public static void plot(SequentialSearchST st,int num) {
	int canvaswidth = 800;
	int canvasheight = 800;
	
	int maxFreq = st.get(st.topN(1)[0]);
	StdDraw.setCanvasSize(canvaswidth,canvasheight);
	// rescale to give a margin
	StdDraw.setXscale(maxFreq*-0.1,maxFreq*1.50);
	StdDraw.setYscale(num*-0.10,num*1.20);

	double x = 5; // x init 
	double y = 3;  // y init
	double dy = 1; // word height
	int maxwordlen = 0;
        for (String s : st.topN(num)) {
	    if (s.length() > maxwordlen) maxwordlen = s.length();
	}
	double wordoffset = maxwordlen; // space for words

	StdDraw.setPenRadius(0.005); // for bars in plot
	
	StdDraw.setFont(new Font("Chalkboard", Font.BOLD, 18));
	StdDraw.text(x+wordoffset+(maxFreq/2),y/2,"Frequency");
	StdDraw.setFont(new Font("Chalkboard", Font.BOLD, 13));
	// Draw word+line from bottom up
	for (String s : st.topN(num)) {
	    StdDraw.setPenColor(StdDraw.DARK_GRAY);
	    StdDraw.textLeft(x,y,s); // word
	    StdDraw.setPenColor(StdDraw.BOOK_RED);
	    StdDraw.line(x+wordoffset,y,x+wordoffset+st.get(s),y); // frequency
	    y += dy;
	}
    }

    
    /**
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
	if (args.length < 1) {
	    System.err.println("USAGE: java Zipf numpts < input_text_file");
	    System.exit(-1);
	}
	int num = Integer.parseInt(args[0]); // num to count
        Scanner scanner = new Scanner(System.in); // scan stdin for input
        SequentialSearchST st = new SequentialSearchST();

	// Get words, insert into table.
        while (scanner.hasNext()) {
            String key = scanner.next();
            if (st.get(key) == null) {
                st.put(key, 1);
            } else {
                st.put(key, 1 + st.get(key));
            }

        }

	plot(st,num);

	System.out.println("Num unique words = " + st.size());
	// Print topN frequencies
        for (String s : st.topN(num)) {
            System.out.println(s + " " + st.get(s));
        }
    }
}
