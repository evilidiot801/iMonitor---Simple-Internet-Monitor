package application;

import java.io.IOException;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;


public class PingTimer extends ScheduledService<Integer> {
	private int runtime = 0;
	
	public Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override
			public Integer call() throws IOException {
				return runtime++;
			}
		};
	}
}
