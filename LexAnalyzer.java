import java.io.*;

public class LexAnalyzer{
	public static void main(String[] args) throws FileNotFoundException, IOException{
		//set up file
		if(args.length != 1){
			System.out.println("Input must be 1 file\nUsage LexAnalyzer input");
			return;
		}
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		token identity = new token();
		identity.characterNum = br.read();
		//reads the characters in until finished
		while(identity.characterNum != -1){
			boolean valid = true;
			char characterRead = (char)identity.characterNum;
			if(identity.characterNum == 10){
				identity.characterNum = br.read();
			}
			else{
				identity.iden = identify();
				//starts parsing multi step tokens
				if(identity.iden.equals("advanced")){
					identity.word = Character.toString((char)identity.characterNum);
					characterNum = br.read();
					//start of complex tokens
					//id
					//int
					//add
					//sub
					//dot
					
					
					//print out results of advanced parse
					if(valid){
						System.out.println(word + " : " + iden);
					}
				}
				//output if single character
				else{
					System.out.println(characterRead + ":" + iden);
					characterNum = br.read();
				}
			}
		}
		br.close();
	} 
}
class token{
	public String word;
	public int characterNum;
	public String iden;
	//constructor
	public token(){
		this.word = "";
		this.characterNum = 0;
		this.iden = "";
	}
	//methods
	public boolean keywordCheck(String input){
		String[] keywords = {"class","if","null","this"};
		for(int i=0;i<keywords.length;i++){
			if(keywords[i].equals(input)){
				return true;
			}
		}
		return false;
	}
	//simple identifiers
	public void identify(){
		int input = this.characterNum;
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
		this.iden = type;
		return;
	}
	//int
	public void integer(BufferedReader br){
		while(characterNum >= 48 || characterNum <= 57){
			word = word + Character.toString((char)characterNum);
			characterNum = br.read();
		}
		return;
	}
	//id
	public void id(BufferedReader br){
		while((this.characterNum >= 65 || this.characterNum <= 90)||
				(this.characterNum >= 97 || this.characterNum <= 122)||
				(this.characterNum >= 48 || this.characterNum <= 57)){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.characterNum = br.read();
		}
		return;
	}
	//e
	public void e(BufferedReader br){
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		return;
	} 
	//dot
	public void dot(BufferedReader br){
		
	}
	//gt
	public void lt(BufferedReader br){
		this.iden = "gt";
		this.characterNum = br.read();
		if(characterNum == 61){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.iden = "ge";
		}
		return;
	}
	//lt
	public void lt(BufferedReader br){
		this.iden = "lt";
		this.characterNum = br.read();
		if(characterNum == 61){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.iden = "le";
		}
		return;
	}
	//float
	public void floatType(BufferedReader br){
		
	}
	//floate
}