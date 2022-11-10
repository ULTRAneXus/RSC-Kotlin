# RSC Kotlin
## Overview
This project is a compiler for the programming language RageScript (which I made up, [documentation](RageScript%20docs.md)) written in Kotlin compiling to Python.
It uses the general three-stage compiler structure found on [Wikipedia](https://en.wikipedia.org/wiki/Compiler). Some modules are missing because they are not necessary for RageScript or are outside the scope of this implementation. <br>
This is a pre-alpha version.

## General Structure
DISCLAIMER: This part is very much a work in progress, first release coming soon
- Front end
  - lexical analysis (Not yet implemented)
  - syntax analysis and building an AST
- Middle end
  - possibly simple data flow analysis and dead code elimination (Not yet implemented)
- Back end
  - python code generation

## TODOs
- expand the readme
- add wiki page in gitHub
- add examples to RageScript documentation
- the current progress can be found in the [Projects tab](https://github.com/ULTRAneXus/RSC-Kotlin/projects?query=is%3Aopen)

## Credit
This project uses a modified version of [this template](https://github.com/fwcd/kotlin-quick-start/) by [fwcd](https://github.com/fwcd)