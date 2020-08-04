/***
 *
 * @author K.D.S.De Silva
 * @IITStudentID 2018238
 * @UoWStudentID w1714958
 * @source https://www.sanfoundry.com/java-program-implement-ford-fulkerson-algorithm/
 * @License GNU Licensing Agreement
 * @Published 31th March 2020
 *
 **/
package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static java.lang.System.out;


public class FordFulkerson_FromNET {
    private int[] parent;
    private Queue<Integer> queue;
    private int numberOfVertices;
    private boolean[] visited;
    private static int sink;
    private static int sourceVertex;
    private static int destinationVertex;


    public FordFulkerson_FromNET(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.queue = new LinkedList<Integer>();
        parent = new int[numberOfVertices + 1];
        visited = new boolean[numberOfVertices + 1];
    }

    public boolean bfs(int source, int goal, int graph[][]) {
        boolean pathFound = false;
        int destination, element;

        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            parent[vertex] = -1;
            visited[vertex] = false;
        }

        queue.add(source);
        parent[source] = -1;
        visited[source] = true;

        while (!queue.isEmpty()) {
            element = queue.remove();
            destination = 1;

            while (destination <= numberOfVertices) {
                if (graph[element][destination] > 0 && !visited[destination]) {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;

            }
        }
        if (visited[goal]) {
            pathFound = true;
        }
        return pathFound;

    }

    public int fordFulkerson(int graph[][], int source, int destination) {
        int u, v;
        int maxFlow = 0;
        int pathFlow;

        int[][] residualGraph = new int[numberOfVertices + 1][numberOfVertices + 1];
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++) {
                residualGraph[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
            }
        }

        List<Integer> flowList = new ArrayList<>();
        List<Integer> totalFlowList = new ArrayList<>();


        while (bfs(source, destination, residualGraph)) {
            pathFlow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = parent[v]) {
                u = parent[v];

                flowList.add(u);
                totalFlowList.add(pathFlow);
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);

            }
            for (v = destination; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;

            }
            maxFlow += pathFlow;

            flowList.add(sink);
            Collections.sort(flowList);

            out.println(flowList + " ");
            out.println("Path Flow :" + totalFlowList.get(totalFlowList.size() - 1) + " \n");
            flowList.clear();


        }

        return maxFlow;
    }

    public static void main(String... arg) {

        int[][] graph;
        int numberOfNodes;
        int source;
        int maxFlow;

        //taking number of nodes
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of nodes");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Please enter Integer Number");
        }
        numberOfNodes = scanner.nextInt();
        graph = new int[numberOfNodes + 1][numberOfNodes + 1];


        //taking graph matrix
        System.out.println("Enter the graph matrix ");
        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++) {
            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++) {
                graph[sourceVertex][destinationVertex] = scanner.nextInt();
            }
        }


        //taking number for source
        System.out.println("Enter the source of the graph (1 to " + numberOfNodes + ")");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Please enter 1 to " + numberOfNodes + " numbers only");
        }
        source = scanner.nextInt();


        //taking number for graph
        System.out.println("Enter the sink of the graph (" + source + " to " + numberOfNodes + ")");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Please enter" + source + " to " + numberOfNodes + " numbers only");
        }
        sink = scanner.nextInt();


        //Timer start
        long startTime2 = System.nanoTime();

        //show the MaxFlow
        FordFulkerson_FromNET fordFulkersonFromNET = new FordFulkerson_FromNET(numberOfNodes);
        maxFlow = fordFulkersonFromNET.fordFulkerson(graph, source, sink);
        System.out.println("The Max Flow is " + maxFlow);


        //Timer end
        long endTime2 = System.nanoTime();
        double timeElapsed2 = endTime2 - startTime2;
        System.out.println("");
        System.out.println("execution time in nano seconds " + timeElapsed2+"bitch");
        System.out.println("execution time in milli seconds " + timeElapsed2 / 1000000);


        Scanner scanner1 = new Scanner(System.in);
        int selectOption = 1;

        while (selectOption > 0) {

            out.println("\nEnter Number you want to do\n");

            out.println("To delete edge to press 1");
            out.println("To edit graph matrix to press 2");
            out.println("To Re-Check Max FLow to press 3");
            out.println("if you want to double the number of nodes and re-check Execution time Press 4");
            out.println("To exit press 5");
            out.print("\n> ");
            selectOption = scanner1.nextInt();

            if (selectOption == 1) {
                out.println("Please enter row number of the edge you delete");
                int delNodeOne = scanner1.nextInt();

                out.println("Please enter column number of the edge you delete");
                int delNodeTwo = scanner1.nextInt();
                sourceVertex = delNodeOne;
                destinationVertex = delNodeTwo;

                graph[sourceVertex][destinationVertex] = 0;

                for (int i = 1; i < numberOfNodes + 1; i++) {
                    for (int j = 1; j < numberOfNodes + 1; j++) {
                        if (graph[i][j] < 10) {
                            System.out.print(graph[i][j] + " ");
                        } else {
                            System.out.print(graph[i][j] + " ");
                        }

                    }
                    System.out.println();
                }

            } else if (selectOption == 2) {
                out.println("Please enter row number of the edge you edit");
                int delNodeOne = scanner1.nextInt();

                out.println("Please enter column number of the edge you edit");
                int delNodeTwo = scanner1.nextInt();

                out.println("enter capacity you want to edit");
                int newCapacity = scanner1.nextInt();

                sourceVertex = delNodeOne;
                destinationVertex = delNodeTwo;

                graph[sourceVertex][destinationVertex] = newCapacity;
                for (int i = 1; i < numberOfNodes + 1; i++) {
                    for (int j = 1; j < numberOfNodes + 1; j++) {
                        if (graph[i][j] < 10) {
                            System.out.print(graph[i][j] + " ");
                        } else {
                            System.out.print(graph[i][j] + " ");
                        }

                    }
                    System.out.println();
                }

            } else if (selectOption == 3) {
                long startTime3 = System.nanoTime();

                //show the MaxFlow
                FordFulkerson_FromNET fordFulkersonFromNET1 = new FordFulkerson_FromNET(numberOfNodes);
                maxFlow = fordFulkersonFromNET1.fordFulkerson(graph, source, sink);
                System.out.println("The Max Flow is " + maxFlow);


                //Timer end
                long endTime3 = System.nanoTime();
                double timeElapsed3 = endTime3 - startTime3;
                System.out.println("");
                System.out.println("execution time in nano seconds " + timeElapsed2);
                System.out.println("execution time in milli seconds " + timeElapsed2 / 1000000);


            }else if(selectOption==4){
                main();

            }else if (selectOption == 5) {
                out.println("you successfully exited ");
                System.exit(0);
            }
        }

        scanner.close();
        scanner1.close();

    }
}

