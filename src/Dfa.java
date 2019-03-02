import java.util.*;

public class Dfa {
    private HashSet<String> language;
    private State startState;
    private HashSet<State> acceptStates;
    private HashSet<State> states;

    public Dfa(){
        language = new HashSet<>();
        startState = null;
        acceptStates = new HashSet<>();
        states = new HashSet<>();
    }


    public String startStateToString(){
        String startStateString = startState.name.toString();
        return startStateString;
    }

    public void addAcceptState(State acceptState){
        acceptStates.add(acceptState);
    }

    public void setLanguage(HashSet<String> lang){
        language = lang;
    }

    public boolean addState(State state){
        if(state.isStart){
            startState = state;
        }
        if(state.isAccept){
            acceptStates.add(state);
        }
        if(states.add(state)){
            return true;
        }else{
            return false;
        }
    }

    public State getStartState() {
        return startState;
    }

    public HashSet<State> getStates() {
        return states;
    }
}
