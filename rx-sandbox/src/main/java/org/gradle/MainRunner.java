package org.gradle;

import rx.Observable;
import rx.Subscriber;
import rx.functions.*;

public class MainRunner {

	public static void main(String[] args) {
		MainRunner r = new MainRunner();
		r.myObservable.subscribe(r.mySubscriber);
		
		Observable<String> myObservable =   Observable.just("Hello, world!222");
		myObservable.subscribe(r.onNextAction);
		
		Observable.just("Hello, world333!")
	    .subscribe(new Action1<String>() {
	        @Override
	        public void call(String s) {
	              System.out.println(s);
	        }
	    });

		Observable.just("Hello, world4444!")
	    .subscribe(s -> System.out.println(s));
		
		
		Observable.just("Hello, world!")
	    .map(new Func1<String, String>() {
	        @Override
	        public String call(String s) {
	            return s + "rafael";
	        }
	    })
	    .subscribe(s -> System.out.println(s));
		
		Observable.just("Hello, world!")
	    .map(s -> s + " -rafael 2")
	    .subscribe(s -> System.out.println(s));
		
		
		Observable.just("Hello, world!")
	    .map(s -> s.hashCode())
	    .subscribe(i -> System.out.println(Integer.toString(i)));
		
		Observable.just("Hello, world!")
	    .map(s -> s.hashCode())
	    .map(i -> Integer.toString(i*2))
	    .subscribe(s -> System.out.println(s));
		
		Observable.just("Hello, world!")
	    .map(s -> s + " -rafael")
	    .map(s -> s.hashCode())
	    .map(i -> Integer.toString(i*3))
	    .subscribe(s -> System.out.println(s));
		
	}

	Observable<String> myObservable = Observable
			.create(new Observable.OnSubscribe<String>() {

				@Override
				public void call(Subscriber<? super String> sub) {
					sub.onNext("Hello, world!");
					sub.onCompleted();
				}
			});

	Subscriber<String> mySubscriber = new Subscriber<String>() {
		@Override
		public void onNext(String s) {
			System.out.println(s);
		}

		@Override
		public void onCompleted() {
		}

		@Override
		public void onError(Throwable e) {
		}
	};
	
	
	Action1<String> onNextAction = new Action1<String>() {
	    @Override
	    public void call(String s) {
	        System.out.println(s);
	    }
	};
	

}
