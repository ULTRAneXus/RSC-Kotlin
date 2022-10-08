# RageScript Documentation

## Structure
- data segment: define new variables, only one variable per line is allowed, ends with AA 
- code segment: code, only one function/conditional statement per line is allowed

## Values
- only binary integers
- must start with 'r'
- will be negative when ending with 'r'
- consists of 'e' and 'a', where 'e' corresponds to 1 and 'a' corresponds to 0
- examples:   
  - 'reaaaeae' equals 69
  - 'reaaaeaer' equals -69

## Variables
- can only be defined in data segment
- variable names must only consist 'E'
- variable names must only consist 'E'
- values can be assigned a value when defined in data segment
- example:
  - 'EE' defines a variable named EE
  - 'EEE reee' defines a variable named EEE with the value 7

## Functions
TODO: add examples
- 3 AAA adds a comment
- 4 AAAA print as integer
- 5 AAAAA print as ascii code
- 6 AAAAAA add, target, addends
- 7 AAAAAAA subtract, target, minuend, subtrahends
- 8 AAAAAAAA multiply, target, multiplicands
- 9 AAAAAAAAA divide, target, dividend, divisors, when multiple divisors interim results will become new dividends
- 10 AAAAAAAAAA modulo, target, dividend, divisor

## Conditional branching
TODO: add examples
- 1 Y else
- 2 YY end if
- 3 YYY if ==
- 4 YYYY if !=
- 5 YYYYY if >=
- 6 YYYYYY if <=
- 7 YYYYYYY if == 0
- 8 YYYYYYYY if != 0
- 9 YYYYYYYYY if >= 0
- 10 YYYYYYYYY if <= 0

## Conditional loops
TODO: add examples
- 2 OO close loop
- 3 OOO while ==
- 4 OOOO while !=
- 5 OOOOO while >=
- 6 OOOOOO while <=
- 7 OOOOOOO while == 0
- 8 OOOOOOOO while != 0
- 9 OOOOOOOOO while >= 0
- 10 OOOOOOOOOO while <= 0
