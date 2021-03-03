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
			identity.word = "";
			identity.iden = "";
			identity.identify();
			//starts parsing multi step tokens
			if(identity.iden.equals("advanced")){
				//start of complex tokens
				//id
				if((identity.characterNum >= 65 && identity.characterNum <= 90)||(identity.characterNum >= 97 && identity.characterNum <= 122)){
					identity.id(br);
					System.out.println(identity.word + " : " + identity.iden);
				}
				//int
				else if(identity.characterNum >= 48 && identity.characterNum <= 57){
					identity.integer(br);
					System.out.println(identity.word + " : " + identity.iden);
				}
				//add
				else if(identity.characterNum == 43){
					identity.add(br);
					System.out.println(identity.word + " : " + identity.iden);
				}
				//sub
				else if(identity.characterNum == 45){
					identity.sub(br);
					System.out.println(identity.word + " : " + identity.iden);
				}
				//dot
				else if(identity.characterNum == 46){
					identity.dot(br);
					System.out.println(identity.word + " : " + identity.iden);
				}
				//lt
				else if(identity.characterNum == 60){
					identity.lt(br);
					System.out.println(identity.word + " : " + identity.iden);
				}
				//gt
				else if(identity.characterNum == 62){
					identity.gt(br);
					System.out.println(identity.word + " : " + identity.iden);
				}
				//control chars
				else if(identity.characterNum >= 0 && identity.characterNum <= 32){
					identity.characterNum = br.read();
				}
				//invalid token
				else{
					System.out.println((char)identity.characterNum + "\t:\tLexical Error, Invalid Token");
					identity.characterNum = br.read();
				}
			}
			//output if single character
			else{
				System.out.println((char)identity.characterNum + "\t:\t" + identity.iden);
				identity.characterNum = br.read();
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
	//see if keyword
	public boolean keywordCheck(){
		String[] keywords = {"class","if","null","this"};
		for(int i=0;i<keywords.length;i++){
			if(keywords[i].equals(this.word)){
				return true;
			}
		}
		return false;
	}
	//simple tokens
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
	//advanced tokens
	//id
	public void id(BufferedReader br)throws IOException{
		this.iden = "id";
		while((this.characterNum >= 65 && this.characterNum <= 90)||(this.characterNum >= 97 && this.characterNum <= 122)||
			  (this.characterNum >= 48 && this.characterNum <= 57)){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.characterNum = br.read();
		}
		if(this.keywordCheck()){
			this.iden = "keyword_"+this.word;
		}
		return;
	}
	//int
	public void integer(BufferedReader br)throws IOException{
		this.iden = "int";
		//digit
		while(this.characterNum >= 48 && this.characterNum <= 57){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.characterNum = br.read();
		}
		//dot
		if(this.characterNum == 46){
			this.floatType(br);
		}
		//e
		else if(this.characterNum == 69 || this.characterNum == 101){
			this.e(br);
		}
		return;
	}
	//dot
	public void dot(BufferedReader br)throws IOException{
		this.iden = "dotOp";
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		//digit
		if(this.characterNum >= 48 && this.characterNum <= 57){
			this.floatType(br);
		}
		return;
	}
	//add
	public void add(BufferedReader br)throws IOException{
		this.iden = "add";
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		//dot
		if(this.characterNum == 46){
			this.decimalPoint(br);
		}
		//digit
		else if(this.characterNum >= 48 && this.characterNum <= 57){
			this.integer(br);
		}
		return;
	}
	//sub
	public void sub(BufferedReader br)throws IOException{
		this.iden = "sub";
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		//dot
		if(this.characterNum == 46){
			this.decimalPoint(br);
		}
		//digit
		else if(this.characterNum >= 48 && this.characterNum <= 57){
			this.integer(br);
		}
		return;
	}
	//gt
	public void gt(BufferedReader br)throws IOException{
		this.iden = "gt";
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		if(characterNum == 61){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.iden = "ge";
			this.characterNum = br.read();
		}
		return;
	}
	//lt
	public void lt(BufferedReader br)throws IOException{
		this.iden = "lt";
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		if(characterNum == 61){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.iden = "le";
			this.characterNum = br.read();
		}
		return;
	}
	//float
	private void floatType(BufferedReader br)throws IOException{
		this.iden = "float";
		//if incoming dot
		if(this.characterNum == 46){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.characterNum = br.read();
		}
		while(this.characterNum >= 48 && this.characterNum <= 57){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.characterNum = br.read();
		}
		if(this.characterNum == 101 || this.characterNum == 69){
			this.e(br);
		}
		return;
	}
	//floate
	private void floatEType(BufferedReader br)throws IOException{
		this.iden = "floatE";
		while(this.characterNum >= 48 && this.characterNum <= 57){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.characterNum = br.read();
		}
		return;
	}
	//e and e + -
	private void e(BufferedReader br)throws IOException{
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		//+ -
		if(this.characterNum == 43 || this.characterNum == 45){
			this.word = this.word + Character.toString((char)this.characterNum);
			this.characterNum = br.read();
			if(this.characterNum >= 48 && this.characterNum <= 57){
				this.floatEType(br);
			}
			else{
				this.iden = "Lexical Error, Invalid Token";
				this.characterNum = br.read();
			}
		}
		//digit
		else if(this.characterNum >= 48 && this.characterNum <= 57){
			this.floatEType(br);
		}
		else{
			this.iden = "Lexical Error, Invalid Token";
			this.characterNum = br.read();
		}
		return;
	} 
	//decimal point
	private void decimalPoint(BufferedReader br)throws IOException{
		this.word = this.word + Character.toString((char)this.characterNum);
		this.characterNum = br.read();
		if(this.characterNum >= 48 && this.characterNum <= 57){
			this.floatType(br);
		}
		else{
			this.word = this.word + Character.toString((char)this.characterNum);
			this.iden = "Lexical Error, Invalid Token";
			this.characterNum = br.read();
		}
		return;
	}
}