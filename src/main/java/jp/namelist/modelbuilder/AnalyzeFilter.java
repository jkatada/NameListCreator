package jp.namelist.modelbuilder;

import org.eclipse.jdt.core.dom.MethodInvocation;

class AnalyzeFilter {

	public boolean isTarget(MethodInvocation node) {
		// model.addAttributeの呼び出しを絞込
		return "addAttribute".equals(node.getName().getIdentifier());
	}
	
}
