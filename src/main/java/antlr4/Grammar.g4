grammar Grammar;

main: mainRule matcher* ;

mainRule : 'main>' ID ';' ;

matcher : rule #matchRule
        | terminal #matchTerminal ;

rule : ID args ':' ID '=' namedRule ('|' namedRule)* ';' ;

args : '(' ')'
           | '(' ID ID (',' ID ID)* ')' ;

namedRule : rightSide MATCH_NAME BLOCK ;

rightSide : callArgs * ;

callArgs : ID '(' REGEX* ')' | ID ;

terminal : 'token>++' ID ':' REGEX ';' ;


REGEX : '$'[[\]"a-zA-Z.:, 0-9()*/+;-]+'$' ;
BLOCK : '{' .+? '}' ;

ID: [a-zA-Z][a-zA-Z0-9]* ;
MATCH_NAME: '#'[a-zA-Z]+ ;


NUMBER : [0-9]+('.'[0-9]+)? ;
STRING : '"' .*? '"' ;
WHITESPACE : (' '|'\n'|'\t'|'\r') -> skip;