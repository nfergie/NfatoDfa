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
    public Dfa createDfa(){
        Dfa dfa = new Dfa();
        State newStart = this.startState.singleStateCreator(startState.eClosure);
        dfa.setStartState(newStart);

        if(newStart.isAccept){
            dfa.addAcceptState(newStart);
        }

        dfa.setLanguage(language);


        HashSet<State> visited = new HashSet<>();
        HashSet<State> allStates = createDfaUtil(dfa, newStart, visited);


        return dfa;
    }

    public HashSet<State> createDfaUtil(Dfa dfa, State startState, HashSet<State> visited){
        Stack<State> stack = new Stack<>();


        stack.push(startState);

        while(!stack.empty()){
            State s = stack.pop();

            if(!visited.contains(s)){





                visited.add(s);
            }
        }

        return visited;
    }






}
