package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MCTS extends Search{

    int N,alpha=1;
    int[][][] policy;
    int[][] globalPolicy;
    Customer stop,nextStop,currentStop;
    Tour best_tour=new Tour(g);
    Tour new_tour=new Tour(g);
    ArrayList<Customer> possible_successors=new ArrayList<>();
    ArrayList<Customer> currentRoute=new ArrayList<>();

    public MCTS(GraphMap g, ArrayList<Customer> list, int maxCapacity) {
        super(g, list, maxCapacity);
        N=list.size();

        }

    public Tour MonteCarloSearch(int level,int iterations){
        this.policy=new int[level][N][N];
        this.globalPolicy=new int[N][N];

        for (int[][] square : policy)
            for (int[] line : square)
                Arrays.fill(line, 0);

        for (int[] square : globalPolicy)
            for (int line : square)
                Arrays.fill(new int[]{line}, 0);

        best_tour.cost=Double.MAX_VALUE;
        long start=System.currentTimeMillis();
        if(level==0)
            return rollout();
        else{
            //level--;
            policy[level]=globalPolicy;

            for(int i=0;i<=iterations;i++){
                //level++;
                new_tour=MonteCarloSearch(level-1,i);
            }


            if(new_tour.getTourcost()< best_tour.getTourcost())
                best_tour=new_tour;

            adapt(best_tour,level);

           if(start+System.currentTimeMillis()>1000)
                return best_tour;

           globalPolicy=policy[level];
           return best_tour;
        }
    }

    public void adapt(Tour a_tour,int level){

        for(int i=0;i<a_tour.vehicles.size();i++){
            for(int j=0;j< a_tour.vehicles.get(i).route.size();j++){
                policy[level][(int) stop.id][(int) nextStop.id]+=alpha;
                double z=0.0;

                for(int k=0;k<list.size();k++){
                    if(k!=stop.id && !list.get(k).visited){
                        Customer move=list.get(k);
                        z+=Math.exp(globalPolicy[(int) stop.id][(int) move.id]);
                    }
                }

                for(int m=0;m<list.size();m++){
                    if(m!=stop.id && !list.get(m).visited){
                        Customer move=list.get(m);
                        policy[level][(int) stop.id][(int) move.id]-=alpha*(Math.exp(globalPolicy[(int) stop.id][(int) move.id])/z);
                    }
                }

                stop.setVisited(true);
            }
        }
    }

    public Tour rollout(){
        new_tour.dispatchNewVehicle();
        currentRoute.add(list.get(0));

        while(true){
            currentStop=currentRoute.get(currentRoute.size()-1);
            for(int i=0;i<list.size();i++){
                if(!list.get(i).checked && list.get(i).id!= currentStop.id){
                    possible_successors.add(list.get(i));
                }
            }

            if(possible_successors.isEmpty()){
                currentRoute.add(list.get(0));

                if(allVisited())
                    break;

                new_tour.dispatchNewVehicle();
                currentRoute.add(list.get(0));

                continue;
            }

            nextStop=select_next_move(currentStop, possible_successors);

            if(nextStop.demands+new_tour.currentVan.cost<=maxCapacity){
                currentRoute.add(nextStop);
                nextStop.setVisited(true);
            }
            else{
                nextStop.setChecked(true);
            }
        }
        return new_tour;
    }

    public Customer select_next_move(Customer currentStop,ArrayList<Customer> possible_successors){
        double[] probability=new double[possible_successors.size()];
        double sum=0;

        for(int i=0;i<possible_successors.size();i++){
            probability[i]=Math.exp(globalPolicy[(int) currentStop.id][(int) possible_successors.get(i).id]);
            sum+=probability[i];
        }
        double mrand=new Random().nextDouble()*sum;
        int i=0;
        sum=probability[0];

        while(sum<mrand){
            sum+=probability[++i];
        }

        return possible_successors.get(i);
    }

    private boolean allVisited(){
        for(int i = 0; i < list.size() ; i++){
            if(!list.get(i).visited){
                return false;
            }
        }
        return true;
    }
}
