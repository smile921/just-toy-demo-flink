package org.smile921;

import java.lang.Exception;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

public class FilterMovies {

	@SuppressWarnings("unused")
	public static void main1(String[] args) throws Exception {

		// Create Flink execution environment final ExecutionEnvironment env =
		// ExecutionEnvironment.getExecutionEnvironment();
		// We will write our code here
		// Start Flink application env.execute(); } }
		ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
		// DataSet<String> lines1 = env.readTextFile("path/to/file.txt");
		// env.readCsvFile("hdfs:///path/to/file.txt");
		// DataSet<Tuple2<Long, String>> lines = env.readCsvFile("data.csv")
		// .types(Long.class, String.class);
		// Create from a list
		// DataSet<String> letters = env.fromCollection(Arrays.asList("a", "b",
		// "c"));
		// Create from an array
		// DataSet<Integer> numbers = env.fromElements(1, 2, 3, 4, 5);

		// map：使用用户定义的函数转换数据集中的项目。每个输入元素都被转换为一个输出元素。
		// filter：根据用户定义的函数过滤数据集中的项目。
		// flatMap：类似于map运算符，但允许返回零个，一个或多个元素。
		// groupBy：按键值分组得元素。与SQL中的GROUP BY运算符类似。
		// project：在tuples(元组)数据集中选择指定的字段，类似于SQL中的SELECT操作符。
		// reduce：使用用户定义的函数将数据集中的元素组合为单个值。
		// 请记住，Java流操作与这些操作之间最大的区别在于Java
		// 8可以处理内存中的数据并且可以访问本地数据，而Flink在分布式环境中处理集群中的数据。
		// Create a dataset of numbers
		DataSet<Integer> numbers1 = env.fromElements(1, 2, 3, 4, 5, 6, 7);
		// Square every number
		DataSet<Integer> result = numbers1.map(new MapFunction<Integer, Integer>() {

			private static final long serialVersionUID = 7655513123512055347L;

			@Override
			public Integer map(Integer integer) throws Exception {
				return integer * integer;
			}
		});
		// Leave only even
		numbers1.filter(new FilterFunction<Integer>() {

			private static final long serialVersionUID = 5772564852582717513L;

			@Override
			public boolean filter(Integer integer) throws Exception {
				return integer % 2 == 0;
			}
		});

		// Output dataset to the standard output
		result.print();
		// Output dataset to the standard err
//		result.printToErr();
		env.execute("demo number filter");
		
//		env = env.getExecutionEnvironment();
//		FilterMovies.loadMovieData(env);
//		env.execute("filter movie");
	}

	public static void main(String[] args) throws Exception{
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		FilterMovies.loadMovieData(env);
		env.execute("filter movie"); 
	}
	
	public static void loadMovieData(ExecutionEnvironment env) throws Exception {
		// Load dataset of movies
		DataSet<Tuple3<Long, String, String>> lines = env.readCsvFile("file:///C:/Dev/just-toy-demo-flink/src/main/resources/csv/movies.csv").ignoreFirstLine()
				.parseQuotedStrings('"').ignoreInvalidLines().types(Long.class, String.class, String.class);

		DataSet<Movie> movies = lines.map(new MapFunction<Tuple3<Long, String, String>, Movie>() {

			private static final long serialVersionUID = -4802700252341526605L;

			@Override
			public Movie map(Tuple3<Long, String, String> csvLine) throws Exception {
				String movieName = csvLine.f1;
				String[] genres = csvLine.f2.split("\\|");
				return new Movie(movieName, new HashSet<>(Arrays.asList(genres)));
			}
		});

		DataSet<Movie> filteredMovies = movies.filter(new FilterFunction<Movie>() {

			private static final long serialVersionUID = 1037077129582979320L;

			@Override
			public boolean filter(Movie movie) throws Exception {
				return movie.getGenres().contains("Action");
			}
		});

		filteredMovies.writeAsText("output1.txt");
		filteredMovies.print();
	}
	/*************************************************/
	/******************************************************************
	 * 下载Grouplens电影数据集。它包含几个电影和电影评级信息的CSV文件。我们将从movies.csv
	 * https://grouplens.org/datasets/movielens/
	 */
	/**
	 * movieId,title,genres 1,Toy Story
	 * (1995),Adventure|Animation|Children|Comedy|Fantasy 2,Jumanji
	 * (1995),Adventure|Children|Fantasy 3,Grumpier Old Men
	 * (1995),Comedy|Romance 4,Waiting to Exhale (1995),Comedy|Drama|Romance
	 * 5,Father of the Bride Part II (1995),Comedy 6,Heat
	 * (1995),Action|Crime|Thriller 7,Sabrina (1995),Comedy|Romance 8,Tom and
	 * Huck (1995),Adventure|Children 9,Sudden Death (1995),Action 10,GoldenEye
	 * (1995),Action|Adventure|Thriller
	 */
	/************************************************** */
}

/*************************
 * https://brewing.codes/2017/02/13/graphs-processing-with-apache-flink/
 *
 */