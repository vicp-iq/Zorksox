package Zgame;

public class RefreshTimer extends StopWatch 
{
	  int delay;
	  
	  public RefreshTimer(int d) 
	  {
	    delay = d;
	  }
	  
	  public int getDelay()
	  {
		  return delay;
	  }
	  
	  public void changeDelay(int x)
	  {
		  delay = x;
	  }
	  
	  
	  public void reset() 
	  {
	    start();
	  }
	  
	  public boolean newActionPossible() 
	  {
	    if (getElapsedTime() > delay) 
	      return true;
	    else
	      return false;
	  }
	  
	  public static void main(String [] args) 
	  {
	    System.out.println("hello");
	  }
	}
