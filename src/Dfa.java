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

    public void setStartState(State startState) {

        this.startState = startState;
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



}
