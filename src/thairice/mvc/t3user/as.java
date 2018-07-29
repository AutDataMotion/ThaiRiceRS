<<<<<<< HEAD
package thairice.mvc.t3user;

public class as {

	public static void main(String[] args) {
		// long a=System.currentTimeMillis() + (3600 * 1000);;
		// System.out.println(new Random().nextInt(1589745));
		/*System.out.println(
				TimeUtil.dateDiff("2018-04-13 00:00:00", "2018-05-13 00:00:00", "yyyy-MM-dd HH:mm:ss", "d"));*/
		// System.out.println(TimeUtil.getHour());
		// test by zw
                      //System.out.println(TimeUtil.isLaterThanNow("2018-04-16 00:00:00"));
	    long day = TimeUtil.dateDiff(TimeUtil.getNow(),"2018-08-07 00:00:00", "yyyy-MM-dd HH:mm:ss", "d");
		if(day<0) {
		    System.out.println("g");
		}else {
		    System.out.println("y");
		}
	    
	}

	// --test
	
	// test 2 by zw
	// test3 by zw

}
=======
package thairice.mvc.t3user;

public class as {

	public static void main(String[] args) {
		// long a=System.currentTimeMillis() + (3600 * 1000);;
		// System.out.println(new Random().nextInt(1589745));
		/*System.out.println(
				TimeUtil.dateDiff("2018-04-13 00:00:00", "2018-05-13 00:00:00", "yyyy-MM-dd HH:mm:ss", "d"));*/
		// System.out.println(TimeUtil.getHour());
		// test by zw
                      //System.out.println(TimeUtil.isLaterThanNow("2018-04-16 00:00:00"));
	    long day = TimeUtil.dateDiff(TimeUtil.getNow(),"2018-06-07 00:00:00", "yyyy-MM-dd HH:mm:ss", "d");
		System.out.println(day);
	}

	// --test
	
	// test 2 by zw
	// test3 by zw

}
>>>>>>> master
