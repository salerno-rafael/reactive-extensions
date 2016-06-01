package org.gradle;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rx.Observable;
import rx.functions.Func1;

public class ReadFilev3 {

	public static void main(String[] args) throws Exception {
		new ReadFilev3().start();
	}
	
	public void start() throws Exception{
		
		Path path = Paths.get(ReadFileV2.class.getResource("/csvFile.txt").toURI());
		Stream<String> linhas = Files.lines(path, Charset.defaultCharset());
		
		 Observable.just(linhas)
		  .flatMap(y-> Observable.just(splitLines(y)))
		  .flatMap(z-> Observable.just(parse(z)))
		  .flatMap(new Func1<Stream<Pojo>, Observable<List<HashMap<String,List<Pojo>>>>>() {
		        @Override
		        public  Observable<List<HashMap<String,List<Pojo>>>>call(Stream<Pojo> lines) {
		         
		        	List<Pojo> allLines =  lines.collect(Collectors.toList());
		        	List<Pojo> l1 = allLines.stream().filter(x-> x.getElement1().equals("001")).collect(Collectors.toList());
		        	List<Pojo> l2 = allLines.stream().filter(x-> x.getElement1().equals("002")).collect(Collectors.toList());
		        
		        	HashMap<String,List<Pojo>> hash = new HashMap<>();
		        	hash.put("001", l1);
		        	hash.put("002",l2);
		        	
		          List<HashMap<String,List<Pojo>>> result = new ArrayList<HashMap<String,List<Pojo>>>();
		          result.add(hash);
		        	
		            return Observable.just(result);
		        }
		    })
		  
		  .subscribe(x->{
			  x.forEach(lists-> {
			
				  System.out.println(lists.keySet());
			  });
			  
			  System.out.println(x);
		  });
	}

	private  Stream<Pojo> parse(Stream<String[]> linhas) {
		return linhas.map(x-> new Pojo(x[1],x[2],x[3]));
	}
	
	private  Stream<String[]> splitLines(Stream<String> lines){
		return lines.map(line -> line.split("\\|"));
	}

}
