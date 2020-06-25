package com.rnd.loyaltydemo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author nikeshshindhe
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PanelListModel extends CTAModel {

    /**
     * The label
     */
    @Inject
    private String label;
    
    /**
     * The RTE Text
     */
    @Inject
    private String text;

    /**
     * The panelList
     */
    @Inject
    @Named("panelList")
    private List<PanelListModel> panelList;

    /**
     * @return label
     */
    public String getLabel() { return label; }
    
    /**
     * @return text
     */
	public String getText() {
		return text;
	}
	
	/**
	 * @return panelList
	 */
	public List<PanelListModel> getPanelList() {
		return panelList;
	}

}
