package cfg;
import java.io.*;
import java.util.*;
import java.awt.*;           // The rest are for tree stuff
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;


/** 
 * A class that keeps track of each step in Earley's
 * algorithm for parsing CFG's.  We name it "EarleyAgent"
 * as opposed to "EarleyRecord" because each instance is highly
 * automated, being endowed with the ability to sense
 * where the Early Algorithm is at and produce the next
 * instances.
 *
 * The algorithm for parsing is also embedded here,
 * as a static method.  
 *
 * @version 0.3 3/11/2001
 * @author <a href="mailto:zeph@cs.columbia.edu">Zeph Grunschlag</a>
 **/
public class EarleyAgent implements Comparable{



    /* Instance fields */
    char LHS;  //left hand side (upper case)
    String[] RHS = new String[2];  // both parts of the production
    int I; // the start index 
    int J; // up to where the parse has succeeded

    // TO CREATE PARSE-TREE ALSO NEED:
    EarleyAgent leftCreator, rightCreator;
    
    /** return true if parse has succeed to get the RHS
     *  completely to the left of the break point
     */ 
    boolean isDone(){
	return RHS[1].length()==0;
    }

    /**
     * Rule always starts looking like:
     * A --> (i)(i).XYZ 
     * unless j is specified
     */
    public EarleyAgent(char left, String right, int i){
	LHS = left;
	RHS[1] = right;
	RHS[0] = "";
	J = I = i;
    }


    /**
     * Rule always starts looking like:
     * A --> (i)(i).XYZ 
     * unless j is specified
     */
    public EarleyAgent(char left, String right, int i, int j){
	LHS = left;
	RHS[0] = right.substring(0,j-i);
	RHS[1] = right.substring(j-i);
	I = i;
	J = j;
    }
    
    /** Constructor for copying */
    public EarleyAgent(EarleyAgent x){
	LHS = x.LHS;
	RHS[0] = x.RHS[0];
	RHS[1] = x.RHS[1];
	I = x.I;
	J = x.J;
    }
	
    /** Empty constructor for extending class CFGParse */
    public EarleyAgent(){}

    public boolean canAdvance(){
	return !isDone() 
	    && J < INPUT.length()
	    && RHS[1].charAt(0)==INPUT.charAt(J);
	    }
    
    /** advance forward regardless of the input string */
    public EarleyAgent advanceRegardless(){
	EarleyAgent temp = new EarleyAgent(this);
	if (temp.isDone())
	    return temp;
	temp.J++;
	temp.RHS[0] += temp.RHS[1].charAt(0);
	temp.RHS[1] = temp.RHS[1].substring(1);
	return temp;
    }

    /**for the advance phase of the algorithm */
    public EarleyAgent advance(){
	if (!canAdvance())
	    return null;
	return advanceRegardless();
    }

    /** true if the first character of second halp of production is a variable */
    public boolean canClose(){
	return !isDone() && GRAMMAR.hasVariable(RHS[1].charAt(0)) ;
    }
	
    /**for the closure phase of the algorithm */
    public EarleyAgent[] close(){
	if (!canClose() ) // the next letter isn't a variable
	    return null;
	char generator = RHS[1].charAt(0);
	return getAllLoops(generator, J);
    }

    public boolean canComplete(EarleyAgent prefix){
	return isDone() && J>I && I == prefix.J 
	    && !prefix.isDone() && prefix.RHS[1].charAt(0)==LHS;
    }


    /**for the completion phase of the algorithm */
    public EarleyAgent complete(EarleyAgent prefix){
	if (!canComplete(prefix))
	    return null;
	EarleyAgent temp = prefix.advanceRegardless();
	temp.J = J;
	return temp;
    }


    /** RECURSIVE NODE BUILDING METHOD **/
    public static DefaultMutableTreeNode ParseTree(EarleyAgent startNode){
	DefaultMutableTreeNode parent =  new DefaultMutableTreeNode(""+startNode.LHS);
	String childstr = startNode.RHS[0];
	EarleyAgent itr = startNode;
	for(int index = childstr.length()-1; index >=0; index--){
	    char c = childstr.charAt(index);
	    // first case, variables.  So right creator exists...
	    if( GRAMMAR.hasVariable(c) )
		parent.insert(ParseTree(itr.rightCreator),0);
	    else //must be a terminal
		parent.insert(new DefaultMutableTreeNode(""+c),0);
	itr = itr.leftCreator;
	}
	return parent;	
    }

    /** JTREE BUILDING METHOD **/
    public static JTree ProofParseTree(EarleyAgent startNode){
	return new JTree(ParseTree(startNode));
    }

    /** JFRAME INTERNAL CLASS FOR SHOWING THE JTREE **/
    static class ParseTreeFrame extends JFrame{
	public ParseTreeFrame(EarleyAgent startNode){
	    setTitle("Parse tree for the string: "+INPUT);
	    setSize(300,200);
	    Container contentPane = getContentPane();
	    if (startNode == null){ //INPUT was rejected
		String warning = GRAMMAR.hasEpsilons()?("WARNING:  Epsilon transitions encounterd.\n"+
							"Unpredictable behavior may result"):"";
		JTextArea expo = new JTextArea("No Parse Tree Available!\n"+warning+"\n"+
					       INPUT+" is rejected by the grammar\n"+GRAMMAR);
		contentPane.add(expo);		
	    }
	    else{
		TreeNode root = ParseTree(startNode);
		JTree jtree = new JTree(root);
		jtree.setFont(new Font(null,Font.BOLD,20));
		contentPane.add(new JScrollPane(jtree));
		
		/** Create a LaTeX ecltree file **/
		String filename = "Error.tex";
		try{ 
		    TreeModel2LaTeX.createFile(root, INPUT, INPUT+".tex" );
		    filename = INPUT+".tex";
		}
		catch(FileNotFoundException e){ // bad file name
		    try{
			TreeModel2LaTeX.createFile(root, INPUT, "TempECLTree.tex");
			filename = "TempECLTree.tex";
		    }catch(FileNotFoundException e2){
			e2.printStackTrace();
		    }
		}
		/* THIS CODE IS BUGGY, SO REMOVED FOR NOW
		if( !filename.equals("Error.tex") ){
		    try{
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(new String[]{"latex ",filename});
			p.waitFor();
			rt.exec(new String[]{"yap ",filename.substring(0,filename.length()-3)+"dvi"});
		    }catch(IOException e){
			e.printStackTrace();
		    }catch(InterruptedException e){
			e.printStackTrace();
		    }		    
		}
		*/
	    }
	    addWindowListener(new WindowAdapter() // anonymous class
		{ public void windowClosing(WindowEvent e){ System.exit(0); } } 
			      );
	    
	} 
    }
    
    /** for implementing Comparable **/
    public int compareTo(Object x){
	EarleyAgent y = (EarleyAgent)x;

	//Rules in record[J-1] come before rules in record[J]
	if( J != y.J )    
	    return J-y.J;

	//Completed rules come first in record[J]
	//Thus they are all contiguous and an extral log(n)
	//Factor can be avoided (if really want to)
	if( (isDone() && !y.isDone()) || (!isDone() && y.isDone()) )
	    return RHS[1].length() - y.RHS[1].length(); 
	
	
	//MAY WANT TO REFINE THIS IN THE FUTURE...
	//Two completed rules are compared by rather arbitrary toString()
	if ( isDone() && y.isDone() )
	    return toString().compareTo(y.toString());

	//Two uncomplete rules are compared by their first RHS char
	//and then arbitrarily (makes it easy to do advance and closure
	if( RHS[1].charAt(0) != y.RHS[1].charAt(0) )
	    return RHS[1].charAt(0) - y.RHS[1].charAt(0);

	//Otherwise, compare arbitarily
	return toString().compareTo(y.toString());
    }

    public boolean equals(Object x){
	return compareTo(x)==0;
    } 

    public String toString(){
	return ""+LHS+" ---> ("+I+") "+
	    RHS[0] +".("+J+") "+RHS[1];
    }
    

    /************************************************************
     * Static portion of the class implements the parsing algorithm 
     ************************************************************/
    
    /* Static fields */
    static EarleyAgent proofOfSuccess;

    static String INPUT;  //The input String to parse
    static CFG GRAMMAR;   //The grammar to parse with

    static TreeSet[] RECORD;  //Array of TreeSets for EarlyRule's
    
    /** Static explicit class Initializer **/
    public static void INITIALIZE(CFG g, String x) 
	throws ExceptionInInitializerError,
	       MalformedGrammarException{
	INPUT = x;
	GRAMMAR = g;
	RECORD = new TreeSet[x.length()+1];
	if( GRAMMAR.hasEpsilons() )
	    /* FOR NOW THE FOLLOWING COMMENTED OUT B/C EXPERIMENTING
	     * WITH BEHAVIOR WHEN EPSILON TRANSITIONS ARE ALLOWED
	     * throw new MalformedGrammarException("Must not have epsilons!");
	     */
	    System.err.println("WARNING:  Epsilon transitions encounterd.\n"+
			       "Unpredictable behavior may result");
    }
    
    /* Static Accessor Methods */
    public static String GETINPUT(){return INPUT;}
    public static CFG GETGRAMMAR(){return GRAMMAR;}

    /**************** Static helper methods **********************/

    /** Cast from Object array to EarleyAgent array **/
    public static EarleyAgent[] O2ER(Object[] x){
	if (x==null)
	    return null;
	EarleyAgent er[] = new EarleyAgent[x.length];
	for(int i=0; i<x.length; i++){
	    er[i] = (EarleyAgent)x[i];
	}
	return er;
    }

    /** used for the "Close" and "initialize" stage of Earley parsing algorithm */
    public static EarleyAgent[] getAllLoops(char left, int j){
	ArrayList al = (ArrayList)(GRAMMAR.ruleMap.get(new Character(left)));
	int numRules = al.size();
	EarleyAgent[] temp = new EarleyAgent[numRules];
	for(int i=0; i<numRules; i++){
	    String s = (String)al.get(i);
	    temp[i] = new EarleyAgent(left,s,j);
	}
	return temp;	
    }


    /************* Methods for Earley's Algorithm ********************/

    /** THE MAIN PARSING ALGORITHM */
    public static void EarleyParse(){
	initializeEarley();             // 0.  INITILIZE
	for(int j=0; j<INPUT.length();){
	    closeEarley(j);             // 1.  CLOSE
	    advanceEarley(j++);         // 2.  ADVANCE
	    completeEarley(j);          // 3.  COMPLETE
	}

	//look for proof of success:
	Iterator recItr = RECORD[INPUT.length()].iterator();
	while( recItr.hasNext() ){
	    EarleyAgent er = (EarleyAgent)recItr.next();
	    if ( er.isDone() && er.I==0 && er.LHS==GRAMMAR.start){
		proofOfSuccess = er;
		return;
	    }
	}
    }


    /** 0. "INITIALIZE" 
     * Set up the first set of records before starting with the while-loop */
    public static void initializeEarley(){
	EarleyAgent[] temp = getAllLoops(GRAMMAR.start,0);
	RECORD[0] = new TreeSet(Arrays.asList(temp));
	//creators are null
    }

    /** 1. "CLOSE" */
    public static void closeEarley(int j){
	LinkedList q = new LinkedList(RECORD[j]);
	while ( q.size() != 0 ){
	    EarleyAgent er = (EarleyAgent)(q.removeFirst());  //dequeue
	    EarleyAgent[] c = er.close();
	    if ( c==null ) continue;
	    for(int k=0; k<c.length; k++)
		if( RECORD[j].add(c[k]) ){ //true if rule was newly created
		    q.add(c[k]);          //then enqueue
		    c[k].leftCreator = er;
		}
	}	
    }

    /** 2. "ADVANCE" stage of Earley parsing algorithm */
    public static void advanceEarley(int j){
	RECORD[j+1] = new TreeSet();
	EarleyAgent[] prev = O2ER(RECORD[j].toArray());
	for(int k=0; k< prev.length; k++){
	    EarleyAgent next = prev[k].advance();
	    if (next==null) continue;
	    RECORD[j+1].add(next);
	    next.leftCreator=prev[k];
	}
    }

    ////THE FOLLOWING METHOD CAN BE SPEEDED UP DRASTICALLY!
    /** 3. "COMPLETE" */
    public static void completeEarley(int j){
	LinkedList q = new LinkedList(RECORD[j]);
	EarleyAgent suffix, prefix, combination;

	while ( q.size() != 0 ){
	    suffix = (EarleyAgent)(q.removeFirst());  //dequeue
	    if (!suffix.isDone()) continue;
	    Iterator recItr = RECORD[suffix.I].iterator();
	    while( recItr.hasNext() ){
		prefix = (EarleyAgent)recItr.next();
		combination = suffix.complete(prefix);
		if (combination==null) continue;
		if( RECORD[j].add(combination) ){ //true if rule was newly created
		    q.add(combination);          //then enqueue
		    combination.leftCreator = prefix;
		    combination.rightCreator= suffix;
		}
	    }
	}	
    }


    /** Main method for parsing context free grammars using Earley's Algorithm */
    public static void main(String[] args)
	throws FileNotFoundException, 
	       MalformedGrammarException,
	       ExceptionInInitializerError {
	if(args.length != 2){
	    System.out.println("Usage: java Parser <grammar_file> <input_string>");
	    System.exit(1);
	}

	String fname = args[0];
	String input = args[1];

	// Build the cfg
	CFG cfg = new CFG();
	cfg.parseFile(fname);
	cfg.makeRuleMap();
	
	/* COPIED THE FOLLOWING FROM CFG's main() METHOD */
	System.out.println(cfg);
	System.out.println("Number of variables = "+ cfg.variableSet.size());
	System.out.println("Number of rules = "+ cfg.ruleSet.size());
	System.out.println("Contains epsilons? "+cfg.hasEpsilons());
	System.out.println("Has no epsilons, except perhaps <START> --> epsilon? "
			   +cfg.hasNoEpsilonsExceptForStart() );
	System.out.println("Contains variable unit transitions? "+cfg.hasUnitTransitions());
	System.out.println("Is in Chomsky Normal Form? "+cfg.isChomskyNormalFormGrammar() );

	/* STATIC INITIALIZOR */
	INITIALIZE(cfg,input);
	
	char start = cfg.start;
	
	System.out.println("Parse the string, using Earley's worst case O(n^3) algorithm.");
	INITIALIZE(cfg,input);
	EarleyParse();
	EarleyAgent[][] everything  = new EarleyAgent[input.length()+1][];
	for(int j=0; j<everything.length; j++){
	    everything[j] = O2ER(RECORD[j].toArray());
	    for(int i=0; i<everything[j].length; i++)
		System.out.println(everything[j][i]);
	}

	System.out.println("\nProof of success: "+proofOfSuccess);

	JFrame jframe = new ParseTreeFrame(proofOfSuccess);
	jframe.setVisible(true);

    }
}


