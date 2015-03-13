package addrGen;

import addrGen.KristAPI;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.ArrayUtils;
import java.util.ArrayList;
import java.util.List;

public class AddressGenerator {
	static final Object hDone = new Object();
	static final Object sLock = new Object();
	static long hashesDone = 0L;
	static boolean search = true;
	static long startTime = System.currentTimeMillis();
	static class DisplayThread extends Thread {
		private String tName;
		public DisplayThread(String tName) {
			this.tName = tName;
		}
		public void run() {
			boolean contDisplay;
			contDisplay = true;
			do {
				try {
					Thread.sleep(5000);
				}catch(InterruptedException ie) {
					//Stuff
				}
				System.out.println(String.valueOf(getHPS() / 1000.0D) + "KH/s");
				contDisplay = getSearch();
			} while (contDisplay);
		}
	}
	static class MineThread extends Thread {
		private String tName;
		private List<String> args;
		public MineThread(String tName, List<String> args) {
			this.tName = tName;
			this.args = args;
		}
		
		public void run() {
			System.out.println(tName + " Started");
			String thash;
			String curAddr;
			List<String> toFind = this.args;
			String rchars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";
			boolean search = true;
			do
			{
				int i = 0;
				for (; i < 20000;)
				{
					thash = RandomStringUtils.random(16, rchars);
					curAddr = KristAPI.makeAddressV2(thash);
					
					if (checkArray(toFind, curAddr)) {
						System.out.println(tName + ": Found " + curAddr + " From " + thash);
						System.out.println(tName + " Stopping.");
						i = 49999;
						setSearch(false);
					}
					i++;
				}
				
				addHDone(i);
				search = getSearch();
			} while (search);
		}
	}
	public static void main(String[] args) {
		int numConvert = 0;
		int numThreads = Integer.parseInt(args[0]);
		List<String> tArgs = new ArrayList<String>();
		for (String findWord : args) {
			if (numConvert > 0) {
				tArgs.add(findWord);
			}
			numConvert++;
		}
		System.out.println("Test " + args[0]);
		List<MineThread> mineThreads = new ArrayList<MineThread>();
	    for (int i = 0; i < numThreads; i++)
	    {
	    	mineThreads.add(new MineThread("Thread" + String.valueOf(i),tArgs));
	    	mineThreads.get(i).start();
	    }
	    DisplayThread displayThread = new DisplayThread("Display");
	    displayThread.start();
	}
	public static double getHPS()
	  {
	    synchronized (hDone)
	    {
	      return hashesDone / (System.currentTimeMillis() - startTime) * 1000.0D;
	    }
	  }
	public static void addHDone(int amt)
	{
	    synchronized (hDone)
	    {
	      hashesDone += amt;
	    }
	}
	public static boolean getSearch()
	{
		synchronized (sLock)
		{
			return search;
		}
	}
	public static void setSearch(boolean toSet)
	{
		synchronized (sLock)
		{
			search = toSet;
		}
	}
	public static boolean checkArray(List<String> List, String check) {
		for (String o : List) {
			if (check.contains(o)) {
				return true;
			}
		}
		return false;
	}
}
