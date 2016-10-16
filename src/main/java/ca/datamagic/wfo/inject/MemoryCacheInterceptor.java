/**
 * 
 */
package ca.datamagic.wfo.inject;

import java.util.Enumeration;
import java.util.Hashtable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg
 *
 */
public class MemoryCacheInterceptor implements MethodInterceptor {
	private static Logger _logger = LoggerFactory.getLogger(MemoryCacheInterceptor.class);
	private static Hashtable<String, Object> _cache = new Hashtable<String, Object>();
	
	public MemoryCacheInterceptor() {
	}

	public static synchronized void clearCache() {
		_cache.clear();
	}
	
	public static synchronized Enumeration<String> getKeys() {
		return _cache.keys();
	}
	
	private static String getKey(MethodInvocation invocation) {
		StringBuffer key = new StringBuffer();
		key.append(invocation.getMethod().getName());
		key.append("(");
		Object[] arguments = invocation.getArguments();
		if (arguments != null) {
			for (int ii = 0; ii < arguments.length; ii++) {
				if (ii > 0) {
					key.append(",");
				}
				key.append(arguments[ii].toString());
			}
		}
		key.append(")");
		return key.toString();
	}
	
	public static synchronized Object getValue(String key) {
		if (_cache.containsKey(key)) {
			return _cache.get(key);
		}
		return null;
	}
	
	public static synchronized void setValue(String key, Object newVal) {
		_cache.put(key, newVal);
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		_logger.debug("invoke");
		String key = getKey(invocation);
		Object value = getValue(key);
		if (value == null) {
			value = invocation.proceed();
			if (value != null) {
				setValue(key, value);
			}
		}
		return value;
	}
}
