/**
 * 
 */
package ca.datamagic.wfo.dto;

import java.util.List;

/**
 * @author Greg
 *
 */
public class CachedItemDTO {
	private String _key = null;
	private WFODTO _wfo = null;
	private List<WFODTO> _list = null;
	
	public CachedItemDTO() {
	}

	public CachedItemDTO(String key, WFODTO wfo) {
		_key = key;
		_wfo = wfo;
	}
	
	public CachedItemDTO(String key, List<WFODTO> list) {
		_key = key;
		_list = list;
	}
	
	public String getKey() {
		return _key;
	}
	
	public void setKey(String newVal) {
		_key = newVal;
	}
	
	public WFODTO getWFO() {
		return _wfo;
	}
	
	public void setWFO(WFODTO newVal) {
		_wfo = newVal;
	}
	
	public List<WFODTO> getList() {
		return _list;
	}
	
	public void setList(List<WFODTO> newVal) {
		_list = newVal;
	}
}
