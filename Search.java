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
                vehicles.add(new Vehicle(g,cost, (ArrayList<Customer>) route.clone(),parcelSent));}
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
            vehicles.add(new Vehicle(g,cost, (ArrayList<Customer>) route.clone(),parcelSent));
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
            vehicles.add(new Vehicle(g,cost, (ArrayList<Customer>) route.clone(),parcelSent));
            route.clear();
            parcelSent=0;
        }
        ResetNodes(list);
        System.out.println("\n------------A* SEARCH------------");
    }


    private boolean allVisited(){
        //check whether all nodes are visited
        for(int i = 0; i < list.size() ; i++){
            if(!list.get(i).visited){ //visited==false
                return false;
            }
        }
        return true;
    }

    private static void ResetNodes(ArrayList<Customer> list) {
        //to reset all the nodes as unvisited (false)
        for (Customer customer : list)
            customer.setVisited(false);
    }

    private int goalindex(){
        //returning index of the goal node by the finding the next shortest edge
        double min = Double.NEGATIVE_INFINITY;
        int goal = 0;
        for (int i = 0; i < list.size() ; i++)
            if (g.adjacency_matrix[0][i] > min) {
                min = g.adjacency_matrix[0][i];
                goal = i;
            }
        return goal;
    }

    int alpha=1;
    int[][][] policy;
    int[][] globalPolicy;
    ArrayList<Customer> possible_successors=new ArrayList<>();
    Tour best_tour=new Tour(g);
    Tour new_tour=new Tour(g);
    Customer currentStop, nextStop;

    public void setPolicy(int level, int N){

        this.policy=new int[level][N][N];
        this.globalPolicy=new int[N][N];

        for(int i=0;i<level;i++)
            for(int j=0;j<N;j++)
                for(int h=0;h<N;h++)
                    policy[i][j][h]=0;

        for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
                globalPolicy[i][j]=0;

        System.out.println("setting policies");
    }

    public Tour MonteCarloSearch(int level,int iterations){ //level=3
        long start=System.currentTimeMillis();
        long end=start+2*1000;
        best_tour.TourCost=Double.MAX_VALUE;
        System.out.println("Search"+level+" ");
        if(level==0)
            return rollout();

        else{
            level--;
            policy[level]=globalPolicy;
            level++;

            for(int iteration=0;iteration<=iterations;iteration++){
                new_tour=MonteCarloSearch(level-1,iteration);

                if(new_tour.TourCost< best_tour.TourCost){
                    best_tour=new_tour;
                    adapt(best_tour,level);
                }

                if(System.currentTimeMillis()>end)
                    return best_tour;
            }
            level--;
            globalPolicy=policy[level];
        }

        return best_tour;
    }

    public void adapt(Tour tour,int level){
        System.out.println("adapting");
        for(int i=0;i<tour.route.size();i++)
            for(int j=0;j<tour.route.get(i).size();j++){
                level--;
                policy[level][(int) currentStop.id][(int) nextStop.id]+=alpha;
                double z=0.0;

                for(int x=0;x< list.size();x++)
                    if(!list.get(x).visited)
                        z+=Math.exp(globalPolicy[(int) currentStop.id][(int) list.get(x).id]);

                for(int x=0;x< list.size();x++)
                    if(!list.get(x).visited)
                        policy[level][(int) currentStop.id][(int) list.get(x).id]-=alpha*(Math.exp(globalPolicy[(int) currentStop.id][(int) list.get(x).id])/z);

                currentStop.setVisited(true);
                level++;
            }
    }

    public Tour rollout(){

        parcelSent=0;
        route=new ArrayList<>();
        route.add(list.get(0));
        list.get(0).setVisited(true);

        while(true){

            currentStop=route.get(route.size()-1);
            currentStop.setVisited(true);
            int count=0;
            for(int x=0;x< list.size();x++)
                if (!list.get(x).checked && list.get(x).id != currentStop.id && !list.get(x).visited){
                    possible_successors.add(list.get(x));
                    count++;
                }

            if (allVisited())
                break;

            nextStop=select_next_move(currentStop, (ArrayList<Customer>) possible_successors.clone());

            if(nextStop.demands+parcelSent<=maxCapacity){
                route.add(nextStop);
                parcelSent+=nextStop.demands;
                nextStop.setVisited(true);
            }
            else
                nextStop.setChecked(true);

            if(count==0){
                route.add(list.get(0));
                new_tour.setRoute((ArrayList<Customer>) route.clone());
                route.clear();
                route.add(list.get(0));
            }

        }
        possible_successors.clear();
        System.out.println("Rollout"+parcelSent+" ");
        return new_tour;
    }

    public Customer select_next_move(Customer currentStop, ArrayList<Customer> possible_successors){
        double[] probability=new double[possible_successors.size()];
        double sum=0;

        for(int x=0;x< possible_successors.size();x++){
            probability[x]=Math.exp(globalPolicy[(int) currentStop.id][(int) possible_successors.get(x).id]);
            sum+=probability[x];
        }

        double mrand=new Random().nextDouble()*sum;
        int i=0;
        sum=probability[0];

        while(sum<mrand)
            sum += probability[++i];

        Customer selected_successor= possible_successors.get(i);
        possible_successors.clear();
        //System.out.println("done selection "+selected_successor.id+" ");
        return selected_successor;
    }
}