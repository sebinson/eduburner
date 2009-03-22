package eduburner.test.mock;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.validation.AbstractBindingResult;

public class MockBindingResult  extends AbstractBindingResult{
	 
	private PropertyEditorRegistry propertyEditorRegistry;
	
	public MockBindingResult(String objectName){
		super(objectName);
		this.propertyEditorRegistry = new PropertyEditorRegistrySupport();
	}
	
	@Override
	public PropertyEditorRegistry getPropertyEditorRegistry() {
		return propertyEditorRegistry;
	}


	@Override
	protected Object getActualFieldValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
