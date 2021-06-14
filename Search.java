package Project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Search {
    GraphMap g;
    ArrayList<Customer> list;
    Stack<Customer> stack=new Stack<>();
    int maxCapacity, parcelSent;
    double TourCost,cost;
    ArrayList<Customer> route= new ArrayList<>();
    Random random=new Random();
    ArrayList<Vehicle> vehicles=new ArrayList<>();
    Customer current,prev,next;


    public Search (GraphMap g, ArrayList<Customer> list, int maxCapacity) {
        this.g = g;
        this.list = list;
        this.maxCapacity = maxCapacity;
        this.TourCost=0;
    }

    public void DFSTraversal() {

       while (!allVisited()) {
                current=prev=null;
                cost=0;
                current = list.get(0); //source
                current.visited = false;
                stack.push(current);

                while (!stack.isEmpty()) {
                    current = stack.peek();
                    if (parcelSent + current.demands <= maxCapacity && !current.visited) {
                        parcelSent += current.demands;
                        current.visited = true;

                        if(prev!=null)
                            cost+=g.getEdge(current,prev);

                        route.add(current);
                        prev = stack.pop();

                        for (int i = 0; i < list.size(); i++) {
                            int j;
                            do {
                                j = random.nextInt(list.size());
                            } while (j == 0);

                            if (prev.id != j)
                                stack.push(list.get(j));
                        }

                    } else stack.pop();
                }
                if(route.size()!=1){
                cost+=g.getEdge(list.get(0), prev);
                TourCost+=cost;
                route.add(list.get(0));
                vehicles.add(new Vehicle(cost, (ArrayList<Customer>) route.clone(),parcelSent));}
                route.clear();
                parcelSent = 0;

            }
        ResetNodes(list);
    }

    public void greedySearch(){
        Customer source=list.get(0);
        while (!allVisited()){
            source.setVisited(false);
            next=current=source;
            cost=0;

            while(parcelSent+ next.demands<=maxCapacity) {

                cost += g.getEdge(next, current);
                current = next;
                parcelSent += current.demands;
                route.add(current);
                current.setVisited(true);

                Double max = Double.MAX_VALUE;
                for (int i = 0; i < list.size() ; i++)
                    if (g.adjacency_matrix[(int) current.id][i] < max && !list.get(i).visited && i != current.id) {
                        max = g.adjacency_matrix[(int) current.id][i];
                        next = list.get(i);
                    }
            }
            route.add(source);
            cost+=g.getEdge(source,current);
            TourCost+=cost;
            vehicles.add(new Vehicle(cost, (ArrayList<Customer>) route.clone(),parcelSent));
            route.clear();
            parcelSent=0;
        }
       ResetNodes(list);
        System.out.println("\n------------GREEDY SEARCH------------");
    }
    
    //A*Search
    public void aStarSearch() {
        Customer goal = list.get(goalindex());
        Customer source=list.get(0);
        while (!allVisited()){
            source.setVisited(false);
            next=current=source;
            cost=0;

            while(parcelSent+ next.demands<=maxCapacity) {

                cost += g.adjacency_matrix[(int)next.id][(int)current.id];
                current = next;
                parcelSent += current.demands;
                route.add(current);
                current.setVisited(true);
                
                //cost+=g.getEdge(source,current);
                
                Double max = Double.MAX_VALUE;
                for (int i = 0; i < list.size() ; i++){
                    double fnNhn = cost + g.adjacency_matrix[i][(int)current.id] + g.adjacency_matrix[(int)goal.id][i];
                    if (fnNhn < max && !list.get(i).visited && i != current.id) {
                        max = fnNhn;
                        next = list.get(i);
                    }
                }
                if(current == next){
                    break;
                }
            }
            route.add(source);
            cost+=g.getEdge(source,current);
            TourCost+=cost;
            vehicles.add(new Vehicle(cost, (ArrayList<Customer>) route.clone(),parcelSent));
            route.clear();
            parcelSent=0;
        }
       ResetNodes(list);
        System.out.println("\n------------A* SEARCH------------");
    }

     //to return true if there's at least one customer that hasn't yet be visited
    private boolean allVisited(){
        for(int i = 0; i < list.size() ; i++){
            if(!list.get(i).visited){
                return false;
            }
        }
        return true;
    }

    protected static void ResetNodes(ArrayList<Customer> list) {
        for (Customer customer : list)
            customer.setVisited(false);
    }
    
    private int goalindex(){
        double min = Double.NEGATIVE_INFINITY;
        int goal = 0;
        for (int i = 0; i < list.size() ; i++)
                    if (g.adjacency_matrix[0][i] > min) {
                        min = g.adjacency_matrix[0][i];
                        goal = i;
                    }
        return goal;
    }
}
