grammar Message;

at
    : '[At:' Integer ']'
    ;

img
    : '[img=' String ']'
    ;

array
    : '[' value (',' value)* ']'
    | '[' ']'
    ;

call
    : Identity '(' value (',' value)* ')'
    | Identity '(' ')'
    ;

value
    : Integer
    | String
    | Identity
    | array
    | at
    | img
    | call
    ;

fragment
Int
    : [0-9]+
    ;

Integer
    : '-'? Int
    ;

fragment
Char
    : ~ [\\"\u0000-\u001F]
    ;

fragment
Escape
    : '\\' [\\nr]
    ;

String
    : '"' (Escape | Char)* '"'
    ;

Identity
    : [a-zA-Z\u4E00-\u9FA5] [0-9a-zA-Z\u4E00-\u9FA5]*
    ;

WS
    : [ \t\n\r] -> skip
    ;
