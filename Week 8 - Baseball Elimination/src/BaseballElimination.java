import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class BaseballElimination {

    // Global Variables

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        //TODO
    }

    // number of teams
    public int numberOfTeams() {
        return 0; //TODO
    }


    // all teams
    public Iterable<String> teams() {
        return new LinkedList<>(); //TODO
    }


    // number of wins for given team
    public int wins(String team) {
        return 0; //TODO
    }

    // number of losses for given team
    public int losses(String team) {
        return 0; //TODO
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return 0; //TODO
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return 0; //TODO
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return false; //TODO
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return new LinkedList<>(); //TODO
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
