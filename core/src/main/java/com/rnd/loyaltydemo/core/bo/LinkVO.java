package com.rnd.loyaltydemo.core.bo;

import java.util.List;

public class LinkVO {
    private String linkName;
    private String linkUrl;
    private String linkNewWindow;
    private List<LinkVO> children;
    private boolean isCurrentPage;
    private String pageName;
    
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getLinkNewWindow() {
		return linkNewWindow;
	}
	public void setLinkNewWindow(String linkNewWindow) {
		this.linkNewWindow = linkNewWindow;
	}
	public List<LinkVO> getChildren() {
		return children;
	}
	public void setChildren(List<LinkVO> children) {
		this.children = children;
	}
	public boolean isCurrentPage() {
		return isCurrentPage;
	}
	public void setCurrentPage(boolean isCurrentPage) {
		this.isCurrentPage = isCurrentPage;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	

}
