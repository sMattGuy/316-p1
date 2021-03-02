import java.io.*;

public class LexAnalyzer{
	String[] keywords = {"class","if","null","this"};
	public static void main(String[] args) throws FileNotFoundException, IOException{
		//set up file
		if(args.length != 1){
			System.out.println("Input must be 1 file\nUsage LexAnalyzer input");
			return;
		}
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		int characterNum = br.read();
		//reads the characters in until finished
		while(characterNum != -1){
			char characterRead = (char)characterNum;
			if(characterNum == 10){
				characterNum = 32;
				characterRead = (char)characterNum;
			}
			String iden = identify(characterNum);
			//starts parsing multi step tokens
			if(iden.equals("advanced")){
				//id
				//int
				//add
				//sub
				//dot
				//lt
				//gt
			}
			//output if single character
			else{
				System.out.println(characterRead + ":" + iden);
			}
			characterNum = br.read();
		}
		br.close();
	}
	static boolean keywordCheck(String input){
		for(int i=0;i<keywords.length();i++){
			if(keywords[i].equals(input)){
				return true;
			}
		}
		return false;
	}
	static String identify(int input){
		String type;
		switch(input){
			case 42: //*
			type = "mul";
			break;
			case 47: ///
			type = "div";
			break;
			case 40: //(
			type = "LParen";
			break;
			case 41: //)
			type = "RParen";
			break;
			case 123: //{
			type = "LBrace";
			break;
			case 125: //}
			type = "RBrace";
			break;
			case 61: //=
			type = "eq";
			break;
			case 124: //|
			type = "or";
			break;
			case 38: //&
			type = "and";
			break;
			case 33: //!
			type = "not";
			break;
			case 58: //:
			type = "colon";
			break;
			default:
			type = "advanced";
			break;
		}
		return type;
	}
}