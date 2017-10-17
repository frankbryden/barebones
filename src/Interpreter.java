import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

public class Interpreter {
	private static final String L_TERM = ";";
	private static final String END = "end";
	private Boolean verbose = false;
	String[] file;
	HashMap<String, Integer> register;
	public Interpreter(String[] file){
		this.file = file;
		this.register = new HashMap<String, Integer>();
	}
	
	public Interpreter(String[] file, Boolean verbose){
		this(file);
		this.verbose = verbose;
	}
	
	public void run(){
		for (int i = 0; i < this.file.length; i++) {
			String line = this.file[i].trim();
			if (line.startsWith("clear")){
				clear(get_var_name(line, "clear"));
				
				
			} else if (line.startsWith("incr")){
				incr(get_var_name(line, "incr"));
				
				
			} else if (line.startsWith("decr")){
				decr(get_var_name(line, "decr"));
				
				
			} else if (line.startsWith("while")){

				_while(i, get_var_name(line, "while"));
				i = get_matching_end_index(i);
			}
			display_state();
		}
	}
	
	public void clear(String name){
		info("Clear command with var " + name);
		register.put(name, 0);
	}
	
	public void incr(String name){
		info("incr command with var " + name);
		if (var_exists(name)){
			register.put(name, register.get(name) + 1);
		} else {
			System.err.printf("Variable %s has not been initialised.", name);
		}
	}
	
	
	public void decr(String name){
		System.out.println("decr command with var " + name);
		if (var_exists(name)){
			if (register.get(name) <= 1){
				System.err.println("Variables cannot be negative!! --> " + name);
			}
			register.put(name, register.get(name) - 1);
		}
	}
	
	public void _while(int start_index, String name){
		WhileLoop loop = new WhileLoop(Arrays.copyOfRange(this.file, start_index, get_matching_end_index(start_index)), this.register);
		loop.run();
	}
	
	public int get_matching_end_index(int start_index) {
		//Finds the next end keyword starting from start_index
		String current_line = "";
		int current_index = start_index;
		int while_count = 1; //Number of while loops found. One because function called with initial while. Avoids matching an end which is meant to be paired with other while
		/*
		 * Example
		 * while a not b
		 * 	while c not d
		 * 		do stuff
		 * 	end ---> without the count, the function would match the first end, which is not meant for the first while statement
		 * end 
		 * 
		 */
		while(while_count > 0) {
			current_index++;
			current_line = this.file[current_index];
			if (current_line.startsWith("while")){
				while_count++;
			} else if (current_line.startsWith(END)){
				while_count--;
			}
			if (current_index >= this.file.length) {
				System.err.println("Invalid while loop : end missing.");
				System.exit(-1);
				return -1;
			}
		}
		System.out.printf("Found end with index %d%n", current_index);
		return current_index;
	}
	
	
	
	public String get_var_name(String line, String command){//Takes a line as input and returns the name of the variable referred to on that line
		return line.substring(line.indexOf(command.charAt(command.length() - 1)) + 1, line.length()).trim();
	}
	
	public Boolean var_exists(String name){//checks to see if variable exists : true if it does, false if not
		if (register.keySet().contains(name)){
			return true;
		}
		return false;
	}
	
	public void info(String message){
		if (verbose){
			System.out.println(message);
		}
	}
	
	public void display_state(){
		System.out.println("============ NEW STATE =============");
		for (String var_name: register.keySet()){
			System.out.printf("%s\t\t%d%n", var_name, register.get(var_name));
		}
	}
}
