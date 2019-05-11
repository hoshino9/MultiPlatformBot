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

WS
    : [ \t\n\r] -> skip
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
    : ~[()[\]{},:=\\" ]+
    ;