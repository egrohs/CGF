package cfg;
import javax.swing.tree.*;
import java.io.*;

public class TreeModel2LaTeX{

    public static String preamble = "\\documentclass[12pt]{article}\n"
	+"\\usepackage{epic,ecltree}\n"
	+"\\begin{document}\n";
    public static String title(String parseString){
	return "\\title{The Parse Tree of $"+parseString+"$ }\n"
	    +"\\maketitle\n";
    }
    public static String postamble = "\\end{document}";

    public static StringBuffer ecltree(TreeNode node){
	StringBuffer temp = new StringBuffer(node.isLeaf()?"":"\\begin{bundle}{");
	temp.append(node);
	if(!node.isLeaf())
	    temp.append("}");
	else
	    return temp;
	for(int i=0; i<node.getChildCount(); i++){
	    temp.append("\n\\chunk{");
	    temp.append(ecltree(node.getChildAt(i)));
	    temp.append("}");
	}
	temp.append("\\end{bundle}\n");
	return temp;
    }

    public static String LaTeXversion(TreeNode node,String parseString){
	return preamble+title(parseString)+ecltree(node)+"\n"+postamble;
    }
    
    public static void createFile(TreeNode node, String parseString, String filename)
	throws FileNotFoundException{
	FileOutputStream fos = null;
	PrintWriter out = null;

	try{
	    fos = new FileOutputStream(filename);
	    out = new PrintWriter(fos);

	    out.println(LaTeXversion(node,parseString));
	    if(out != null ) out.close();
	    if(fos != null ) fos.close();
	}catch(FileNotFoundException e){ // bad file name
	    try{
		fos = new FileOutputStream("TemporaryFile.tex");
		out = new PrintWriter(fos);
		
		out.println(LaTeXversion(node,parseString));
		if(out != null ) out.close();
		if(fos != null ) fos.close();
	    }catch(Exception e2){
		e2.printStackTrace();
	    }
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }

}
