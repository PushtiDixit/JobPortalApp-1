package com.example.android.jobportal1;

public class Job {
  private String title,skills,contact,company;
  public Job()
  {

  }
  public Job(String title,String skills,String contact,String company)
  {
      this.title=title;
      this.skills=skills;
      this.contact=contact;
      this.company=company;
  }
  public String getTitle()
  {
      return title;
  }

    public String getSkills() {
        return skills;
    }

    public String getContact() {
        return contact;
    }
    public String getCompany() {
        return company;
    }


}
