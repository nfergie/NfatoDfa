/*
This file containes the Dfa object specification and related functionality.
This is the target object for the project.

A Dfa is made up of:
HashSet<String> language // all of the strings in the language available to the dfa
State startState // The start state of the dfa
HashSet<State> acceptStates //The accepting states of the dfa
HashSet<State> states //all of the states in the dfa
 */

import java.io.*;
import java.util.*;

class Dfa {
    private HashSet<String> language;
    private State startState;
    private HashSet<State> acceptStates;
    private HashSet<State> states;

    Dfa(){
        language = new HashSet<>();
        startState = null;
        acceptStates = new HashSet<>();
        states = new HashSet<>();
    }

    void setLanguage(HashSet<String> lang){
        language = lang;
    }

    //adds a state to the dfa while also adding it to startState or acceptStates if it applies
    void addState(State state){
        if(state.isStart){
            startState = state;
        }
        if(state.isAccept){
            acceptStates.add(state);
        }
        states.add(state);
    }

    //Changes the objects in the transitionFunction of a state to point to states in the
    // dfa instead of those in the nfa.
    // A helper function in the nfa to dfa conversion
    void dfaCleanup(){
        for(State state : states){
            for(String input : language){
                HashSet<State> transStates = state.transitionFunction.get(input);
                HashSet<String> stateNames = new HashSet<>();
                for(State transState : transStates){
                    stateNames.addAll(transState.name);
                }
                for(State posStates : states){
                    if(posStates.name.size() == stateNames.size()
                    && posStates.name.containsAll(stateNames)){
                        HashSet<State> newState = new HashSet<>();
                        newState.add(posStates);
                        state.transitionFunction.put(input, newState);

                    }
                }
            }
        }
    }

    //writes the dfa to output.dfa
    void dfaWriter(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.dfa"));
            StringBuilder s = new StringBuilder();

            //write all states
            for (State state : states){
                String st = state.name.toString().replace(
                        '[', '{').replace(']', '}');
                s.append(st);
                s.append('\t');
            }
            s.append('\n');

            //write language
            for(String input : language){
                s.append(input);
                s.append('\t');
            }
            s.append('\n');

            //write start state
            String st = startState.name.toString().replace(
                    '[', '{').replace(']', '}');
            s.append(st);
            s.append('\n');

            //write accept states
            for (State state : acceptStates){
                st = state.name.toString().replace(
                        '[', '{').replace(']', '}');
                s.append(st);
                s.append('\t');
            }
            s.append('\n');

            //write transition functions
            for(State state : states){
                String name = state.name.toString().replace(
                        '[', '{').replace(']', '}');
                for(String input : language){
                    s.append(name);
                    s.append(", ");
                    s.append(input);
                    s.append(" = ");
                    HashSet<State> transFunc = state.transitionFunction.get(input);
                    for(State transState : transFunc){
                        s.append(transState.name.toString().replace(
                                '[', '{').replace(']', '}'));
                    }
                    s.append('\n');
                }
            }
            //System.out.println(s.toString());
            writer.write(s.toString());
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }



    }
}
