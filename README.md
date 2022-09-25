# RSC Kotlin
## Overview
This project is a compiler for the programming language RageScript (which I made up, documentation following soon<sup>TM</sup>) written in Kotlin compiling to Python.
It uses the general three-stage compiler structure found on [Wikipedia](https://en.wikipedia.org/wiki/Compiler). Some modules are missing because they are not necessary for RageScript or are outside the scope of this implementation.
This is a pre-alpha version.

## General Structure
DISCLAIMER: This part is very much a work in progress
- Front end
  - lexical analysis
  - syntax analysis and building an AST
- Middle end
  - possibly simple data flow analysis
  - possibly simple dead code elimination
- Back end
  - code generation

## TODOs
- expand the readme
- write a todo list
- move the todo list into its designated area

## Credit
This project uses a modified version of [this template](https://github.com/fwcd/kotlin-quick-start/) by [fwcd](https://github.com/fwcd)