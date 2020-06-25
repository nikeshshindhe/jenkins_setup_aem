package com.rnd.loyaltydemo.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.models.injectors.annotation.HierarchicalPageProperty;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.rnd.loyaltydemo.core.bo.LinkVO;
import com.rnd.loyaltydemo.core.constants.CookieConstants;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.LinksUtil;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderModel {
	
	private static final String DISPLAY_IN_NAVIGATION = "displayInNavigation";

	private static final Logger log = LoggerFactory.getLogger(HeaderModel.class);
	
	@SlingObject
	private ResourceResolver resourceResolver;
	
	private Boolean loggedIn = Boolean.FALSE;
	
	@Inject
	@Named("headerLogoRef")
	@Via("resource")
	private String headerLogo;
	
	@Inject
	@Via("resource")
	private String headerAltText;
	@Inject
	@Via("resource")
    private String showSearch;
	
	@Inject
	@Via("resource")
    private String showCart;
	
	@Inject
	@Via("resource")
    private String showLanguage;
	
	@ChildResource
	@Via("resource")
    private Resource languageList;
	
	@ChildResource
	@Via("resource")
    private Resource additionalSecNavList;
	
	@Inject
	@Via("resource")
    private String loginLabel;
	
	@Inject
	@Via("resource")
    private String registerLabel;
	
	@Inject
	@Via("resource")
    private String loginAndRegisterDivider;
	
	@Inject
	@Via("resource")
    private String showBelowUserName;
	
    @Inject
    @Via("resource")
    private String userInfo;
    
    @ChildResource
    @Via("resource")
    private Resource userNavLinks;
    
    @Inject
    @Via("resource")
    private String logoutIcon;
    
    @Inject
    @Via("resource")
    private String logoutLabel;
    
    @Inject
	@Via("resource")
    private String navigationRoot;
    @Inject
    @Via("resource")
    private boolean skipNavigationRoot;
    @Inject
    @Via("resource")
    private int structureDepth;
    
    @Self 
    SlingHttpServletRequest request;
    
	@SlingObject
	private Resource resource;
    
    @ScriptVariable
    private Page currentPage;
    
    Page mainNavRootPagepage;
    
    private List<LinkVO> mainNavList;
    
    @HierarchicalPageProperty
    private String searchPath;
    
    @HierarchicalPageProperty
    private String cartPath;
    
    @HierarchicalPageProperty
    private String signoutPath;
    
    @HierarchicalPageProperty
    private String homePagePath;
    
    @HierarchicalPageProperty
    private String loginPagePath;
    
    @HierarchicalPageProperty
    private String registerPath;
    
    @PostConstruct
	protected void init() {

		log.debug("HeaderModel :: init :: Start");

		try {
			homePagePath = LinksUtil.checkInternalURLByPath(homePagePath, resourceResolver);
			loginPagePath = LinksUtil.checkInternalURLByPath(loginPagePath, resourceResolver);
			registerPath = LinksUtil.checkInternalURLByPath(registerPath, resourceResolver);
			searchPath = LinksUtil.checkInternalURLByPath(searchPath, resourceResolver);
			cartPath = LinksUtil.checkInternalURLByPath(cartPath, resourceResolver);
			signoutPath = LinksUtil.checkInternalURLByPath(signoutPath, resourceResolver);

			if (request.getAttribute(CookieConstants.LOGGED_IN) != null) {
				loggedIn =  (Boolean)request.getAttribute(CookieConstants.LOGGED_IN);
			}
			
			if (StringUtils.isNotEmpty(navigationRoot)) {
				setmainNavItems();
			}
			
		} catch (final Exception e) {
			log.error("HeaderModel : error  :{} ", e);
		}
		log.debug("HeaderModel :: init :: End");
	}
    
    private LinkVO getPageLinkProperties(Page page)
	{
		LinkVO linkVO = null;
		ValueMap valueMap = page.getProperties();
		String displayInNavigation = valueMap.get(DISPLAY_IN_NAVIGATION, String.class);
		if (displayInNavigation != null  && (displayInNavigation.equals("2") || (loggedIn && displayInNavigation.equals("3"))
				|| (!loggedIn && displayInNavigation.equals("4")))) {
			linkVO = new LinkVO();
			linkVO.setLinkUrl(LinksUtil.checkInternalURLByPage(page, request));
			linkVO.setLinkName(LinksUtil.getTitle(page));
			linkVO.setCurrentPage(getIsActivePage(page));
			linkVO.setPageName(page.getName());
		}
		return linkVO;
	}
    
    private boolean getIsActivePage(Page page) {
    	Boolean active = Boolean.FALSE;
    	if (currentPage.getPath().equals(page.getPath())){
			active =  Boolean.TRUE;
		}
    	return active;
    }
    
	private List<LinkVO> setmainNavItems() {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if(null == pageManager)
		{
			return Collections.emptyList();
		}
		mainNavRootPagepage = pageManager.getPage(navigationRoot);
		if(null == mainNavRootPagepage)
		{
			return Collections.emptyList();
		}
		int depth = -1;
		if(structureDepth != 0) {
			depth = structureDepth;		
		}
		
		mainNavList = getNavItems(depth, mainNavRootPagepage, loggedIn);
		return mainNavList;

	}
	private List<LinkVO> getNavItems(int depth, Page page, boolean loggedIn) {
        List<LinkVO> pages = new ArrayList<>();
        if(!skipNavigationRoot && getLevel(mainNavRootPagepage) == getLevel(page)) {
        	LinkVO parentLink = getPageLinkProperties(page);
        	if(null != parentLink) {
        		pages.add(parentLink);
            }
        }
        if (depth == -1 || getLevel(page) <= depth) {
            Iterator<Page> it = page.listChildren();
            while (it.hasNext()) {
                Page childPage = it.next();
                List<LinkVO> children = getNavItems(depth, childPage, loggedIn);
                LinkVO childPageLink = getPageLinkProperties(childPage);
                if(null != childPageLink) {
	                childPageLink.setChildren(children);
	                pages.add(childPageLink);
                }
            }
        }
        return pages;
    }
	
	private int getLevel(Page page) {
        return StringUtils.countMatches(page.getPath(), "/") - 1;
    }

	public String getHeaderLogo() {
		return headerLogo;
	}

	public String getHeaderAltText() {
		return headerAltText;
	}
	
	public String getShowSearch() {
		return showSearch;
	}

	public String getShowCart() {
		return showCart;
	}

	public String getShowLanguage() {
		return showLanguage;
	}
	
	public List<IconModel> getLanguageList() {
		return CommonUtil.getCTAandIconItems(languageList);
	}
	
	public List<IconModel> getAdditionalSecNavList() {
		return CommonUtil.getCTAandIconItems(additionalSecNavList);
	}
	
	public String getLoginLabel() {
		return loginLabel;
	}

	public String getLoginLink() {
		return loginPagePath;
	}

	public String getRegisterLabel() {
		return registerLabel;
	}

	public String getRegisterLink() {
		return registerPath;
	}

	public String getLoginAndRegisterDivider() {
		return loginAndRegisterDivider;
	}

	public String getShowBelowUserName() {
		return showBelowUserName;
	}

	public String getUserInfo() {
		return userInfo;
	}
	
	public List<IconModel> getUserNavLinks() {
		return CommonUtil.getCTAandIconItems(userNavLinks);
	}

	public String getLogoutIcon() {
		return logoutIcon;
	}

	public String getLogoutLabel() {
		return logoutLabel;
	}
	
	public List<LinkVO> getMainNavigationList() {
		return mainNavList;
	}

	public String getSearchPath() {
		return searchPath;
	}

	public String getCartPath() {
		return cartPath;
	}

	public String getSignoutPath() {
		return signoutPath;
	}
	
	public String getHomePagePath() {
		return homePagePath;
	}
}
