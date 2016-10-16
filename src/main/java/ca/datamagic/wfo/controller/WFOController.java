/**
 * 
 */
package ca.datamagic.wfo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.wfo.dao.WFODAO;
import ca.datamagic.wfo.dto.CachedItemDTO;
import ca.datamagic.wfo.dto.WFODTO;
import ca.datamagic.wfo.inject.DAOModule;
import ca.datamagic.wfo.inject.MemoryCacheInterceptor;

/**
 * @author Greg
 *
 */
@RestController
@RequestMapping("/api")
public class WFOController extends BaseController {
	private static Logger _logger = LoggerFactory.getLogger(WFOController.class);
	private WFODAO _dao = null;
	
	public WFOController() throws IOException {
		Injector injector = Guice.createInjector(new DAOModule());
		_dao = injector.getInstance(WFODAO.class);
	}

	@RequestMapping(method=RequestMethod.GET, value="/")
    public List<WFODTO> list() throws Exception {
		try {
			return _dao.list();
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
    public WFODTO readById(@PathVariable String id) throws Exception {
		try {
			return _dao.read(id);
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{latitude}/{longitude}/coordinates")
    public List<WFODTO> readByLocation(@PathVariable String latitude, @PathVariable String longitude) throws Exception {
		try {
			return _dao.read(Double.parseDouble(latitude), Double.parseDouble(longitude));
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
	}
		
	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET, value="/cache")
	public List<CachedItemDTO> getCachedItems() {
		List<CachedItemDTO> items = new ArrayList<CachedItemDTO>();
		Enumeration<String> keys = MemoryCacheInterceptor.getKeys();
		if (keys != null) {
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				Object value = MemoryCacheInterceptor.getValue(key);
				if (value instanceof WFODTO) {
					items.add(new CachedItemDTO(key, (WFODTO)value));
				} else if (value instanceof List<?>) {
					try {
						items.add(new CachedItemDTO(key, (List<WFODTO>)value));
					} catch (Throwable t) {
						_logger.warn("Exception", t);
					}
				}
			}
		}
		return items;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/cache")
	public void clearCachedItems() {
		MemoryCacheInterceptor.clearCache();
	}
}
