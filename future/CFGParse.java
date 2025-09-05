package future;
import java.io.*;

/** 
 *  The purpose of this class is to act as a parser and a derivation
 *  tree generator for a given grammar and string.
 *
 *  USAGE:
 *
 *  java CFGParse <grammar-filename> <input-string>
 *
 *  See the readme file inside the grammar directory for 
 *  valid grammar definition files.B
 *
 *  This is an example of dubious Object Oriented design.
 *  I shouldn't have embedded the parser method inside
 *  the EarleyRule method...
 *  As a temporary fix I've created this dummy class which just runs
 *  EarleyRule's main method for taking CFG's and parsing a string.
 *  
 *  @version 0.1 2/18/2001
 *  @author <a href="mailto:zeph@cs.columbia.edu">Zeph Grunschlag</a>
 * 
 */

public class CFGParse extends EarleyAgent{

    public static void main(String[] args) 
	throws FileNotFoundException, MalformedGrammarException
    {
	EarleyAgent.main(args);
    }

}





