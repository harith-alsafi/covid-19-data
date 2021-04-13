

import java.time.LocalDate;

public class CaseRecord {
  // fields
  private LocalDate date;
  private int staffCases;
  private int studentCases;
  private int otherCases;

  // TODO: Write stub for constructor 
  public CaseRecord(LocalDate dateday, int staff_cases, int student_cases, int other_cases)
  {
    // checking parameters
    if(staff_cases < 0 || student_cases < 0 || other_cases < 0)
    {
      throw new DatasetException("Invalid input");
    }
    // assigning everything
    staffCases =  staff_cases;
    studentCases = student_cases;
    otherCases = other_cases;
    date = dateday;
  }

  // TODO: Write stubs for four getter methods
  // local date
  public LocalDate getDate()
  {
    return date;
  }
  // staff cases
  public int getStaffCases()
  {
    return staffCases;
  }
  // studnet cases 
  public int getStudentCases()
  {
    return studentCases;
  }
  // other cases
  public int getOtherCases()
  {
    return otherCases;
  }
  // TODO: Write stub for totalCases()
  public int totalCases()
  {
    return staffCases+studentCases+otherCases;
  }
  
  // TODO: Write stub for toString()
  @Override
  public String toString()
  {
    return String.format(date.toString()+": %d staff, %d students, %d other", 
    staffCases, studentCases, otherCases);
  }
}
