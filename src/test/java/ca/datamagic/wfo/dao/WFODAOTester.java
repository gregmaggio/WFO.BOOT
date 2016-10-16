/**
 * 
 */
package ca.datamagic.wfo.dao;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.datamagic.wfo.dto.WFODTO;

/**
 * @author Greg
 *
 */
public class WFODAOTester {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WFODAO.setFileName((new File("src/main/resources/data/w_10nv15/w_10nv15.shp")).getAbsolutePath());
	}

	@Test
	public void test1() throws Exception {
		WFODAO dao = new WFODAO();
		List<WFODTO> items = dao.list();
		for (WFODTO item : items) {
			System.out.println("WFO: " + item.getWFO());
		}
	}
	
	@Test
	public void test2() throws Exception {
		WFODAO dao = new WFODAO();
		WFODTO item = dao.read("LWX");
		System.out.println("WFO: " + item.getWFO());
	}
	
	@Test
	public void test3() throws Exception {
		WFODAO dao = new WFODAO();
		double latitude = 38.9967;
	    double longitude = -76.9275;	
		List<WFODTO> items = dao.read(latitude, longitude);
		for (WFODTO item : items) {
			System.out.println("WFO: " + item.getWFO());
		}
	}
}
