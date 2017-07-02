import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Converter {

	private String filePath; 
	private String[] data; 
	private String[] textData; 
	private String[] addrData;
	private String[] subData;;
	private String[] hexData;
	private String[] outputData;
	private String decimal;
	private int length;
	private int[] line;
	
	public Converter(String path)
	{
		
		filePath = path;
		try {
			openFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				writeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void writeFile () throws IOException{
		BufferedWriter writer = null;
		try
		{
		    writer = new BufferedWriter( new FileWriter("convert.mcd"));
		    
		    for (int i = 0 ; i <length; i++)
			{
		    	writer.write( outputData[i])	;
		    	writer.newLine();
			};

		}
		catch ( IOException e)
		{
		}
		finally
		{
		    try
		    {
		        if ( writer != null)
		        writer.close( );
		    }
		    catch ( IOException e)
		    {
		    }
		}
	}
	public void openFile () throws IOException{
		FileReader fr = new FileReader(filePath);
		BufferedReader textReader = new BufferedReader(fr);
		
		length = countLines (filePath);
		textData= new String[length];
		addrData = new String[length];
		hexData = new String[length];
		outputData = new String[length];
		
		System.out.println("Total Lines : "+ length);
		for (int i = 0 ; i < length; i++)
		{
			textData[i]= textReader.readLine().toLowerCase();
			System.out.println(textData[i]);
		}


		ArrayList<String> conv = new ArrayList<String>();
		ArrayList<String> indexConv = new ArrayList<String>();
		int index;
		//check and store
		for(int i = 0; i < length; i++) {
			if(textData[i].indexOf(":") == -1) {
				
				addrData[i]=textData[i];
			}
			else
			{	
				conv.add(textData[i].substring(0, (textData[i].indexOf(":"))));
				index=i*4;
				indexConv.add(Integer.toString(index));
				addrData[i]=textData[i].substring(textData[i].indexOf(":")+2);
			}
		}
	//	System.out.println("CHECK");
		
		//replace
		System.out.println("\nADDRESS CODE:");
		for(int i = 0; i < length; i++) {
			for(int j=0; j<conv.size(); j++)
			{
				addrData[i]=addrData[i].replace(conv.get(j), indexConv.get(j));
			}
			System.out.println(addrData[i]);
		}
		System.out.println();
		
		System.out.println("MACHINE CODE (hex):");
		for(int i = 0; i < length; i++) {
		//	addrData[i]= convertToAddrCode(textData[i]);
			hexData[i] = Convert(addrData[i]);
			System.out.println(hexData[i]);
			
		}
		System.out.println();
		
		System.out.println("MACHINE CODE (dec):");
		for(int i = 0; i < length; i++) {
			outputData[i]= convertHSD(hexData[i]);
			System.out.println(outputData[i]);
			}
		System.out.println();
		
		textReader.close();
		//return textData;
		
	}
	
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}

	public String Convert(String data)
	{
		/* FUNC ADDZERO BUAT NAMBAHIN 0 SESUAI LIMIT DARI BYTENYA
		 * KALAU CONVERT SHS BUAT CONVERT DARI STRING KE HEXASTRING JADI MISALNYA 15 JADINYA F TAPI TTP STRING
		 * KALAU MAU COBA JALANIN RUN PROGRAMNYA -> BROWSE PILIH FOLDER YANG ADA CODE DATANYA		 
		 * */
		
		//if(data.)
		
		int[] saveLine;
		
		
		String newdata = data.replaceAll(".*: ", "");
		//System.out.println(newdata);
		subData =newdata.split("\\s*(:|,|\\s)\\s*");

		for (int i=1 ; i<subData.length;i++)
		{
			subData[i] = subData[i].replace("r", "");
		}
		
		//System.out.println(subData[0]);
		//System.out.println(subData[1]);
		//System.out.println(subData[2]);
		//System.out.println(subData[3]);
			
			if (subData[0].equals("add"))
			{
				decimal = "0x00" + convertSHS(subData[1])+ convertSHS(subData[2])+ convertSHS(subData[3])+"000" ;
				
			}
			
			else if (subData[0].equals("addi"))
			{
				decimal = "0x01" + convertSHS(subData[1])+ convertSHS(subData[2])+ addZero(convertSHS((subData[3])),4);
				
			}
		
			else	if (subData[0].equals("movr"))
			{
				decimal = "0x02" + convertSHS(subData[1])+ convertSHS(subData[2])+"0000" ;
			}
			
			else	if (subData[0].equals("movi"))
			{
				decimal = "0x03" + convertSHS(subData[1])+addZero(convertSHS((subData[2])),5) ;
				
			}

			else	if (subData[0].equals("mov"))
			{
				subData[1] = subData[1].replace("[","");
				subData[1] = subData[1].replace("]","");		
				String[] sub = subData[1].split("\\+");
				decimal = "0x04" + convertSHS(sub[0])+ convertSHS(subData[2])+addZero(convertSHS((sub[1])),4) ;
				
			}

			else if (subData[0].equals("movm"))
				{
				subData[2] = subData[2].replace("[","");
				subData[2] = subData[2].replace("]","");			
				String[] sub = subData[2].split("\\+");
					decimal = "0x05"+ convertSHS(subData[1])+ convertSHS(sub[0])+addZero(convertSHS((sub[1])),4);
					
				}
			
			else	if (subData[0].equals("movb"))
			{
				subData[1] = subData[1].replace("[","");
				subData[1] = subData[1].replace("]","");		
				String[] sub = subData[1].split("\\+");
				decimal = "0x06" + convertSHS(sub[0])+ convertSHS(subData[2])+addZero(convertSHS((sub[1])),4) ;
				
			}
			else if (subData[0].equals("movmb"))
			{
				subData[2] = subData[2].replace("[","");
				subData[2] = subData[2].replace("]","");			
				String[] sub = subData[2].split("\\+");
				decimal = "0x07"+ convertSHS(subData[1])+ convertSHS(sub[0])+addZero(convertSHS((sub[1])),4);
				//System.out.println(decimal);
			}
			else	if (subData[0].equals("movh"))
			{
				subData[1] = subData[1].replace("[","");
				subData[1] = subData[1].replace("]","");		
				String[] sub = subData[1].split("\\+");
				decimal = "0x08" + convertSHS(sub[0])+ convertSHS(subData[2])+addZero(convertSHS((sub[1])),4) ;
				
			}
			else	if (subData[0].equals("movl"))
			{
				subData[1] = subData[1].replace("[","");
				subData[1] = subData[1].replace("]","");		
				String[] sub = subData[1].split("\\+");
				decimal = "0x0A" + convertSHS(sub[0])+ convertSHS(subData[2])+addZero(convertSHS((sub[1])),4) ;
				
			}
			
			else	if (subData[0].equals("jmp"))
			{				
				decimal = "0x15" +addZero(convertSHS((subData[1])),6) ;				
			}
			else if (subData[0].equals("jmpr"))
			{
				decimal = "0x16"+convertSHS(subData[1])+addZero("",5);
			}
			else	if (subData[0].equals("je"))
			{
				decimal = "0x17"+ convertSHS(subData[1])+ convertSHS(subData[2])+ addZero(convertSHS((subData[3])),4);
			}
			else if (subData[0].equals("jne"))
			{
				decimal = "0x18"+ convertSHS(subData[1])+ convertSHS(subData[2])+ addZero(convertSHS((subData[3])),4);
			}
			else if (subData[0].equals("jlt"))
			{
				decimal = "0x19"+ convertSHS(subData[1])+ convertSHS(subData[2])+ addZero(convertSHS((subData[3])),4);
			}
			else if (subData[0].equals("jgt"))
			{
				decimal = "0x1A"+ convertSHS(subData[1])+ convertSHS(subData[2])+ addZero(convertSHS((subData[3])),4);
			}
			if (subData[0].equals("sub"))
			{
				decimal = "0x1B" + convertSHS(subData[1])+ convertSHS(subData[2])+ convertSHS(subData[3])+"000" ;
				
			}
			else if (subData[0].equals("subi"))
			{
				decimal = "0x1C" + convertSHS(subData[1])+ convertSHS(subData[2])+ addZero(convertSHS((subData[3])),4);
				
			}
			else	if (subData[0].equals("movmhw"))
			{
				subData[1] = subData[1].replace("[","");
				subData[1] = subData[1].replace("]","");		
				String[] sub = subData[1].split("\\+");
				decimal = "0x1D" + convertSHS(sub[0])+ convertSHS(subData[2])+addZero(convertSHS((sub[1])),4) ;
			}
			
			else if (subData[0].equals("halt"))
			{
				decimal = "0x7F000000";
			}
		
		return decimal;				
	}
	
	public String convertHSD(String data)
	{

		data = data.replace("0x", "");
		int x = Integer.parseInt(data,16);
		return Integer.toString(x);
	}
	public String convertSHS(String data)
	{
		int x = Integer.parseInt(data);
		String result = Integer.toHexString(x).toUpperCase();
		return result;
	}
	public String addZero(String data,int limit)
	{
		StringBuilder _sb = new StringBuilder(data);
		while (_sb.length()<limit) _sb.insert(0, "0");
		String result = _sb.toString();
		return result;
	}
}
