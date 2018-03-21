import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class SPACESHIP {
    static Graph<Block> G;
    static Block[] blocks;

    public static void main(String[] args) throws IOException {
        int n,k;
        G = new Graph<>();
        InputStreamReader reader = new InputStreamReader(System.in);
        //InputStreamReader reader = new InputStreamReader(new FileInputStream("4.in"));
        BufferedReader in = new BufferedReader(reader);

        // Parse first line
        String[] nk = in.readLine().split(" ");
        n = Integer.parseInt(nk[0]);
        k = Integer.parseInt(nk[1]);

        blocks = new Block[n+1];

        // Parse blocks and gates
        for (int t = 0; t < n-1; t++) {
            String[] edge = in.readLine().split(" ");
            int a = Integer.parseInt(edge[0]);
            int b = Integer.parseInt(edge[1]);
            boolean c = Boolean.parseBoolean(edge[2]);
            if(c){
                if(blocks[a]==null) blocks[a] = new Block(a);
                if(blocks[b]==null) blocks[b] = new Block(b);
            } else {
                Block ab = new Block(a);
                ab.ids.add(b);

                if(blocks[a]==null) blocks[a] = ab;
                else blocks[a].ids.add(b);
                if(blocks[b]==null) blocks[b] = ab;
                else blocks[a].ids.add(a);
            }
            G.addEdge(blocks[a],blocks[b]);
        }

        PriorityQueue<Block> maxHeap = constructMaxHeap();

        while(!maxHeap.isEmpty())
            System.out.println(maxHeap.poll());
    }

    private static PriorityQueue<Block> constructMaxHeap() {
        PriorityQueue<Block> heap = new PriorityQueue<>((o1, o2) -> o2.getSize()-o1.getSize());
        heap.addAll(G.adjacencyList.keySet());
        return heap;
    }

    public static class Graph<T> {
        final private HashMap<T, HashSet<T>> adjacencyList;

        public Graph() {
            this.adjacencyList = new HashMap<>();
        }

        public void addEdge(T v, T u) {
            if (!this.adjacencyList.containsKey(v) || !this.adjacencyList.containsKey(u)) {
                this.adjacencyList.putIfAbsent(v, new HashSet<>());
                this.adjacencyList.putIfAbsent(u, new HashSet<>());
            }
            this.adjacencyList.get(v).add(u);
            this.adjacencyList.get(u).add(v);
        }

        public Set<T> getNeighbors(T v) { return this.adjacencyList.get(v); }
    }

    public static class Block {
        HashSet<Integer> ids;

        public Block(int id){
            this.ids.add(id);
        }

        public int getSize(){
            return this.ids.size();
        }

        @Override
        public String toString() {
            return getSize() + "\t" + ids.stream().map (i -> i.toString ()).collect (Collectors.joining (","));
        }
    }
}
