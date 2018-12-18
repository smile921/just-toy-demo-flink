/**
 * 
 */
package org.smile921.official;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.Vertex;
import org.apache.flink.graph.library.SingleSourceShortestPaths;
//import org.apache.flink.graph.Edge;
//import org.apache.flink.graph.Graph;
//import org.apache.flink.graph.Vertex;
//import org.apache.flink.graph.library.SingleSourceShortestPaths;
import org.apache.flink.types.NullValue;
/**
 * @author Frere921
 *
 */
public class GellyShortestPath {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
        // @fourzerotwo
        int sourceVertex = 3359851;
        // @soulpancake
        int targetVertex = 19636959;
        int maxIterations = 10;

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        InputFormat input = new StanfordTweetsDataSetInputFormat("/Users/ivanmushketyk/Flink/twitter");
		DataSet<TwitterFollower> twitterFollowers = env.createInput(input );

        DataSet<Edge<Integer, NullValue>> twitterEdges = twitterFollowers
                .map(new MapFunction<TwitterFollower, Edge<Integer, NullValue>>() {
                   
					private static final long serialVersionUID = -4221597136517913755L;

					@Override
                    public Edge<Integer, NullValue> map(TwitterFollower value) throws Exception {
                        Edge<Integer, NullValue> edge = new Edge<>();
                        edge.setSource(value.getFollower());
                        edge.setTarget(value.getUser());

                        return edge;
                    }
                });

        Graph<Integer, NullValue, NullValue> followersGraph = Graph.fromDataSet(twitterEdges, env);
        // SSSP only works with weighted graphs
		Graph<Integer, NullValue, Double> weightedFollowersGraph = followersGraph
				.mapEdges(new MapFunction<Edge<Integer, NullValue>, Double>() {

					private static final long serialVersionUID = 7876733189994637197L;

					@Override
					public Double map(Edge<Integer, NullValue> edge) throws Exception {
						return 1.0;
					}
				});


        SingleSourceShortestPaths<Integer, NullValue> singleSourceShortestPaths = new SingleSourceShortestPaths<>(sourceVertex, maxIterations);
        DataSet<Vertex<Integer, Double>> result = singleSourceShortestPaths.run(weightedFollowersGraph);

        result.filter(vertex -> vertex.getId().equals(targetVertex))
                .print();
    }
}