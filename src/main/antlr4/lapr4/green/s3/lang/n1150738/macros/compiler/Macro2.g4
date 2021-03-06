grammar Macro2;
@header {
    package lapr4.green.s3.lang.n1150738.macros.compiler;
}

macro
    : NEWLINE* header (SEMI (~NEWLINE)* NEWLINE+| expression)* (SEMI (~NEWLINE)* NEWLINE+ | EQ? comparison) NEWLINE* EOF
    ;

header
    :  'macro' IDENTIFIER LPAR parameters RPAR NEWLINE+
    ;

parameters
    :
    | IDENTIFIER
    | parameters COMMA IDENTIFIER
    ;

parameters_val_list
    :
    | literal
    | parameters_val_list COMMA literal
    ;


expression
	: EQ? comparison NEWLINE+
	;

block
	: L_CURLY_BRACKET comparison ( SEMI comparison )* R_CURLY_BRACKET
	;

comparison
	: concatenation
		( ( EQ | NEQ | GT | LT | LTEQ | GTEQ ) concatenation )?
	;

concatenation
        : ( MINUS )? atom
        | concatenation PERCENT
        | <assoc=right> concatenation POWER concatenation
        | concatenation ( MULTI | DIV ) concatenation
        | concatenation ( PLUS | MINUS ) concatenation
        | concatenation AMP concatenation
        ;

script
    : ( SPECIAL_CHAR | 'macro_start' | 'macro_end' | (( '<![SHELL[' | '<[SHELL[' ) script ']]>') | ~( '<![SHELL[' | '<[SHELL['  | ']]>'))*
    ;

shellscript
    :   ( '<![SHELL[' | '<[SHELL[' ) script ']]>'
    ;

atom
    : macro_invoked
	|	function_call
	|   PARAMETER_REFERENCE
	|	reference
	|	literal
	|	LPAR comparison RPAR
	|	block
	|	assignment
	|   shellscript
	|   LOCAL_VARIABLE
	;

macro_invoked : LPAR_SQ IDENTIFIER LPAR parameters_val_list RPAR RPAR_SQ;

assignment
	:  LPAR (reference | LOCAL_VARIABLE) ASSIGN comparison RPAR
	;

function_call
	:	IDENTIFIER LPAR
		( comparison ( SEMI comparison )* )?
		RPAR
	;

reference
	:	(  CELL_REF | IDENTIFIER  )
		( ( COLON ) (  CELL_REF | IDENTIFIER   )    )?
	;

literal
	:	NUMBER
	|	STRING
	;


fragment LETTER: ('a'..'z'|'A'..'Z') ;

IDENTIFIER
        :IDENTIFIER_FRAG
        ;

fragment IDENTIFIER_FRAG
        : LETTER (LETTER|DIGIT)*
        ;

//FUNCTION :
//	  ( LETTER )+
//	;


CELL_REF
	:
		( ABS )? IDENTIFIER_FRAG
		( ABS )? ( DIGIT )+
	|   ( ABS )? IDENTIFIER_FRAG
	;

LOCAL_VARIABLE
    : UNDERSCORE LETTER (DIGIT|LETTER)*
    ;

PARAMETER_REFERENCE
    :   '$''{'IDENTIFIER_FRAG'}'
    ;

/* String literals, i.e. anything inside the delimiters */

STRING  : QUOT ('\\"' | ~'"')* QUOT
        ;

QUOT: '"'
	;

/* Numeric literals */
NUMBER: ( DIGIT )+ ( COMMA ( DIGIT )+ )? ;

fragment DIGIT : '0'..'9' ;

/* Comparison operators */
EQ		: '=' ;
NEQ		: '<>' ;
LTEQ	: '<=' ;
GTEQ	: '>=' ;
GT		: '>' ;
LT		: '<' ;

/* Text operators */
AMP		: '&' ;

/* Arithmetic operators */
PLUS	: '+' ;
MINUS	: '-' ;
MULTI	: '*' ;
DIV		: '/' ;
POWER	: '^' ;
PERCENT : '%' ;

/* Reference operators */
fragment ABS : '$' ;
fragment EXCL:  '!'  ;
COLON	: ':' ;

/* Miscellaneous operators */
COMMA	: ',' ;
SEMI	: ';' ;
LPAR	: '(' ;
RPAR	: ')' ;
L_CURLY_BRACKET	: '{' ;
R_CURLY_BRACKET	: '}' ;
LPAR_SQ : '[' ;
RPAR_SQ : ']' ;

/* assignment operator */
ASSIGN  : ':=' ;

fragment PARAGRAPH : ('\r'? '\n' | '\r');
fragment UNDERSCORE : '_';

NEWLINE : PARAGRAPH;

/* Items to be ignored */
WHITESPACE   : (' ' | '\t')         -> skip;

SPECIAL_CHAR : [.!?_] ;
