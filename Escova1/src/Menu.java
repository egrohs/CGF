import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  

public class Menu extends JMenuBar
{	
	private JFrame f;
	private JDesktopPane desktop;
	private JMenu File,About;
	private JMenuItem sing,mult,exit,about,help;
	private JDialog jd;
	/*private Pc pc;
	private Jogador pl;*/
	/**Cria os itens dos menus colocando funções a eles.*/	
	public Menu(JFrame f, JDesktopPane desktop/*, Pc pc, Jogador pl*/)
	{
		super();
		this.f=f;
		this.desktop=desktop;
		/*this.pc=pc;
		this.pl=pl;*/
		criaItens();
		adicionaItens();
		escutaItens();

		f.show();
	}
	/**Cria os menus e menuitens.*/
	private void criaItens()
	{	
		File=new JMenu("File");
		About=new JMenu("About");	
			
		sing=new JMenuItem("Single Player");
		mult=new JMenuItem("Multi Player");
		exit=new JMenuItem("Exit");
		about=new JMenuItem("About");
		help=new JMenuItem("Help");			
	}
	/**Adiciona os menus e menuitens.*/
	private void adicionaItens()
	{
		f.setJMenuBar(this);
		
		add(File);
		add(About);	
        	
		File.add(sing);
		File.add(mult);
		File.addSeparator();
		File.add(exit);
		
		About.add(about);
		About.add(help);	
	}
	
	/**Atribui ações aos menus e menuitens.*/
	private void escutaItens()
	{		
			sing.addActionListener(new ActionListener() 
			{
    		public void actionPerformed(ActionEvent e) 
    		{
    			String nome=JOptionPane.showInputDialog(desktop,"ASDFGH","Qual seu nome?",JOptionPane.QUESTION_MESSAGE);
    			mostraOpcoes("single");
     		}
  	  });
		
			exit.addActionListener(new ActionListener() 
			{ 
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			});	
		
		
			help.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
									
					Object[] options = {"Sim, por favor",
                            "Não. Volta pro programa"
                            };
           int n = JOptionPane.showOptionDialog(f,
               "A ajuda não está feita. Você quer ver o que está pronto?",
               "Titulo = Ajuda!!",
               JOptionPane.YES_NO_CANCEL_OPTION,
               JOptionPane.QUESTION_MESSAGE,
               null,
               options,
               options[1]);
               
               if (n==0)
               		JOptionPane.showMessageDialog(f,"Ainda não fiz NADA","Titulo = ERRO FATAL", JOptionPane.ERROR_MESSAGE);

				}
			});
		
			about.addActionListener(new ActionListener() 
			{ 
				public void actionPerformed(ActionEvent e)
				{
					JOptionPane.showMessageDialog(f, "Elaborado por Emanuel Motta Grohs");
				}
			});	
	}
	public void mostraOpcoes(String tipo)
	{
		JLabel l=new JLabel("Estiva");
		JTextField pl2=new JTextField();
		JTextField pl3=new JTextField();
		JTextField pl4=new JTextField();
		JComboBox ia=new JComboBox();
		JComboBox nivel=new JComboBox();
		JButton ok=new JButton("OK");
		JButton cancel=new JButton("Cancel");
		
		ok.addActionListener(new ActionListener() 
		{ 
			public void actionPerformed(ActionEvent e)
			{
				jd.dispose();
				Janela j=new Janela(desktop/*,pc,pl*/);
			}
		});
		cancel.addActionListener(new ActionListener() 
		{ 
			public void actionPerformed(ActionEvent e)
			{
				jd.dispose();
			}
		});
		
		ia.addItem("Human");
		ia.addItem("Computer");
		nivel.addItem("Easy");
		nivel.addItem("Medium");
		nivel.addItem("Hard");
		
		if(tipo.equals("single"))
		{
			jd=new JDialog();
			jd.getContentPane().add(l);
    	jd.getContentPane().add(pl2);
    	jd.getContentPane().add(ia);
    	jd.getContentPane().add(nivel);
    	jd.getContentPane().add(pl3);
    	jd.getContentPane().add(ia);
    	jd.getContentPane().add(nivel);
    	jd.getContentPane().add(pl4);
    	jd.getContentPane().add(ia);
    	jd.getContentPane().add(nivel);
    	jd.getContentPane().setLayout(new GridLayout(3,3));
    	jd.getContentPane().add(ok);
    	jd.getContentPane().add(cancel);
    	jd.pack();
    	jd.setLocation(40,40);
    	jd.show();
    	jd.setVisible(true);
		}
		else
		{
			//jd=new JDialog(/*"Multiplayer Game"*/);
		}
	}
}