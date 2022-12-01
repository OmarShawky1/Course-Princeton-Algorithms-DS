import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;

import java.util.HashMap;

public class BaseballElimination {

    // Global Variables
    private static final int SOURCE_INDEX = 0;
    private static final int SINK_INDEX = 1;
    private final int[][] against; // games left between team i and team j
    private final int[] wins, losses, remaining;
    private final String[] teams;
    private final int numberOfTeams;
    private final HashMap<String, Integer> teamsHM;
    private final HashMap<String, SET<String>> eliminatingTeamsHM;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        //Open file
        In in = new In(filename);

        numberOfTeams = in.readInt(); // read number of teams

        against = new int[numberOfTeams][numberOfTeams];
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        teams = new String[numberOfTeams];
        teamsHM = new HashMap<>();
        eliminatingTeamsHM = new HashMap<>();

        // register each teams name, wins, loses, remaining
        for (int i = 0; !in.isEmpty(); i++) {
            String teamName = in.readString();
            teamsHM.put(teamName, i);
            teams[i] = teamName;
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();

            // register games left for team i against every other team
            for (int j = 0; j < numberOfTeams; j++) against[i][j] = in.readInt();
        }
        in.close();
    }

    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teamsHM.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return wins[teamIndex(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return losses[teamIndex(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remaining[teamIndex(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return against[teamIndex(team1)][teamIndex(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return eliminationSet(team).iterator().hasNext();
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return isEliminated(team) ? eliminationSet(team) : null;
    }

    private Iterable<String> eliminationSet(String team) {
        if (teamsHM.get(team) == null) throw new IllegalArgumentException();

        if (eliminatingTeamsHM.get(team) == null && !isAlreadyEliminated(team)) evaluateEliminatingTeams(team);

        return eliminatingTeamsHM.get(team);
    }

    // checks for elimination without drawing flow network; Just from the current scores
    private boolean isAlreadyEliminated(String team) {
        SET<String> eliminatingTeams = new SET<>();
        int maxWins = wins[teamIndex(team)] + remaining[teamIndex(team)];

        for (String t : teamsHM.keySet()) if (wins[teamsHM.get(t)] > maxWins) eliminatingTeams.add(t);

        eliminatingTeamsHM.put(team, eliminatingTeams);
        return !eliminatingTeams.isEmpty();
    }

    // calculates -if possible- subset of R set that eliminates team *team*
    // check files in Miscellaneous folder for further explanation
    private void evaluateEliminatingTeams(String team) {
        int TI = teamIndex(team); // teams' index
        int opponentTeams = teams.length - 1;

        // nCr (n choose r) where r=2 which is # of teams per game
        // int opponentsGames = factorial(opponentTeams)/(factorial(opponentTeams - 2) * 2); //// un-comment "factorial"
        // alternative way, n teams play, each team plays with (n-1) teams (to not count itself), don't count game twice
        int opponentsGames = opponentTeams * (opponentTeams - 1) / 2;

        int vertices = 2 + opponentsGames + opponentTeams; // 2 is the count of source and sink

        // Create & Connect the flow network
        FlowNetwork network = new FlowNetwork(vertices);

        // Check table in Assignment while reading
        // For each row (i), connect current team to sink
        int gameIndex = 2 + opponentTeams; // maintain pointer for played games independent of other pointers
        double maxWins = wins[TI] + remaining[TI]; // Used to limit any team wins to maxWins
        for (int i = 0; i < numberOfTeams; i++) {
            if (i == TI) continue;

            network.addEdge(new FlowEdge(flowNetworkTI(i, TI), SINK_INDEX, maxWins - wins[i])); // T-->sink

            // for each column (j) connect source to game, game to team 1 & 2
            // Don't add game_ij unless j>i to avoid adding edges from source-->game twice (once from r_ij & other r_ji)
            for (int j = 0; j < i; j++) {
                if (j == TI) continue;

                // add edge source-->game_ij with vertex-index "gameIndex" & capacity r_ij
                network.addEdge(new FlowEdge(SOURCE_INDEX, gameIndex, against[i][j]));

                // add game-->team edge
                network.addEdge(new FlowEdge(gameIndex, flowNetworkTI(i, TI), Double.POSITIVE_INFINITY)); // team 1
                network.addEdge(new FlowEdge(gameIndex, flowNetworkTI(j, TI), Double.POSITIVE_INFINITY)); // team 2

                gameIndex++; // next game
            }
        }

        // calculate the mincut from the maxflow by detecting the not-full-forward-edge which represents number of games
        // that cannot be played due to the capacity constraints "maxWins"
        FordFulkerson maxFlow = new FordFulkerson(network, SOURCE_INDEX, SINK_INDEX);
        SET<String> eliminatingTeams = new SET<>();
        for (int i = 0; i < numberOfTeams; i++)
            if (i != TI && maxFlow.inCut(flowNetworkTI(i, TI))) eliminatingTeams.add(teams[i]);

        eliminatingTeamsHM.put(team, eliminatingTeams);
    }

    private int teamIndex(String team) {
        Integer ind = teamsHM.get(team);
        if (ind == null) throw new IllegalArgumentException();
        return ind;
    }

    // maps index of i in team[i] to vertex v in the flow network
    private int flowNetworkTI(int i, int TI) {
        // shift right by 2 (source & sink vertices are 0 & 1 respectively) or shift left as studiedTeam isn't included
        return i < TI ? i + 2 : i + 1;
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
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
    /*
    private int factorial(int i) {
        int acc = 1;
        while (i > 1) acc *= i--;
        return acc;
    }
    */
}