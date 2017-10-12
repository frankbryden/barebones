import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Condition {
	//In this class, condition to parse refers to the first line of the while loop
	// eg : while abc != 5
	String var_name;
	int value;
	Comparator comparator;
	public enum Comparator {EQUAL, NOT_EQUAL, GREATER, SMALLER};
	
	public Condition (String condition_to_parse) {
		condition_to_parse = condition_to_parse.substring("while".length()).trim();
		this.var_name = get_var_name(condition_to_parse);
		this.comparator = get_comparator(condition_to_parse);
		this.value = get_value(condition_to_parse);
	}
	
	public Boolean evaluate(HashMap<String, Integer> vars) {
		int var_value = vars.get(this.var_name);
		if (comparator == Comparator.EQUAL && this.value == var_value) {
			return true;
		}
		if (comparator == Comparator.NOT_EQUAL && this.value != var_value) {
			return true;
		}
		return false;
	}
	
	public String get_var_name(String condition) {
		Pattern r = Pattern.compile("^([a-zA-Z])+\\s");
		Matcher m = r.matcher(condition);
		System.out.println("Evaluating " + condition);
		return m.group(0);
	}
	
	public Comparator get_comparator(String condition) {
		String name_trimmed = condition.substring(this.var_name.length());
		if (name_trimmed.startsWith("not") && name_trimmed.startsWith("!=")) {
			return Comparator.NOT_EQUAL;
		} else if (name_trimmed.startsWith("=")) {
			return Comparator.EQUAL;
		} else if (name_trimmed.startsWith(">")){
			return Comparator.GREATER;
		} else if (name_trimmed.startsWith("<")){
			return Comparator.SMALLER;
		}
		System.err.println("Could not evaluate " + name_trimmed);
		return Comparator.NOT_EQUAL;
	}
	
	public int get_value(String condition) {
		Pattern r = Pattern.compile("([0-9]+)$");
		Matcher m = r.matcher(condition);
		return (int) Integer.valueOf(m.group());
	}
}
