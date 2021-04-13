

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class CovidDataset {
  // fields
  private List<CaseRecord> caserecords;
  // constructor 
  public CovidDataset()
  {
    caserecords = new ArrayList<>();
  }
  // TODO: Write stub for size()
  public int size()
  {
    return caserecords.size();
  }

  // TODO: Write stub for getRecord()
  public CaseRecord getRecord(int index)
  {
    // checking index
    if(index < 0 || index >= caserecords.size())
    {
      throw new DatasetException("Invalid index");
    }
    return caserecords.get(index);
  }

  // TODO: Write stub for addRecord()
  public void addRecord(CaseRecord rec)
  {
    caserecords.add(rec);
  }

  // TODO: Write stub for dailyCasesOn()
  public CaseRecord dailyCasesOn(LocalDate day)
  {
    // caserecord null object
    CaseRecord dailycase = null;
    // looping through all caserecords
    for(CaseRecord i: caserecords)
    {
      // checking the date
      if(i.getDate() == day)
      {
        dailycase = i;
      }
    }
    // if nothing was found 
    if (dailycase == null)
    {
      throw new DatasetException("Record on givin day not found");
    } 
    return dailycase;
  }

  // TODO: Write stub for readDailyCases()
  public void readDailyCases(String filename) throws FileNotFoundException, IOException
  {
    // clearing record
    caserecords.clear();
    // scanner object
    Scanner input = new Scanner(new File(filename));

    // integer read from string 
    List<Integer> dateint = new ArrayList<>();
    List<Integer> casesint = new ArrayList<>();

    // string reads
    String lines = null;
    List<String[]> dates = new ArrayList<>();
    List<String[]> cases = new ArrayList<>();

    // header
    String header = input.next();
    // reading as string
    int count = 0;
    while (input.hasNext())
    {
        lines = input.next();
        // reading year, month
        dates.add(lines.split("-"));
        // reading day, staff, student, other
        cases.add(dates.get(count)[2].split(","));
        count++;
    }
    // closing input 
    input.close();
    // converting to int
    for(int i = 0; i < dates.size(); i++)
    {
      // reading year, month as int
      for(int j = 0; j < 2; j++)
        {
          dateint.add(Integer.parseInt(dates.get(i)[j]));
        }
        // adding first element of dataint (since its day)
        dateint.add(Integer.parseInt(cases.get(i)[0]));
        // reading staff, student, other (starting from 1, cause 0 is day)
        for(int k = 1; k < cases.get(i).length; k++)
        {
          casesint.add(Integer.parseInt(cases.get(i)[k]));
        }
    }
    // if one colum is less it wont be divisable by 3 (we have 3 colums per row)
    if (casesint.size()%3 != 0 || dateint.size()%3 != 0)
    {
      throw new DatasetException("Missing colum ");
    }
    // variables and counters
    int count2 = 0; // for dateint
    int count3 = 0; // for caseint
    int year = 0;
    int month = 0; 
    int day = 0;
    int student = 0;
    int staff = 0;
    int other = 0;
    LocalDate date;
    // reading from list of dates and cases
    while (count2 < casesint.size())
    {
      // iterate through date integers
      year = dateint.get(count2);
      count2++;
      month = dateint.get(count2);
      count2++;
      day = dateint.get(count2);
      count2++;
      date = LocalDate.of(year, month, day);

      // iterates through case integers
      staff = casesint.get(count3);
      count3++;
      student = casesint.get(count3);
      count3++;
      other = casesint.get(count3);
      count3++;

      // reads the data
      caserecords.add(new CaseRecord(date, staff, student, other));
    }
  }

  // TODO: Write stub for writeActiveCases()
  public void writeActiveCases(String filename) throws IOException
  {
    // condition
    if (caserecords.size() < 10)
    {
      throw new DatasetException("Too small database");
    }
    // file 
    Path path = Paths.get(filename);
    PrintWriter out = new PrintWriter(Files.newBufferedWriter(path));
    // initial headers 
    out.println("Date,Staff,Students,Other");
    // sum of active cases
    List<CaseRecord> activacases = new ArrayList<>();
    int sumstudent = 0;
    int sumstaff = 0;
    int sumother = 0;

    for(int i = 0; i < caserecords.size(); i++)
    {
      // adding 
      sumstaff += caserecords.get(i).getStaffCases();
      sumstudent += caserecords.get(i).getStudentCases();
      sumother += caserecords.get(i).getOtherCases();
      // active cases gets removed after 10 days
      if(i >= 10)
      {
        sumstaff -= caserecords.get(i-10).getStaffCases();
        sumstudent -= caserecords.get(i-10).getStudentCases();
        sumother -= caserecords.get(i-10).getOtherCases();
      }
      // active cases begin after 10 days
      if(i >= 9)
      {
        activacases.add(new CaseRecord(caserecords.get(i).getDate(), sumstaff, sumstudent, 
        sumother));
      }

    }
    // printing active cases
    for(int i = 0; i < activacases.size(); i++)
    {
      // printing 
      out.printf(activacases.get(i).getDate()+",%d,%d,%d", 
      activacases.get(i).getStaffCases(), activacases.get(i).getStudentCases(), 
      activacases.get(i).getOtherCases());
      // do we dont print extra line at the end of the file 
      if (i != activacases.size()-1)
      {
        out.printf("\n");
      }
    }
    // closing 
    out.close();
  }
}
