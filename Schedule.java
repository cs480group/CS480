public class Schedule 
{
	private int category;
	private String section;
	private boolean firstTA;
	private boolean secondTA;
	private String[] TAs;
	private int classIndex;
	private boolean dates[][];
	
	public Schedule(int category, String section, boolean[][] dates, int classIndex)
	{
		this.category = category;
		this.section = section;
		this.firstTA = false;
		this.secondTA = false;
		this.TAs = new String[2];
		this.dates = dates;
		this.classIndex = classIndex;
	}

	public int getCategory() {
		return category;
	}

	public String getSection() {
		return section;
	}

	public boolean getFirstTA() {
		return firstTA;
	}

	public void setFirstTA(boolean input) {
		this.firstTA = input;
	}

	public boolean getSecondTA() {
		return secondTA;
	}

	public void setSecondTA(boolean input) {
		this.secondTA = input;
	}
	
	public void setTAName(String name, int roundNum) {
		if(roundNum == 1) {
			TAs[0] = name;
		}else {
			TAs[1] = name;
		}
	}

	public String[] getTANames() {
		return TAs;
	}

	public int getClassIndex() {
		return classIndex;
	}

	public boolean[][] getDates() {
		return dates;
	}
}
