import java.util.Arrays;
import java.util.HashMap;

public class WhileLoop {
	String[] code;
	HashMap<String, Integer> variables;
	Parser parser;
	Condition condition;
	WhileLoop whileLoop;
	
	public WhileLoop(String[] code, HashMap<String, Integer> variables) {
		this.code = code;
		this.variables = variables;
		this.parser = new Parser(variables);
		this.condition = new Condition(this.code[0]);
		this.whileLoop = null;
	}
	
	public void run() {
		System.out.println("I am a while Loop");
		while (this.condition.evaluate(variables)) {
			for (int i = 1; i < this.code.length; i++){// truncate first and last line of while -- only keep what must be executed
				if (this.code[i].startsWith("while")){
					int matching_end_index = this.parser.get_matching_end_index(this.code, i);
					this.whileLoop = new WhileLoop(Arrays.copyOfRange(this.code, i, matching_end_index), this.variables);
					this.whileLoop.run();
					i = matching_end_index;
				} else {
					this.parser.parse(this.code[i]);
					display_state();
				}
			}
		}
		System.out.println("exit while Loop");
	}
	
	public void display_state(){
		System.out.println("============ WHILE LOOP STATE =============");
		for (String var_name: variables.keySet()){
			System.out.printf("%s\t\t%d%n", var_name, variables.get(var_name));
		}
	}
}
