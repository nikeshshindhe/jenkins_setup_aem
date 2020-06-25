  use(function () {
	var isDAMPath = org.apache.commons.lang3.StringUtils.contains(this.ctaLink, "/content/dam/");
	var isInternal = org.apache.commons.lang3.StringUtils.startsWith(this.ctaLink, "/content/");
	var isValidInternalPath = org.apache.commons.lang3.StringUtils.endsWith(this.ctaLink, ".html");
    var isModalLink = (org.apache.commons.lang3.StringUtils.startsWith(this.ctaLink, "#") && 
                      org.apache.commons.lang3.StringUtils.length(this.ctaLink) > 1);

	var convertedPath = '';
	if((!isDAMPath) && isInternal && !isValidInternalPath) {
		convertedPath = this.ctaLink + '.html';
	} else {
		convertedPath = this.ctaLink;
	}
	
    return {
		convertedPath: convertedPath,
        isModalLink: isModalLink		
    };
});