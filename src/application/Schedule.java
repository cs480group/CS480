package application;

public class Schedule 
{
	private int category;
	private String section;
	private boolean firstTA;
	private boolean secondTA;
	private boolean has492TA;
	private Student[] TAs;
	private int classIndex;
	private boolean[][] dates;
	
	public Schedule(int category, String section, boolean[][] dates, int classIndex)
	{
		this.category = category;
		this.section = section;
		this.firstTA = false;
		this.secondTA = false;
		this.has492TA = false;
		this.TAs = new Student[2];
		this.dates = dates;
		this.classIndex = classIndex;
	}

	public int getCategory() {
		return category;
	}

	public String getSection() {
		return section;
	}

	public boolean hasFirstTA() {
		return firstTA;
	}

	public boolean hasSecondTA() {
		return secondTA;
	}
	
	public boolean getHas492TA() {
		return has492TA;
	}

	public void setTA(Student input, int roundNum) {
		if(roundNum == 1) {
			TAs[0] = input;
			this.firstTA = true;
		}else {
			TAs[1] = input;
			this.secondTA = true;
		}
		if(input.getTaType() == 492){
			this.has492TA = true;
		}
	}

	public Student[] getTAs() {
		return TAs;
	}

	public int getClassIndex() {
		return classIndex;
	}

	public boolean[][] getDates() {
		return dates;
	}
}
