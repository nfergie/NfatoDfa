/*
This file contains the Nfa object specification and related functionality.
This is the starting object for the project.

A Nfa is made up of:
HashSet<String> language // all of the strings in the language available to the nfa
State startState // The start state of the nfa
HashSet<State> acceptStates //The accepting states of the nfa
HashSet<State> states //all of the states in the nfa
 */

import java.util.*;

class Nfa {
    private HashSet<String> language;
    private State startState;
    private HashSet<State> acceptStates;
    private HashSet<State> states;

    Nfa(){
        language = new HashSet<>();
        startState = null;
        acceptStates = new HashSet<>();
        states = new HashSet<>();
    }

    void setLanguage(HashSet<String> language) {
        this.language = language;
    }

    private void setStartState(State state){
        this.startState = state;
    }
    private void addAcceptState(State state){
        this.acceptStates.add(state);
    }

    //adds a state to the Nfa and checks if it also belongs in acceptStates or startState
    void addState(State state){
        states.add(state);
        if(state.getIsAccept()){
            this.addAcceptState(state);
        }
        if(state.getIsStart()){
            this.setStartState(state);
        }
    }

    //translates nfa to dfa with helper functions in a DFS recursive fashion.
    Dfa createDfa() {
        Dfa dfa = new Dfa();
        dfa.setLanguage(language);

        //create startState
        State startStateDfa = startState.startStateCreator(startState.eClosure);
        startStateDfa.transitionFunction = newTransFunc(startState.eClosure);
        dfa.addState(startStateDfa);

        HashSet<State> visited = new HashSet<>();
        //dfs for creation of nfa recursion
        createDfaUtil(visited, startStateDfa);

        for(State state : visited){
            if(state.name.size() != 0){
                dfa.addState(state);
            }
        }

        return dfa;
    }

    //recursive helper function for createDfa()
    private void createDfaUtil(HashSet<State> visited, State state){
        if(state != null) {
            visited.add(state);

            for (String input : language) {
                HashSet<State> next = state.nextStates(input);
                if (next != null) {
                    State newState = stateCreator(next);
                    newState.transitionFunction = newTransFunc(next);

                    if (!visited.contains(newState)) {
                        createDfaUtil(visited, newState);
                    }
                }
            }
        }
    }


    //creates the new transition function for the dfa state from the collection of nfa states.
    private HashMap<String, HashSet<State>> newTransFunc(HashSet<State> states){
        HashMap<String, HashSet<State>> transFunc = new HashMap<>();
        for(String input : language){
            HashSet<State> nextStates = new HashSet<>();
            for(State state :states){
                HashSet<State> next = state.nextStates(input);
                if(next != null){
                    nextStates.addAll(next);
                }

            }
            transFunc.put(input, nextStates);
        }
        return transFunc;
    }

    //Creates a DFA state from a collection of NFA states. A helper function to createDfaUtil()
    private State stateCreator(HashSet<State> states){
        State newState = new State();
        newState.isStart = false;
        for(State state : states){
            newState.name.addAll(state.name);
            if(state.isAccept){
                newState.setIsAccept();
            }
        }
        return newState;
    }
}
