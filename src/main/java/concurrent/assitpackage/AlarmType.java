

package concurrent.assitpackage;

public enum AlarmType {
	FAULT("fault"),
	RESUME("resume");
	
	private final String name;
	private AlarmType(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		
		return name;
	}
}
