import java.util.*;

public class Nfa {
    private HashSet<String> language;
    private State startState;
    private HashSet<State> acceptStates;
    private HashSet<State> states;

    public Nfa(){
        language = new HashSet<String>();
        startState = null;
        acceptStates = new HashSet<State>();
        states = new HashSet<State>();
    }

    public void setLanguage(HashSet<String> language) {
        this.language = language;
    }

    public HashSet<String> getLanguage() {
        return language;
    }

    public void setStartState(State state){
        this.startState = state;
    }
    public boolean addAcceptState(State state){
        return this.acceptStates.add(state);
    }

    public void addState(State state){
        states.add(state);
        if(state.getIsAccept()){
            this.addAcceptState(state);
        }
        if(state.getIsStart()){
            this.setStartState(state);
        }
    }

    public HashSet<State> getStates() {
        return states;
    }

    public State getStartState() {
        return startState;
    }


    //translates nfa to dfa with helper functions
    public Dfa createDfa() {
        Dfa dfa = new Dfa();
        dfa.setLanguage(language);

        //create startState
        State startStateDfa = startState.startStateCreator(startState.eClosure);
        startStateDfa.transitionFunction = newTransFunc(startState.eClosure);
        dfa.addState(startStateDfa);

        HashSet<State> visited = new HashSet<>();
        //dfs for creation of nfa recursion
        dfaUtil(visited, startStateDfa);

        for(State state : visited){
            dfa.addState(state);
        }

        return dfa;
    }

    private void dfaUtil(HashSet<State> visited, State state){
        if(state != null){
            visited.add(state);
        }


        for(String input : language){
            HashSet<State> next = state.nextStates(input);
            State newState = stateCreator(next);
            newState.transitionFunction = newTransFunc(next);

            if(!visited.contains(newState)){
                dfaUtil(visited, newState);
            }
       }
    }


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
