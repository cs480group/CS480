public class Schedule 
{
	private int category;
	private String section;
	private String days;
	private String sTime;
	private String eTime;
	
	public Schedule(int category, String section, String days, String sTime, String eTime)
	{
		this.category = category;
		this.section = section;
		this.days = days;
		this.sTime = sTime;
		this.eTime = eTime;
	}

	public int getCategory() 
	{
		return category;
	}

	public void setCategory(int category) 
	{
		this.category = category;
	}

	public String getSection() 
	{
		return section;
	}

	public void setSection(String section) 
	{
		this.section = section;
	}

	public String getDays() 
	{
		return days;
	}

	public void setDays(String days) 
	{
		this.days = days;
	}

	public String getsTime() 
	{
		return sTime;
	}

	public void setsTime(String sTime) 
	{
		this.sTime = sTime;
	}

	public String geteTime() 
	{
		return eTime;
	}

	public void seteTime(String eTime) 
	{
		this.eTime = eTime;
	}
}
