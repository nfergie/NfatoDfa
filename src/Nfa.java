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


        return dfa;
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
}
