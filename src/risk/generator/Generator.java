package risk.generator;

import java.util.Random;

public class Generator {
	
	private static int []test=new int[0];
	private static int i;
	private static Random generator=new Random();

	/**
	 * Defines pre-determined numbers to be generated
	 * @param ttest
	 */
	public static void setTest(int [] ttest) {
		test=ttest;
		i=0;
	}

	/**
	 * Similar to java's Random.nextInt
	 * @param limit
	 * @return A number, random or not, depending if test was set.
	 */
	public static int nextInt(int limit) {
		if(test.length==0)
			return generator.nextInt(limit);

		i++;

		if(i>test.length)
			return generator.nextInt(limit);

		return test[i-1];
	}
}
