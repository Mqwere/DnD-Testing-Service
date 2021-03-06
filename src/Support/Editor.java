package Support;

import java.util.ArrayList;

public class Editor {

	private static boolean DEFAULT_VALUE = true;
	
	private static String addbrackets(String input,String bracket) {
		return "<"+bracket+">"+input+"</"+bracket+">";
	}
	
	public static String forceHtmlize(String input, boolean center, int size){
		String output = new String();

		for(int i=0;i<input.length();i++) {
			boolean copy = true;
			if (input.charAt(i) == '\n') {
				output += "<br/>";
				copy = false;
			}
			if(copy) output += input.charAt(i);
		}
		if(center) output = addbrackets(output,"center");
		output = "<font size=\""+size+"\">"+output+"</font>";
		return addbrackets(output,"html");
	}
	
	public static String htmlize(String input) {
		return htmlize(input, DEFAULT_VALUE);
	}
	
	public static String htmlize(String input,int size) {
		return htmlize(input, DEFAULT_VALUE, size);
	}
	
	public static String htmlize(String input, boolean center) {
		input = dehtmlize(input);
		return forceHtmlize(input, center,3);
	}
	
	public static String htmlize(String input, boolean center, int size) {
		input = dehtmlize(input);
		return forceHtmlize(input, center,size);
	}
	
	public static String dehtmlize(String input) {
		ArrayList<Integer> start = new ArrayList<>();
		int offset = 0;
		String output = new String();
		for(int i=0;i<input.length();i++) {
			boolean copy = true;
			if		(input.charAt(i) == '<') {start.add(i);}
			else if	(input.charAt(i) == '>' && start.size()>0) {
				String cutoff = output.substring(start.get(start.size()-1)-offset);
				output = output.substring(0,start.get(start.size()-1)-offset);
				offset+=cutoff.length()+1;
				start.remove(start.size()-1);
				if(cutoff.equals("<br/") || cutoff.equals("<br")) {
					output += "\n";
					offset--;
				}
				copy = false;
			}
			
			if(copy) output+=input.charAt(i);
		}
		
		return output;
	}
	
	public static String leaveNumbers(String input) {
		String internal = new String("");
		for(int i=0; i<input.length();i++) {
			if(input.charAt(i)>='0'&&input.charAt(i)<='9') {
				internal += input.charAt(i);
			}
		}
		return internal;
	}
	
	public static Integer tryParse(String input) {
		Integer output;
		try {
			output = Integer.parseInt(input);
		}
		catch(Exception e){
			input  = leaveNumbers(input);
			output = Integer.parseInt(input);
		}
		return output;
	}

}
