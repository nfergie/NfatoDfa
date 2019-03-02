import java.util.*;

public class State {
    public HashSet<String> name;
    public boolean isStart;
    public boolean isAccept;
    public HashMap<String, HashSet<State>> transitionFunction;
    public HashSet<State> eClosure;

    public State(){
        name = new HashSet<>();
        isStart = false;
        isAccept = false;
        transitionFunction = new HashMap<>();
    }

    public void addToName(HashSet<String> name) {
        this.name.addAll(name);
    }

    public void addToName(String name){
        this.name.add(name);
    }

    public boolean getIsAccept(){
        return isAccept;
    }

    public void setIsAccept(){
        isAccept = true;
    }

    public boolean getIsStart(){
        return isStart;
    }

    public void setIsStart(){
        isStart = true;
    }

    //safely adds transition function to the state
    public void addTransition(String input, State nextState){
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

    public void addTransition(String input, HashSet<State> nextState){
        if(!transitionFunction.containsKey(input)){
            transitionFunction.put(input, nextState);
        }else{
            HashSet<State> currStates = transitionFunction.get(input);
            currStates.addAll(nextState);
            transitionFunction.put(input, currStates);
        }
    }

    //sets epsilon closure for state
    public void setEClosure(){
        HashSet<State> closed = this.findEClosure();
        eClosure = closed;
    }

    //recursive helper function
    public HashSet<State> findEClosure(){
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
    public State startStateCreator(HashSet<State> states){
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

    public HashSet<State> nextStates(String input) {
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
