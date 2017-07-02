import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


	public class mainForm extends JFrame{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JFileChooser chooser;
		private JButton browse;
		private JButton converting;
		private JLabel foldername;
		String name;
		String File;
		private Converter convert;
		
		public mainForm() {
		        initComponents();
		    }
		
		public void initComponents()
		{
			chooser = new JFileChooser();
			browse = new JButton();
			converting = new JButton();
			foldername = new JLabel();
			JPanel pane = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			  setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		        setBackground(new java.awt.Color(145, 53, 53));
		        setSize(200, 200);
		        setLocation(500, 200);
		        setResizable(false);
		        setTitle("Converter");
		        
		        browse.setText("Browse");
		        browse.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                getFileMenuActionPerformed(evt);
		            }
		        });
		        
		      
		        
		        foldername.setText("No File");
		        
		        
		        add(pane);		     
		        c.fill = GridBagConstraints.HORIZONTAL;
		        c.ipady = 20;      //make this component tall
		        c.weightx = 0.0;
		        c.gridwidth = 1;
		        c.gridx = 0;
		        c.gridy = 0;
		        pane.add(browse, c);
		        
		        c.fill = GridBagConstraints.VERTICAL;
		        c.ipady = 20;      //make this component tall
		        c.weightx = 20.0;
		        c.gridwidth = 1;
		        c.gridx = 0;
		        c.gridy = 10;
		        pane.add(foldername, c);
		        

		}
		


		protected void getFileMenuActionPerformed(ActionEvent evt) {
			// TODO Auto-generated method stub
		
			int intval = chooser.showOpenDialog(this);
	        if(intval == chooser.APPROVE_OPTION){
	        	File = chooser.getSelectedFile().toString();
        		name = chooser.getSelectedFile().getName();
	        }
	        		
	        else
	        	 	File = null;
	        if (File != null)
	        {
	        	foldername.setText("Convert : "+name);
	        	convert = new Converter(File);
	        	//convert.Convert("movl [r3+6]],r2");
	        }	        
		}

		public static void main(String args[])
		{
			java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new mainForm().setVisible(true);
	            }
	        });
		}
	}



