package Project;

public class Customer <T extends Comparable<T>, N extends Comparable<N>>{

    //CUSTOMER CLASS WILL REPRESENT THE NODES FOR THE GRAPH //
    double demands,id,x,y;
//each customer ade demands, id masing2 & coordinates lokasi

    public Customer(double x, double y, double demands, double id) {  //constructor
        this.demands = demands;
        this.x= x;
        this.y= y;
        this.id=id;
    }


    @Override
    public String toString() {  //for printing all the details of each customer
        return "Customer{" +
                "demands=" + demands +
                ", id=" + id +
                ", location= (" + this.x +", "+this.y+") "+
                '}';
    }
}