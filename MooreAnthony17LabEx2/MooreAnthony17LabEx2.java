// MooreAnthony17LabEx2 - Your Name here
// Classes taught and pay earned by gym instructors

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class MooreAnthony17LabEx2
{
    public static void main(String[] args)throws FileNotFoundException
    {
		// Constants - File objects - Record Layout - Other Variables
		Scanner inInstructorFile = new Scanner(new FileReader("InstructorDetails.dat"));
		PrintWriter outInstructorRep = new PrintWriter("InstructorReport.dat");
		PrintWriter outPlacementFile = new PrintWriter("PlacementReport.dat");
		//PrintWriter outEarlyBirdFile = new PrintWriter("EarlyBirdDiscount.dat");

		final double BASE_RATE = 22.00;
		final double EXTRA_RATE = 25.00;
		final double SALARY_RATE = 600.00;
		final int MAX_CLASS_WEEKS = 5;
		final int MAX_LAST_NAME = 8;
		final int MAX_FIRST_NAME = 8;
		final String DEFAULT_STATUS = "Placement";

		// Initialise variables
		int classInstructorID = 0;
		char classGymCode;
		String classLastName;
		String classFirstName;
		char classInstructorPay = ' ';
		int classNumbers = 0;

		String fullName = "";
		String gymName = "";
		String payType = "";
		int classTotal = 0;
		double classPay = 0.00;

		int i;
		int placementCounter = 0;
		int instructorCounter = 0;
		int sCounter = 0;
		int rCounter = 0;
		int mCounter = 0;
		int bCounter = 0;
		int aCounter = 0;
		int classCounter = 0;
		int salaryCounter = 0;
		int plus1K = 0;

		int highClass = 0;
		int lowClass = 0;
		String highInstructor = "";
		String lowInstructor = "";
		String highGym = "";
		String lowGym = "";
		String restOfLine;

		// Screen / Report Headers
		System.out.println("\n ID  Gym      Instructor Name    Pay Code       wk1   wk2   wk3   wk4   wk5      Total   Pay");
		System.out.println("==============================================================================================");
		outInstructorRep.println("\n ID  Gym      Instructor Name    Pay Code       wk1   wk2   wk3   wk4   wk5      Total   Pay");
		outInstructorRep.println("==============================================================================================");

		//header for placement report file
		outPlacementFile.println("                     ID  Gym   Instructor   Pay Code");
		outPlacementFile.println("========================================================");


		// while file Input until EOF
		while (inInstructorFile.hasNext())
		{

			classInstructorID = inInstructorFile.nextInt();
			classGymCode = inInstructorFile.next().charAt(0);
			classLastName = inInstructorFile.next();
			classFirstName = inInstructorFile.next();
			classInstructorPay = inInstructorFile.next().charAt(0);

			//calculate full name
			fullName = classLastName + ", " + classFirstName;


			//calculations before inner for loop
			if (classGymCode == 'P' || classGymCode == 'p')
			{
				placementCounter++;
				restOfLine = inInstructorFile.nextLine(); //input dump string
				//output placement report
				outPlacementFile.println("Placement Instructor: " + classInstructorID + "  " +  classGymCode + " " + fullName + "   " + classInstructorPay);
			}
			else
			{
				instructorCounter++;
				//calculate gym Name
				switch(classGymCode)
				{
					case 's':
					case 'S':
						gymName = "Salthill";
						sCounter++;
						break;
					case 'r':
					case 'R':
						gymName = "Renmore";
						rCounter++;
						break;
					case 'm':
					case 'M':
						gymName = "Merlin";
						mCounter++;
						break;
					case 'b':
					case 'B':
						gymName = "Barna";
						bCounter++;
						break;
					case 'a':
					case 'A':
						gymName = "Athenry";
						aCounter++;
						break;
				}//switch

				//calculate pay Type
				if (classInstructorPay == 's' || classInstructorPay == 'S')
				{
					payType = "Salary";
					salaryCounter++;
				}
				else if (classInstructorPay == 'c' || classInstructorPay == 'C')
				{
					payType = "By Class";
					classCounter++;
				}
				else
				{
					payType = "Code Invalid";
				}//if

				// Output Screen / Report Footers
				System.out.printf(" %3d %-8s %-18s %-10s ", classInstructorID, gymName, fullName, payType);
				outInstructorRep.printf(" %3d %-8s %-18s %-10s ", classInstructorID, gymName, fullName, payType);

				classTotal = 0; //reset counter
				// inner loop for the 5 week inputs
				for (i = 0; i < MAX_CLASS_WEEKS; i++)
				{
					classNumbers = inInstructorFile.nextInt();
					classTotal += classNumbers;
					System.out.printf("%5d ", classNumbers);
					outInstructorRep.printf("%5d ", classNumbers);
					//calculate highest weekly classes
					if(highClass < classNumbers)
					{
						highClass = classNumbers;
						highInstructor = classFirstName + " " + classLastName;
						highGym = gymName;
					}
				}
				//output class totals
				System.out.printf("%9d", classTotal);
				outInstructorRep.printf("%9d", classTotal);

				//calculate high/low class numbers
				if(lowClass <= 0)
				{
					lowClass = classTotal;
					lowInstructor = classFirstName + " " + classLastName;
					lowGym = gymName;
				}
				else if(lowClass > classTotal)
				{
					lowClass = classTotal;
					lowInstructor = classFirstName + " " + classLastName;
					lowGym = gymName;
				}

				//calculate Pay
				if(classTotal > 20)
				{
					classPay = ((classTotal - 20) * EXTRA_RATE ) + (20 * BASE_RATE);
					// test if on salary
					if(classInstructorPay == 's' || classInstructorPay == 'S')
					{
						classPay = ((classTotal - 20) * EXTRA_RATE ) + (20 * BASE_RATE) + SALARY_RATE;
					}
				}
				else
				{
					classPay = (classTotal * BASE_RATE);
					// test if on salary
					if(classInstructorPay == 's' || classInstructorPay == 'S')
					{
						classPay = (classTotal * BASE_RATE) + SALARY_RATE;
					}
				}

				//counter for paid over $1000.00
				if (classPay > 1000.00)
				{
					plus1K++;
				}
				//output class totals
				System.out.printf("%9.2f\n", classPay);
				outInstructorRep.printf("%9.2f\n", classPay);

			}// big else

		}//while

		//output summary details
		System.out.println("\n Instructor and Class counts \n");
		System.out.printf(" %-18s %4d %-18s %4d \n", "Placement Count: ", placementCounter, "Instructor Count", instructorCounter);
		System.out.printf(" %s %-10s %d %-10s %d %-10s %d %-10s %d %-10s %d\n", "Gyms: ", "Salthill", sCounter, "Renmore", rCounter, "Merlin", mCounter, "Barna", bCounter, "Athenry", aCounter);
		System.out.printf(" %s %d %s %d \n", "Instructors Paid by Class: ", classCounter, " Instructors Paid by Base Salary: ", salaryCounter);
		//output high/low details
		System.out.println("\n " + highInstructor + " from the " + highGym + " taught the most weekly classes over the past 5 weeks of " + highClass);
		System.out.println(" " + lowGym + "'s " + lowInstructor + " had the lowest total number of classes taught in 5 weeks with only " + lowClass + " classes being taught");
		//output Instructors over 1000
		System.out.println();
		System.out.println(" " + plus1K + " Instructors got paid more than 1000.00 euros over the 5 weeks");

		//output summary Report file
		outInstructorRep.println("\n Instructor and Class counts \n");
		outInstructorRep.printf(" %-18s %4d %-18s %4d \n", "Placement Count: ", placementCounter, "Instructor Count", instructorCounter);
		outInstructorRep.printf(" %s %-10s %d %-10s %d %-10s %d %-10s %d %-10s %d\n", "Gyms: ", "Salthill", sCounter, "Renmore", rCounter, "Merlin", mCounter, "Barna", bCounter, "Athenry", aCounter);
		outInstructorRep.printf(" %s %d %s %d \n", "Instructors Paid by Class: ", classCounter, " Instructors Paid by Base Salary: ", salaryCounter);
		//output high/low details
		outInstructorRep.println("\n " + highInstructor + " from the " + highGym + " taught the most weekly classes over the past 5 weeks of " + highClass);
		outInstructorRep.println(" " + lowGym + "'s " + lowInstructor + " had the lowest total number of classes taught in 5 weeks with only " + lowClass + " classes being taught");
		//output Instructors over 1000
		outInstructorRep.println();
		outInstructorRep.println(" " + plus1K + " Instructors got paid more than 1000.00 euros over the 5 weeks");

		// Close Files
		outInstructorRep.close();
		inInstructorFile.close();
		outPlacementFile.close();

    }  // main

} // class