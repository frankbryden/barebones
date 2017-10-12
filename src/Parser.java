import java.util.HashMap;
public class Parser {
	HashMap<String, Integer> register;
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
			
			
		} else if (line.startsWith("while")){
			//TODO fix _while
			//_while(0, get_var_name(line, "while")); 
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
			System.out.println(message);
		}
	}
	
	public Boolean var_exists(String name){//checks to see if variable exists : true if it does, false if not
		if (register.keySet().contains(name)){
			return true;
		}
		return false;
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
	
	public String get_var_name(String line, String command){//Takes a line as input and returns the name of the variable referred to on that line
		return line.substring(line.indexOf(command.charAt(command.length() - 1)) + 1, line.length()).trim();
	}
	
}
