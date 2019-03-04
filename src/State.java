import java.util.*;

public class State {
    HashSet<String> name;
    boolean isStart;
    boolean isAccept;
    HashMap<String, HashSet<State>> transitionFunction;
    HashSet<State> eClosure;

    State(){
        name = new HashSet<>();
        isStart = false;
        isAccept = false;
        transitionFunction = new HashMap<>();
    }

    void addToName(String name){
        this.name.add(name);
    }

    boolean getIsAccept(){
        return isAccept;
    }

    void setIsAccept(){
        isAccept = true;
    }

    boolean getIsStart(){
        return isStart;
    }

    void setIsStart(){
        isStart = true;
    }

    //safely adds transition function to the state
    void addTransition(String input, State nextState){
        if(!transitionFunction.containsKey(input)){
            HashSet<State> transitions = new HashSet<>();
            transitions.add(nextState);
            transitionFunction.put(input, transitions);
        }else{
            HashSet<State> transitions = transitionFunction.get(input);
            transitions.add(nextState);
            transitionFunction.put(input, transitions);
        }
    }

    //sets epsilon closure for state
    void setEClosure(){
        eClosure = this.findEClosure();
    }

    //recursive helper function
    private HashSet<State> findEClosure(){
        HashSet<State> closed = new HashSet<>();
        closed.add(this);

        HashSet<State> eTransStates = this.transitionFunction.get("EPS");
        if(eTransStates != null){
            for(State e : eTransStates){
                closed.addAll(e.findEClosure());
            }
        }

        return closed;
    }

    //converts a HashSet<State> into a single state for use in nfa to dfa conversion
    State startStateCreator(HashSet<State> states){
        State newState = new State();
        newState.setIsStart();
        for(State state : states){
            newState.name.addAll(state.name);
            if(state.isAccept){
                newState.setIsAccept();
            }
        }
        return newState;
    }

    HashSet<State> nextStates(String input) {
        HashSet<State> nextSet = transitionFunction.get(input);
        HashSet<State> next = null;
        if(nextSet != null){
            next = new HashSet<>();
            for(State state : nextSet){
                next.addAll(state.eClosure);
            }
        }
        return next;
    }





    /*
    overrides for equals and hashcode solely based on name;
    equals checks if they are the same size and contain
    the same objects therefore order doesn't matter;
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        boolean sameSize = state.name.size() == name.size();
        boolean containsAll = state.name.containsAll(name);
        return sameSize && containsAll;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
