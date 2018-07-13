package org.oreframework.common.entity.page;

/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-3-23]
 */
public class PageContext extends Paginator {
	private static final long serialVersionUID = 1L;
	private static ThreadLocal<PageContext> context = new ThreadLocal<PageContext>();

	public static PageContext getContext() {
		PageContext ci = context.get();
		if (ci == null) {
			ci = new PageContext();
			context.set(ci);
		}
		return ci;
	}

	public static void removeContext() {
		context.remove();
	}

	public static void setContext(PageContext cxt) {

		context.set(cxt);
	}

	protected void initialize() {

	}

}

