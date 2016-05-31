package org.gradle;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rx.Observable;

public class ReadFileV2 {

	public static void main(String[] args) throws Exception {
		new ReadFileV2().start();
	}
	
	public void start() throws Exception{
		
		Path path = Paths.get(ReadFileV2.class.getResource("/csvFile.txt").toURI());
		Stream<String> linhas = Files.lines(path, Charset.defaultCharset());
		
		  Observable.just(linhas)
		  .flatMap(y-> Observable.just(splitLines(y)))
		  .flatMap(z->Observable.just(parse(z)))
		  .subscribe(x->{
			  System.out.println(x.collect(Collectors.toList()));
		  });
	}
	

	private  Stream<Pojo> parse(Stream<String[]> linhas) {
		return linhas.map(x-> new Pojo(x[1],x[2],x[3]));
	}
	
	private  Stream<String[]> splitLines(Stream<String> lines){
		return lines.map(line -> line.split("\\|"));
	}

}
