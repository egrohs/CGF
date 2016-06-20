This files explains the conventions of how a grammar file looks like:

1. Each line contains white-space separated strings callsed "tokens"
2. The first token on any line must be a string of length 1 and 
	represents a variable.
3. All consequent tokens on the given line represent alternate rules from
	the variable which was the first token.
4. The start variable is the variable at the top of the file.
5. All characters which are not variables, are terminals.
6. The empty word (epsilon) is represented by the double-quote character.
7. Epsilon must appear by itself in any given production.

For example consider the following file:
------- Begins on next line:
S " AB
A 0A 0
B B1 1
------- Stopped on previous line.
Then the grammar represented by this file is described by:

		Variable-Set = { A, B, S }

		Terminal-Set = { 0, 1 }

		Start-Variable = S

		Productions are:
			S --> <epsilon> | AB
			A --> 0A | 0
			B --> B1 | 1