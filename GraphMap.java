package Project;
public class GraphMap  <T extends Comparable<T>, N extends Comparable<N>>{
    private final int vertices; //no of customer+depot
    protected double[][] adjacency_matrix;

    public GraphMap(int v) //constructor
    {
        vertices = v;
        adjacency_matrix = new double[vertices+1][vertices+1]; //utk store value of edge

    }


    public void makeEdge(Customer to, Customer from)
    {
        try
        {   //yg ni guna id customer sbg index utk pinpoint location in the matrix
            //pastu cost is evaluated by using distance x2-y2 tu, Pythagoras theorem
            adjacency_matrix[(int) to.id][(int) from.id] =Math.sqrt(Math.pow((to.x-from.x),2)+Math.pow((to.y-from.y),2));
        }
        catch (ArrayIndexOutOfBoundsException index)
        {
            System.out.println("The vertices does not exists");
        }
    }

    public double getEdge(Customer to, Customer from) //yg ni utk get balik value yg kite store kat makeEdge tadi
    {
        try
        {
            return adjacency_matrix[(int) to.id][(int) from.id]; //i used round to make it cleaner, klau x byk sgt dp
        }
        catch (ArrayIndexOutOfBoundsException index)
        {
            System.out.println("The vertices does not exists");
        }
        return -1;
    }
}
