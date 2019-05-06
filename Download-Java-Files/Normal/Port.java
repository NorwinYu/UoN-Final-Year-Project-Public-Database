package org.yesworkflow.model;

import org.yesworkflow.annotations.Begin;
import org.yesworkflow.annotations.Flow;
import org.yesworkflow.annotations.UriAnnotation;
import org.yesworkflow.data.UriTemplate;

public class Port {

    public final Integer id;
    public final Begin beginAnnotation;
	public final Flow flowAnnotation;
	public final UriTemplate uriTemplate;
	public final Data data;
	
    private static Integer nextPortId;
    
    public static void resetIds() {
        nextPortId = 1;
    }

	public Port(Data data, Flow flowAnnotation, Begin beginAnnotation) {
	    
	    this.id = nextPortId++;
	    this.data = data;
		this.flowAnnotation = flowAnnotation;
		this.beginAnnotation = beginAnnotation;
		
		UriAnnotation uriAnnotation = this.flowAnnotation.uriAnnotation();
		this.uriTemplate = (uriAnnotation != null) ? new UriTemplate(uriAnnotation.value()) : null;
	}
	
	@Override
    public String toString() {
        return String.format("%s:%s", this.beginAnnotation.value(), this.flowAnnotation.binding());
    }
}
