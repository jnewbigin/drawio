/**
 * Copyright (c) 2006-2017, JGraph Ltd
 * Copyright (c) 2006-2017, Gaudenz Alder
 */
GitHubEnterpriseLibrary = function(ui, data, meta)
{
	GitHubEnterpriseFile.call(this, ui, data, meta);
};

//Extends mxEventSource
mxUtils.extend(GitHubEnterpriseLibrary, GitHubEnterpriseFile);

/**
 * Overridden to avoid updating data with current file.
 */
GitHubEnterpriseLibrary.prototype.doSave = function(title, success, error)
{
	this.saveFile(title, false, success, error);
};

/**
 * Returns the location as a new object.
 * @type mx.Point
 */
GitHubEnterpriseLibrary.prototype.open = function()
{
	// Do nothing - this should never be called
};
