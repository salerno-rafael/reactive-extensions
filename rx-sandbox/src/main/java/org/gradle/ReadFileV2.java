package org.gradle;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rx.Observable;
import rx.functions.Func1;

public class ReadFileV2 {

	public static void main(String[] args) throws Exception {
		new ReadFileV2().start();
	}
	
	public void start() throws Exception{
		
		Path path = Paths.get(ReadFileV2.class.getResource("/csvFile.txt").toURI());
		Stream<String> linhas = Files.lines(path, Charset.defaultCharset());
		
		 Observable.just(linhas)
		  .flatMap(y-> Observable.just(splitLines(y)))
		  .flatMap(z-> Observable.just(parse(z)))
		  .flatMap(new Func1<Stream<Pojo>, Observable<List<List<Pojo>>>>() {
		        @Override
		        public  Observable<List<List<Pojo>>> call(Stream<Pojo> lines) {
		         
		        	List<Pojo> allLines =  lines.collect(Collectors.toList());
		        	List<Pojo> l1 = allLines.stream().filter(x-> x.getElement1().equals("001")).collect(Collectors.toList());
		        	List<Pojo> l2 = allLines.stream().filter(x-> x.getElement1().equals("002")).collect(Collectors.toList());
		        
		        	List<List<Pojo>> lista = new ArrayList<List<Pojo>>();
		        	lista.add(l1);
		        	lista.add(l2);
		        	
		            return Observable.just(lista);
		        }
		    })
		  
		  .subscribe(x->{
			  x.forEach(lista-> {
				  System.out.println(lista);
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
