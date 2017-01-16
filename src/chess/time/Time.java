package chess.time;

import java.util.Timer;
import java.util.TimerTask;

public class Time{
	
	private int delay;
	private int period;
	private Timer timer;
	private int interval;
	
	private String result;
	private final String resultFormat = "%1$02d:%2$02d";
	
	private enum State{
		RUNNING, PAUSED, STOPPED;
	}
	
	private State state;
	
	public Time(int time, int delay, int period){
		this.delay = delay;
		this.period = period;
		this.interval = time;
		this.result = String.format(resultFormat, 
				TimeUtils.IntegerToMinutes(interval), 
				TimeUtils.IntegerToSeconds(interval));
		this.state = State.RUNNING;
	}
	
	public Time(int time){
		this.delay = 1000;
		this.period = 1000;
		this.interval = time;
		this.result = String.format(resultFormat, 
				TimeUtils.IntegerToMinutes(interval), 
				TimeUtils.IntegerToSeconds(interval));
		this.state = State.RUNNING;
	}
	
	public void start(){
		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				changeState();
			}
		}, this.delay, this.period);
	}
	
	private void changeState(){
		if(state == State.PAUSED) {
			this.timer.cancel();
		} else {
			if(this.interval != 0){
				int temp = setInterval();
				this.result = String.format(resultFormat, 
								TimeUtils.IntegerToMinutes(temp), 
								TimeUtils.IntegerToSeconds(temp));
			}
		}
	}
	
	private int setInterval(){
		if(interval == 1){
			timer.cancel();
		} 
		return --interval;
	}
	
	public void stop(){
		this.timer.cancel();
	}
	
	public void resume(){
		if(this.state == State.PAUSED){
			this.state = State.RUNNING;
			this.timer.cancel();
			start();
		}
	}
	
	public void pause(){
		if(this.state == State.RUNNING)
			this.state = State.PAUSED;
	}
	
	public void reset(){
		this.timer.cancel();
		start();
	}
	
	public String getTime(){
		return this.result;
	}
	
	public int getInterval(){
		return this.interval;
	}
	
	@Override
	public String toString(){
		return "Timer";
	}
}
