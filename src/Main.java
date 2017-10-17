import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String file = "";
		try{
			FileReader fr = new FileReader("prog2.bb");
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			while((currentLine = br.readLine()) != null){
				file += currentLine.trim();
			}
		}catch(FileNotFoundException e){
			System.err.println(e.getMessage());
		}catch(IOException e){
			System.err.println(e.getMessage());
		}
		if (file != ""){
			Interpreter i = new Interpreter(file.replace("\n", "").replace("\t",  "").split(";"));
			i.run();
		}
	}

}
