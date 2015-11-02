public enum State {
    START(0),
    COM_IDEN(0),
    INT(2),
    FLOAT(3),
    ID(4),
    STRING(5),
    TYPE_INT(6,"int"),
    TYPE_FLOAT(7,"float"),
    TYPE_STRING(8,"string"),
    TYPE_BOOL(9,"bool"),
    TYPE_VOID(10,"void"),
    LINE_COM_IDEN(11),
    BLCK_COM_IDEN_B(12),
    BLCK_COM_IDEN_E(13),
    OP_ASSIGN(14),
    OP_AND(15),
    OP_OR(16),
    OP_COM_GREAT(17),
    OP_COM_GREAT_EQ(18),
    OP_COM_LESS(19),
    OP_COM_LESS_EQ(20),
    OP_COM(21),
    OP_NOT(22),
    OP_NADD(23),
    OP_NADD_ONE(24),
    OP_ASSIGN_NADD(25),
    OP_ADD(26),
    OP_ADD_ONE(27),
    OP_ASSIGN_ADD(28),
    OP_MUL(29),
    OP_ASSIGN_MUL(30),
    OP_DIV(31),
    OP_ASSIGN_DIV(31),
    IDEN_FALSE(32,"false"),
    IDEN_TRUE(33,"true"),
    FUNC_NAME(34),
    FUNC_ELSE(35,"else"),
    FUNC_IF(36,"if"),
    FUNC_FOR(37,"for"),
    FUNC_IN(38,"scan"),
    FUNC_OUT(39,"print"),
    FUNC_WHILE(40,"while"),
    DELIM_PAR_L(41),
    DELIM_PAR_R(42),
    DELIM_BR_L(43),
    DELIM_BR_R(44),
    DELIM_CUR_L(45),
    DELIM_CUR_R(46),
    DELIM_COMMA(47),
    DELIM_SEMI_COL(48),
    DELIM_DOT(49),
    ERROR_UNKNOWN_SYM(50);
    
    private final int id;
    private final String name;

    // Setting state without reserved words
    State(int _id){
        this.id = _id;
        this.name ="";
    }

    // Setting state with reserved word
    State(int _id, String _name){
        this.id = _id;
        this.name = _name;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
}
