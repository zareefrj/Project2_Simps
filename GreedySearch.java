/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Amirul Hakiem
 */
public class GreedySearch {
    private GraphMap g;
    private ArrayList<Customer> list;
    public int idCurrent, idPrevious, maxCapacity;
    public int parcelSent = 0;
    public int vehicleCount = 1;
    private ArrayList<Double> distance = new ArrayList<>();
    private ArrayList<Integer> road = new ArrayList<>();
         

    public GreedySearch(GraphMap g, ArrayList<Customer> list, int maxCapacity) {
        this.g = g;
        this.list = list;
        this.maxCapacity = maxCapacity;
        idCurrent = 0;
    }
    
    public void performGreed(){
        System.out.println("***** Greedy Search *****");
         while(true){
          if(parcelSent + list.get(idCurrent).demands <= maxCapacity){
               list.get(idCurrent).visited = true; //set currrent customer as visited
               parcelSent+= list.get(idCurrent).demands; 
               road.add(idCurrent); 
               if(allVisited()){
                   double k = getShortest(idCurrent);
                   distance.add(k);
               } else {
                    idPrevious = idCurrent;
                    printTour();
                    break;
               }
          } else {
              printTour();
              road.clear();
              distance.clear();
              vehicleCount++;
              idCurrent = 0;
              parcelSent = 0;
          }
        }
    }
    
    //To print the path, vehicle, and cost
    private void printTour(){
              System.out.println("Vehicle : " + vehicleCount);
              System.out.println("Capacity : " + parcelSent);
              System.out.println("Cost : " + sumCost());
              for(Integer no : road)
                 System.out.print(no + " --> ");
              System.out.print(0); //back to depot
              System.out.println("\n");
    }
    
    //To sum up all distances recorded
    private double sumCost(){
        double i = 0;
        if(distance.size() != 1){
        for(int j = 0; j < distance.size()-1 ; j++)
                 i += distance.get(j);
        } else {
                 i = distance.get(0);
        }
        i+=g.adjacency_matrix[0][idPrevious];
        return i;
    }
    
    //to return true if there's at least one customer that hasn't yet be visited
    private boolean allVisited(){
       for(int i = 0; i < list.size() ; i++){
           if(list.get(i).visited == false){
              return true;
           }
       }
       return false;
    }
    
    //find the shortest path to determine the next node
    private double getShortest(int i){
        ArrayList<Double> tour = new ArrayList<>();
        ArrayList<Double> temp = new ArrayList<>();
        for(int j=0; j < list.size(); j++){
                temp.add(g.adjacency_matrix[j][i]); //
                if(list.get(j).visited == false){ //only the paths to unvisited nodes will get to the array.
                    tour.add(g.adjacency_matrix[j][i]);
                }
        }
        Collections.sort(tour); //to sort in increasing order
        idPrevious = idCurrent;
        idCurrent = temp.indexOf(tour.get(0)); //to know which customer has the shortest path
        return tour.get(0); 
    }
    
    
}
