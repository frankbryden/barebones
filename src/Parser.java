import java.util.HashMap;
public class Parser {
	HashMap<String, Integer> register;
	private static final String END = "end";
	public Parser(HashMap<String, Integer> register) {
		this.register = register;
	}
	
	public void parse(String line) {
		if (line.startsWith("clear")){
			clear(get_var_name(line, "clear"));
			
			
		} else if (line.startsWith("incr")){
			incr(get_var_name(line, "incr"));
			
			
		} else if (line.startsWith("decr")){
			decr(get_var_name(line, "decr"));
				
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
	
	public void info(String message){
		if (true){
			//System.out.println(message);
		}
	}
	
	public Boolean var_exists(String name){//checks to see if variable exists : true if it does, false if not
		if (register.keySet().contains(name)){
			return true;
		}
		return false;
	}
	
	
	public void decr(String name){
		System.out.println("parser decr command with var " + name);
		if (var_exists(name)){
			if (register.get(name) < 1){
				System.err.println("Variables cannot be negative!! --> " + name);
				System.exit(-1);
			}
			register.put(name, register.get(name) - 1);
		}
	}
	
	public int get_matching_end_index(String[] code, int start_index) {
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
			current_line = code[current_index];
			if (current_line.startsWith("while")){
				while_count++;
			} else if (current_line.startsWith(END)){
				while_count--;
			}
			if (current_index >= code.length) {
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
	
}
