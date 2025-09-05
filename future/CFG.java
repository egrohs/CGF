package future;
/**
 *      This class encapsulates Context Free Grammars.  Zeph Grunschlag and
 *      Yoav Hirsch are the authors.
 *
 *      Usage: java CFG <grammar_file> [<conversion instruction>]
 *      
 *      a grammar file is specified as follows:
 *         o  Each line consists of white-space separated strings which
 *         o  The first string of any line must have length 1 and is a variable
 *         o  Subsequent strings on a given line are the right hand sides
 *                of productions from the variable for that line
 *         o  The first variable in the file is the start variable
 *         o  A single double-quote symbol " represents the empty
 *                string epsilon 
 *                (we think of " as two single quotes with nothing in between)
 *         o  Any character which isn't a variable, is a terminal
 *
 *      For example a*b* with rule-set {S->AB,A->",A->aA,B->",B->bB} is
 *      given by the file which looks like:
S AB
A " aA
B " bB
 *     
 *      Conversion Instructions:  There are three possible flags which may not be
 *      combined at the moment:  -removeEpsilons, -removeUnits, -makeCNF.
 *      If no flags are specified, the program tries to create a CFG and gives some
 *      information.
 * 
 *      1)  -removeEpsilons  -- given an arbitrary grammar, modify the grammar to an
 *                             equivalent grammar with only one allowable epsilon
 *                             transtion:  start ---> epsilon
 *      2)  -removeUnits     -- given a grammar with no epsilon transitions, except possibly
 *                             from the start variable, modify the grammar to an equivalent
 *                             grammar with no unit transitions
 *      3)  -makeCNF         -- given a grammar with no epsilon transitions and no unit
 *                             transitions, convert into an equivalent grammar in Chomsky
 *                             Normal Form
 *
 *      removeEpsilons() is tested by hasNoEpsilonsExceptForStart(),
 *      removeUnits() is tested by hasUnitTransitions(), and
 *      makeCNF() is tested by isChomskyNormalFormGrammar()
 *
 * @author <a href="mailto:zeph@cs.columbia.edu">Zeph Grunschlag</a>
 * @author Yoav Hirsch
 * @version 0
 ****/


import java.util.*;
import java.io.*;

/**
 * A class that keeps track of general
 * Context Free Grammars
 */
public class CFG{
    char start;  // start variable
    TreeSet ruleSet; // keep the rules sorted
    HashMap ruleMap; // map from char to ArrayList of rule Strings

    TreeSet variableSet; // set of Characters

    /** Returns true if char represents a variable */
    public boolean hasVariable(char c){
	return variableSet.contains(new Character(c));
    }

    /** The constructor does almost nothing.
     * To do anything useful with the grammar, 
     * one needs to call 
     * parseFile(filename)  followed by 
     * makeRuleMap()
     * which respectively read in a grammar file in to the ruleSet
     * and set the other member variables according to the ruleSet.
     */
    public CFG(){
	ruleSet = new TreeSet();
    }

    /**The following constructor tries to build the
     * grammar using a supposed filename. It handles
     * exceptions itself.  This constructor should
     * be used carefully because if something goes
     * wrong in the input phase, the grammar will
     * be unpredictable.  
     * For constructing new grammars it is recommended
     * the the user use the following statements instead:
     *
     * CFG foo = new CFG();
     * foo.parseFile(filename);
     * foo.makeRuleMap();
     *
     * and then handle any resulting exception manually
     */
    public CFG(String fname){
	this();
	try{
	    parseFile(fname);
	    makeRuleMap();
	}catch( Exception e ){
	    System.err.println("WARNING:  Corrupt CFG has been defined!!!");
	    e.printStackTrace();
	}
    }

    /**The following constructor is used as a copier.
     * Make sure to call only after all the member have been defined
     */
    public CFG(CFG g){
	start = g.start;
	ruleSet = new TreeSet();
	Object[] objarray = g.ruleSet.toArray();
	for(int i = 0; i<objarray.length; i++){
	    Rule r = (Rule)objarray[i];
	    ruleSet.add(new Rule(r.LHS,r.RHS));
	}
	makeRuleMap();
    }

    /**
     * The first method that needs to be called after a constructor
     */
    public void parseFile(String fname) 
    	throws FileNotFoundException, MalformedGrammarException{
	FileInputStream fis;
	try{
	    fis = new FileInputStream(fname);
	    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	    StringTokenizer tokens;
	    int line_num = 0;
	    char currVar;
	    variableSet = new TreeSet();

	    
	    while (br.ready()){
		//String tok;
		String line = br.readLine();
		if( line == null ) continue; //when line ends immediately in '\n'
		tokens = new StringTokenizer(line, " \t");
		if (!tokens.hasMoreTokens()){ // empty line
		    System.out.println("empty");
		    continue;
		}

		/* FOR DEBUGGING
		StringTokenizer testT = new StringTokenizer(line, " \t");
		for(String t = testT.nextToken(); testT.hasMoreTokens(); t = testT.nextToken())
		    System.out.print(t+" ");
		*/

		// get the variable
		String variable = tokens.nextToken();
		if (variable.length() != 1)
		    throw new MalformedGrammarException();
		currVar = variable.charAt(0);
		variableSet.add(new Character(currVar));

		// start variable is at the beginning of the file
		if (line_num == 0)
		    start = currVar;

		// get the productions
		while(tokens.hasMoreTokens()){
		    String prod = tokens.nextToken();
		    if (prod.equals("\"")) // epsilon production 
			ruleSet.add(new Rule(currVar,"")); 
		    else if( prod.indexOf('\"')!=-1 ){
			throw new MalformedGrammarException();
		    }
		    else
			ruleSet.add(new Rule(currVar,prod));
		}
		line_num++;
	    }
	    fis.close();
	}catch(IOException e){
	    if(e instanceof FileNotFoundException){
		e.printStackTrace();
		throw new FileNotFoundException();
	    }
	    throw new MalformedGrammarException();
	}catch(NullPointerException e){
		e.printStackTrace();
		throw new MalformedGrammarException();
	}
    }

    public void createFile(String filename)
	throws FileNotFoundException{
	FileOutputStream fos = null;
	PrintWriter out = null;

	try{
	    fos = new FileOutputStream(filename);
	    out = new PrintWriter(fos);

	    Object[] LHSarray = variableSet.toArray();
	    Character LHS;
	    Object[] RHSarray;
	    for(int i = 0; i<LHSarray.length; i++){
		LHS = (Character)LHSarray[i];
		out.print(LHS);
		RHSarray = ((ArrayList)ruleMap.get(LHS)).toArray();
		for(int j=0; j<RHSarray.length; j++)
		    out.print(" "+RHSarray[j]);
		out.print("\n");
	    }
	
	    if(out != null ) out.close();
	    if(fos != null ) fos.close();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }
    

    /**
     * This method resets the ruleMap and variableSet
     * in accordance with the ruleSet
     * Should be called right after parseFile(filename)
     */
    public void makeRuleMap(){
	Object[] vars  = variableSet.toArray();
	Object[] rules = ruleSet.toArray();
	ArrayList al;  // temp ArrayList variable
	Character curr; // temp variable

	// initialize the ruleMap
	ruleMap = new HashMap();
	for(int i=0; i<vars.length; i++){
	    curr = (Character)(vars[i]);
	    al = new ArrayList();
	    ruleMap.put(curr, al);
	}

	// fill the ruleMap	    
	for(int i=0; i<rules.length; i++){
	    Rule r = (Rule)rules[i];
	    curr = new Character(r.LHS);
	    al = (ArrayList)ruleMap.get(curr);
	    al.add(r.RHS);
	}
    }	

    public String toString(){
	Object[] vars = variableSet.toArray();
	StringBuffer temp = new StringBuffer();
	temp.append("Variable set is {");
	for(int i=0; i<vars.length; i++){
	    temp.append(""+(i>0?",":"")+" "+vars[i]);
	}
        temp.append("}\nStart variable is "+start);
	for(int i=0; i<vars.length; i++){
	    Character c = (Character)vars[i];
	    ArrayList al = (ArrayList)ruleMap.get(c);
	    temp.append("\n"+c+" --> ");
	    for(int j=0; j<al.size(); j++)
		temp.append( (j>0?" | " : " " )+showEpsilon(al.get(j)) );
	}	
	return temp.toString();
    }

    /*** Convert to "Epsilon" if x.toString().equals("") */
    private static String showEpsilon(Object x){
	String input = x.toString();
	return ( input.equals("") ? "<epsilon>" : input );
    }

    /** returns true if epsilons transitions exist in the grammar */
    public boolean hasEpsilons(){
	//uses the fact that the empty string "" is the smallest string
	//so that epsilon transition are firsr in the rule map
	Object[] v = variableSet.toArray();
	for(int i=0; i<v.length; i++)
	    if ( ((ArrayList)ruleMap.get((Character)v[i] )).size() > 0 && 
	         ((ArrayList)ruleMap.get((Character)v[i] )).get(0).equals("") )
		return true;
	return false;
    }

    /** returns true if only epsilon transition is from the start variable.
	This is okay for a Chomsky Normal Form Grammar
    */
    public boolean hasNoEpsilonsExceptForStart(){
	//slight modificatino of hasEpsilons() to case allow for start --> epsilon:
	Object[] v = variableSet.toArray();
	for(int i=0; i<v.length; i++)
	    if ( ( (ArrayList)ruleMap.get((Character)v[i] )).size() > 0 
		 && ( (ArrayList)ruleMap.get( (Character)v[i] )  ).get(0).equals("") 
		  && !v[i].equals(new Character(start)) )
		return false;
	return true;
    }

    /** returns true if unit variable transitions exist in the grammar */
    public boolean hasUnitTransitions(){
	Object[] v = variableSet.toArray();
	for(int i=0; i<v.length; i++){
	    ArrayList al = (ArrayList)ruleMap.get( (Character)v[i] );
	    for(int j=0; j<al.size(); j++){
		String RHS = (String)al.get(j);
		if( RHS.length() == 1 && variableSet.contains(new Character(RHS.charAt(0))) )
		    return true;
	    }
	}
	return false;
    }

    /** returns true if the grammar is in Chomsky Normal Form 
     *  I.E.
     *  1. Only allowable epsilon production is from start variable
     *  2. No unit productions
     *  3. All non-terminal production are of length 2 and contain no terminals
     *  4. The start symbol is not on the right hand side of any production
     */
    public boolean isChomskyNormalFormGrammar(){
	if( !hasNoEpsilonsExceptForStart() )
	    return false;

	// unit transitions?
	if ( hasUnitTransitions() )
	    return false;

	// now check that all the transitions are length <= 2, 
	// and if == 2, contain no terminals, and don't contain start symbol
	Object[] v = variableSet.toArray();

	//System.out.println("Checking for final stage of CNF: ");
	for(int i=0; i<v.length; i++){
	    ArrayList al = (ArrayList)ruleMap.get( (Character)v[i] );
	    for(int j=0; j<al.size(); j++){
		String RHS = (String)al.get(j);
		//System.out.println(v[i]+" -> "+RHS);
		if( RHS.length() > 2)
		    return false;
		else if ( RHS.length() == 2 ){
		    //System.out.println(RHS);
		    if(!( variableSet.contains(new Character(RHS.charAt(0)))
			  &&  variableSet.contains(new Character(RHS.charAt(1)) ) )
		       || RHS.charAt(0)==start || RHS.charAt(1)==start )
			return false;
		}
	    }//for loop going through RHS's
	}//for loop going throw variables
	return true;
    }
    
    /** Modify this for your purposes **/
    public static void main(String[] args) 
	throws FileNotFoundException, MalformedGrammarException{
	if(args.length < 1 || args.length > 2) {
	    System.out.println("Usage: java Parser <grammar_file> [<conversion instruction>]");
	    System.exit(1);
	}
	
	String fname = args[0];
	String conversionInstruction = (args.length == 2) ? args[1] : null ;
	
	// Build the cfg
	CFG cfg = new CFG();
	cfg.parseFile(fname);
	cfg.makeRuleMap();
	
	fname = (new StringTokenizer(fname,".")).nextToken();
	if( conversionInstruction != null){
	    String suffix = "";
	    if (conversionInstruction.equals("-removeEpsilons")){
		cfg.removeEpsilons();
		suffix = "_noeps";
	    }
	    if (conversionInstruction.equals("-removeUnits")){
		cfg.removeUnits();
		suffix = "_nounits";
	    }
	    if (conversionInstruction.equals("-makeCNF")){
		cfg.makeCNF();
		suffix = "_cnf";
	    }
	    cfg.createFile(fname+suffix+".cfg");
	}

	/* Yoav's main:
	test(cfg);
	System.out.println();
	System.out.println(" ******* After CNF'ing: ********");
	cfg.removeEpsilons();
	cfg.removeUnits();
	cfg.makeCNF();
	test(cfg);

	*/
    }


    public static void test(CFG cfg) {
	System.out.println(cfg);
	System.out.println("Number of variables = "+ cfg.variableSet.size());
	System.out.println("Number of rules = "+ cfg.ruleSet.size());
	System.out.println("Contains epsilons? "+cfg.hasEpsilons());
	System.out.println("Has no epsilons, except perhaps <START> --> epsilon? "
			   +cfg.hasNoEpsilonsExceptForStart() );
	System.out.println("Contains variable unit transitions? "+cfg.hasUnitTransitions());
	System.out.println("Is in Chomsky Normal Form? "+cfg.isChomskyNormalFormGrammar() );
	


    }

    /**** EXTRA CREDIT. EACH METHOD WORTH 5 pts. ****/
    
    /**     given an arbitrary grammar, modify the grammar to an
     *     equivalent grammar with only one allowable epsilon
     *     transtion:  start ---> epsilon
     **/
    public void removeEpsilons(){

	/* Before we do anything, add a new start symbol */
	Rule newStart = new Rule(getUnusedSymbol(),start+"");
	start = newStart.getLHS();
	ruleSet.add(newStart);
	makeRuleMap();
	
       
	Object[] vars = variableSet.toArray();
	Iterator itr;
	Rule rule;
	char symbol;
	TreeSet tempSet = new TreeSet();
	
	int test = 0;
	
	while(!hasNoEpsilonsExceptForStart()) {
	
	    for(int i=0; i<vars.length; i++) {
		symbol = ((Character)vars[i]).charValue();
		System.out.println("i: "+i+"  curr symbol: "+symbol);
		//try to remove the rule that correspons to this symbol deriving an epsilon
		//if you successfully remove it, you need to update other symbols...
		if(ruleSet.remove(new Rule(symbol, "" ))) {
		    //System.out.println("removed epsilon rule for symbol: "+symbol);
		    itr = ruleSet.iterator();
		    while(itr.hasNext()) {
			rule = ((Rule)itr.next()).copyAndReplaceAll(symbol,"");
			if(rule != null) {
			    //Here I add to the new rule to tempSet, and at the end of the loop
			    //do a batch add to the ruleSet. The reason for this is that a TreeSet
			    //is not allowed to change while an iterator is operating on it.
			    if(!rule.isEpsilonRule() || rule.getLHS() == start) 
				tempSet.add(rule);
			}//if 
		    }//while
		}//if
		
	    }//for

	    //add all the new rules made to the ruleSet
	    ruleSet.addAll(tempSet);
	    tempSet = new TreeSet();
	    //update the ruleMap so that hasNoEpsilonsExceptForStart() will work correctly
	    makeRuleMap();
	}
    }


    /**     given a grammar with no epsilon transitions, except possibly
     *      from the start variable, modify the grammar to an equivalent
     *      grammar with no unit transitions
     *   @throws MalformedGrammarException if there is a non-start
     *      epsilon transition in the input
     **/
    public void removeUnits() throws MalformedGrammarException{
	if (!hasNoEpsilonsExceptForStart())
	    throw new MalformedGrammarException("When removing unit transitions"+
                                                " may not have non-start epsilon transitions");
	Rule rule;
	Iterator iter1;
	Iterator iter2;

	TreeSet removeSet; //will be used for batch removal
	TreeSet addSet; //will be used for batch addition
	

	while(hasUnitTransitions()) {

	    removeSet = new TreeSet();
	    addSet = new TreeSet();
	    iter1 = ruleSet.iterator();
	    
	    while(iter1.hasNext()) {
		rule = (Rule)iter1.next();
		if(rule.isUnitRule()) {

		    char LHS = rule.getRHS().charAt(0);
		    if(variableSet.contains(new Character(LHS))) {
			removeSet.add(rule);
			if(rule.isReflexiveRule()) continue; 
			//if the transition is to itself, there is no processing to do
		    }
		    else continue; //trasition to a terminal, rule is ok
		    

		    //the new rules I create here are the bounds of the subset.
		    //the subSet method returns the set that falls with in the range: [rule1,rule2)
		    //hopefully all rules have RHS's strictly less than "zzzzzzzzzzz"
		    
		    SortedSet subset = ruleSet.subSet(new Rule(LHS,""),new Rule(LHS,"zzzzzzzzzzzzzzz"));
		    iter2 = subset.iterator();			
		    while(iter2.hasNext()) {
			Rule currRule = (Rule)iter2.next();
			Rule newRule = new Rule(rule.getLHS(),currRule.getRHS());
			if(!newRule.isReflexiveRule())
			    addSet.add(newRule);
			
		    } //while
		    //}//if
		}//if
	    }//while

	    ruleSet.removeAll(removeSet);
	    ruleSet.addAll(addSet);

	    //update the map 
	    makeRuleMap();
	    
	}//while hass unit transitions
	
	
    }

    /**      given a grammar with no epsilon transitions and no unit
     *       transitions, convert into an equivalent grammar in Chomsky Normal Form
     *  @throws MalformedGrammarException if the input grammar contains non-start
     *       epsilon transitions, or unit terminal transitions
     */
    public void makeCNF() throws MalformedGrammarException{
	if (hasUnitTransitions() || !hasNoEpsilonsExceptForStart())
	    throw new MalformedGrammarException("When converting to Chomsky Normal Form"+
                                                " must not have non-start epsilon transitions"+
						" and must have gotten rid of unit transitions");
	Iterator iter;
	TreeSet addSet;
	
	int test = 0;
	while(!isChomskyNormalFormGrammar()) {
	  
	    iter = ruleSet.iterator();
	    addSet = new TreeSet();

	    while(iter.hasNext()) {
		Rule rule = (Rule)iter.next();
		String RHS = rule.getRHS();
		
		if(rule.isEpsilonRule()) continue; //do nothing, it must be the start symbol
		
		if(rule.isUnitRule()) continue; //do nothing, it must be a terminal
		
		//if one or both characters in the RHS are terminals we have to process the rule
		if(RHS.length() == 2) {
		    char c1 = RHS.charAt(0);
		    char c2 = RHS.charAt(1);
		    String newRHS = "";

		    if(!variableSet.contains(new Character(c1))) {
			newRHS += addNewRuleIfNeeded(c1+"",addSet);
		    }
		    else newRHS += c1;
	
		    if(!variableSet.contains(new Character(c2))) {
			newRHS += addNewRuleIfNeeded(c2+"",addSet);	
		    }
		    else newRHS += c2;
		    
		    rule.setRHS(newRHS);
 
		    continue;
		}//if RHS length == 2
		    
		String substr = RHS.substring(1,RHS.length());
		rule.setRHS(RHS.charAt(0)+""+addNewRuleIfNeeded(substr,addSet));
	    }
	    ruleSet.addAll(addSet);
	    makeRuleMap();
	    // System.out.println(toString());
	    

	}//whlie not in CNF 
    }


    //helper methods used in makeCNF()

    //If in the process of making the CNF a new symbol needs to be used, use this method.
    //It only support 26 symbols. So if a CNF grammar needs more than that, we will have problems.
    //This also takes care of adding the new symbol to the variableSet
    private char getUnusedSymbol() {
	Character c;

	for(int i=(int)'A';i<=(int)'Z';i++)
	    if(!variableSet.contains(c = new Character((char)i))) {
		variableSet.add(c);
		//System.out.println("Adding new symbol: "+(char)i);
		return (char)i;
	  }

	//The following loop is commented out because using lowercase
	//letters for terminals is not uncommon.  Since terminals do not
	//go into the variable set, this loop might cause a terminal character
	//to be returned as the new symbol to be used.  
	/*
	  for(int i=(int)'a';i<=(int)'z';i++)
	  if(!variableSet.contains(c = new Character((char)i))) {
	  variableSet.add(c);
	  return (char)i;
	  }
	*/
	//hopefully the program will never get to this point
	return '\0';
    }

    
    //used by makeCNF() if it needs to make a new rule.  If it creates 
    //a new rule it will add it to the addSet argument.
    private char addNewRuleIfNeeded(String RHS,TreeSet addSet) {
	//System.out.print("addNewRuleIfNeeded: RHS = "+RHS);
	Iterator iter = variableSet.iterator();
	ArrayList al;
	Character symbol;
	while(iter.hasNext()) {
	    symbol = (Character)iter.next();
	    //System.out.println("Symbol = "+symbol);
	    al = (ArrayList)ruleMap.get(symbol);
	    
	    //if the symbol was added to the varlist, but not yet to the
	    //ruleSet or ruleMap, the ArrayList will be null.  The addSet
	    //passed in must then have the rule for this symbol
	    if(al == null) {
		Iterator iter2 = addSet.iterator();
		while(iter2.hasNext()) {
		    Rule tmp = (Rule)iter2.next();
		    if(tmp.getRHS().equals(RHS))
			return tmp.getLHS();
		} 
		continue; 
	    }
	    if(al.size() == 1 && al.get(0).equals(RHS))
		return symbol.charValue();
	}

	//hopefully the getUnusedSymbol() method does not run out of  
	//characters return '\0'
	Rule newRule =  new Rule(getUnusedSymbol(),RHS);
	addSet.add(newRule);
	return newRule.getLHS();
    }
    

    public char getStartSymbol() {
	return start;
    }

}// class CFG







