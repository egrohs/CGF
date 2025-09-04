package future;
/**
 * A class useful when the Grammar is not 
 * correctly defined
 */

class MalformedGrammarException extends Exception{
    MalformedGrammarException(){
	super();
    }

    MalformedGrammarException(String s){
	super(s);
    }
}
