/**
 * 
 */
package ca.datamagic.wfo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import ca.datamagic.wfo.dao.WFODAO;

/**
 * @author Greg
 *
 */
public abstract class BaseController {
	private static Logger _logger = LoggerFactory.getLogger(BaseController.class);
	
	static {
		try {
			DefaultResourceLoader loader = new DefaultResourceLoader();       
		    Resource resource = loader.getResource("classpath:data/w_10nv15/w_10nv15.shp");
		    String fileName = resource.getFile().getAbsolutePath();
		    _logger.debug("filename: " + fileName);
			WFODAO.setFileName(fileName);
		} catch (Throwable t) {
			_logger.error("Exception", t);
		}
	}

}
