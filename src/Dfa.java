import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public HashSet<State> getAcceptStates() {
        return acceptStates;
    }

    public void setLanguage(HashSet<String> lang){
        language = lang;
    }

    public HashSet<String> getLanguage() {
        return language;
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

    public void dfaCleanup(){
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


    public void dfaWriter(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.dfa"));
            StringBuffer s = new StringBuffer();

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
            /*for(State state : states){
                String name = state.name.toString().replace(
                        '[', '{').replace(']', '}');
                for(String input : language){
                    s.append(name + ", " + input + " = {");
                    HashSet<State> trans = state.transitionFunction.get(input);
                    s.append(trans);
                    s.append('\n');
                }
            }*/
            System.out.println(s.toString());

        } catch (IOException e){
            e.printStackTrace();
        }



    }
}
