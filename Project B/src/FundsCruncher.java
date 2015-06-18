import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;


public class FundsCruncher {
	public double FindValueWithinTimePeriod(double value,String interval){
		double result = 0.0;
		Calendar mycal = new GregorianCalendar();
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int weeks = mycal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		System.out.println("weeks: "+weeks);
		switch (interval){
		case "Daily":
			result = value*daysInMonth;
			break;
		case "Weekly":
			result = value*weeks;
			break;
		case "Bi-Weekly":
			result = value*Math.floor(weeks/2);
			break;
		case "Monthly":
			result = value;
			break;
			default:
				JOptionPane.showMessageDialog(null,
					    "You have an entry with a \"Frequency\" value ("+interval+") out of the range of the specified time period. (Monthly)\nFor this reason, it will not be considered in your balance.",
					    "Something will not be considered.",
					    JOptionPane.WARNING_MESSAGE);
		}
		return result;
	}
}
