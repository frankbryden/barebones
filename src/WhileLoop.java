import java.util.HashMap;

public class WhileLoop {
	String[] code;
	HashMap<String, Integer> variables;
	Parser parser;
	Condition condition;
	
	public WhileLoop(String[] code, HashMap<String, Integer> variables) {
		this.code = code;
		this.variables = variables;
		this.parser = new Parser(variables);
		this.condition = new Condition(this.code[0]);
	}
	
	public void present() {
		System.out.println("I am a while Loop");
		while (this.condition.evaluate(variables)) {
			for (String line : this.code) {
				this.parser.parse(line);
			}
		}
	}
	
	public void display_state(){
		System.out.println("============ WHILE LOOP STATE =============");
		for (String var_name: variables.keySet()){
			System.out.printf("%s\t\t%d%n", var_name, variables.get(var_name));
		}
	}
}
