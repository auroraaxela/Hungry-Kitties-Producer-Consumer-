/**
 *
 * @author: Chris Benda
 */

class Producer extends Thread
{
	private Resource item;

	public Producer(Resource boxIn)
	{
            item = boxIn;
	}

	public void run()
	{
		int pause;
		//int newLevel;

		do
		{
			try
			{
				//Add 1 to level and return new level...
				//newLevel = item.addOne();
				//System.out.println(
				//	 "<Producer> New level: " + newLevel);
                                item.addOne();
                                System.out.println("Cheezburgerz available: "+ item.getLevel());
				pause = (int)(Math.random() * 50000);
				//'Sleep' for 0-5 seconds...
				sleep(pause);
			}
			catch (InterruptedException e)
			{
				System.out.println(e.toString());
			}
		}while (true);  //end of do()
	}//end of run
}//end of class
