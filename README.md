# NfatoDfa
This project contains code that translates a Non-deterministic Finite Automata(NFA) into a Deterministic Finite Automata(DFA).

## Requirements
This project requires an input file as CMD line argument.

This file will have the form:

Line 1: A list of states, Q, separated by tabs.

Line 2: A list of the symbols in Σ, separated by tabs. The string E
will not be explicitly included.

Line 3: The start state, q0 ∈ Q.

Line 4: The set of accept states, F , separated by tabs

A sample input file can be found [here](https://github.com/nfergie/NfatoDfa/blob/master/src/input.nfa).

## Output
The output will be found in an output.dfa file of the same form as the input

## How to compile and Run
A Makefile has been provided so all that is needed is to download the src folder and run the following commands:

      make
      java ./Converter [input_file]

## Details
This project was created for an Algorithm Analysis class at Chapman University taught by Prof. Erik Linstead.

Collaboration by Scott Weller
