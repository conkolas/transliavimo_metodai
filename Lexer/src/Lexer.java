import java.io.IOException;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

class Lexer {
    private State currentState;
    private String lexem;
    private boolean reserved;
    
    public Lexer(String sampleProg) throws IOException {

        // Setting initial states
        Reader read = null;
        currentState = State.START;
        lexem = "";
        reserved = false;
        
        try{
            // Opens a file
            read = new Reader(sampleProg);
            int c = read.getCharNumber();

            // Reads file until the end
            while(c != -1){
                // Casts to char and analyses
                analyse((char)c);
                c = read.getCharNumber();
            }

            // Prints an error if file ends with unclosed comment or string
            if((currentState==State.BLCK_COM_IDEN_B) || (currentState==State.STRING)){
                currentState = State.ERROR_UNKNOWN_SYM;
                reset();
                printError(currentState);
            }
        } catch (IOException exp){
            exp.printStackTrace();
        } finally{
            read.closeReader();
        }
    }
   
    // Iterates through states until matches and gets printed out
    public void analyse(char c){
        switch(currentState){
            case START:
                if(isDigit(c)){
                    currentState = State.INT;
                    lexem+=c;
                    break;
                }
                else if(isLetter(c)){
                    currentState = State.ID;
                    lexem+=c;
                    break;
                }
                else if(c=='"'){
                    currentState = State.STRING;
                    break;
                }
                else if(c=='/'){
                    currentState = State.COM_IDEN;
                    break;
                }
                else if(c=='='){
                    currentState = State.OP_ASSIGN;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c=='&'){
                    currentState = State.OP_AND;
                    lexem+=c;
                    break;
                }
                else if(c=='|'){
                    currentState = State.OP_OR;
                    lexem+=c;
                    break;
                }
                else if(c=='>'){
                    currentState = State.OP_COM_GREAT;
                    lexem+=c;
                    break;
                }
                else if(c=='<'){
                    currentState = State.OP_COM_LESS;
                    lexem+=c;
                    break;
                }
                else if(c=='?'){
                    currentState = State.OP_COM;
                    lexem+=c;
                    break;
                }
                else if(c=='!'){
                    currentState = State.OP_NOT;
                    lexem+=c;
                    break;
                }
                else if(c=='-'){
                    currentState = State.OP_NADD;
                    lexem+=c;
                    break;
                }
                else if(c=='+'){
                    currentState = State.OP_ADD;
                    lexem+=c;
                    break;
                }         
                else if(c=='*'){
                    currentState = State.OP_MUL;
                    lexem+=c;
                    break;
                }
                else if(c=='$'){
                    currentState = State.FUNC_NAME;
                    lexem+=c;
                    break;
                }
                else if(c=='('){
                    currentState = State.DELIM_PAR_L;
                    lexem+=c;
                    printAndReset();
                    break;
                }                
                else if(c==')'){
                    currentState = State.DELIM_PAR_R;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c=='['){
                    currentState = State.DELIM_BR_R;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c==']'){
                    currentState = State.DELIM_BR_L;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c=='{'){
                    currentState = State.DELIM_CUR_L;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c=='}'){
                    currentState = State.DELIM_CUR_R;
                    lexem+=c;
                    printAndReset();
                    break;
                } 
                else if(c==','){
                    currentState = State.DELIM_COMMA;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c==';'){
                    currentState = State.DELIM_SEMI_COL;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c=='.'){
                    currentState = State.DELIM_DOT;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else if(c==' '){
                    reset();
                    break;
                }
                else if(c=='\r'){
                    reset();
                    break;
                }                
                else if(c=='\n'){
                    reset();
                    break;
                }
                else if(c=='\t'){
                    reset();
                    break;
                }
                else{
                    currentState = State.ERROR_UNKNOWN_SYM;
                    lexem+=c;
                    printAndReset();
                    break;
                }
                
            case INT:
                if(isDigit(c)){
                    currentState = State.INT;
                    lexem+=c;
                    break;
                }
                else if(c == ','){
                    currentState = State.FLOAT;
                    lexem+=c;
                    break;
                }
                else{
                    printAndReset();
                    analyse(c);
                    break;
                }
            
            case FLOAT:
                if(isDigit(c)){
                    currentState = State.FLOAT;
                    lexem+=c;
                    break;
                }
                else {
                    printAndReset();
                    analyse(c);
                    break;
                }
            
            case STRING:
                if(c!='"'){
                    currentState = State.STRING;
                    lexem+=c;
                    break;
                }
                else if (c=='"'){
                    printAndReset();
                    break;
                }
            case ID:
                if(isLetter(c) || isDigit(c)){
                    currentState = State.ID;
                    lexem+=c;
                    break;
                }
                else{
                    for(State loopState: State.values()){
                        if(loopState.getName().equals(lexem)){
                            print(loopState,lexem);
                            reset();
                            //analyse(c);
                            reserved = true;
                            break;
                        }
                    }
                    if(reserved){
                        reserved=false;
                        analyse(c);
                        break;
                    }
                    printAndReset();
                    analyse(c);
                    break;
                }
            case COM_IDEN:
                if(c=='/'){
                    currentState = State.LINE_COM_IDEN;
                    break;
                }
                else if(c=='*'){
                    currentState = State.BLCK_COM_IDEN_B;
                    break;
                }
                else if(c=='='){
                    currentState = State.OP_ASSIGN_DIV;
                    lexem+='/';
                    lexem+=c;
                    printAndReset();
                    break;
                }
                else{
                    lexem+='/';
                    currentState = State.OP_DIV;
                    printAndReset();
                    break;
                }
            case FUNC_NAME:
                if (c != '(') {
                    currentState = State.FUNC_NAME;
                    lexem += c;
                    break;
                } else {
                    printAndReset();
                    analyse(c);
                    break;
                }
            case LINE_COM_IDEN:
                if(c=='\n' | c=='\r'){
                    printAndReset();
                    analyse(c);
                    break;
                } else{
                    currentState = State.LINE_COM_IDEN;
                    lexem+=c;
                    break;
                }
            case BLCK_COM_IDEN_B:
                if(c=='*'){
                    currentState = State.BLCK_COM_IDEN_E;
                    break;
                } else {
                    currentState = State.BLCK_COM_IDEN_B;
                    lexem+=c;
                    break;
                }
            case BLCK_COM_IDEN_E:
                if(c=='/'){
                    printAndReset();
                    break;
                }
                else{
                    currentState = State.BLCK_COM_IDEN_B;
                    lexem+='-';
                    analyse(c);
                    break;
                }
            case OP_AND:
                if(c=='&'){
                    lexem+=c;
                    printAndReset();
                    break;
                } else{
                    currentState = State.ERROR_UNKNOWN_SYM;
                    break;
                }
            case OP_OR:
                if(c=='|'){
                    lexem+=c;
                    printAndReset();
                    break;
                } else{
                    currentState = State.ERROR_UNKNOWN_SYM;
                    break;
                }
            case OP_COM_GREAT:
                if(c=='='){
                    lexem+=c;
                    currentState = State.OP_COM_GREAT_EQ;
                    printAndReset();
                    break;
                } else {
                    printAndReset();
                    break;
                }
            case OP_COM_LESS:
                if(c=='='){
                    lexem+=c;
                    currentState = State.OP_COM_LESS_EQ;
                    printAndReset();
                    break;
                } else {
                    printAndReset();
                    break;
                }
            case OP_COM:
                if(c=='='){
                    lexem+=c;
                    printAndReset();
                    break;
                } else{
                    currentState = State.ERROR_UNKNOWN_SYM;
                    printAndReset();
                    break;
                }
            case OP_NOT:
                if(c=='='){
                    lexem+=c;
                    printAndReset();
                    break;
                } else{
                    currentState = State.ERROR_UNKNOWN_SYM;
                    printAndReset();
                    break;
                }
            case OP_NADD:
                if(c=='=') {
                    currentState = State.OP_ASSIGN_NADD;
                    lexem += c;
                    printAndReset();
                    break;
                } else if(c=='-'){
                    currentState = State.OP_NADD_ONE;
                    lexem+=c;
                    break;
                } else{
                    printAndReset();
                    break;
                }
            case OP_ADD:
                if(c=='=') {
                    currentState = State.OP_ASSIGN_ADD;
                    lexem += c;
                    printAndReset();
                    break;
                } else if(c=='+'){
                    currentState = State.OP_ADD_ONE;
                    lexem+=c;
                    printAndReset();
                    break;
                } else{
                    printAndReset();
                    break;
                }
            case OP_MUL:
                if(c=='='){
                    currentState = State.OP_ASSIGN_MUL;
                    lexem+=c;
                    printAndReset();
                    break;
                } else{
                    printAndReset();
                    break;
                }
            case ERROR_UNKNOWN_SYM:
                printAndReset();
                analyse(c);
                break;
        }
    }
    
    private void printAndReset(){
        print(this.currentState,this.lexem);
        reset();
    }

    // Resets analyzer states
    private void reset(){
        this.currentState = State.START;
        this.lexem = "";
        this.reserved = false;
    }
    
    // Prints out lexem type with its value
    private void print(State state, String lexem){
        System.out.println(state.name()+"\t"+lexem);
    }

    private void printError(State state){
        System.out.println("Error! State ID: "+state.getId());
    }
    
}
